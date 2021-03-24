package co.id.telkom.epuskesmas.repository;

import co.id.telkom.epuskesmas.model.PoliModel;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface PoliRepository extends PagingAndSortingRepository<PoliModel, Integer> {

    Optional<PoliModel> getPoliByNama(String nama);

}
