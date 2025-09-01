package nkm.study.user_crud.service;

import lombok.RequiredArgsConstructor;
import nkm.study.user_crud.domain.User;
import nkm.study.user_crud.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User validateLogin(String email, String password) {

        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(()->new IllegalStateException("사용자를 찾을 수 없습니다."));

        if(!passwordEncoder.matches(password, currentUser.getPassword())){
            throw new IllegalStateException("비밀번호가 틀립니다.");
        }

        return currentUser;
    }

    public User updateUser(User user){
        User currentUser = userRepository.findById(user.getId())
                .orElseThrow(()->new IllegalStateException("사용자를 찾을 수 없습니다."));

        if(!currentUser.getEmail().equals(user.getEmail())){
            validateEmail(user.getEmail());
            currentUser.setEmail(user.getEmail());
        }

        if(!currentUser.getUsername().equals(user.getUsername())){
            currentUser.setUsername(user.getUsername());
        }

        if(!user.getPassword().isEmpty()){
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            currentUser.setPassword(encodedPassword);
        }

        return userRepository.save(currentUser);
    }

    public User deleteUser(User user){
        userRepository.delete(user);
        return user;
    }

    private void validateEmail(String email){
        userRepository.findByEmail(email)
                .ifPresent(x->{
                    throw new IllegalStateException("이미 가입된 이메일입니다.");
                });
    }
}