package nkm.study.user_crud.controller;

import nkm.study.user_crud.security.CustomUserDetails;
import nkm.study.user_crud.domain.User;
import nkm.study.user_crud.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MyPageController {

    UserService userService;

    public MyPageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/mypage/{id}")
    public String mypage(@PathVariable Long id,
                         @AuthenticationPrincipal CustomUserDetails userDetails,
                         Model model){

        User user = userDetails.getUser();

        if(!id.equals(user.getId())){
            return "redirect:/home";
        }

        model.addAttribute("user", user);
        return "mypage";
    }

    @PostMapping("/mypage/{id}/update")
    public String updateUser(@PathVariable Long id,
                             @AuthenticationPrincipal CustomUserDetails userDetails,
                             @RequestParam String username,
                             @RequestParam String email,
                             @RequestParam String password,
                             @RequestParam String password_re){

        User user = userDetails.getUser();

        if(!id.equals(user.getId())){
            return "redirect:/home";
        }
        if(!password.equals(password_re)){
            return "redirect:/mypage/{id}";
        }

        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        userService.updateUser(user);

        return "redirect:/mypage/" + id;
    }

    @PostMapping("/mypage/{id}/delete")
    public String deleteUser(@PathVariable Long id,
                             @AuthenticationPrincipal CustomUserDetails userDetails){

        User user = userDetails.getUser();
        if(!id.equals(user.getId())){
            return "redirect:/home";
        }

        userService.deleteUser(user);

        return "redirect:/logout";
    }
}