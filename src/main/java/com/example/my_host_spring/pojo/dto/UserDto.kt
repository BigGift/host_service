package com.example.my_host_spring.pojo.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty
import org.hibernate.validator.constraints.Length
import org.hibernate.validator.constraints.NotBlank


data class UserDto(
        var userId: Int? = null,
        @field:NotBlank(message = "username should not be blank")
        var username: String,
        @field:NotBlank(message = "password should not be blank")
        @field:Length(min = 6)
        var password: String,
        @field:Email
        var email: String
)