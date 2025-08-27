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
        validateEmail(user.getEmail());

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);
        return user;
    }

    public User updateUser(User user){
        User currentUser = userRepository.findById(user.getId())
                .orElseThrow(()->new IllegalStateException("사용자를 찾을 수 없습니다."));

        if(!currentUser.getEmail().equals(user.getEmail())){
            validateEmail(user.getEmail());
            currentUser.setEmail(user.getEmail());
        }

        if(!currentUser.getName().equals(user.getName())){
            currentUser.setName(user.getName());
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

    public List<User> findAll(){
        return userRepository.findAll();
    }

    private void validateEmail(String email){
        userRepository.findByEmail(email)
                .ifPresent(x->{
                    throw new IllegalStateException("이미 가입된 이메일입니다.");
                });
    }
}