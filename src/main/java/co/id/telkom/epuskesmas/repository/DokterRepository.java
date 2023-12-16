package co.id.telkom.epuskesmas.repository;

import co.id.telkom.epuskesmas.model.DokterModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DokterRepository extends JpaRepository<DokterModel, Integer> {

    Iterable<DokterModel> findByIdPoli(int idPoli);
    Iterable<DokterModel> findByIdPoliAndNamaContains(int idPoli, String nama);
    Iterable<DokterModel> findByIdPoliOrNamaContains(int idPoli, String nama);

}
