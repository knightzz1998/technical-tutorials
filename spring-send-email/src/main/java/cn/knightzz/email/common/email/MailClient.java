package cn.knightzz.email.common.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author 王天赐
 * @title: MailClient
 * @projectName geek-zone-community
 * @description: 邮件客户端
 * @website <a href="https://knightzz.cn/">https://knightzz.cn/</a>
 * @github <a href="https://github.com/knightzz1998">https://github.com/knightzz1998</a>
 * @create: 2023-03-25 20:12
 */
@Component
public class MailClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailClient.class);

    @Resource
    JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sourceMail;

    /**
     * 发送邮件
     *
     * @param targetMail 目标邮箱
     * @param subject    邮件主题
     * @param content    邮件内容
     */
    public void sendMail(String targetMail, String subject, String content) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom(sourceMail);
            helper.setTo(targetMail);

            // 设置邮件主题和内容
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(helper.getMimeMessage());

        } catch (MessagingException e) {
            LOGGER.error("mail send error", e);
        }
    }
}
