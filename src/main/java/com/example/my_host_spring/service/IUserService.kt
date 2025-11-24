package com.example.my_host_spring.service

import com.example.my_host_spring.pojo.User
import com.example.my_host_spring.pojo.dto.UserDto

interface IUserService {
    abstract fun  add(userDto: UserDto)
    abstract fun getUser(userId: Int): User
    abstract fun edit(userDto: UserDto): User
    abstract fun delete(userId: Int)
}