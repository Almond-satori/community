package com.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.community.Bean.UserKey;
import com.community.mapper.SecretKeyMapper;
import com.community.service.SecretKeyService;
import org.springframework.stereotype.Service;

@Service
public class SecretKeyServiceImpl extends ServiceImpl<SecretKeyMapper, UserKey> implements SecretKeyService {

}
