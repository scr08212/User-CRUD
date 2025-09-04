package nkm.study.user_crud.controller.view;

import jakarta.validation.Valid;
import nkm.study.user_crud.domain.dto.UserDTO;
import nkm.study.user_crud.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SignUpController {

    private final UserService userService;

    SignUpController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String signUp(Model model){
        model.addAttribute("userDTO", new UserDTO());
        return "signup";
    }

    @PostMapping("/signup/process")
    public String signUpProc(@Valid UserDTO userDTO,
                             BindingResult bindingResult,
                             Model model){

        if(bindingResult.hasErrors()){
            return "signup";
        }

        if(!userDTO.getPassword().equals(userDTO.getConfirmPassword())){
            model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
            return "signup";
        }

        try{
            userService.signUpUser(userDTO);
        } catch(IllegalStateException e){
            model.addAttribute("error",e.getMessage());
            return "signup";
        }

        return "redirect:/home";
    }
}