package nkm.study.user_crud.controller;

import nkm.study.user_crud.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final UserService userService;

    public HomeController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/")
    public String home(){
        return "home";
    }
}