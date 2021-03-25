package co.id.telkom.epuskesmas.repository;

import co.id.telkom.epuskesmas.model.PoliModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoliRepository extends JpaRepository<PoliModel, Integer> {

    Iterable<PoliModel> findByNamaContains(String nama);

}
