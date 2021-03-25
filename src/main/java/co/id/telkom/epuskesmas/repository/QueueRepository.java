package co.id.telkom.epuskesmas.repository;

import co.id.telkom.epuskesmas.model.QueueModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QueueRepository extends JpaRepository<QueueModel, Integer> {

    Iterable<QueueModel> getQueueByIdUser(int id);
    Iterable<QueueModel> getQueueByIdPoli(int id);
    Iterable<QueueModel> getQueueByIdDokter(int id);

}
