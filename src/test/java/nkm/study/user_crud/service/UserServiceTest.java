package nkm.study.user_crud.service;

import jakarta.transaction.Transactional;
import nkm.study.user_crud.domain.User;
import nkm.study.user_crud.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.assertj.core.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @DisplayName("회원가입시 중복 이메일")
    @Test
    void signup_emailDuplicated() {
        // given
        User user1 = new User();
        user1.setName("user1");
        user1.setEmail("user1@gmail.com");
        user1.setPassword("password");
        userService.createUser(user1);

        User user2 = new User();
        user2.setName("user2");
        user2.setEmail("user1@gmail.com");
        user2.setPassword("password");

        // when
        IllegalStateException e = assertThrows(IllegalStateException.class, ()->{
            userService.createUser(user2);
        });

        // then
        Assertions.assertThat(e.getMessage()).isEqualTo("이미 가입된 이메일입니다.");
    }

    @DisplayName("비밀번호 암호화 테스트")
    @Test
    void signup_passwordEncoded() {
        // given
        User user1 = new User();
        user1.setName("user1");
        user1.setEmail("user1@gmail.com");
        user1.setPassword("password");

        // when
        User savedUser = userService.createUser(user1);

        // then
        Assertions.assertThat(savedUser.getPassword()).isNotEqualTo("password");
        Assertions.assertThat(savedUser.getPassword()).startsWith("$2a$");
    }

    @DisplayName("유저 단건 조회 테스트")
    @Test
    void findUserByEmail() {
        User user1 = new User();
        user1.setName("user1");
        user1.setEmail("user1@gmail.com");
        user1.setPassword("password");
        userService.createUser(user1);

        User savedUser = userRepository.findByEmail(user1.getEmail()).get();
        Assertions.assertThat(savedUser.getName()).isEqualTo(user1.getName());
    }

    @DisplayName("유저 정보 업데이트 테스트")
    @Test
    void updateUser() {
        // given
        User user1 = new User();
        user1.setName("user1");
        user1.setEmail("user1@gmail.com");
        user1.setPassword("password");
        userService.createUser(user1);

        // when
        user1.setName("user1 updated");
        userService.updateUser(user1);

        // then
        User savedUser = userRepository.findByEmail(user1.getEmail()).get();
        Assertions.assertThat(savedUser.getName()).isEqualTo(user1.getName());
    }

    @DisplayName("유저 삭제 테스트")
    @Test
    void deleteUser() {
        // given
        User user1 = new User();
        user1.setName("user1");
        user1.setEmail("user1@gmail.com");
        user1.setPassword("password");
        userService.createUser(user1);

        // when
        userService.deleteUser(user1);

        // then
        Assertions.assertThat(userRepository.findByEmail(user1.getEmail())).isEmpty();
    }
}