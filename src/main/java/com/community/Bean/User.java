package com.community.Bean;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class User {

    private Integer id;

    private String accountId;

    private String name;

    private String token;

    @TableField("gmt_modified")
    private Long createTime;

    @TableField("gmt_create")
    private Long modifiedTime;
}
