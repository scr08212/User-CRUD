package nkm.study.user_crud.service;

import nkm.study.user_crud.domain.User;
import nkm.study.user_crud.repository.UserRepository;

import java.util.List;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long createUser(User user){

        userRepository.findByEmail(user.getEmail())
            .ifPresent(x->{
                throw new IllegalStateException("이미 가입된 이메일입니다.");
            });
        userRepository.save(user);
        return user.getId();
    }

    public User deleteUser(User user){
        userRepository.delete(user);
        return user;
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }
}