package co.id.telkom.epuskesmas.repository;

import co.id.telkom.epuskesmas.model.PuskesmasModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PuskesmasRepository extends JpaRepository<PuskesmasModel, Integer> {

    @Query(value = "SELECT * FROM puskesmas WHERE nama = ?1 LIMIT 1", nativeQuery = true)
    Optional<PuskesmasModel> getPuskesmasByNama(String nama);

}
