package co.id.telkom.epuskesmas.repository;

import co.id.telkom.epuskesmas.model.QueueModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QueueRepository extends JpaRepository<QueueModel, Integer> {

    Iterable<QueueModel> findByIdUser(int idUser);
    Iterable<QueueModel> findByIdPoli(int idPoli);
    Iterable<QueueModel> findByIdDokter(int idDokter);

    Iterable<QueueModel> findByIdUserAndIdPoli(int idUser, int idPoli);
    Iterable<QueueModel> findByIdUserAndIdDokter(int idUser, int idDokter);
    Iterable<QueueModel> findByIdPoliAndIdDokter(int idPoli, int idDokter);

    Iterable<QueueModel> findByIdUserAndIdPoliAndIdDokter(int idUser, int idPoli, int idDokter);

}
