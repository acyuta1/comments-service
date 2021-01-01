package com.acyuta.microservices.commentsservice.domain.proxy;

import com.acyuta.microservices.commentsservice.domain.dto.UserDto;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//@FeignClient(name = "user-service", url = "localhost:8000/api") #Since we use ribbon, we will talk to multiple instances.
//@FeignClient(name = "user-service") Now, don't connect directly, use zuul.
@FeignClient(name = "netflix-zuul-api-gateway-server")
@RibbonClient(name = "user-service")
public interface UserServicesProxy {

//    @GetMapping("/api/users/by-email") When using zuul, it will not be direct. http://localhost:8765/{service-name}/{uri}
    @GetMapping("/user-service/api/users/by-email")
    public UserDto getUserByEmail(@RequestParam("email") String email);
}
