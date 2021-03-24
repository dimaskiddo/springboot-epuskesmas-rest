package co.id.telkom.epuskesmas.repository;

import co.id.telkom.epuskesmas.model.PuskesmasModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface PuskesmasRepository extends PagingAndSortingRepository<PuskesmasModel, Integer> {

    @Query(value = "SELECT * FROM puskesmas WHERE nama = ?1 LIMIT 1", nativeQuery = true)
    Optional<PuskesmasModel> getPuskesmasByNama(String nama);

}
