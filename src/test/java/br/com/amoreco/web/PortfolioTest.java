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
        // Fecha o contexto padrão (Desktop) que foi aberto automaticamente antes do teste
        context.close();

        // Recria o contexto oficial do robô simulando um celular e com a câmera ligada!
        context = browser.newContext(new Browser.NewContextOptions()
            .setViewportSize(390, 844)
            .setIsMobile(true)
            .setRecordVideoDir(java.nio.file.Paths.get("videos/"))
            .setRecordVideoSize(390, 844));
            
        page = context.newPage();
        
        portfolioPage = new PortfolioPage(page);

        portfolioPage.navegar();
        portfolioPage.abrirMenuMobile();
        
        registrarEvidencia("Evidência - Simulação Mobile (Menu Aberto)", portfolioPage.tirarPrintTelaInteira("print-mobile-menu.png"), 390);
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

    // =================================================================================
    // CENÁRIO 7: Validar a abertura do Modal de Depoimentos
    // =================================================================================
    @Test
    public void testarAberturaModalDepoimento() {
        portfolioPage.navegar();
        
        portfolioPage.abrirModalDepoimento();
        
        registrarEvidencia("Evidência - Modal de Depoimento Aberto", portfolioPage.tirarPrintModalDepoimento("print-modal-depoimento.png"));
    }

    // =================================================================================
    // CENÁRIO 8: Validar preenchimento e cancelamento do Modal de Depoimentos
    // =================================================================================
    @Test
    public void testarPreenchimentoECancelamentoModal() {
        portfolioPage.navegar();
        portfolioPage.abrirModalDepoimento();
        
        portfolioPage.preencherModalDepoimento("Flaviana QA", "Engenheira de Qualidade", "Testando o preenchimento automático do Playwright com sucesso!");
        
        registrarEvidencia("Evidência - Modal de Depoimento Preenchido", portfolioPage.tirarPrintModalDepoimento("print-modal-preenchido.png"));
        
        portfolioPage.cancelarDepoimento();
        
        assertTrue(portfolioPage.modalEstaOculto(), "Erro Crítico: O modal ainda está visível na tela após clicar em Cancelar!");
    }
}
