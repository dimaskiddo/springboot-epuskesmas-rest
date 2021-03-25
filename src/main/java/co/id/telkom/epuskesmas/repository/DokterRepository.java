package co.id.telkom.epuskesmas.repository;

import co.id.telkom.epuskesmas.model.DokterModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DokterRepository extends JpaRepository<DokterModel, Integer> {
    
}
