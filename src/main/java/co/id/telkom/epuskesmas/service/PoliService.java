package co.id.telkom.epuskesmas.service;

import co.id.telkom.epuskesmas.repository.PoliRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PoliService {

    private PoliRepository poliRepository;

    @Autowired
    public PoliService(PoliRepository poliRepository) {
        this.poliRepository = poliRepository;
    }
}
