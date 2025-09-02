package nkm.study.user_crud.controller;

import nkm.study.user_crud.domain.User;
import nkm.study.user_crud.security.CustomUserDetails;
import nkm.study.user_crud.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(@AuthenticationPrincipal CustomUserDetails userDetails,
                       Model model){

        if(userDetails != null){
            User user = userService.findById(userDetails.getUser().getId());
            model.addAttribute("username",  user.getUsername());
        }
        return "home";
    }
}