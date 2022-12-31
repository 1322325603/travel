package com.group_8.domain.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableName;


@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("tab_user")
public class User  {
    @TableId(value = "uid",type = IdType.AUTO)
    private Integer id;
    
    private String username;
    
    private String password;
    
    private String name;
    
    private Date birthday;
    
    private String sex;
    
    private String telephone;
    
    private String email;
    
    private String status;
    
    private String code;
}
