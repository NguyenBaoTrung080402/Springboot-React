package DSTA.Security.LoginSecurity.Repository;

import DSTA.Security.LoginSecurity.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query(value = "SELECT a FROM UserEntity a WHERE a.username = :username")
    UserEntity getAccountByUsername(String username);

    UserEntity getAccountById(Long id);
}
