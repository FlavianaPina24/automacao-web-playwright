package br.com.amoreco.web;

import com.microsoft.playwright.Download;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.Page;

public class PortfolioPage {
    private Page page;

    // Mapeamento dos Elementos (Locators)
    private String preloader = "#preloader";
    private String btnStartPreloader = "#btn-start-preloader";
    private String btnThemeToggle = "#theme-toggle";
    private String inputName = "#contact input[name='name']";
    private String inputEmail = "#contact input[name='email']";
    private String inputMessage = "#contact textarea[name='message']";
    private String formContact = "#contact .contact-form";
    private String sectionAbout = "#about";
    private String btnDownload = "text=Baixar Currículo";
    private String btnHamburger = "#hamburger-btn";
    private String btnGithub = "footer a[aria-label='GitHub']";
    private String btnLinkedin = "footer a[aria-label='LinkedIn']";
    private String footer = "footer";
    private String modalDepoimento = "#feedback-modal .popup-content";
    private String inputDepoimentoNome = "#leave-feedback-form input[name='name']";
    private String inputDepoimentoCargo = "#leave-feedback-form input[name='role']";
    private String inputDepoimentoMensagem = "#leave-feedback-form textarea[name='message']";
    private String btnCancelarDepoimento = "text=Cancelar";
    private String btnPublicarDepoimento = "text=Publicar Depoimento";
    private String popupSucesso = "#success-popup";
    private String btnA11y = "#a11y-btn";
    private String panelA11y = "#a11y-panel";
    private String btnDyslexia = "button[aria-label='Fonte para dislexia']";

    // Construtor
    public PortfolioPage(Page page) {
        this.page = page;
    }

    // Ações na Página
    public void navegar() {
        // Cache-Busting: Força o servidor a entregar a versão mais recente do site
        page.navigate("https://flavianapina.com.br/?nocache=" + System.currentTimeMillis());
        
        // Inteligência QA: O nosso site possui o "Passe VIP" que oculta o preloader para robôs (Webdriver/Lighthouse).
        // Portanto, o robô só tenta clicar no botão de iniciar se ele realmente estiver visível!
        if (page.locator(btnStartPreloader).isVisible()) {
            page.locator(btnStartPreloader).click();
        }
        page.locator(preloader).waitFor(new Locator.WaitForOptions().setState(com.microsoft.playwright.options.WaitForSelectorState.HIDDEN));
    }

    public String getTitulo() {
        return page.title();
    }

    public void alternarTema() {
        page.locator(btnThemeToggle).click();
    }

    public String getClasseDoBody() {
        return page.locator("body").getAttribute("class");
    }

    public void preencherFormulario(String nome, String email, String mensagem) {
        // Centraliza o formulário perfeitamente na tela para a gravação do vídeo
        page.locator(formContact).evaluate("el => el.scrollIntoView({ behavior: 'smooth', block: 'center' })");
        page.waitForTimeout(1000); // Aguarda 1 segundo para a câmera focar antes de digitar

        page.locator(inputName).fill(nome);
        page.locator(inputEmail).fill(email);
        page.locator(inputMessage).fill(mensagem);
    }

    public Download baixarCurriculo() {
        // Centraliza a seção Sobre Mim perfeitamente na tela para a gravação do vídeo
        page.locator(sectionAbout).evaluate("el => el.scrollIntoView({ behavior: 'smooth', block: 'center' })");
        page.waitForTimeout(1000); // Aguarda a câmera focar antes de clicar

        return page.waitForDownload(() -> {
            page.locator(btnDownload).click();
        });
    }

    public String getLinkGithub() {
        return page.locator(btnGithub).getAttribute("href");
    }

    public String getLinkLinkedin() {
        return page.locator(btnLinkedin).getAttribute("href");
    }

    public void abrirMenuMobile() {
        page.locator(btnHamburger).click();
    }

    public void abrirModalDepoimento() {
        // Localizador supremo focado em QA! Imune a mudanças de CSS.
        page.getByTestId("btn-open-feedback").click();
        page.locator(modalDepoimento).waitFor(); // Espera a animação do pop-up terminar
    }

    public void preencherModalDepoimento(String nome, String cargo, String mensagem) {
        page.locator(inputDepoimentoNome).fill(nome);
        page.locator(inputDepoimentoCargo).fill(cargo);
        page.locator(inputDepoimentoMensagem).fill(mensagem);
    }

