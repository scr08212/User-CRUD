package nkm.study.user_crud.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserForm {
    private String name;
    private String email;
    private String password;
    private String confirmPassword;
}