package com.example.my_host_spring.pojo

import org.springframework.boot.autoconfigure.graphql.GraphQlProperties.Http
import org.springframework.http.HttpStatus

class ResponseMessage(
        var code: Int,
        var message:String,
        var data:Any?
) {
    companion object{
        fun success(data: Any?):ResponseMessage{
            return ResponseMessage(
                    code = HttpStatus.OK.value(),
                    message = "success!",
                    data = data
            )
        }
    }
}