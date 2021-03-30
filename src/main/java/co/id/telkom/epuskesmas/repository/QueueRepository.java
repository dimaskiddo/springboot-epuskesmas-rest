package co.id.telkom.epuskesmas.repository;

import co.id.telkom.epuskesmas.model.QueueModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QueueRepository extends JpaRepository<QueueModel, Integer> {

    Iterable<QueueModel> findByIdUser(int id);
    Iterable<QueueModel> findByIdPoli(int id);
    Iterable<QueueModel> findByIdDokter(int id);
    Iterable<QueueModel> findByNoAntrian(int noAntrian);

}
