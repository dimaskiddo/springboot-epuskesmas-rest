package co.id.telkom.epuskesmas.service;

import co.id.telkom.epuskesmas.model.PoliModel;
import co.id.telkom.epuskesmas.repository.PoliRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PoliService {

    @Autowired
    private ModelMapper modelMapper;

    private PoliRepository poliRepository;

    @Autowired
    public PoliService(PoliRepository poliRepository) {
        this.poliRepository = poliRepository;
    }

    public PoliModel createPoli(PoliModel poliModel) {
        return poliRepository.save(poliModel);
    }

    public Iterable<PoliModel> getPoli() {
        return poliRepository.findAll();
    }

    public Optional<PoliModel> getPoliById(int id) {
        return poliRepository.findById(id);
    }

    public Optional<PoliModel> getPoliByNama(String nama) {
        return poliRepository.getPoliByNama(nama);
    }

    public PoliModel updatePoli(int id, PoliModel poliModel) {
        Optional<PoliModel> currentPoli = poliRepository.findById(id);

        if (currentPoli.isPresent()) {
            return poliRepository.save(poliModel);
        }

        return null;
    }

    public PoliModel patchPoli(int id, PoliModel poliModel) {
        Optional<PoliModel> currentPoli = poliRepository.findById(id);

        if (currentPoli.isPresent()) {
            PoliModel dataPoli = currentPoli.get();

            if (poliModel.getIdPuskesmas() != null && poliModel.getIdPuskesmas() != 0) {
                dataPoli.setIdPuskesmas(poliModel.getIdPuskesmas());
            }

            if (poliModel.getNama() != null && poliModel.getNama().isEmpty()) {
                dataPoli.setNama(poliModel.getNama());
            }

            if (poliModel.getWaktuBuka() != null) {
                dataPoli.setWaktuBuka(poliModel.getWaktuBuka());
            }

            if (poliModel.getWaktuTutup() != null) {
                dataPoli.setWaktuTutup(poliModel.getWaktuTutup());
            }

            return poliRepository.save(dataPoli);
        }

        return null;
    }

    public boolean deletePoliById(int id) {
        Optional<PoliModel> currentPoli = poliRepository.findById(id);

        if (currentPoli.isPresent()) {
            poliRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
