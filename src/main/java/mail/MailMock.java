package mail;

import util.Traceable;

/**
 * メール擬似送信クラス
 * @author sankame
 */
public class MailMock implements Traceable, Mail {

    private String to = null;
    private String from = null;
    private String charset = null;
    private String subject = null;
    private String body = null;

    public void sendMail() throws Exception{
        out("[to] " + this.to);
        out("[from] " + this.from);
        out("[charset] " + this.charset);
        out("[subject] " + this.subject);
        out("[body] " + this.body);
        out("この設定でsendMailメソッドを実行しましたが、メール送信は行っていません。");
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getCharset() {
        return this.charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
