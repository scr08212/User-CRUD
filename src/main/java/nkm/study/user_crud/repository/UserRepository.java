package nkm.study.user_crud.repository;

import nkm.study.user_crud.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>
{
}