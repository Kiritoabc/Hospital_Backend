package com.example.hospital.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.hospital.entity.domain.Depart;
import com.example.hospital.entity.domain.Doctor;
import com.example.hospital.entity.domain.Room;
import com.example.hospital.entity.domain.User;
import com.example.hospital.entity.dto.OptionItems;
import com.example.hospital.entity.dto.RegisterDTO;
import com.example.hospital.entity.dto.RolesValue;
import com.example.hospital.entity.result.Result;
import com.example.hospital.mapper.DepartMapper;
import com.example.hospital.mapper.DoctorMapper;
import com.example.hospital.mapper.RoomMapper;
import com.example.hospital.service.DoctorService;
import com.example.hospital.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private UserService userService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private DepartMapper departMapper;

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private DoctorMapper doctorMapper;

    @PostMapping("/login")
    public Result Login(@RequestBody User user){
        if(userService.login(user) != null) {
            // 第二步：根据账号id，进行登录
            StpUtil.login(userService.login(user));
            String tokenValue = StpUtil.getTokenValue();
//            StpUtil.checkRole();
            return Result.ok().data("token",tokenValue);
        }
        return Result.error().message("登录失败");
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @PostMapping("/register")
    public Result register(@RequestBody RegisterDTO user){
        // 事务管理
        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(transactionDefinition);
        try {
            Result result = userService.register(user);
            if (result.getSuccess()) {
                doctorService.register(user);
            } else {
                throw new Exception("注册失败");
            }
            transactionManager.commit(status);
            return result;
        } catch (Exception e) {
            transactionManager.rollback(status);
            return Result.error().message(e.getMessage());
        }
    }

    @SaCheckLogin
    @GetMapping("/info")
    public Result Info(@RequestParam(name = "token",required = false) String tokenValue){
        Map<String, Object> data = doctorService.info(StpUtil.getLoginId());
        if (data != null) {
            return Result.ok().data(data);
        }
        return Result.error().message("获取失败");
    }

    /**
     * 更换头像
     * @param file
     * @return
     */
    @PostMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam("file") MultipartFile file ){
        return doctorService.updateAvatar(file);
    }

    @GetMapping("/list")
    public Result Chart(){
        Map<String, Object> data = new HashMap<>();
        List<OptionItems> items = new ArrayList<>();
        List<Depart> integers = departMapper.selectList(null);
        for (int i = 0; i < integers.size(); i++) {
            LambdaQueryWrapper<Room> wrapper = new LambdaQueryWrapper<>();
            String departName = integers.get(i).getDepartName();
            wrapper.eq(Room::getDepartId, integers.get(i).getDepartId());
            Long roomNum = roomMapper.selectCount(wrapper);
            OptionItems optionItems = new OptionItems();
            optionItems.setName(departName);
            optionItems.setValue(roomNum);
            items.add(optionItems);
        }

        List<OptionItems> items1 = new ArrayList<>();
        List<Room> integers1 = roomMapper.selectList(null);
        for (int i = 0; i < integers1.size(); i++) {
            LambdaQueryWrapper<Doctor> wrapper1 = new LambdaQueryWrapper<>();
            String roomName = integers1.get(i).getRoomName();
            wrapper1.eq(Doctor::getDepartId,integers1.get(i).getRoomId());
            Long doctorNum = doctorMapper.selectCount(wrapper1);
            OptionItems optionItems = new OptionItems();
            optionItems.setName(roomName);
            optionItems.setValue(doctorNum);
            items1.add(optionItems);
        }

        List<OptionItems> items2 = new ArrayList<>();
        RolesValue roles = doctorMapper.selectCountRoles();

         items2.add(new OptionItems("医生",roles.getDoctor()));
         items2.add(new OptionItems("管理员",roles.getAdmin()));
         items2.add(new OptionItems("医生兼管理员",roles.getDoctorAndAdmin()));


        data.put("data",items);
        data.put("data1",items1);
        data.put("data2",items2);
        return Result.ok().data(data);
    }

}
