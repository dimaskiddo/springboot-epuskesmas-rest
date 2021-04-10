package co.id.telkom.epuskesmas.repository;

import co.id.telkom.epuskesmas.model.PoliModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoliRepository extends JpaRepository<PoliModel, Integer> {

    Iterable<PoliModel> findByIdPuskesmas(int idPuskesmas);
    Iterable<PoliModel> findByIdPuskesmasAndNamaContains(int idPuskesmas, String nama);
    Iterable<PoliModel> findByIdPuskesmasOrNamaContains(int idPuskesmas, String nama);

}
