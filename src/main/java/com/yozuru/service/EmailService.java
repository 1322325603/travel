package com.yozuru.service;

/**
 * @author Yozuru
 */

public interface EmailService {
    boolean sendActivationEmail(String email,String uid);
}
