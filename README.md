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
## 🎯 Diferenciais Técnicos e Estratégicos (Projetos em Destaque)
Este projeto vai além de uma simples automação, demonstrando um ecossistema completo de qualidade e segurança:

### 1. Engenharia de Automação Moderna (Java & Playwright)
*   **Framework Robusto:** Suíte de testes E2E com **Java 21 e Playwright**, utilizando o padrão **Page Object Model (POM)** para máxima manutenibilidade.
*   **CI/CD & Nightly Builds:** Pipeline no **GitHub Actions** que executa a suíte completa a cada *push* e diariamente (builds noturnas), garantindo a saúde contínua da aplicação.
*   **Relatórios Executivos:** Geração de relatórios via **ExtentReports** com evidências visuais (prints em Base64) e vídeos (`.webm`) de cada cenário executado.
*   **Testes de UX e Mobile:** Validação de responsividade em viewports de dispositivos móveis, testes de componentes interativos (modais, painéis) e fluxos de usuário ponta a ponta.

### 2. DevSecOps em Profundidade (Shift-Left Security)
*   **Análise Dinâmica de Segurança (DAST):** Integração do **OWASP ZAP** na esteira de CI/CD para realizar varreduras de vulnerabilidade automaticamente após o deploy.
*   **Testes de Injeção (XSS):** Cenário de teste específico que simula um ataque de *Cross-Site Scripting*, validando a sanitização de dados no back-end (API em Node.js) e a resiliência do front-end.
*   **Auditoria de Cabeçalhos de Segurança:** Teste que verifica a presença de *headers* HTTP defensivos (ex: `X-Frame-Options`), mitigando riscos como *Clickjacking*.

### 3. Qualidade Inclusiva e IA (A11y & Blue Team)
*   **Auditoria de Acessibilidade (A11y):** Varredura autônoma com a biblioteca **Axe-Core** integrada aos testes, garantindo a conformidade com as diretrizes da WCAG.
*   **IA na Cibersegurança (Projeto Blue Team):** Desenvolvimento de um sistema paralelo em **Python com Machine Learning (Scikit-learn)**, capaz de analisar logs, detectar anomalias e simular a neutralização de ataques de *Brute Force*, demonstrando uma visão estratégica e proativa de defesa.

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
