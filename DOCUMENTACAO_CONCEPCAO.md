# Documentacao de Concepcao e Engenharia de Software

## JM Vision Intelligence System

### 1. Identificacao do projeto

**Nome do sistema:** JM Vision Intelligence System

**Tipo de sistema:** Aplicacao desktop JavaFX para gestao, visualizacao e analise tecnica de imagens.

**Organizacao/autoria:** JM-Tecnologias

**Tecnologia principal:** JavaFX, Java, FXML, CSS, Ant/NetBeans.

**Classe principal:** `jmvis.Main`

**Objetivo geral:** disponibilizar uma ferramenta desktop capaz de organizar imagens, visualizar ficheiros multimidia, extrair metadados EXIF, identificar informacao geografica e gerar relatorios tecnicos em PDF.

---

## 2. Visao geral

O JM Vision Intelligence System foi concebido como uma solucao desktop para analise e organizacao de imagens. A aplicacao permite ao utilizador navegar por uma estrutura propria de diretorios, importar imagens de outras pastas, visualizar miniaturas, abrir imagens em modo preview, consultar propriedades tecnicas e gerar relatorios.

O sistema tambem explora metadados geograficos quando estes existem na imagem. Ao encontrar latitude e longitude nos dados EXIF, a aplicacao pode enriquecer a informacao com dados de localizacao e abrir o ponto correspondente no Google Maps.

Ao nivel atual, o software ja contempla os principais modulos funcionais:

- interface grafica JavaFX;
- estrutura FXML para separacao entre layout e logica;
- camada de controladores;
- camada de servicos;
- modelo de dados para imagem;
- leitura de metadados;
- importacao e navegacao de imagens;
- gestao basica de diretorios;
- geracao de relatorios PDF;
- empacotamento previsto para ambiente Windows.

---

## 3. Contexto e problema

Profissionais que trabalham com imagens digitais, fotografia tecnica, inspecao visual, OSINT, investigacao, documentacao tecnica ou gestao de media precisam frequentemente de consultar dados que nao estao visiveis diretamente na imagem.

Esses dados podem incluir:

- fabricante e modelo da camera;
- software usado;
- data original da captura;
- ISO;
- tempo de exposicao;
- distancia focal;
- coordenadas GPS;
- altitude;
- localizacao aproximada.

Sem uma ferramenta integrada, o utilizador precisa alternar entre explorador de ficheiros, visualizadores de imagem, ferramentas EXIF, navegador web e editores de relatorio. O JM Vision Intelligence System resolve este problema ao centralizar o fluxo numa unica aplicacao desktop.

---

## 4. Objetivos do sistema

### 4.1. Objetivo principal

Criar uma aplicacao desktop capaz de apoiar a analise tecnica de imagens, permitindo visualizacao, organizacao, extracao de metadados, consulta geografica e emissao de relatorios.

### 4.2. Objetivos especificos

- Permitir a navegacao por pastas e subpastas.
- Permitir importacao de imagens a partir de diretorios externos.
- Apresentar miniaturas das imagens existentes numa pasta.
- Permitir preview de imagem em area central.
- Permitir zoom, pan e rotacao visual da imagem.
- Extrair metadados EXIF de imagens suportadas.
- Apresentar dados tecnicos da imagem na interface.
- Extrair coordenadas GPS quando disponiveis.
- Enriquecer coordenadas com dados de localizacao.
- Abrir coordenadas no mapa.
- Permitir anotacoes do utilizador.
- Gerar relatorios PDF.
- Disponibilizar base para instalador Windows.

---

## 5. Stakeholders

| Stakeholder | Interesse no sistema |
| --- | --- |
| Utilizador final | Analisar, organizar e documentar imagens com rapidez. |
| Tecnico de imagem | Consultar dados EXIF e informacoes de captura. |
| Investigador/analista | Localizar imagens e relacionar evidencias visuais com dados geograficos. |
| Administrador de TI | Instalar, atualizar e manter a aplicacao em maquinas Windows. |
| Equipa de desenvolvimento | Evoluir o sistema, corrigir defeitos e adicionar funcionalidades. |
| Gestor do projeto | Avaliar estado atual, riscos, roadmap e viabilidade tecnica. |

---

## 6. Elicitacao de requisitos

