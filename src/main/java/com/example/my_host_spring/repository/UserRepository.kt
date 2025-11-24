package com.example.my_host_spring.repository

import com.example.my_host_spring.pojo.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<User,Int> {

//    override fun <S : User?> save(entity: S): S {
//        TODO("Not yet implemented")
//    }
}