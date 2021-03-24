package co.id.telkom.epuskesmas.repository;

import co.id.telkom.epuskesmas.model.QueueModel;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface QueueRepository extends PagingAndSortingRepository<QueueModel, Integer> {

    Iterable<QueueModel> getQueueByIdUser(int id);
    Iterable<QueueModel> getQueueByIdPoli(int id);
    Iterable<QueueModel> getQueueByIdDokter(int id);

}
