package nkm.study.user_crud;

import nkm.study.user_crud.repository.UserRepository;
import nkm.study.user_crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig{
    private final UserRepository userRepository;

    @Autowired
    public SpringConfig(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Bean
    public UserService userService(){
        return new UserService(userRepository);
    }
}