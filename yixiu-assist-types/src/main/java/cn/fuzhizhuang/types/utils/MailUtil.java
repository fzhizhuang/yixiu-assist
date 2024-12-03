package cn.fuzhizhuang.types.utils;

import cn.hutool.extra.spring.SpringUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.util.Date;

/**
 * 邮箱工具类
 *
 * @author Fu.zhizhuang
 */
@Slf4j
public class MailUtil {

    private static JavaMailSender mailSender;
    private static MailProperties properties;

    static {
        MailUtil.mailSender = SpringUtil.getBean(JavaMailSender.class);
        MailUtil.properties = SpringUtil.getBean(MailProperties.class);
    }

    /**
     * 发送文本邮件
     *
     * @param to      邮箱
     * @param subject 主题
     * @param content 内容
     */
    public static void sendEmail(String to, String subject, String content) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setFrom(properties.getUsername());
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content);
            helper.setSentDate(new Date());
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("send email error", e);
            throw new RuntimeException(e);
        }
    }
}
