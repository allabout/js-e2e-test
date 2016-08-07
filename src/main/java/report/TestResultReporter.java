package report;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import mail.Mail;
import mail.MailManager;
import mail.MailMock;
import util.Traceable;

/**
 * テスト結果通知クラス
 * @author sankame
 */
public class TestResultReporter implements Traceable{

    private boolean mailSend = false;
    private Map<String, String> mailConfig;
    private String appName = null;
    private String testResult = null;

    /**
     * コンストラクター
     * @param mailConfig メール設定情報(from、toなど)
     * @param mailSend メール送信フラグ(trueの場合はメール送信する)
     */
    public TestResultReporter(Map<String, String> mailConfig, boolean mailSend) {
        this.mailSend = mailSend;
        this.mailConfig = mailConfig;
    }

    /**
     * 結果送信
     * @throws Exception
     */
    public void sendReport() throws Exception{

        Mail mail;
        //フラグがtrueの場合のみ実際のメール送信を行う。
        if(this.mailSend){
            mail = new MailManager();
        }else{
            mail = new MailMock();
        }

        mail.setFrom( this.mailConfig.get("from") );
        mail.setTo( this.mailConfig.get("to") );

        //文字コード指定。
        String charset = this.mailConfig.get("charset");
        mail.setCharset(charset);

        //件名作成。
        String todayAsString = this.createTodayAsString();
        String subject = this.mailConfig.get("subject-prefix")
                         + " " + this.appName
                         + " (" + todayAsString + ")";
        mail.setSubject(subject);

        //本文作成。
        StringBuilder mailBody = new StringBuilder();
        mailBody.append(this.mailConfig.get("body-prefix") + "<p>");
        mailBody.append(this.mailConfig.get("body-prefix2") + "<p>");
        mailBody.append(this.testResult);
        mail.setBody(mailBody.toString());

        mail.sendMail();
    }

    /**
     * 当日日付を作成
     * @return 当日日付(文字型)
     */
    private String createTodayAsString(){
        Date today = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy'/'MM'/'dd");
        String todayAsString = dateFormat.format(today);
        return todayAsString;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getTestResult() {
        return testResult;
    }

    public void setTestResult(String testResult) {
        this.testResult = testResult;
    }

}
