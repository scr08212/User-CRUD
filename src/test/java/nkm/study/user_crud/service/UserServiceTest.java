package nkm.study.user_crud.service;

import jakarta.transaction.Transactional;
import nkm.study.user_crud.domain.User;
import nkm.study.user_crud.domain.dto.UserDTO;
import nkm.study.user_crud.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.assertj.core.api.Assertions;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @DisplayName("로그인 비번 틀림")
    @Test
    void validateLogin_wrongPassword(){
        UserDTO user1 = new UserDTO();
        user1.setUsername("user1");
        user1.setEmail("user1@gmail.com");
        user1.setPassword("password");
        user1.setConfirmPassword("password");
        userService.signUpUser(user1);

        IllegalStateException e = assertThrows(IllegalStateException.class, ()->{
            validateLogin("user1@gmail.com", "password1");
        });

        Assertions.assertThat(e.getMessage()).isEqualTo("비밀번호가 틀립니다.");
    }

    @DisplayName("로그인 사용자 없음")
    @Test
    void validateLogin_wrongEmail(){
        UserDTO user1 = new UserDTO();
        user1.setUsername("user1");
        user1.setEmail("user2@gmail.com");
        user1.setPassword("password");
        userService.signUpUser(user1);

        IllegalStateException e = assertThrows(IllegalStateException.class, ()->{
            validateLogin("user1@gmail.com", "password");
        });

        Assertions.assertThat(e.getMessage()).isEqualTo("사용자를 찾을 수 없습니다.");
    }

    @DisplayName("회원가입시 중복 이메일")
    @Test
    void signup_emailDuplicated() {
        // given
        UserDTO user1 = new UserDTO();
        user1.setUsername("user1");
        user1.setEmail("user1@gmail.com");
        user1.setPassword("password");
        userService.signUpUser(user1);

        UserDTO user2 = new UserDTO();
        user2.setUsername("user2");
        user2.setEmail("user1@gmail.com");
        user2.setPassword("password");
        user2.setConfirmPassword("password");

        // when
        IllegalStateException e = assertThrows(IllegalStateException.class, ()->{
            userService.signUpUser(user2);
        });

        // then
        Assertions.assertThat(e.getMessage()).isEqualTo("이미 가입된 이메일입니다.");
    }

    @DisplayName("비밀번호 암호화 테스트")
    @Test
    void signup_passwordEncoded() {
        // given
        UserDTO user1 = new UserDTO();
        user1.setUsername("user1");
        user1.setEmail("user1@gmail.com");
        user1.setPassword("password");
        user1.setConfirmPassword("password");

        // when
        User savedUser = userService.signUpUser(user1);

        // then
        Assertions.assertThat(savedUser.getPassword()).isNotEqualTo("password");
        Assertions.assertThat(savedUser.getPassword()).startsWith("$2a$");
    }

    @DisplayName("유저 단건 조회 테스트")
    @Test
    void findUserByEmail() {
        UserDTO user1 = new UserDTO();
        user1.setUsername("user1");
        user1.setEmail("user1@gmail.com");
        user1.setPassword("password");
        user1.setConfirmPassword("password");
        userService.signUpUser(user1);

        User savedUser = userRepository.findByEmail(user1.getEmail()).get();
        Assertions.assertThat(savedUser.getUsername()).isEqualTo(user1.getUsername());
    }

    @DisplayName("유저 정보 업데이트 테스트")
    @Test
    void updateUser() {
        // given
        UserDTO user1 = new UserDTO();
        user1.setUsername("user1");
        user1.setEmail("user1@gmail.com");
        user1.setPassword("password");
        user1.setConfirmPassword("password");
        User user = userService.signUpUser(user1);

        // when
        user1.setUsername("user1 updated");

        userService.updateUser(user.getId(), user1);

        // then
        User savedUser = userRepository.findByEmail(user1.getEmail()).get();
        Assertions.assertThat(savedUser.getUsername()).isEqualTo(user1.getUsername());
    }

    @DisplayName("유저 삭제 테스트")
    @Test
    void deleteUser() {
        // given
        UserDTO user1 = new UserDTO();
        user1.setUsername("user1");
        user1.setEmail("user1@gmail.com");
        user1.setPassword("password");
        user1.setConfirmPassword("password");
        User user = userService.signUpUser(user1);

        // when
        userService.deleteUser(user.getId());

        // then
        Assertions.assertThat(userRepository.findByEmail(user1.getEmail())).isEmpty();
    }

    public User validateLogin(String email, String password) {
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(()->new IllegalStateException("사용자를 찾을 수 없습니다."));
        if(!passwordEncoder.matches(password, currentUser.getPassword())){
            throw new IllegalStateException("비밀번호가 틀립니다.");
        }

        return currentUser;
    }
}