package com.yozuru.domain.entity;

import java.util.Date;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (User)表实体类
 *
 * @author Yozuru
 * @since 2022-12-17 19:38:28
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("tab_user")
public class User  {
    
    private Integer uid;
    
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
