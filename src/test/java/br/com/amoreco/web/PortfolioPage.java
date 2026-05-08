package br.com.amoreco.web;

import com.microsoft.playwright.Download;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class PortfolioPage {
    private Page page;

    // Mapeamento dos Elementos (Locators)
    private String btnThemeToggle = "#theme-toggle";
    private String inputName = "#contact input[name='name']";
    private String inputEmail = "#contact input[name='email']";
    private String inputMessage = "#contact textarea[name='message']";
    private String formContact = "#contact .contact-form";
    private String sectionAbout = "#about";
    private String btnDownload = "text=Baixar Currículo";
    private String btnHamburger = "#hamburger-btn";
    private String btnGithub = "a[aria-label='GitHub']";
    private String btnLinkedin = "a[aria-label='LinkedIn']";
    private String footer = "footer";
    private String modalDepoimento = "#feedback-modal .popup-content";
    private String inputDepoimentoNome = "#leave-feedback-form input[name='name']";
    private String inputDepoimentoCargo = "#leave-feedback-form input[name='role']";
    private String inputDepoimentoMensagem = "#leave-feedback-form textarea[name='message']";
    private String btnCancelarDepoimento = "text=Cancelar";

    // Construtor
    public PortfolioPage(Page page) {
        this.page = page;
    }

    // Ações na Página
    public void navegar() {
        page.navigate("https://flavianapina24.github.io/meu-portfolio/");
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
}