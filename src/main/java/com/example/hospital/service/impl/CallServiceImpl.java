package com.example.hospital.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.hospital.entity.domain.Call;
import com.example.hospital.mapper.CallMapper;
import com.example.hospital.service.CallService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @author kirit
* @description 针对表【call】的数据库操作Service实现
* @createDate 2023-07-01 09:56:10
*/
@Transactional
@Service
public class CallServiceImpl extends ServiceImpl<CallMapper, Call>
    implements CallService {

}




