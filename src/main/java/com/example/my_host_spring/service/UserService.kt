package com.example.my_host_spring.service

import com.example.my_host_spring.pojo.User
import com.example.my_host_spring.pojo.dto.UserDto
import com.example.my_host_spring.repository.UserRepository
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service //配置成spring的bean
class UserService :IUserService{

    @Autowired
    lateinit var userRepository:UserRepository



    override fun add(userDto: UserDto) {
        val user = User()
        BeanUtils.copyProperties(userDto,user)
        userRepository.save(user)
    }

    override fun getUser(userId: Int): User {
        return userRepository.findById(userId).orElseThrow {
            throw IllegalArgumentException("用户不存在，参数异常！")
        }
    }

    override fun edit(userDto: UserDto): User {
        val user = User()
        BeanUtils.copyProperties(userDto,user)
        return userRepository.save(user)
    }

    override fun delete(userId: Int) {
        return userRepository.deleteById(userId)
    }

}