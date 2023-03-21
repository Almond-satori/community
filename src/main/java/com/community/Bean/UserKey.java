package com.community.Bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName("t_key")
public class UserKey {

    private Integer id;

    private String secretKey;
}
