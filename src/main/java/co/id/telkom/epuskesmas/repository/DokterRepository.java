package co.id.telkom.epuskesmas.repository;

import co.id.telkom.epuskesmas.model.DokterModel;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DokterRepository extends PagingAndSortingRepository<DokterModel, Integer> {
    
}
