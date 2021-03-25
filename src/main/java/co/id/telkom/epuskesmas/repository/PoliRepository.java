package co.id.telkom.epuskesmas.repository;

import co.id.telkom.epuskesmas.model.PoliModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PoliRepository extends JpaRepository<PoliModel, Integer> {

    @Query(value = "SELECT * FROM poli WHERE nama = ?1 LIMIT 1", nativeQuery = true)
    Optional<PoliModel> getPoliByNama(String nama);

}
