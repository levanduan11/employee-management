package com.coding.web.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static com.coding.core.constant.AppConstant.ROLE_USER;
@RestController
@RequestMapping("api/v1")
public class HomeResource {
    @GetMapping
    String home(){
        return "Employee management API";
    }
    @PreAuthorize("hasRole('" + ROLE_USER + "')")
    @GetMapping("/only-user")
    String onlyUser() {
        return "Only Admin";
    }
}
