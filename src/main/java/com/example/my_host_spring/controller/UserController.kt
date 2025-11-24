package com.example.my_host_spring.controller

import com.example.my_host_spring.pojo.ResponseMessage
import com.example.my_host_spring.pojo.dto.UserDto
import com.example.my_host_spring.service.IUserService
import com.example.my_host_spring.service.UserService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController  //接口返回对象，转换成json
@RequestMapping("/user")  //localhost:8080/user/**
class UserController {

    @Autowired
    lateinit var userService:IUserService

    //REST
    //add
    @PostMapping
    fun add(@Validated @RequestBody userDto: UserDto):ResponseMessage{
        userService.add(userDto)
        return ResponseMessage.success(userDto)
    }

    //query
    @GetMapping("/{userId}")
    fun get(@PathVariable userId:Int):ResponseMessage{
        val newUser =userService.getUser(userId)
        return ResponseMessage.success(newUser)
    }

    //change
    @PutMapping
    fun edit(@Validated @RequestBody userDto: UserDto):ResponseMessage{
        val newUser =userService.edit(userDto)
        return ResponseMessage.success(newUser)
    }

    //Delete
    @DeleteMapping("/{userId}")
    fun delete(@PathVariable userId:Int):ResponseMessage{
        userService.delete(userId)
        return ResponseMessage.success(null)
    }
}