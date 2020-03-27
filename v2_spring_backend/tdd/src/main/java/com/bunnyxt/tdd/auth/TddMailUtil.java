package com.bunnyxt.tdd.auth;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class TddMailUtil {

    private static String mailFrom = "user@example.com";// 指明邮件的发件人
    private static String password_mailFrom = "yourPassw0rd";// 指明邮件的发件人登陆密码
    private static String mailTo = null;    // 指明邮件的收件人
    private static String mailTittle = null;// 邮件的标题
    private static String mailText = null;    // 邮件的文本内容
    private static String mail_host = "smtp.example.com";    // 邮件的服务器域名

    private static MimeMessage createSimpleMail(Session session, String mailfrom, String mailTo, String mailTittle,
                                               String mailText) throws Exception {
        // 创建邮件对象
        MimeMessage message = new MimeMessage(session);
        // 指明邮件的发件人
        message.setFrom(new InternetAddress(mailfrom));
        // 指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
        // 邮件的标题
        message.setSubject(mailTittle);
        // 邮件的文本内容
        message.setContent(mailText, "text/html;charset=UTF-8");
        // 返回创建好的邮件对象
        return message;
    }

    public static boolean sendCode(String email, String code) {
        mailTo = email;
        mailTittle = "天钿Daily - 注册验证码";
        mailText = "亲爱的用户：<br>" +
                "您正在使用本邮箱注册「天钿Daily」用户，验证码为：" + code + "，有效期为5分钟，请勿告知他人此验证码，若非本人操作请忽视<br>" +
                "by.bunnyxt";

        try {
            Properties prop = new Properties();
            prop.setProperty("mail.host", mail_host);
            prop.setProperty("mail.transport.protocol", "smtp");
            prop.setProperty("mail.smtp.auth", "true");

            // 使用JavaMail发送邮件的5个步骤

            // 1、创建session
            Session session = Session.getInstance(prop);
            // 开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
//            session.setDebug(true);
            // 2、通过session得到transport对象
            Transport ts = session.getTransport();
            // 3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
            ts.connect(mail_host, mailFrom, password_mailFrom);
            // 4、创建邮件
            Message message = createSimpleMail(session, mailFrom, mailTo, mailTittle, mailText);
            // 5、发送邮件
            ts.sendMessage(message, message.getAllRecipients());
            ts.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
