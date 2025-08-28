package nkm.study.user_crud.controller;

import jakarta.servlet.http.HttpSession;
import nkm.study.user_crud.domain.User;
import nkm.study.user_crud.domain.UserForm;
import nkm.study.user_crud.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @PostMapping("/")
    public String tryLogin(@RequestParam String email, @RequestParam String password, HttpSession session, Model model){
        try{
            User user = userService.validateLogin(email,password);
            session.setAttribute("user",user);
            return "redirect:/mypage/" + user.getId();
        } catch(Exception e){
            model.addAttribute("error", e.getMessage());
            return "home";
        }
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

    @GetMapping("/mypage/{id}")
    public String mypage(@PathVariable Long id, HttpSession session, Model model){
        User user = (User)session.getAttribute("user");

        if(user == null || !id.equals(user.getId())){
            return "redirect:/";
        }

        model.addAttribute("user", user);
        return "mypage";
    }

    @PostMapping("/mypage/{id}/update")
    public String updateUser(@PathVariable Long id, @RequestParam String name, @RequestParam String email,@RequestParam String password, @RequestParam String password_re, HttpSession session){
        User user = (User)session.getAttribute("user");
        if(user == null || !id.equals(user.getId())){
            return "redirect:/";
        }
        if(!password.equals(password_re)){
            return "redirect:/mypage/{id}";
        }

        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        userService.updateUser(user);

        return "redirect:/mypage/" + id;
    }

    @PostMapping("/mypage/{id}/delete")
    public String deleteUser(@PathVariable Long id, HttpSession session){
        User user = (User)session.getAttribute("user");
        if(user == null || !id.equals(user.getId())){
            return "redirect:/";
        }

        userService.deleteUser(user);
        session.invalidate();

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }

}