package br.com.amoreco.web;

import com.microsoft.playwright.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class PortfolioTest extends BaseTest {

    // =================================================================================
    // CENÁRIO 1: Validar se o portfólio carrega corretamente e exibe o título esperado
    // =================================================================================
    @Test
    public void meuPrimeiroTesteComPlaywright() {
        portfolioPage.navegar();
        
        registrarEvidencia("Evidência - Tela Inicial do Portfólio", portfolioPage.tirarPrintTelaInteira("print-portfolio.png"));
        
        String tituloAtual = portfolioPage.getTitulo();
        assertEquals("Flaviana Pina | Portfolio", tituloAtual, "O título da página está incorreto!");
    }

    // =================================================================================
    // CENÁRIO 2: Validar o funcionamento do botão de Modo Claro (Light Mode)
    // =================================================================================
    @Test
    public void testarModoClaro() {
        portfolioPage.navegar();
        
        portfolioPage.alternarTema();
        
        registrarEvidencia("Evidência - Modo Claro Ativado", portfolioPage.tirarPrintTelaInteira("print-modo-claro.png"));
        
        String classDoBody = portfolioPage.getClasseDoBody();
        assertTrue(classDoBody != null && classDoBody.contains("light-mode"), "O Modo Claro não foi ativado!");
    }

    // =================================================================================
    // CENÁRIO 3: Validar o preenchimento automático do Formulário de Contato
    // =================================================================================
    @Test
    public void testarPreenchimentoFormularioContato() {
        portfolioPage.navegar();

        portfolioPage.preencherFormulario("Flaviana (Teste Automatizado)", "teste@qa.com", "Oi, Flaviana! O Playwright preencheu isso sozinho. :)");

        registrarEvidencia("Evidência - Formulário Preenchido", portfolioPage.tirarPrintFormulario("print-formulario.png"));
    }

    // =================================================================================
    // CENÁRIO 4: Validar se o botão de "Baixar Currículo" faz o download do arquivo PDF
    // =================================================================================
    @Test
    public void testarDownloadCurriculo() {
        portfolioPage.navegar();

        registrarEvidencia("Evidência - Área de Download do Currículo", portfolioPage.tirarPrintSecaoAbout("print-download.png"));

        Download download = portfolioPage.baixarCurriculo();

        assertEquals("Curriculo_Flaviana_Pina.pdf", download.suggestedFilename(), "Nome do arquivo incorreto!");

        download.saveAs(java.nio.file.Paths.get("Curriculo_Baixado_Teste.pdf"));
    }

    // =================================================================================
    // CENÁRIO 5: Validar a responsividade e o menu Hamburger simulando a tela de um Celular
    // =================================================================================
    @Test
    public void testarMenuMobile() {
        
        // Cria um contexto de navegador específico simulando a tela de um celular (ex: iPhone)
        BrowserContext mobileContext = browser.newContext(new Browser.NewContextOptions()
            .setViewportSize(390, 844)
            .setIsMobile(true));
            
        Page mobilePage = mobileContext.newPage();
        PortfolioPage portfolioMobile = new PortfolioPage(mobilePage); // Usa a mesma classe para o mobile!
        portfolioMobile.navegar();
        
        // Clica no botão hamburger que só fica visível em telas pequenas
        portfolioMobile.abrirMenuMobile();
        
        // Tira um print do formato mobile com o menu aberto!
        registrarEvidencia("Evidência - Simulação Mobile (Menu Aberto)", portfolioMobile.tirarPrintTelaInteira("print-mobile-menu.png"), 390);
        
        mobileContext.close();
    }

    // =================================================================================
    // CENÁRIO 6: Validar os links das Redes Sociais no rodapé
    // =================================================================================
    @Test
    public void testarRedesSociais() {
        portfolioPage.navegar();

        String linkGithub = portfolioPage.getLinkGithub();
        String linkLinkedin = portfolioPage.getLinkLinkedin();

        assertEquals("https://github.com/FlavianaPina24", linkGithub, "Link do GitHub incorreto!");
        assertEquals("https://www.linkedin.com/in/flaviana-pina-3848a39a", linkLinkedin, "Link do LinkedIn incorreto!");

        registrarEvidencia("Evidência - Rodapé e Redes Sociais", portfolioPage.tirarPrintRodape("print-rodape.png"));
    }
}
