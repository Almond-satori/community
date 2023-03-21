package com.community.Bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_user")
public class User {

    //数据库中的id
    private Integer id;

    private String password;

    private String email;

    private String name;

    private String token;

    //个性签名
    private String sign;

    @TableField("gmt_modified")
    private Long createTime;

    @TableField("gmt_create")
    private Long modifiedTime;
}
