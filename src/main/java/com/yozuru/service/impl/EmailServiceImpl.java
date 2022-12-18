package com.yozuru.service.impl;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.yozuru.domain.constant.EmailConstant;
import com.yozuru.domain.constant.RedisConstant;
import com.yozuru.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.UUID;

/**
 * @author Yozuru
 */
@Service
public class EmailServiceImpl implements EmailService {

    @CreateCache(area = "emailCode", name = RedisConstant.EMAIL_CODE_KEY_PREFIX,cacheType = CacheType.REMOTE)
    private Cache<Integer, String> emailCodeCache;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean sendActiveEmail(Integer uid, String name, String email) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        String code = passwordEncoder.encode(UUID.randomUUID().toString());
        String link = getActivationEmailLink(uid,code);
        String content = getActivationEmailContent(name,link);
        try {
            helper.setFrom(EmailConstant.SENDER_EMAIL);
            helper.setTo(email);
            helper.setSubject(EmailConstant.ACTIVATION_EMAIL_SUBJECT);
            helper.setText(content,true);
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            return false;
        }
        emailCodeCache.put(uid,code);
        return true;
    }

    private String getActivationEmailContent(String name,String link) {
        StringBuilder sb = new StringBuilder();
        sb.append(EmailConstant.ACTIVATION_EMAIL_CONTENT_PRE_NAME)
                .append(name)
                .append(EmailConstant.ACTIVATION_EMAIL_CONTENT_PRE_LINK)
                .append(link)
                .append(EmailConstant.ACTIVATION_EMAIL_CONTENT_FINAL);
        return sb.toString();
    }

    private String getActivationEmailLink(Integer uid,String code) {
        return EmailConstant.ACTIVATION_LINK_PRE + code+"&uid="+uid;
    }
}
