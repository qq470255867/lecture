package com.icycraft.league_lecture.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 *
 * 邮件服务类
 * Created by ASUS on 2018/5/5
 *
 * @Authod Grey Wolf
 */
 
@Service("mailService")
public class MailServiceImpl implements MailService {
 
    @Autowired
    private JavaMailSender mailSender;



    @Value("${spring.mail.username}")
    private String from;


    /**
     * 发送带附件的邮件
     * @param subject 主题
     * @param content 内容
     * @param filePath 文件路径
     */
    @Override
    public void sendAttachmentsMail(String mail,String subject, String content, String filePath) {
        MimeMessage message=mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper=new MimeMessageHelper(message,true);
            helper.setFrom(from);
            helper.setTo(mail);
            helper.setSubject(subject);
            helper.setText(content);
            FileSystemResource file=new FileSystemResource(new File(filePath));
            helper.addAttachment(file.getFilename(),file);
            mailSender.send(message);
            System.out.println("带附件的邮件发送成功");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("发送带附件的邮件失败");
        }

    }
 
 
}