A elicitacao de requisitos considera as necessidades observadas no dominio da aplicacao e as funcionalidades ja implementadas no codigo atual.

### 6.1. Tecnicas de elicitacao recomendadas

- Entrevistas com utilizadores que trabalham com imagens.
- Observacao do fluxo atual de analise de imagens.
- Analise de ferramentas concorrentes de EXIF e visualizacao.
- Prototipagem rapida em JavaFX.
- Revisao dos casos reais de uso.
- Priorizacao por valor operacional.

### 6.2. Necessidades identificadas

- Centralizar imagens num repositorio local da aplicacao.
- Reduzir o tempo necessario para encontrar e analisar imagens.
- Evitar consulta manual de metadados em varias ferramentas.
- Gerar evidencias documentais em PDF.
- Identificar rapidamente se uma imagem possui localizacao GPS.
- Abrir coordenadas em mapa externo.
- Usar uma interface desktop sem depender de navegador.

---

## 7. Requisitos funcionais

| Codigo | Requisito | Estado atual |
| --- | --- | --- |
| RF-01 | O sistema deve iniciar uma interface desktop JavaFX. | Implementado |
| RF-02 | O sistema deve criar diretorios base no ambiente do utilizador. | Implementado |
| RF-03 | O sistema deve apresentar uma arvore de diretorios. | Implementado |
| RF-04 | O sistema deve permitir selecionar uma pasta. | Implementado |
| RF-05 | O sistema deve listar imagens da pasta selecionada. | Implementado |
| RF-06 | O sistema deve carregar miniaturas das imagens. | Implementado |
| RF-07 | O sistema deve permitir selecionar uma imagem. | Implementado |
| RF-08 | O sistema deve apresentar preview da imagem selecionada. | Implementado |
| RF-09 | O sistema deve permitir navegar para imagem anterior e seguinte. | Implementado |
| RF-10 | O sistema deve permitir zoom e pan no preview. | Implementado |
| RF-11 | O sistema deve permitir rotacao visual da imagem. | Implementado |
| RF-12 | O sistema deve extrair metadados EXIF. | Implementado |
| RF-13 | O sistema deve apresentar informacoes da camera. | Implementado |
| RF-14 | O sistema deve apresentar especificacoes tecnicas da imagem. | Implementado |
| RF-15 | O sistema deve apresentar dados de data da imagem. | Implementado |
| RF-16 | O sistema deve identificar latitude, longitude e altitude. | Implementado |
| RF-17 | O sistema deve enriquecer localizacao por reverse geocoding. | Implementado |
| RF-18 | O sistema deve abrir a localizacao em mapa externo. | Implementado |
| RF-19 | O sistema deve permitir importacao de imagens. | Implementado |
| RF-20 | O sistema deve permitir criar diretorios. | Implementado |
| RF-21 | O sistema deve permitir renomear diretorios. | Implementado |
| RF-22 | O sistema deve permitir apagar diretorios vazios. | Implementado |
| RF-23 | O sistema deve permitir selecao multipla de imagens. | Implementado |
| RF-24 | O sistema deve permitir escrever notas. | Implementado |
| RF-25 | O sistema deve limitar o tamanho das notas. | Implementado |
| RF-26 | O sistema deve gerar relatorio PDF da imagem atual. | Implementado |
| RF-27 | O sistema deve gerar relatorios PDF para imagens selecionadas. | Implementado |
| RF-28 | O sistema deve permitir abrir a pasta/ficheiro de relatorios. | Implementado |
| RF-29 | O sistema deve suportar empacotamento para Windows. | Parcial |
| RF-30 | O sistema deve exportar metadados em JSON/CSV. | Futuro |
| RF-31 | O sistema deve permitir pesquisa e filtros por metadados. | Futuro |
| RF-32 | O sistema deve manter historico de analises. | Futuro |

---

## 8. Requisitos nao funcionais

