package mail;

/**
 * メールインターフェース
 * メール送信に必要な振る舞いを定義する。
 * @author sankame
 */
public interface Mail {

	public void sendMail() throws Exception;
	
	public String getTo();

	public void setTo(String to);

	public String getFrom();

	public void setFrom(String from);

	public String getCharset();

	public void setCharset(String charset);

	public String getSubject();

	public void setSubject(String subject);

	public String getBody();

	public void setBody(String body);

}
