package co.id.telkom.epuskesmas.repository;

import co.id.telkom.epuskesmas.model.PuskesmasModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PuskesmasRepository extends JpaRepository<PuskesmasModel, Integer> {

    Iterable<PuskesmasModel> findByNamaContains(String nama);

}