    public void cancelarDepoimento() {
        page.locator(btnCancelarDepoimento).click();
    }

    public void publicarDepoimento() {
        page.locator(btnPublicarDepoimento).click();
    }

    public void abrirPainelAcessibilidade() {
        page.locator(btnA11y).click();
        // Espera a animação do CSS terminar e o painel ficar totalmente visível
        page.locator(panelA11y).waitFor(new Locator.WaitForOptions().setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE));
    }

    public boolean painelAcessibilidadeVisivel() {
        return page.locator(panelA11y).isVisible();
    }

    public void ativarFonteDislexia() {
        page.locator(btnDyslexia).click();
    }

    public boolean fonteDislexiaAtiva() {
        String htmlClass = page.locator("html").getAttribute("class");
        return htmlClass != null && htmlClass.contains("a11y-dyslexia");
    }

    public boolean mensagemSucessoEstaVisivel() {
        // Espera ativamente a animação do pop-up de sucesso aparecer na tela
        // Aumentamos o timeout para 60 segundos (60000ms) para perdoar o "sono" da API gratuita do Render
        page.locator(popupSucesso).waitFor(new Locator.WaitForOptions()
            .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE)
            .setTimeout(60000));
        return page.locator(popupSucesso).isVisible();
    }

    public String getTextoMensagemSucesso() {
        return page.locator(popupSucesso + " h3").innerText();
    }

    public boolean modalEstaOculto() {
        // Espera ativamente a animação do modal sumir da tela antes de verificar
        page.locator(modalDepoimento).waitFor(new Locator.WaitForOptions().setState(com.microsoft.playwright.options.WaitForSelectorState.HIDDEN));
        return page.locator(modalDepoimento).isHidden();
    }

    // Métodos Auxiliares para Screenshots (retornam a imagem em bytes para o relatório)
    public byte[] tirarPrintTelaInteira(String nomeArquivo) {
        return page.screenshot(new Page.ScreenshotOptions().setPath(java.nio.file.Paths.get(nomeArquivo)));
    }

    public byte[] tirarPrintFormulario(String nomeArquivo) {
        return page.locator(formContact).screenshot(new Locator.ScreenshotOptions().setPath(java.nio.file.Paths.get(nomeArquivo)));
    }

    public byte[] tirarPrintSecaoAbout(String nomeArquivo) {
        return page.locator(sectionAbout).screenshot(new Locator.ScreenshotOptions().setPath(java.nio.file.Paths.get(nomeArquivo)));
    }

    public byte[] tirarPrintRodape(String nomeArquivo) {
        return page.locator(footer).screenshot(new Locator.ScreenshotOptions().setPath(java.nio.file.Paths.get(nomeArquivo)));
    }

    public byte[] tirarPrintModalDepoimento(String nomeArquivo) {
        return page.locator(modalDepoimento).screenshot(new Locator.ScreenshotOptions().setPath(java.nio.file.Paths.get(nomeArquivo)));
    }

    public byte[] tirarPrintSucesso(String nomeArquivo) {
        return page.locator(popupSucesso + " .popup-content").screenshot(new Locator.ScreenshotOptions().setPath(java.nio.file.Paths.get(nomeArquivo)));
    }

    // Clica no botão "Solicitar Auditoria" na seção de serviços
    public void clicarSolicitarAuditoria() {
        // O localizador foi atualizado para refletir a mudança de texto no front-end.
        // O robô agora procura pelo botão "Vamos Conversar" dentro do card de serviço de auditoria.
        page.locator(".service-card:has-text('Auditoria de Qualidade')").getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Vamos Conversar")).click();
    }

    // Obtém o texto atual do campo de mensagem no formulário de contato
    public String getTextoDaMensagem() {
        return page.locator(inputMessage).inputValue();
    }

    // Clica no link "Ler Artigo" do card de UX Multiplataforma
    public void clicarNoLinkDoArtigoDeUx() {
        // Localizador robusto que encontra o card pelo título e clica no link "Ler Artigo" dentro dele
        page.locator(".skill-card:has-text('Projetando Experiências Multiplataforma')")
            .getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Ler Artigo")).click();
    }
}