| Codigo | Requisito | Descricao |
| --- | --- | --- |
| RNF-01 | Usabilidade | A interface deve ser clara para exploracao, selecao e analise de imagens. |
| RNF-02 | Desempenho | O carregamento de miniaturas deve evitar bloqueio perceptivel da interface. |
| RNF-03 | Disponibilidade | A aplicacao deve funcionar localmente mesmo sem internet, exceto recursos de mapa/geocoding. |
| RNF-04 | Portabilidade | A base deve permitir empacotamento para Windows. |
| RNF-05 | Manutenibilidade | A logica deve permanecer separada em controllers, services, models e utils. |
| RNF-06 | Confiabilidade | Erros de leitura de metadados nao devem derrubar a aplicacao. |
| RNF-07 | Segurança | Operacoes destrutivas devem exigir confirmacao. |
| RNF-08 | Privacidade | Imagens e relatorios devem permanecer em armazenamento local do utilizador. |
| RNF-09 | Escalabilidade local | O sistema deve suportar pastas com varias imagens usando carregamento incremental/assincrono. |
| RNF-10 | Observabilidade | Versoes futuras devem adicionar logs tecnicos para diagnostico. |

---

## 9. Regras de negocio

- A aplicacao organiza os ficheiros dentro da pasta local `Documents/JM Vision Intelligence`.
- As imagens importadas devem ser copiadas para a pasta de destino selecionada.
- Uma imagem sem coordenadas GPS nao pode abrir localizacao no mapa.
- A geracao de relatorio exige que exista uma imagem selecionada.
- A selecao multipla deve permitir gerar varios relatorios numa unica operacao.
- A nota do utilizador deve respeitar o limite definido na interface.
- A eliminacao de diretorio exige confirmacao explicita.
- O reverse geocoding depende de conectividade com a internet.

---

## 10. Casos de uso

### UC-01: Iniciar aplicacao

**Ator:** Utilizador

**Fluxo principal:**

1. O utilizador abre a aplicacao.
2. O sistema carrega `MainView.fxml`.
3. O sistema inicializa servicos e componentes.
4. O sistema cria diretorios base se ainda nao existirem.
5. A janela principal e apresentada.

**Resultado:** Aplicacao pronta para uso.

### UC-02: Selecionar pasta e visualizar imagens

**Ator:** Utilizador

**Fluxo principal:**

1. O utilizador seleciona uma pasta na arvore.
2. O sistema limpa a lista atual de miniaturas.
3. O sistema carrega miniaturas da pasta selecionada.
4. O utilizador escolhe uma imagem.
5. O sistema apresenta o preview.
6. O sistema extrai e apresenta os metadados.

**Resultado:** Imagem e detalhes tecnicos visiveis.

### UC-03: Importar imagens

**Ator:** Utilizador

**Fluxo principal:**

1. O utilizador seleciona uma pasta de destino na arvore.
2. O utilizador aciona a importacao.
3. O sistema abre seletor de pasta de origem.
4. O utilizador escolhe a pasta.
5. O sistema copia imagens para a pasta de destino.
6. O sistema atualiza a lista de imagens.

**Resultado:** Imagens importadas para a estrutura da aplicacao.

### UC-04: Consultar localizacao no mapa

**Ator:** Utilizador

**Fluxo principal:**

1. O utilizador seleciona uma imagem.
2. O sistema verifica se existem coordenadas GPS.
3. O utilizador aciona o mapa.
4. O sistema abre Google Maps no navegador.

**Fluxo alternativo:**

- Se nao houver coordenadas, o sistema informa que a localizacao nao esta disponivel.

**Resultado:** Localizacao aberta no mapa quando possivel.

### UC-05: Gerar relatorio PDF

**Ator:** Utilizador

**Fluxo principal:**

1. O utilizador seleciona uma imagem ou varias imagens.
2. O utilizador escreve notas, se necessario.
3. O utilizador aciona a geracao de relatorio.
4. O sistema extrai os dados necessarios.
5. O sistema cria relatorio PDF.
6. O sistema informa o resultado.

**Resultado:** Relatorio tecnico gerado.

---

## 11. Arquitetura do sistema

O sistema segue uma arquitetura em camadas simples, adequada a uma aplicacao desktop JavaFX.

```text
Interface JavaFX/FXML
        |
Controllers
        |
Services
        |
Models e Utils
        |
Sistema de ficheiros, bibliotecas externas e servicos web
```

### 11.1. Camada de interface

Contem ficheiros FXML e CSS.

Localizacao:

```text
src/jmvis/view/
src/jmvis/styles/
```

Responsabilidades:

- estruturar telas;
- definir componentes visuais;
- aplicar estilos;
- permitir interacao do utilizador.

