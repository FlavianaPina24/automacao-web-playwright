# 🎭 Automação E2E - Portfólio Web (Playwright + Java)

![Status](https://img.shields.io/badge/Status-Concluído-success)
![Java](https://img.shields.io/badge/Java-21-blue)
![Playwright](https://img.shields.io/badge/Playwright-1.44.0-2E8B57)
![GitHub Actions](https://img.shields.io/badge/CI%2FCD-GitHub_Actions-2088FF)

Este repositório contém um framework de automação de testes de ponta a ponta (E2E) desenvolvido para validar o meu portfólio pessoal. O objetivo deste projeto é demonstrar a aplicação de boas práticas de Engenharia de Qualidade (QA) e ferramentas modernas de automação no mercado.

## 🚀 Tecnologias e Padrões Utilizados

* **[Java 21](https://jdk.java.net/21/):** Linguagem de programação base.
* **[Playwright](https://playwright.dev/java/):** Ferramenta moderna da Microsoft para automação web (rápida e resiliente, sem a necessidade de instanciar WebDrivers locais).
* **[JUnit 5](https://junit.org/junit5/):** Motor de execução dos testes e asserções.
* **[Maven](https://maven.apache.org/):** Gerenciador de dependências e processo de build.
* **[ExtentReports](https://extentreports.com/):** Gerador de relatórios executivos em HTML com evidências embutidas (Base64).
* **Page Object Model (POM):** Padrão de arquitetura de software para separação de responsabilidades (regras de negócio vs. roteiros de teste) e fácil manutenção.
* **GitHub Actions (CI/CD):** Esteira de integração contínua executando os testes de forma automática e "Headless" a cada novo *push*.

## 🎯 Cenários de Teste Implementados

A suíte de testes atual é capaz de cobrir as principais funcionalidades do portfólio em tempo recorde:
1. **Carregamento e Título:** Validação do acesso inicial.
2. **Modo Claro/Escuro:** Teste de alteração de estado no CSS via clique (Dark/Light mode).
3. **Preenchimento de Formulários:** Automação de digitação rápida em campos de texto no formulário de contato.
4. **Download de Arquivos:** Interceptação assíncrona e validação de download de PDF (Currículo).
5. **Responsividade Mobile:** Simulação de contexto de navegador móvel (dimensões de iPhone) e testes do menu *Hamburger*.
6. **Validação de Links:** Verificação de atributos do DOM (redes sociais no rodapé).

## 📁 Estrutura do Projeto

```text
📦 Automacao_Teste_Web
 ┣ 📂 .github/workflows      # Pipeline de CI/CD (GitHub Actions)
 ┣ 📂 src/test/java/.../web
 ┃ ┣ 📜 BaseTest.java        # Configurações do navegador, ciclo de vida e relatórios
 ┃ ┣ 📜 PortfolioPage.java   # Page Object Model (Mapeamento de elementos e ações)
 ┃ ┗ 📜 PortfolioTest.java   # Casos de teste e asserções (Roteiro limpo)
 ┣ 📜 .gitignore             # Arquivos não rastreados pelo versionamento
 ┗ 📜 pom.xml                # Dependências estruturais
```

## ⚙️ Como Executar Localmente

**Pré-requisitos:** Ter o Java 21 e o Maven instalados e configurados nas variáveis de ambiente do sistema.

1. Clone o repositório:
   ```bash
   git clone https://github.com/FlavianaPina24/automacao-web-playwright.git
   ```
2. Acesse a pasta do projeto via terminal:
   ```bash
   cd automacao-web-playwright
   ```
3. Execute a suíte de testes automatizada:
   ```bash
   mvn test
   ```

> **Nota:** Ao rodar através do Maven, os testes serão executados no modo invisível (*Headless*). O relatório executivo detalhado será gerado automaticamente na pasta `target/`.

## 📊 Relatórios de Execução

O framework está configurado para injetar as evidências (screenshots e recortes do DOM) no formato **Base64** diretamente dentro do arquivo HTML gerado pelo ExtentReports, mitigando o risco de perda do histórico visual. O _timezone_ foi adaptado internamente via código para padronizar os relatórios em nuvem (EUA) para o Horário de Brasília (BRT).

---
Desenvolvido com 🩵 por **Flaviana Pina**.
