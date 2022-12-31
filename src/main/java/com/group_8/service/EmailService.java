package com.group_8.service;



public interface EmailService {
    boolean sendActiveEmail(Integer uid,String name,String email);
}
