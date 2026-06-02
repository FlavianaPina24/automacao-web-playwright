# 📓 Diário de Bordo - Automação Web Playwright

## 🏆 O que construímos até agora
- Implementação do padrão **Page Object Model (POM)**.
- Herança inteligente com a classe `BaseTest` para esconder complexidades.
- Relatórios executivos lindíssimos (ExtentReports) com imagens blindadas em **Base64** salvas na pasta `target/`.
- Ajuste de TimeZone para o Horário de Brasília.
- Esteira de **CI/CD no GitHub Actions** rodando em modo Headless no `ubuntu-22.04`.
- Configuração de **Nightly Builds** (CRON) para rodar automaticamente todos os dias às 00h00 (BRT).
- Mapeamento avançado de elementos web utilizando a melhor prática de QA: `data-testid`.
- 9 Cenários de teste completos cobrindo CSS, Downloads, Mobile, Modais e Acessibilidade (A11y)!
- **Bônus:** Atualização do portfólio destacando a visão BA & QA, com gravação de vídeo (`.webm`) nativa do Playwright executando a automação!

## 🎯 Conquistas de Hoje!
- [x] Configuração do `.gitignore` para manter o repositório leve (ignorando vídeos em `.webm`).
- [x] **Cenário 8:** Teste do novo Pop-up de Depoimentos interagindo com os campos.
- [x] Bug do "Modal Fantasma" detectado em ambiente de Produção e corrigido via CSS (`visibility: hidden`).
- [x] Exploração de asserções avançadas do Playwright (`isHidden` com `waitFor`).
- [x] **Cenário 9:** Teste do novo Painel de Acessibilidade (A11y) interativo e validação visual da Fonte para Dislexia.

---
*Anotação:* Suíte de testes atualizada para 9 cenários rodando perfeitamente e documentação em dia! 🚀