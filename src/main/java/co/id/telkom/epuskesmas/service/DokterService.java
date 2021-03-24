package co.id.telkom.epuskesmas.service;

import co.id.telkom.epuskesmas.repository.DokterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DokterService {

    private DokterRepository dokterRepository;

    @Autowired
    public DokterService(DokterRepository dokterRepository) {
        this.dokterRepository = dokterRepository;
    }
}
