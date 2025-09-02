package nkm.study.user_crud.controller;

import nkm.study.user_crud.domain.dto.UserDTO;
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

    @GetMapping("/mypage")
    public String mypage(@AuthenticationPrincipal CustomUserDetails userDetails,
                         Model model){

        User user = userService.findById(userDetails.getUser().getId());
        model.addAttribute("user", user);
        return "mypage";
    }

    @PostMapping("/mypage/update")
    public String updateUser(@AuthenticationPrincipal CustomUserDetails userDetails,
                             UserDTO userDTO,
                             Model model){

        User user = userDetails.getUser();
        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            model.addAttribute("error", "비밀번호가 같지 않습니다");
            return "mypage";
        }

        userService.updateUser(user, userDTO);

        return "redirect:/mypage";
    }

    @PostMapping("/mypage/delete")
    public String deleteUser(@AuthenticationPrincipal CustomUserDetails userDetails){

        User user = userDetails.getUser();
        userService.deleteUser(user);

        return "redirect:/logout";
    }
}