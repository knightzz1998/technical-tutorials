package cn.knightzz.email.common.email;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;

/**
 * @author 王天赐
 * @title: MailClientTest
 * @projectName technical-tutorials
 * @description:
 * @website <a href="https://knightzz.cn/">https://knightzz.cn/</a>
 * @github <a href="https://github.com/knightzz1998">https://github.com/knightzz1998</a>
 * @create: 2023-03-25 20:45
 */
@SpringBootTest
class MailClientTest {

    @Resource
    private MailClient client;

    @Resource
    private TemplateEngine templateEngine;

    @Test
    void sendMail() {

        String targetMail = "1029836827@qq.com";
        String subject = "测试";
        String content = "邮件测试内容";
        client.sendMail(targetMail, subject, content);
    }

    @Test
    void sendHtmlMail() {

        String targetMail = "1029836827@qq.com";
        String subject = "测试HTML";

        // 封装模板
        Context context = new Context();
        context.setVariable("username", "张三...");
        String content = templateEngine.process("/mail/mail-template.html", context);


        client.sendMail(targetMail, subject, content);
    }
}