package DSTA.Security.LoginSecurity.Repository;

import DSTA.Security.LoginSecurity.Entity.Shoes;
import DSTA.Security.LoginSecurity.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoesRepository extends JpaRepository<Shoes, Long> {
    @Query(value = "SELECT a FROM Shoes a WHERE a.nameProduct = :nameProduct")
    Optional<Shoes> findByName(String nameProduct);
    @Query(value = "SELECT a FROM Shoes a WHERE a.id = :id")
    Shoes getShoesById(Long id);
}
