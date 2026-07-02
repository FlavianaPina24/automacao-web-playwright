package br.com.amoreco.web;

import com.microsoft.playwright.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PortfolioTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(PortfolioTest.class);

    // =================================================================================
    // CENÁRIO 1: Validar se o portfólio carrega corretamente e exibe o título esperado
    // =================================================================================
    @Test
    public void meuPrimeiroTesteComPlaywright() {
        logger.info("CENÁRIO 1: Navegando para o portfólio para validar o título.");
        portfolioPage.navegar();
        
        registrarEvidencia("Evidência - Tela Inicial do Portfólio", portfolioPage.tirarPrintTelaInteira("print-portfolio.png"));
        
        String tituloAtual = portfolioPage.getTitulo();
        logger.info("Título da página encontrado: '{}'", tituloAtual);
        assertEquals("Flaviana Pina | Portfolio", tituloAtual, "O título da página está incorreto!");
    }

    // =================================================================================
    // CENÁRIO 2: Validar o funcionamento do botão de Modo Claro (Light Mode)
    // =================================================================================
    @Test
    public void testarModoClaro() {
        logger.info("CENÁRIO 2: Testando a funcionalidade do Modo Claro.");
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
        logger.info("CENÁRIO 3: Testando o preenchimento do formulário de contato.");
        portfolioPage.navegar();

        portfolioPage.preencherFormulario("Flaviana (Teste Automatizado)", "teste@qa.com", "Oi, Flaviana! O Playwright preencheu isso sozinho. :)");

        registrarEvidencia("Evidência - Formulário Preenchido", portfolioPage.tirarPrintFormulario("print-formulario.png"));
    }

    // =================================================================================
    // CENÁRIO 4: Validar se o botão de "Baixar Currículo" faz o download do arquivo PDF
    // =================================================================================
    @Test
    public void testarDownloadCurriculo() {
        logger.info("CENÁRIO 4: Testando o download do currículo.");
        portfolioPage.navegar();

        registrarEvidencia("Evidência - Área de Download do Currículo", portfolioPage.tirarPrintSecaoAbout("print-download.png"));

        Download download = portfolioPage.baixarCurriculo();

        assertEquals("Curriculo_Flaviana_Pina.pdf", download.suggestedFilename(), "Nome do arquivo incorreto!");

        download.saveAs(java.nio.file.Paths.get("Curriculo_Baixado_Teste.pdf"));
        logger.info("Download do arquivo '{}' concluído com sucesso.", download.suggestedFilename());
    }

    // =================================================================================
    // CENÁRIO 5: Validar a responsividade e o menu Hamburger simulando a tela de um Celular
    // =================================================================================
    @Test
    public void testarMenuMobile() {
        logger.info("CENÁRIO 5: Testando a responsividade e o menu mobile.");
        
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
    // CENÁRIO 9: Validar a abertura e funcionamento do Painel de Acessibilidade (A11y)
    // =================================================================================
    @Test
    public void testarPainelAcessibilidade() {
        logger.info("CENÁRIO 9: Testando o painel de acessibilidade (A11y).");
        portfolioPage.navegar();

        portfolioPage.abrirPainelAcessibilidade();
        assertTrue(portfolioPage.painelAcessibilidadeVisivel(), "O painel de acessibilidade não abriu!");

        portfolioPage.ativarFonteDislexia();
        assertTrue(portfolioPage.fonteDislexiaAtiva(), "A fonte de dislexia não foi ativada na página!");

        registrarEvidencia("Evidência - Painel A11y e Fonte Dislexia", portfolioPage.tirarPrintTelaInteira("print-a11y.png"));
    }
}
