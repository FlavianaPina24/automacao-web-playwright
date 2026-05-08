# 📓 Diário de Bordo - Automação Web Playwright

## 🏆 O que construímos até agora
- Implementação do padrão **Page Object Model (POM)**.
- Herança inteligente com a classe `BaseTest` para esconder complexidades.
- Relatórios executivos lindíssimos (ExtentReports) com imagens blindadas em **Base64** salvas na pasta `target/`.
- Ajuste de TimeZone para o Horário de Brasília.
- Esteira de **CI/CD no GitHub Actions** rodando em modo Headless no `ubuntu-22.04`.
- Configuração de **Nightly Builds** (CRON) para rodar automaticamente todos os dias às 00h00 (BRT).
- Mapeamento avançado de elementos web utilizando a melhor prática de QA: `data-testid`.
- 7 Cenários de teste completos cobrindo CSS, Downloads, Mobile e Modais!
- **Bônus:** Atualização do portfólio destacando a visão BA & QA, com gravação de vídeo (`.webm`) nativa do Playwright executando a automação!

## 🎯 Próximos Passos (Para Amanhã)
- [ ] **Cenário 8:** Testar o novo Pop-up de Depoimentos (preencher Nome, Cargo, Mensagem e validar o clique de fechamento/envio).
- [ ] Continuar refinando a testabilidade do `index.html`.
- [ ] Explorar asserções avançadas do Playwright (ex: garantir que o pop-up realmente sumiu da tela ao cancelar).
- [ ] Configurar o arquivo `.gitignore` para ignorar a nova pasta `videos/` e não pesar o seu GitHub.

---
*Anotação:* Paramos logo após gravar o vídeo da automação e incluir no site. O ambiente local do VS Code foi limpo e está pronto para amanhã. 🚀