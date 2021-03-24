package co.id.telkom.epuskesmas.repository;

import co.id.telkom.epuskesmas.model.PuskesmasModel;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface PuskesmasRepository extends PagingAndSortingRepository<PuskesmasModel, Integer> {

    Optional<PuskesmasModel> getPuskesmasByNama(String nama);

}
