package br.com.amoreco.web;

import com.microsoft.playwright.*;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class BaseTest {

    // Usamos 'protected' para que a classe de teste consiga enxergar essas variáveis
    protected static Playwright playwright;
    protected static Browser browser;
    protected BrowserContext context;
    protected Page page;
    protected PortfolioPage portfolioPage;

    protected static ExtentReports extent;
    protected ExtentTest relatorioTeste;
    protected String evidenciaHtml = "";

    @BeforeAll
    public static void iniciarNavegador() {
        // Força a máquina a usar o fuso horário de Brasília (mesmo rodando nos EUA)
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));

        String dataHora = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date());
        ExtentSparkReporter spark = new ExtentSparkReporter("target/Relatorio-Elegante_" + dataHora + ".html");
        spark.config().setDocumentTitle("Relatório de Automação");
        spark.config().setReportName("Portfólio - Flaviana Pina");
        extent = new ExtentReports();
        extent.attachReporter(spark);

        // Ignora a falta de DLLs do Windows (como a zlib1.dll) para navegadores que não usamos
        Map<String, String> env = new HashMap<>();
        env.put("PLAYWRIGHT_SKIP_VALIDATE_HOST_REQUIREMENTS", "true");
        
        playwright = Playwright.create(new Playwright.CreateOptions().setEnv(env));
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
    }

    @AfterAll
    public static void fecharNavegador() {
        playwright.close();
        extent.flush(); 
    }

    @BeforeEach
    public void novaAba(TestInfo testInfo) {
        evidenciaHtml = ""; 
        relatorioTeste = extent.createTest(testInfo.getDisplayName());
        
        // Instrui o Playwright a gravar um vídeo de cada teste e salvar na pasta "videos/"
        context = browser.newContext(new Browser.NewContextOptions()
            .setRecordVideoDir(java.nio.file.Paths.get("videos/"))
            .setRecordVideoSize(1280, 720)); // Resolução HD para ficar bonito no site!
            
        page = context.newPage();
        portfolioPage = new PortfolioPage(page);
    }

    // Método ajudante para registrar evidências com largura padrão (600px)
    protected void registrarEvidencia(String titulo, byte[] printBytes) {
        registrarEvidencia(titulo, printBytes, 600);
    }

    // Método ajudante para registrar evidências com largura personalizada (ex: 390px para mobile)
    protected void registrarEvidencia(String titulo, byte[] printBytes, int largura) {
        String base64 = Base64.getEncoder().encodeToString(printBytes);
        evidenciaHtml = "<b>" + titulo + ":</b><br><img src='data:image/png;base64," + base64 + "' style='max-width: 100%; width: " + largura + "px; border-radius: 8px; margin-top: 10px; border: 1px solid #ccc;' />";
    }

    @AfterEach
    public void fecharAba() {
        relatorioTeste.pass("Cenário executado com sucesso!");
        if (!evidenciaHtml.isEmpty()) {
            relatorioTeste.info(evidenciaHtml); 
        }
        context.close();
    }
}