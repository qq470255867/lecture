package com.icycraft.league_lecture.service;

/**
 * 邮件服务接口
 * Created by ASUS on 2018/5/5
 *
 * @Authod Grey Wolf
 */
public interface MailService {
 
   
    /**
     * 发送带附件的邮件
     * @param to
     * @param subject
     * @param content
     * @param filePath
     */
    void sendAttachmentsMail(String mail,String subject,String content,String filePath);
 
}