### 11.2. Camada de controladores

Localizacao:

```text
src/jmvis/controller/
```

Responsabilidades:

- receber eventos da interface;
- coordenar chamadas aos servicos;
- atualizar labels, listas, arvore e preview;
- gerir a experiencia da tela principal.

Classe central:

```text
MainViewController
```

### 11.3. Camada de modelo

Localizacao:

```text
src/jmvis/model/
```

Responsabilidades:

- representar dados da imagem;
- armazenar dados de dispositivo, GPS, especificacoes tecnicas e datas;
- transportar informacao entre servicos e interface.

Classe principal:

```text
ImageModel
```

### 11.4. Camada de servicos

Localizacao:

```text
src/jmvis/service/
```

Responsabilidades:

- encapsular regras e operacoes especificas;
- reduzir acoplamento do controller;
- tornar o sistema mais testavel e manutenivel.

Servicos principais:

| Servico | Responsabilidade |
| --- | --- |
| `ImageService` | Preview, zoom, pan e rotacao visual. |
| `ImageMetadataService` | Leitura EXIF e enriquecimento geografico. |
| `ImageReportService` | Coordenacao da geracao de relatorios. |
| `MapService` | Abertura de coordenadas no mapa. |
| `FileManagerService` | Gestao de diretorios base e operacoes em pastas. |
| `DirectoryTreeService` | Configuracao e atualizacao da arvore de diretorios. |
| `ThumbnailService` | Carregamento e selecao de miniaturas. |
| `CopyService` | Importacao/copia de imagens. |
| `DialogService` | Dialogos, mensagens e pedidos ao utilizador. |
| `ReportFileService` | Abertura ou acesso aos relatorios. |
| `TitleBarService` | Comportamento da barra de titulo customizada. |

### 11.5. Camada utilitaria

Localizacao:

```text
src/jmvis/util/
```

Responsabilidades:

- geracao auxiliar de PDF;
- binding entre modelo e labels da interface;
- utilitarios de imagem e carregamento de telas.

---

## 12. Desenho logico dos dados

### 12.1. ImageModel

O modelo `ImageModel` representa a imagem analisada e agrega informacoes em subestruturas.

```text
ImageModel
|
|-- fileName
|-- absolutePath
|-- size
|-- notes
|-- DeviceInfo
|   |-- make
|   |-- model
|   |-- software
|
|-- GPSInfo
|   |-- latitude
|   |-- longitude
|   |-- altitude
|   |-- country
|   |-- countryCode
|   |-- city
|   |-- district
|
|-- ImageSpecification
|   |-- exposureTime
|   |-- shutterSpeedValue
|   |-- isoSpeedRatings
|   |-- focalLength
|   |-- focalLengthIn35mmFilm
|   |-- whiteBalance
|   |-- exifImageHeight
|   |-- exifImageWidth
|
|-- ImageDate
    |-- dateTimeOriginal
    |-- dateTimeDigitized
    |-- offsetTime
```

---

## 13. Fluxos tecnicos principais

### 13.1. Fluxo de inicializacao

```text
Main.main()
        |
Application.launch()
        |
Main.start()
        |
FXMLLoader carrega MainView.fxml
        |
MainViewController.initialize()
        |
Inicializacao dos servicos
        |
Criacao/configuracao da arvore de diretorios
        |
Aplicacao pronta
```

### 13.2. Fluxo de analise de imagem

```text
Utilizador seleciona imagem
        |
MainViewController.previewImage()
        |
ImageService.setImagePreview()
        |
ImageMetadataService.analyze()
        |
metadata-extractor le EXIF
        |
ImageModel e preenchido
        |
ImageDetailsBinder atualiza a interface
```

### 13.3. Fluxo de geolocalizacao

```text
Imagem contem GPS EXIF
        |
ImageMetadataService le latitude/longitude
        |
Nominatim reverse geocoding
        |
GPSInfo recebe pais, codigo, cidade e distrito
        |
Interface apresenta dados
        |
MapService abre Google Maps quando solicitado
```

### 13.4. Fluxo de relatorio

```text
Utilizador seleciona uma ou varias imagens
        |
Utilizador escreve notas
        |
MainViewController.saveImageDetail()
        |
ImageReportService.generateReports()
        |
PDFReport gera documento
        |
Sistema informa sucesso ou erro
```

