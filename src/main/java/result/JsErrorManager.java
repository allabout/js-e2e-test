package result;

import java.net.URI;
import java.net.URLEncoder;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import net.jsourcerer.webdriver.jserrorcollector.JavaScriptError;
import util.Traceable;

/**
 * JavaScriptエラー管理
 * WebDriverからJSエラーを収集し、HTML形式にした上でフィールドに保持する。
 * @author sankame
 */
public class JsErrorManager implements Traceable{

    private final int indexOfJsError = 0;
    private StringBuffer errorDetails = new StringBuffer();
    private Set<String> excludeDomains;

    /**
     * コンストラクター
     * @param excludeDomains テスト対象外ドメイン
     * (主に外部のJSタグで発生したエラーを無視するために使用)
     */
    public JsErrorManager(Set<String> excludeDomains) {
        super();
        this.excludeDomains = excludeDomains;
    }

    /**
     * エラー抽出
     * @param webdriver　ページアクセス後のWebDriverオブジェクト
     * @param url アクセスしたページのURL
     */
    public void extractError(WebDriver webdriver, String url) throws Exception{

        List<JavaScriptError> jsErrors = JavaScriptError.readErrors(webdriver);

        int i = 0;

        URI uri = null;

        for(i = indexOfJsError; i < jsErrors.size(); i++) {

                String encodedSourceName = URLEncoder.encode( jsErrors.get(i).getSourceName() , "UTF-8");
                uri = URI.create(encodedSourceName);

                if ( !excludeDomains.contains(uri.getHost()) ){
                        errorDetails.append("<tr>");
                        errorDetails.append("<td>" + url + "</td>");
                        errorDetails.append("<td>" + jsErrors.get(i).toString() + "</td>");
                        errorDetails.append("</tr>");
                }
        }
    }

    /**
     * 全エラー内容取得
     * エラー件数が0の場合、空文字を返す。
     * @return HTMLタグで整形後のエラー情報
     */
    public String getAllErrors(){

        if (this.errorDetails.length() == 0){
            return "";
        }

        return "<table border='2'><tbody>"
                + "<tr bgcolor='#cccccc'><th>URL</th><th>Error</th></tr>"
                + errorDetails.toString()
                + "</tbody></table>";
    }
}
