package mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import util.Traceable;

/**
 * メール送信クラス
 * @author sankame
 */
public class MailManager implements Traceable, Mail{

    private String to = null;
    private String from = null;
    private String charset = null;
    private String subject = null;
    private String body = null;

    /**
     * メール送信
     * @throws Exception
     */
    public void sendMail() throws Exception{

        Properties props = new Properties();
        Session session = Session.getInstance(props, null);
        Message msg = new MimeMessage(session);

        InternetAddress[] address = {new InternetAddress(this.to)};
        msg.setRecipients(Message.RecipientType.TO, address);
        msg.setFrom(new InternetAddress(this.from, this.from));
        msg.setSubject(this.subject);
        msg.setContent(this.body, "text/html; charset=" + this.charset);
        Transport.send(msg);

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
        return charset;
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
