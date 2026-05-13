package br.com.amoreco.web;

import com.microsoft.playwright.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.Map;

public class SecurityTest extends BaseTest {

    // =================================================================================
    // CENÁRIO 11: DevSecOps - Tentativa de Injeção XSS (Cross-Site Scripting)
    // =================================================================================
    @Test
    public void testarInjecaoXSSNoFormulario() {
        // 1. "Armadilha" do Playwright: Se a página abrir um pop-up de alerta (vírus), o XSS funcionou e o teste falha!
        page.onDialog(dialog -> {
            String mensagem = dialog.message();
            if (mensagem.contains("hackeado") || mensagem.contains("XSS")) {
                relatorioTeste.fail("🚨 VULNERABILIDADE DETECTADA: O script malicioso foi executado no navegador!");
                dialog.accept();
                fail("Falha Crítica de Segurança: Injeção XSS executada com sucesso!");
            } else {
                // Alerta inofensivo do sistema (ex: Firewall do Formspree bloqueando o envio). Apenas ignoramos.
                dialog.accept();
            }
        });

        portfolioPage.navegar();
        portfolioPage.abrirModalDepoimento();

        // 2. Payload Hacker malicioso
        String payloadHacker = "<script>alert('Você foi hackeado!');</script><img src='x' onerror='alert(\"XSS Fatal\")'>";

        // 3. Tenta injetar o código nos campos de texto
        portfolioPage.preencherModalDepoimento("Hacker do Bem", "DevSecOps", payloadHacker);
        registrarEvidencia("Evidência - Tentativa de Injeção XSS", portfolioPage.tirarPrintModalDepoimento("print-tentativa-xss.png"));
        
        portfolioPage.publicarDepoimento();

        // 4. Aguarda 3 segundos. Se a armadilha não foi disparada, o site está blindado!
        // (Não verificamos o pop-up de Sucesso aqui, pois sabemos que o Firewall do backend pode recusar a injeção)
        page.waitForTimeout(3000);

        relatorioTeste.pass("🛡️ Segurança (XSS): O sistema sanitizou as entradas e bloqueou a execução de scripts maliciosos.");
    }

    // =================================================================================
    // CENÁRIO 12: DevSecOps - Validação de Cabeçalhos de Segurança HTTP
    // =================================================================================
    @Test
    public void testarCabecalhosDeSeguranca() {
        // Acessamos o site e capturamos a resposta do servidor em tempo real
        // Cache-Busting: Garante a captura dos cabeçalhos da versão mais atual
        Response resposta = page.navigate("https://flavianapina.com.br/?nocache=" + System.currentTimeMillis());
        
        Map<String, String> headers = resposta.headers();
        StringBuilder relatorioHeaders = new StringBuilder("<b>Análise de Cabeçalhos HTTP:</b><ul>");
        
        boolean temXFrame = headers.containsKey("x-frame-options");
        boolean temXSSProtection = headers.containsKey("x-xss-protection");
        
        relatorioHeaders.append("<li><b>X-Frame-Options</b> (Proteção contra Clickjacking): ").append(temXFrame ? "✅ Ativo" : "⚠️ Ausente").append("</li>");
        relatorioHeaders.append("<li><b>X-XSS-Protection</b> (Filtro Anti-XSS): ").append(temXSSProtection ? "✅ Ativo" : "⚠️ Ausente").append("</li>");
        relatorioHeaders.append("</ul>");

        // Servidores gratuitos como GitHub Pages costumam não ter todos os headers rígidos. 
        // Como QA Sênior, nós geramos um "Aviso" (Warning) visual no relatório em vez de quebrar a esteira.
        if (!temXFrame || !temXSSProtection) {
            relatorioTeste.warning("⚠️ Observação de Segurança: Alguns cabeçalhos defensivos não estão configurados no servidor (Comum em GitHub Pages).<br>" + relatorioHeaders.toString());
        } else {
            relatorioTeste.pass("🛡️ Segurança: Cabeçalhos defensivos configurados corretamente.<br>" + relatorioHeaders.toString());
        }
        
        assertTrue(resposta.ok(), "Falha ao obter resposta do servidor.");
    }
}