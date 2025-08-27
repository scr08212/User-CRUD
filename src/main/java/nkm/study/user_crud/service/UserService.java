package nkm.study.user_crud.service;

import nkm.study.user_crud.domain.User;
import nkm.study.user_crud.repository.UserRepository;

import java.util.List;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user){
        return userRepository.save(user);
    }

    public User deleteUser(User user){
        userRepository.delete(user);
        return user;
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }
}