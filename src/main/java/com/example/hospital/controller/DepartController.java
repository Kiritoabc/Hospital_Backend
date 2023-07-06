package com.example.hospital.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.hospital.entity.domain.Depart;
import com.example.hospital.entity.domain.Doctor;
import com.example.hospital.entity.domain.Room;
import com.example.hospital.entity.result.Result;
import com.example.hospital.mapper.DepartMapper;
import com.example.hospital.mapper.RoomMapper;
import com.example.hospital.service.DepartService;
import com.example.hospital.service.DoctorService;
import com.example.hospital.service.RoomService;
import com.example.hospital.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ?
 * @since 2023-07-01
 */
@RestController
@RequestMapping("/depart")
@CrossOrigin
public class DepartController {

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private DepartService iDepartService;

    @Autowired
    private DepartMapper departMapper;

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private UserService userService;

    @GetMapping("list")
    public Result All(
            @RequestParam(name = "searchName", required = false) String searchName,
            @RequestParam(name = "pageNum", required = false) Integer pageNum,
            @RequestParam(name = "pageSize", required = false)Integer pageSize,
            @RequestParam(name = "departId", required = false)Integer departId){
        Map<String, Object> data = iDepartService.all(searchName,pageNum,pageSize,departId);
        if (data != null) {
            return Result.ok().data(data);
        }
        return Result.error().message("获取失败");
    }

    @PostMapping("/delete")
    @SaCheckPermission(value = {"admin"},mode = SaMode.OR)
    public Result Del(@RequestParam(name = "id") Integer id){

        LambdaQueryWrapper<Depart> wrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<Room> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<Doctor> lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
        // 事务管理
        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(transactionDefinition);
        try {
            wrapper.eq(Depart::getDepartId,id);
            lambdaQueryWrapper1.eq(Doctor::getDepartId,id);
            lambdaQueryWrapper.eq(Room::getDepartId,id);
            List<Doctor> list = doctorService.list(lambdaQueryWrapper1);
            for (Doctor doctor : list) {
                // 删除用户表
                userService.deleteByPhone(doctor.getPhone());
            }
            // 删除医生表
            doctorService.remove(lambdaQueryWrapper1);
            // 删除诊室
            roomService.remove(lambdaQueryWrapper);
            // 删除科室
            iDepartService.remove(wrapper);
            transactionManager.commit(status);
            return Result.ok().message("删除成功");
        } catch (Exception e) {
            transactionManager.rollback(status);
            return Result.error().message("删除失败");
        }
    }

    @SaCheckPermission(value = {"admin"},mode = SaMode.OR)
    @PostMapping("/add")
    public Result Add(@RequestBody Depart dto){
        boolean save = iDepartService.save(dto);
        if(save){
            return Result.ok().message("增加成功");
        }
        return Result.error().message("增加失败");
    }

    @SaCheckPermission(value = {"admin"})
    @PostMapping("/update")
    public Result Update(@RequestBody Depart depart){
        LambdaQueryWrapper<Depart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Depart::getDepartId,depart.getDepartId());
        boolean update = iDepartService.update(depart,wrapper);
        if(update){
            return Result.ok().message("修改成功");
        }
        return Result.error().message("修改失败");
    }


    @GetMapping("/departId")
    public Result idList(){
        List<Integer> departIds = departMapper.selectIdList();
        return Result.ok().data("departIds",departIds);
    }
}
