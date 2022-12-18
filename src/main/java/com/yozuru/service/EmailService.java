package com.yozuru.service;

/**
 * @author Yozuru
 */

public interface EmailService {
    boolean sendActiveEmail(Integer uid,String name,String email);
}
