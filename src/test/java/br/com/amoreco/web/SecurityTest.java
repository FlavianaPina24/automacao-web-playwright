package br.com.amoreco.web;

import com.microsoft.playwright.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class SecurityTest extends BaseTest {

    // =================================================================================
    // CENÁRIO 13: DevSecOps - Tentativa de Injeção XSS (Cross-Site Scripting)
    // =================================================================================
    @DisplayName("CENÁRIO 13: DevSecOps - Tentativa de Injeção XSS no Formulário")
    @Test
    public void testarInjecaoXSSNoFormulario() {
        AtomicBoolean xssDisparado = new AtomicBoolean(false);

        // 1. "Armadilha" do Playwright: Se a página abrir um pop-up de alerta (vírus), o XSS funcionou e registramos a falha
        page.onDialog(dialog -> {
            String mensagem = dialog.message();
            if (mensagem.contains("hackeado") || mensagem.contains("XSS")) {
                xssDisparado.set(true);
                relatorioTeste.fail("🚨 VULNERABILIDADE DETECTADA: O script malicioso foi executado no navegador! Mensagem: " + mensagem);
                dialog.accept();
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

        // 4. Aguarda 3 segundos para confirmar que nenhum script malicioso disparou alert()
        page.waitForTimeout(3000);

        assertFalse(xssDisparado.get(), "Falha Crítica de Segurança: Injeção XSS executada com sucesso!");
        relatorioTeste.pass("🛡️ Segurança (XSS): O sistema sanitizou as entradas e bloqueou a execução de scripts maliciosos.");
    }

    // =================================================================================
    // CENÁRIO 14: DevSecOps - Validação de Cabeçalhos de Segurança HTTP
    // =================================================================================
    @DisplayName("CENÁRIO 14: DevSecOps - Validação de Cabeçalhos de Segurança HTTP")
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