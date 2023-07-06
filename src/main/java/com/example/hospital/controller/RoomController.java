package com.example.hospital.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.hospital.entity.domain.Room;
import com.example.hospital.entity.result.Result;
import com.example.hospital.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/room")
@CrossOrigin
public class RoomController {

    @Autowired
    private RoomService iRoomService;


    @GetMapping("list")
    public Result All(
            @RequestParam(name = "searchName", required = false) String searchName,
            @RequestParam(name = "pageNum", required = false) Integer pageNum,
            @RequestParam(name = "pageSize",required = false) Integer pageSize,
            @RequestParam(name = "roomId", required = false) Integer roomId,
            @RequestParam(name = "departId", required = false)Integer departId){
        Map<String, Object> data = iRoomService.all(searchName,pageNum,pageSize,roomId,departId);
        if (data != null) {
            return Result.ok().data(data);
        }
        return Result.error().message("获取失败");
    }

    @SaCheckPermission(value = {"admin"},mode = SaMode.OR)
    @PostMapping("/delete")
    public Result Del(@RequestParam(name = "id") Integer id){

        LambdaQueryWrapper<Room> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Room::getRoomId,id);
        boolean isDel = iRoomService.remove(wrapper);
        if (isDel) {
            return Result.ok().message("删除成功");
        }
        return Result.error().message("删除失败");
    }

    @SaCheckPermission(value = {"admin"},mode = SaMode.OR)
    @PostMapping("/add")
    public Result Add(@RequestBody Room room){
        boolean save = iRoomService.save(room);
        if(save){
            return Result.ok().message("增加成功");
        }
        return Result.error().message("增加失败");
    }

    @SaCheckPermission(value = {"admin"},mode = SaMode.OR)
    @PostMapping("/update")
    public Result Update(@RequestBody Room room){
        LambdaQueryWrapper<Room> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Room::getRoomId,room.getRoomId());
        boolean update = iRoomService.update(room,wrapper);
        if(update){
            return Result.ok().message("修改成功");
        }
        return Result.error().message("修改失败");
    }
}
