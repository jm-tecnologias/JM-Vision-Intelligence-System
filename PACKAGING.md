# Guia de empacotamento: jlink, jpackage, MSI e EXE

Este guia esta adaptado ao projeto **JM Vision Intelligence System**.

Dados atuais do projeto:

- JAR principal: `dist/JMVisionIntelligenceSystem.jar`
- Classe principal: `jmvis.Main`
- Aplicacao: JavaFX
- JDK encontrado no ambiente: 17.0.19
- JavaFX SDK encontrado: `C:\javafx\javafx-sdk-17`

## 1. Conceitos rapidos

### jlink

Cria uma imagem de runtime Java pequena, contendo apenas os modulos necessarios para executar a aplicacao. Em vez de pedir ao cliente para instalar Java, o instalador leva um Java embutido.

Saida tipica:

```text
packaging\runtime
```

### jpackage

Cria a aplicacao final ou o instalador nativo.

Pode gerar:

- `app-image`: pasta pronta para executar
- `exe`: instalador `.exe`
- `msi`: instalador `.msi`

### MSI

Instalador Windows mais usado em ambientes empresariais. Facil de distribuir por politicas de dominio, ferramentas de TI e instalacao silenciosa.

### EXE

Instalador Windows comum para usuario final. Normalmente e mais simples para distribuir manualmente.

## 2. Pre-requisitos

### 2.1. JDK 17 ou superior

Verificar:

```powershell
java -version
jlink --version
jpackage --version
```

No ambiente atual, `jlink` e `jpackage` ja estao disponiveis.

### 2.2. JavaFX JMODS

Para usar `jlink` com JavaFX, nao basta ter:

```text
C:\javafx\javafx-sdk-17\lib
```

Tambem e necessario baixar o pacote **JavaFX JMODS** da mesma familia de versao, por exemplo:

```text
C:\javafx\javafx-jmods-17
```

Dentro dessa pasta devem existir arquivos como:

```text
javafx.controls.jmod
javafx.fxml.jmod
javafx.graphics.jmod
```

### 2.3. WiX Toolset

No Windows, para gerar `.msi` e `.exe` com `jpackage`, instala o **WiX Toolset 3.x** e garante que estes comandos estejam no `PATH`:

```powershell
candle.exe
light.exe
```

Verificar:

```powershell
where candle
where light
```

## 3. Preparar o JAR

No NetBeans:

1. Clica com botao direito no projeto.
2. Escolhe **Clean and Build**.
3. Confirma que foi criado:

```text
dist\JMVisionIntelligenceSystem.jar
```

Testa o JAR com as bibliotecas da pasta `dist\lib`:

```powershell
java --module-path "C:\javafx\javafx-sdk-17\lib" --add-modules javafx.controls,javafx.fxml,javafx.swing,javafx.media,javafx.web -cp "dist\JMVisionIntelligenceSystem.jar;dist\lib\*" jmvis.Main
```

Se a aplicacao abrir, podes seguir para o empacotamento.

Nota: neste projeto, usar `java -jar "dist\JMVisionIntelligenceSystem.jar"` nao carrega automaticamente os JARs de `dist\lib`. Por isso, dependencias como `metadata-extractor-2.20.0.jar` podem falhar com `ClassNotFoundException`.

## 4. Descobrir modulos necessarios

Usa `jdeps` para ter uma ideia dos modulos Java usados:

```powershell
jdeps --multi-release 17 --ignore-missing-deps --print-module-deps "dist\JMVisionIntelligenceSystem.jar"
```

Como a aplicacao e JavaFX, normalmente vais precisar incluir tambem:

```text
javafx.controls,javafx.fxml,javafx.swing,javafx.media,javafx.web
```

Para este projeto, uma base pratica para comecar e:

```text
java.base,java.desktop,java.logging,java.sql,java.xml,javafx.controls,javafx.fxml,javafx.swing,javafx.media,javafx.web
```

## 5. Criar runtime com jlink

Cria uma pasta para os ficheiros de empacotamento:

```powershell
New-Item -ItemType Directory -Force packaging
```

Apaga runtime antigo, se existir:

```powershell
Remove-Item -Recurse -Force "packaging\runtime" -ErrorAction SilentlyContinue
```

Cria o runtime:

```powershell
jlink `
  --module-path "$env:JAVA_HOME\jmods;C:\javafx\javafx-jmods-17" `
  --add-modules java.base,java.desktop,java.logging,java.sql,java.xml,javafx.controls,javafx.fxml,javafx.swing,javafx.media,javafx.web `
  --output "packaging\runtime" `
  --strip-debug `
  --compress=2 `
  --no-header-files `
  --no-man-pages
```

Testa o runtime:

```powershell
.\packaging\runtime\bin\java.exe --version
```

## 6. Criar app-image com jpackage

O `app-image` cria uma pasta executavel, mas ainda sem instalador.

```powershell
jpackage `
  --type app-image `
  --name "JM Vision Intelligence System" `
  --app-version 1.0.0 `
  --vendor "JM-Tecnologias" `
  --input "dist" `
  --main-jar "JMVisionIntelligenceSystem.jar" `
  --main-class "jmvis.Main" `
  --runtime-image "packaging\runtime" `
  --dest "packaging\output" `
  --java-options "--add-modules=javafx.controls,javafx.fxml,javafx.swing,javafx.media,javafx.web" `
  --win-console
```

