package co.id.telkom.epuskesmas.repository;

import co.id.telkom.epuskesmas.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Integer> {

    Iterable<UserModel> findByTeleponContains(String telepon);
    Optional<UserModel> findByTelepon(String telepon);

}
