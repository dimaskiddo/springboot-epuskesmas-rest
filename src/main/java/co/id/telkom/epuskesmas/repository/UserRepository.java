package co.id.telkom.epuskesmas.repository;

import co.id.telkom.epuskesmas.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Integer> {

    Optional<UserModel> findByTelepon(String telepon);

    Iterable<UserModel> findByTeleponContains(String telepon);
    Iterable<UserModel> findByNamaContains(String nama);

    Iterable<UserModel> findByTeleponContainsAndNamaContains(String telepon, String nama);

}