Testa:

```powershell
& "packaging\output\JM Vision Intelligence System\JM Vision Intelligence System.exe"
```

Se abrir corretamente, podes remover `--win-console` nos instaladores finais.

## 7. Criar instalador EXE

```powershell
jpackage `
  --type exe `
  --name "JM Vision Intelligence System" `
  --app-version 1.0.0 `
  --vendor "JM-Tecnologias" `
  --input "dist" `
  --main-jar "JMVisionIntelligenceSystem.jar" `
  --main-class "jmvis.Main" `
  --runtime-image "packaging\runtime" `
  --dest "packaging\installer" `
  --java-options "--add-modules=javafx.controls,javafx.fxml,javafx.swing,javafx.media,javafx.web" `
  --win-dir-chooser `
  --win-menu `
  --win-shortcut
```

Saida esperada:

```text
packaging\installer\JM Vision Intelligence System-1.0.0.exe
```

## 8. Criar instalador MSI

```powershell
jpackage `
  --type msi `
  --name "JM Vision Intelligence System" `
  --app-version 1.0.0 `
  --vendor "JM-Tecnologias" `
  --input "dist" `
  --main-jar "JMVisionIntelligenceSystem.jar" `
  --main-class "jmvis.Main" `
  --runtime-image "packaging\runtime" `
  --dest "packaging\installer" `
  --java-options "--add-modules=javafx.controls,javafx.fxml,javafx.swing,javafx.media,javafx.web" `
  --win-dir-chooser `
  --win-menu `
  --win-shortcut `
  --win-per-user-install
```

Saida esperada:

```text
packaging\installer\JM Vision Intelligence System-1.0.0.msi
```

## 9. Com icone

Cria ou coloca um icone `.ico`, por exemplo:

```text
packaging\app.ico
```

Depois adiciona isto aos comandos `jpackage`:

```powershell
--icon "packaging\app.ico"
```

## 10. Instalacao silenciosa

### MSI

```powershell
msiexec /i "JM Vision Intelligence System-1.0.0.msi" /quiet
```

Desinstalar:

```powershell
msiexec /x "JM Vision Intelligence System-1.0.0.msi" /quiet
```

### EXE

O modo silencioso depende do instalador gerado pelo `jpackage`, mas normalmente:

```powershell
.\JM Vision Intelligence System-1.0.0.exe /quiet
```

## 11. Ordem recomendada

1. Gerar o JAR no NetBeans.
2. Testar o JAR com JavaFX.
3. Instalar JavaFX JMODS.
4. Criar runtime com `jlink`.
5. Gerar `app-image` com `jpackage`.
6. Testar o `.exe` dentro da pasta `app-image`.
7. Instalar WiX Toolset.
8. Gerar `.exe`.
9. Gerar `.msi`.
10. Testar em uma maquina limpa, sem JDK instalado.

## 12. Problemas comuns

### Erro: jpackage nao encontrado

Usa um JDK completo, nao apenas JRE. Confirma:

```powershell
jpackage --version
```

### Erro: JavaFX runtime components are missing

Faltou adicionar JavaFX:

```powershell
--java-options "--add-modules=javafx.controls,javafx.fxml,javafx.swing,javafx.media,javafx.web"
```

ou o runtime criado com `jlink` nao incluiu os modulos JavaFX.

### Erro: ClassNotFoundException ou NoClassDefFoundError

Exemplo:

```text
java.lang.ClassNotFoundException: com.drew.metadata.Directory
```

Significa que uma biblioteca de `dist\lib` nao foi carregada. Para testar localmente, usa `-cp`:

```powershell
java --module-path "C:\javafx\javafx-sdk-17\lib" --add-modules javafx.controls,javafx.fxml,javafx.swing,javafx.media,javafx.web -cp "dist\JMVisionIntelligenceSystem.jar;dist\lib\*" jmvis.Main
```

### Aviso: FXML API 26 com runtime 17

Exemplo:

```text
WARNING: Loading FXML document with JavaFX API of version 26 by JavaFX runtime of version 17
```

Normalmente e um aviso, nao a causa principal da falha. Ele indica que algum ficheiro `.fxml` foi salvo com JavaFX/Scene Builder 26, mas esta a ser executado com JavaFX 17. Para evitar incompatibilidades, usa Scene Builder/JavaFX da mesma versao do runtime ou ajusta os FXML para JavaFX 17.

### Erro ao criar MSI ou EXE

Verifica WiX:

```powershell
where candle
where light
```

### Aplicacao abre pelo NetBeans, mas nao pelo instalador

Confirma se todos os JARs necessarios estao em:

```text
dist\lib
```

Tambem testa o `app-image` antes de gerar MSI/EXE.
