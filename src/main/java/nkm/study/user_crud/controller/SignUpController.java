package nkm.study.user_crud.controller;

import nkm.study.user_crud.domain.User;
import nkm.study.user_crud.domain.dto.UserDTO;
import nkm.study.user_crud.service.SignUpService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SignUpController {

    private SignUpService signUpService;

    SignUpController(SignUpService signUpService) {
        this.signUpService = signUpService;
    }

    @GetMapping("/signup")
    public String signUp(){
        return "signup";
    }

    @PostMapping("/signupProc")
    public String signUpProc(UserDTO userDTO, Model model){
        if(!userDTO.getPassword().equals(userDTO.getConfirmPassword())){
            model.addAttribute("error", "비밀번호가 틀림");
            return "signup";
        }

        User result = signUpService.signUpUser(userDTO);
        if(result == null)
            return "signup";
        return "redirect:/home";
    }

}
