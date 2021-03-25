package co.id.telkom.epuskesmas.repository;

import co.id.telkom.epuskesmas.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Integer> {

    @Query(value = "SELECT * FROM user WHERE no_hp = ?1 LIMIT 1", nativeQuery = true)
    Optional<UserModel> getUserByPhone(String phone);

}
