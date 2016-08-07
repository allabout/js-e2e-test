package jstest;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;


import net.jsourcerer.webdriver.jserrorcollector.JavaScriptError;
import report.TestResultReporter;
import result.JsErrorManager;
import util.Traceable;

/**
 * テスト管理クラス
 * WebDriverや各種クラスを使用してJSテストを実施する。
 * @author sankame
 */
public class TestOrganizer implements Traceable{
    private String appName = null;
    private String filePathToUrls = null;
    private String os = null;
    private String SELENIUM_SERVER_URL = null;
    private boolean MAIL_SEND = false;
    private Set<String> EXCLUDE_DOMAINS = new HashSet<String>();
    private Map<String, String> mailConfig = new HashMap<String, String>();

    /**
     * コンストラクター
     * @param appName アプリ名
     * @param filePathToUrls テスト対象URLを含むファイルへのパス
     * @param os ブラウザーテストを実行するOS名
     */
    public TestOrganizer(String appName, String filePathToUrls, String os) {
        super();
        this.appName = appName;
        this.filePathToUrls = filePathToUrls;
        this.os = os;
    }

    /**
     * テスト実行
     */
    public void doTest(){

        try{
            this.loadConfig();

            DesiredCapabilities capability = new DesiredCapabilities();
            //現状、firefoxのみが対象。
            capability.setBrowserName("firefox");
            capability.setPlatform( this.getPlatform(this.os) );
            capability.setJavascriptEnabled(true);

            FirefoxProfile fp = new FirefoxProfile();

            JavaScriptError.addExtension(fp);
            capability.setCapability(FirefoxDriver.PROFILE, fp);

            WebDriver webdriver = null;
            webdriver = new RemoteWebDriver(new URL(SELENIUM_SERVER_URL), capability);
            webdriver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
            webdriver.manage().timeouts().pageLoadTimeout(90, TimeUnit.SECONDS);

            JsErrorManager jsErrorManager = new JsErrorManager(this.EXCLUDE_DOMAINS);

            //テスト対象のURL一覧を読み込む。
            File file = new File(this.filePathToUrls);
            BufferedReader br = new BufferedReader(new FileReader(file));

            String url = br.readLine();

            while(url != null){
                //xmlは対象外。
                if(url.endsWith(".xml")){
                }else{
                    //各URLにアクセスした後、WebDriverオブジェクトからJSエラーを収集する。
                    webdriver.get(url);
                    Thread.sleep(5000);
                    jsErrorManager.extractError(webdriver, url);
                }
                url = br.readLine();
            }

            br.close();

            String allErrors = jsErrorManager.getAllErrors();

            //エラーが発生した時だけ通知する。
            if( !StringUtils.isEmpty(allErrors) ){
                TestResultReporter testResultReporter
                         = new TestResultReporter(this.mailConfig, this.MAIL_SEND);
                testResultReporter.setAppName(this.appName);
                testResultReporter.setTestResult(allErrors);
                testResultReporter.sendReport();
            }else{
                out("エラーが0件だったため、通知をスキップしました。");
            }
            webdriver.quit();
            System.exit(0);

        }catch(Exception exception){
            exception.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * 設定ファイル読み込み
     * 設定ファイルに定義された値をクラスのフィールドにセットする。
     * @throws Exception
     */
    private void loadConfig() throws Exception{

        Config parsedConfig = ConfigFactory.parseFile(new File("conf/application.conf"));
        Config config = ConfigFactory.load(parsedConfig);

        this.SELENIUM_SERVER_URL = config.getString("selenium-server-url");

        ArrayList<String> excludeDomains = (ArrayList<String>) config.getStringList("exclude-domains");
        for(String eachDomain : excludeDomains){
            this.EXCLUDE_DOMAINS.add(eachDomain);
        }

        this.MAIL_SEND = config.getBoolean("mail-send");

        this.mailConfig.put("from", config.getString("mail.from"));
        this.mailConfig.put("to", config.getString("mail.to"));
        this.mailConfig.put("charset", config.getString("mail.charset"));
        this.mailConfig.put("subject-prefix", config.getString("mail.subject-prefix"));
        this.mailConfig.put("body-prefix", config.getString("mail.body-prefix"));
        this.mailConfig.put("body-prefix2", config.getString("mail.body-prefix2"));

    }

    /**
     * プラットフォーム取得
     * @param platformId ブラウザーテストを実行するプラットフォーム(OS)
     * @return WebDriverに渡すプラットフォーム識別子
     */
    private Platform getPlatform(String platformId){
        String upperCaseId = platformId.toUpperCase();

        if(upperCaseId.equals("WINDOWS")){
            return Platform.WINDOWS;
        }
        if(upperCaseId.equals("MAC")){
            return Platform.MAC;
        }

        return null;
    }
}
