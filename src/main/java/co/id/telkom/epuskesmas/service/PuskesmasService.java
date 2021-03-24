package co.id.telkom.epuskesmas.service;

import co.id.telkom.epuskesmas.repository.PuskesmasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PuskesmasService {

    private PuskesmasRepository puskesmasRepository;

    @Autowired
    public PuskesmasService(PuskesmasRepository puskesmasRepository) {
        this.puskesmasRepository = puskesmasRepository;
    }
}
