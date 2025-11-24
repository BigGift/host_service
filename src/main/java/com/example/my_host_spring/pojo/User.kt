package com.example.my_host_spring.pojo

import jakarta.persistence.*

@Table(name = "tb_user")
@Entity
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "user_id")
        var userId: Int? = null,
        @Column(name = "user_name")
        var username: String = "",
        @Column(name = "password")
        var password: String = "",
        @Column(name = "email")
        var email: String = ""
)