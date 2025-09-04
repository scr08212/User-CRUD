package nkm.study.user_crud.service;

import lombok.RequiredArgsConstructor;
import nkm.study.user_crud.domain.entity.User;
import nkm.study.user_crud.domain.dto.UserDTO;
import nkm.study.user_crud.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Create
    @Transactional
    public User signUpUser(UserDTO userDTO){
        if(userRepository.existsByEmail(userDTO.getEmail())){
            throw new IllegalStateException("이미 가입된 이메일입니다.");
        }

        User user = User.from(userDTO, passwordEncoder);

        return userRepository.save(user);
    }

    // Read
    public User findById(Long id){
        return userRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("유저가 존재하지 않습니다."));
    }

    // Update
    @Transactional
    public void updateUser(Long userId, UserDTO userDTO) {
        User user = findById(userId);

        if(!userDTO.getEmail().isEmpty() && !user.getEmail().equals(userDTO.getEmail())){
            validateEmail(userDTO.getEmail());
        }

        String encodedPassword = null;
        if(userDTO.getPassword() != null &&  !userDTO.getPassword().isEmpty()){
            encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        }

        user.update(userDTO.getUsername(), userDTO.getEmail(), encodedPassword);
    }

    // Delete
    @Transactional
    public void deleteUser(Long userId){
        userRepository.deleteById(userId);
    }

    private void validateEmail(String email){
        userRepository.findByEmail(email)
                .ifPresent(x->{
                    throw new IllegalStateException("이미 가입된 이메일입니다.");
                });
    }


}