---

## 14. Tecnologias e bibliotecas

| Tecnologia | Uso no sistema |
| --- | --- |
| Java | Linguagem principal. |
| JavaFX | Interface desktop. |
| FXML | Declaracao das telas. |
| CSS | Estilizacao visual. |
| Ant | Build do projeto NetBeans. |
| metadata-extractor | Leitura de metadados EXIF. |
| xmpcore | Apoio a metadados XMP. |
| JSON.org | Processamento de resposta JSON do geocoding. |
| iText PDF | Geracao de relatorios PDF. |
| Nominatim/OpenStreetMap | Reverse geocoding. |
| Google Maps | Visualizacao externa de localizacao. |
| jlink | Criacao de runtime customizado. |
| jpackage | Geracao de app-image, EXE e MSI. |

---

## 15. Estrutura fisica de diretorios

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
|-- dist/
|-- build/
|-- packaging/
|-- README.md
|-- PACKAGING.md
|-- DOCUMENTACAO_CONCEPCAO.md
```

### 15.1. Diretorios criados em tempo de execucao

No ambiente do utilizador, a aplicacao trabalha em:

```text
Documents/JM Vision Intelligence/
```

Subpastas principais:

```text
File Explore
FileImageSystem
Report
```

---

## 16. Estado atual da implementacao

### Implementado

- Aplicacao JavaFX funcional.
- Janela principal carregada via FXML.
- Barra de titulo customizada.
- Arvore de diretorios.
- Carregamento de miniaturas.
- Preview de imagem.
- Zoom e pan no preview.
- Rotacao visual.
- Navegacao anterior/proxima.
- Extracao EXIF.
- Modelo estruturado para dados da imagem.
- Reverse geocoding com Nominatim.
- Abertura de coordenadas no Google Maps.
- Importacao de imagens.
- Criacao, renomeacao e remocao de diretorios.
- Selecao multipla.
- Notas limitadas a 300 caracteres.
- Geracao de relatorios PDF.
- Guia de empacotamento em `PACKAGING.md`.

### Parcialmente implementado

- Empacotamento nativo Windows.
- Estrategia formal de testes.
- Tratamento padronizado de erros.
- Logs tecnicos.
- Internacionalizacao.

### Ainda nao implementado

- Exportacao JSON/CSV.
- Pesquisa avancada por metadados.
- Base de dados local.
- Historico de analises.
- Sistema de perfis/utilizadores.
- Assinatura digital de relatorios.
- Atualizacao automatica da aplicacao.

---

## 17. Plano de testes

### 17.1. Testes funcionais

| Caso | Objetivo | Resultado esperado |
| --- | --- | --- |
| TF-01 | Abrir aplicacao | Janela principal aparece sem erro. |
| TF-02 | Selecionar pasta vazia | Sistema nao falha e mostra lista vazia. |
| TF-03 | Selecionar pasta com imagens | Miniaturas sao apresentadas. |
| TF-04 | Selecionar imagem JPEG | Preview e metadados sao apresentados. |
| TF-05 | Selecionar imagem sem EXIF | Sistema apresenta campos vazios sem falhar. |
| TF-06 | Selecionar imagem com GPS | Coordenadas e localizacao sao preenchidas. |
| TF-07 | Abrir mapa sem GPS | Sistema informa ausencia de coordenadas. |
| TF-08 | Abrir mapa com GPS | Navegador abre Google Maps. |
| TF-09 | Importar imagens | Imagens sao copiadas para destino. |
| TF-10 | Gerar relatorio atual | PDF e criado com dados da imagem. |
| TF-11 | Gerar relatorios multiplos | Um relatorio e criado para cada imagem selecionada. |
| TF-12 | Criar diretorio | Diretorio aparece na arvore. |
| TF-13 | Renomear diretorio | Nome e atualizado na arvore. |
| TF-14 | Apagar diretorio | Diretorio e removido quando valido. |

### 17.2. Testes nao funcionais

- Verificar tempo de carregamento em pastas com muitas imagens.
- Verificar consumo de memoria com imagens grandes.
- Verificar funcionamento sem internet.
- Verificar comportamento em caminhos com espacos.
- Verificar permissao de escrita em `Documents`.
- Verificar compatibilidade com Windows limpo sem JDK instalado, apos empacotamento.

### 17.3. Testes de regressao

Antes de cada release, validar:

- abertura da aplicacao;
- importacao;
- preview;
- metadados EXIF;
- GPS;
- relatorio PDF;
- build;
- empacotamento.

---

## 18. Deploy e empacotamento

O deploy previsto e voltado principalmente para Windows.

### 18.1. Build da aplicacao

Pelo NetBeans:

```text
Clean and Build
```

Pelo terminal:

```powershell
ant clean jar
```

### 18.2. Teste do JAR

Quando JavaFX esta separado do JDK:

```powershell
java --module-path "C:\javafx\javafx-sdk-17\lib" --add-modules javafx.controls,javafx.fxml,javafx.swing,javafx.media,javafx.web -cp "dist\JMVisionIntelligenceSystem.jar;dist\lib\*" jmvis.Main
```

### 18.3. Runtime customizado

O runtime deve ser criado com `jlink`, incluindo os modulos Java e JavaFX necessarios.

### 18.4. Instalador

O instalador deve ser gerado com `jpackage`, podendo produzir:

- `app-image`;
- `.exe`;
- `.msi`.

### 18.5. Documentacao complementar

Os comandos detalhados de empacotamento estao no ficheiro:

```text
PACKAGING.md
```

---

## 19. Manutencao

### 19.1. Manutencao corretiva

Objetivo: corrigir erros identificados em producao.

Exemplos:

- falhas ao abrir imagens corrompidas;
- erros em ficheiros sem EXIF;
- problemas de permissao em diretorios;
- falha no reverse geocoding;
- excecoes ao gerar PDF.

### 19.2. Manutencao adaptativa

Objetivo: adaptar o sistema a mudancas externas.

Exemplos:

- novas versoes do JavaFX;
- mudancas na API do Nominatim;
- atualizacao de bibliotecas iText;
- alteracoes no empacotamento Windows;
- compatibilidade com novos formatos de imagem.

### 19.3. Manutencao evolutiva

Objetivo: adicionar novas funcionalidades.

Exemplos:

- exportacao de dados;
- filtros avancados;
- dashboard;
- base de dados;
- assinatura digital;
- integracao com IA.

### 19.4. Manutencao preventiva

Objetivo: reduzir risco tecnico futuro.

Exemplos:

- refatoracao controlada;
- aumento de cobertura de testes;
- padronizacao de logs;
- melhoria de tratamento de excecoes;
- revisao de dependencias.

---

## 20. Riscos tecnicos

| Risco | Impacto | Mitigacao |
| --- | --- | --- |
| Dependencia de internet para geocoding | Localizacao enriquecida pode falhar | Manter dados EXIF basicos e tratar falha silenciosamente. |
| Imagens muito grandes | Alto uso de memoria | Carregar imagens com dimensoes controladas e liberar recursos. |
| Pastas com muitas imagens | Interface lenta | Usar carregamento assincrono e paginacao/lazy loading. |
| Falta de JavaFX no ambiente | Aplicacao nao abre | Distribuir runtime customizado com jlink/jpackage. |
| Mudanca em API externa | Reverse geocoding pode falhar | Isolar acesso em service e permitir troca de provedor. |
| Ausencia de testes automatizados | Regressao em funcionalidades | Implementar suite minima de testes unitarios e funcionais. |
| Operacoes destrutivas em ficheiros | Perda de dados | Confirmacoes, lixeira interna ou backup antes de remover. |

---

## 21. Melhorias futuras recomendadas

### 21.1. Funcionalidades

- Exportar metadados para JSON, CSV e Excel.
- Adicionar pesquisa por nome, data, camera, GPS e tamanho.
- Criar filtros por tipo de imagem, periodo, localizacao e camera.
- Implementar dashboard com estatisticas da colecao.
- Criar mapa interno com todos os pontos GPS encontrados.
- Adicionar timeline geografica.
- Implementar historico de imagens analisadas.
- Permitir drag and drop de imagens e pastas.
- Adicionar comparacao lado a lado entre imagens.
- Gerar relatorio consolidado de uma pasta inteira.
- Permitir modelos customizaveis de relatorio.
- Adicionar assinatura digital dos relatorios.
- Adicionar OCR para extrair texto de imagens.
- Adicionar classificacao automatica por IA.
- Detectar duplicados por hash perceptual.
- Permitir edicao controlada de metadados EXIF.

### 21.2. Arquitetura

- Introduzir injeccao de dependencias simples para servicos.
- Criar interfaces para servicos externos, como geocoding e mapas.
- Separar mais claramente regras de negocio da camada JavaFX.
- Criar camada de persistencia local.
- Adicionar base de dados SQLite para historico e indexacao.
- Criar DTOs para exportacao.
- Padronizar tratamento de erros com excecoes especificas.
- Adicionar logs com niveis `INFO`, `WARN` e `ERROR`.

### 21.3. Qualidade e testes

- Criar testes unitarios para `ImageMetadataService`.
- Criar testes unitarios para `FileManagerService`.
- Criar testes de geracao de PDF.
- Criar testes de integracao com imagens de exemplo.
- Criar pipeline de build.
- Validar empacotamento em maquina limpa.
- Criar checklist de release.

### 21.4. Usabilidade

- Melhorar mensagens de erro para utilizador final.
- Adicionar barra de progresso em importacoes grandes.
- Adicionar indicador de carregamento de miniaturas.
- Adicionar tema claro/escuro.
- Adicionar atalhos documentados.
- Adicionar preferencias do utilizador.
- Permitir configurar pasta raiz.

### 21.5. Seguranca e conformidade

- Criar mecanismo de backup antes de remocao.
- Validar caminhos para evitar operacoes fora do escopo definido.
- Adicionar politica de privacidade para tratamento local de imagens.
- Adicionar aviso de uso de servicos externos quando usar geocoding.
- Assinar instalador Windows.
- Assinar relatorios PDF.

---

## 22. Roadmap sugerido

### Fase 1: Consolidacao tecnica

- Padronizar nomes e mensagens.
- Corrigir encoding de comentarios e ficheiros.
- Criar logs.
- Criar testes unitarios iniciais.
- Revisar tratamento de excecoes.

### Fase 2: Melhorias de uso

- Adicionar filtros e pesquisa.
- Melhorar barra de progresso.
- Configurar pasta raiz pela interface.
- Melhorar feedback de importacao e relatorios.

### Fase 3: Dados e historico

- Introduzir SQLite.
- Indexar imagens analisadas.
- Guardar historico de analises.
- Exportar JSON/CSV/Excel.

### Fase 4: Relatorios avancados

- Relatorio por lote.
- Templates de relatorio.
- Assinatura digital.
- Capa e identidade visual.

### Fase 5: Distribuicao profissional

- Gerar instalador EXE/MSI.
- Assinar instalador.
- Criar atualizador.
- Criar notas de versao.
- Testar em maquinas limpas.

### Fase 6: Inteligencia e automacao

- OCR.
- Classificacao automatica.
- Deteccao de duplicados.
- Analise forense basica.
- Mapa consolidado e timeline.

---

## 23. Criterios de aceite para a versao atual

A versao atual pode ser considerada aceitavel quando:

- a aplicacao abre corretamente no ambiente alvo;
- o utilizador consegue importar imagens;
- o utilizador consegue navegar pelas imagens;
- o preview funciona sem bloquear a interface em uso normal;
- os metadados principais sao extraidos quando existem;
- imagens sem EXIF nao quebram o fluxo;
- coordenadas GPS abrem corretamente no mapa;
- relatorios PDF sao gerados;
- o build e reproduzivel;
- existe procedimento documentado para empacotamento.

---

## 24. Conclusao

O JM Vision Intelligence System encontra-se num estagio funcional de aplicacao desktop JavaFX para analise de imagens. A estrutura atual ja demonstra separacao entre interface, controladores, servicos, modelo e utilitarios, permitindo evolucao organizada.

O proximo passo recomendado e consolidar a qualidade tecnica: testes, logs, padronizacao de erros e empacotamento confiavel. Depois disso, o sistema pode evoluir para recursos mais avancados como indexacao local, historico, exportacao de dados, relatorios profissionais, mapa consolidado e funcionalidades inteligentes de analise.

Esta documentacao deve ser revista a cada release, mantendo alinhamento entre requisitos, implementacao, deploy e manutencao.
