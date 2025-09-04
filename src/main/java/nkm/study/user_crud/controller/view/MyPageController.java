package nkm.study.user_crud.controller.view;

import jakarta.validation.Valid;
import nkm.study.user_crud.domain.dto.UserDTO;
import nkm.study.user_crud.security.CustomUserDetails;
import nkm.study.user_crud.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MyPageController {

    UserService userService;

    public MyPageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/mypage")
    public String mypage(@AuthenticationPrincipal CustomUserDetails userDetails,
                         Model model){

        model.addAttribute("user", userDetails.getUser());
        return "mypage";
    }

    @PostMapping("/mypage/update")
    public String updateUser(@AuthenticationPrincipal CustomUserDetails userDetails,
                             @Valid UserDTO userDTO,
                             BindingResult bindingResult,
                             Model model){

        if(bindingResult.hasErrors()){
            return "mypage";
        }
        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            model.addAttribute("error", "비밀번호가 같지 않습니다");
            return "mypage";
        }

        userService.updateUser(userDetails.getUser().getId(), userDTO);

        return "mypage";
    }

    @PostMapping("/mypage/delete")
    public String deleteUser(@AuthenticationPrincipal CustomUserDetails userDetails){

        userService.deleteUser(userDetails.getUser().getId());
        return "redirect:/logout";
    }
}