package nkm.study.user_crud.service;

import lombok.RequiredArgsConstructor;
import nkm.study.user_crud.domain.User;
import nkm.study.user_crud.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(User user){

        userRepository.findByEmail(user.getEmail())
            .ifPresent(x->{
                throw new IllegalStateException("이미 가입된 이메일입니다.");
            });

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);
        return user;
    }

    public User deleteUser(User user){
        userRepository.delete(user);
        return user;
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }
}