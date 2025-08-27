package nkm.study.user_crud.controller;

import nkm.study.user_crud.domain.User;
import nkm.study.user_crud.domain.UserForm;
import nkm.study.user_crud.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("/signup")
    public String signup(){
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(UserForm user, Model model){
        if(!user.getPassword().equals(user.getConfirmPassword())){
            model.addAttribute("error", "비밀번호가 틀림");
            return "signup";
        }

        User newUser = new User();
        newUser.setName(user.getName());
        newUser.setPassword(user.getPassword());
        newUser.setEmail(user.getEmail());
        userService.createUser(newUser);
        return "redirect:/";
    }
}