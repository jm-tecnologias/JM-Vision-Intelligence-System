# JM Vision Intelligence System

**JM Vision Intelligence System** e uma aplicacao desktop desenvolvida em **JavaFX** para organizacao, visualizacao e analise tecnica de imagens. O sistema permite navegar por diretorios, importar imagens, visualizar propriedades EXIF, identificar coordenadas GPS, abrir a localizacao no mapa e gerar relatorios em PDF.

O projeto esta estruturado como uma aplicacao JavaFX criada no NetBeans, usando FXML para as interfaces, CSS para os estilos e servicos Java para separar a logica de imagem, metadados, ficheiros, relatorios e mapa.

---

## Funcionalidades principais

- Exploracao de pastas e imagens por arvore de diretorios.
- Importacao de imagens a partir de uma pasta de origem.
- Visualizacao de miniaturas e preview da imagem selecionada.
- Navegacao entre imagens anteriores e seguintes.
- Rotacao manual da imagem.
- Leitura de metadados EXIF da imagem.
- Detecao de coordenadas GPS quando disponiveis.
- Enriquecimento geografico por reverse geocoding via Nominatim/OpenStreetMap.
- Abertura da localizacao no Google Maps.
- Criacao, renomeacao e remocao de diretorios.
- Selecao multipla de imagens.
- Geracao de relatorios PDF com detalhes da imagem e notas do utilizador.
- Interface JavaFX com janela personalizada, FXML e estilos CSS.

---

## Tecnologias utilizadas

- Java 8 / JavaFX
- NetBeans Ant Project
- FXML
- CSS
- metadata-extractor
- JSON.org
- iText PDF
- OpenStreetMap Nominatim
- Google Maps via navegador do sistema

---

## Estrutura do projeto

```text
JM Vision Intelligence System
|
|-- src/
|   |-- jmvis/
|       |-- Main.java
|       |-- controller/
|       |-- model/
|       |-- service/
|       |-- util/
|       |-- view/
|       |-- styles/
|       |-- assets/
|
|-- nbproject/
|-- build.xml
|-- manifest.mf
|-- README.md
```

### Pacotes principais

| Pacote | Responsabilidade |
| --- | --- |
| `jmvis` | Classe principal da aplicacao JavaFX. |
| `jmvis.controller` | Controladores FXML e eventos da interface. |
| `jmvis.model` | Modelo de dados da imagem e respetivos detalhes. |
| `jmvis.service` | Servicos de imagem, metadados, mapa, ficheiros, relatorios e miniaturas. |
| `jmvis.util` | Utilitarios de apoio, binding de dados e geracao auxiliar. |
| `jmvis.view` | Ficheiros FXML da interface. |
| `jmvis.styles` | Estilos CSS usados pela interface JavaFX. |
| `jmvis.assets` | Imagens, icones e recursos visuais. |

---

## Classe principal

A entrada da aplicacao esta em:

```text
src/jmvis/Main.java
```

Classe principal:

```text
jmvis.Main
```

O ficheiro `nbproject/project.properties` tambem define:

```text
main.class=jmvis.Main
javafx.main.class=jmvis.Main
javafx.enabled=true
```

---

## Dependencias

As dependencias do projeto sao JARs referenciados pelo NetBeans no ficheiro `nbproject/project.properties`.

Principais bibliotecas:

- `metadata-extractor-2.20.0.jar`
- `xmpcore-6.1.11.jar`
- `json-20250517.jar`
- iText PDF 7.1.3
- bibliotecas JavaFX do JDK configurado no NetBeans

Nao e necessario criar ambiente virtual Python nem instalar pacotes com `pip`, porque este projeto ja nao e uma aplicacao Python.

---

## Como executar no NetBeans

1. Abrir o NetBeans.
2. Selecionar **File > Open Project**.
3. Escolher a pasta do projeto:

```text
JM Vision Intelligence System
```

4. Confirmar que o JDK configurado possui JavaFX.
5. Clicar com o botao direito no projeto.
6. Selecionar **Run**.

---

## Como compilar

Pelo NetBeans:

1. Clicar com o botao direito no projeto.
2. Selecionar **Clean and Build**.
3. Verificar a pasta `dist/`.

Pelo terminal, usando Ant:

```powershell
ant clean
ant jar
```

ou:

```powershell
ant clean jar
```

---

## Como executar o JAR

Depois do build, o JAR principal e gerado na pasta `dist/`.

Exemplo de execucao quando se usa JavaFX separado do JDK:

```powershell
java --module-path "C:\javafx\javafx-sdk-17\lib" --add-modules javafx.controls,javafx.fxml,javafx.swing,javafx.media,javafx.web -cp "dist\JMVisionIntelligenceSystem.jar;dist\lib\*" jmvis.Main
```

Nota: dependendo da configuracao do NetBeans, o nome do JAR pode aparecer como:

```text
dist/JM Vision Intelligence System.jar
```

ou:

```text
dist/JMVisionIntelligenceSystem.jar
```

Confirme o nome real dentro da pasta `dist/` antes de executar o comando.

---

## Fluxo de utilizacao

```text
Abrir aplicacao
        |
Selecionar pasta na arvore
        |
Carregar miniaturas
        |
Selecionar imagem
        |
Mostrar preview
        |
Ler metadados EXIF
        |
Mostrar detalhes tecnicos
        |
Se existir GPS, permitir abertura no mapa
        |
Gerar relatorio PDF quando necessario
```

---

## Metadados analisados

O sistema pode apresentar informacoes como:

- fabricante da camera;
- modelo da camera;
- software;
- latitude;
- longitude;
- altitude;
- pais;
- codigo do pais;
- cidade;
- regiao/distrito;
- tempo de exposicao;
- velocidade do obturador;
- ISO;
- distancia focal;
- distancia focal equivalente em 35 mm;
- data original;
- data de digitalizacao;
- offset de tempo;
- nome, caminho e tamanho do ficheiro.

---

## Relatorios PDF

A geracao de relatorios e feita pelos servicos:

```text
src/jmvis/service/ImageReportService.java
src/jmvis/service/ReportFileService.java
src/jmvis/util/PDFReport.java
```

Os relatorios usam os metadados extraidos da imagem e podem incluir notas inseridas pelo utilizador na interface.

---

## Empacotamento

O guia de empacotamento para gerar runtime, `app-image`, instalador `.exe` e instalador `.msi` esta documentado em:

```text
PACKAGING.md
```

Esse ficheiro cobre o uso de:

- `jlink`
- `jpackage`
- JavaFX SDK/JMODS
- WiX Toolset
- instaladores Windows

---

## Documentacao de concepcao

A documentacao completa de engenharia, desde a elicitacao de requisitos ate deploy, manutencao e melhorias futuras, esta em:

```text
DOCUMENTACAO_CONCEPCAO.md
```

---

## Roadmap

- [x] Leitura de metadados EXIF.
- [x] Preview de imagens.
- [x] Navegacao por diretorios.
- [x] Importacao de imagens.
- [x] Selecao multipla.
- [x] Geracao de relatorios PDF.
- [x] Abertura de coordenadas no mapa.
- [x] Melhorar suporte a instalador MSI/EXE.
- [ ] Exportar metadados para JSON/CSV.
- [ ] Melhorar testes automatizados.
- [ ] Adicionar assinatura digital ao instalador.

---

## Autor

**JM-Tecnologias**

Software Engineering, Automation, GIS Systems e Image Analysis.

GitHub:

```text
https://github.com/jm-tecnologias
```

---

## Licenca

MIT License
