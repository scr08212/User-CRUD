package nkm.study.user_crud.service;

import nkm.study.user_crud.domain.User;
import nkm.study.user_crud.domain.dto.UserDTO;
import nkm.study.user_crud.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SignUpService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public SignUpService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signUpUser(UserDTO userDTO){
        if(userRepository.existsByEmail(userDTO.getEmail())){
            throw new IllegalStateException("이미 가입된 이메일입니다.");
        }

        User user = new User();

        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole("ROLE_USER");

        userRepository.save(user);

        return user;
    }
}
