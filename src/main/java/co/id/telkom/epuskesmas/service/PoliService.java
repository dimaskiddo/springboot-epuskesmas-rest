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

    public PoliService(PoliRepository poliRepository) {
        this.poliRepository = poliRepository;
    }

    public PoliModel createPoli(PoliModel poliModel) {
        return poliRepository.save(poliModel);
    }

    public Iterable<PoliModel> getAllPoli() {
        return poliRepository.findAll();
    }

    public Iterable<PoliModel> getAllPoliBySearch(int idPuskesmas, String nama) {
        if (idPuskesmas > 0 && !nama.isEmpty()) {
            return poliRepository.findByIdPuskesmasAndNamaContains(idPuskesmas, nama);
        } else if (idPuskesmas > 0) {
            return poliRepository.findByIdPuskesmas(idPuskesmas);
        }

        return poliRepository.findByIdPuskesmasOrNamaContains(idPuskesmas, nama);
    }

    public Optional<PoliModel> getPoliById(int id) {
        return poliRepository.findById(id);
    }

    public PoliModel updatePoliById(int id, PoliModel poliModel) {
        Optional<PoliModel> currentPoli = poliRepository.findById(id);

        if (currentPoli.isPresent()) {
            poliModel.setId(id);
            return poliRepository.save(poliModel);
        }

        return null;
    }

    public PoliModel patchPoliById(int id, PoliModel poliModel) {
        Optional<PoliModel> currentPoli = poliRepository.findById(id);

        if (currentPoli.isPresent()) {
            PoliModel dataPoli = currentPoli.get();

            if (poliModel.getIdPuskesmas() > 0) {
                dataPoli.setIdPuskesmas(poliModel.getIdPuskesmas());
            }

            if (poliModel.getNama() != null && !poliModel.getNama().isEmpty()) {
                dataPoli.setNama(poliModel.getNama());
            }

            if (poliModel.getWaktuBuka() != null) {
                dataPoli.setWaktuBuka(poliModel.getWaktuBuka());
            }

            if (poliModel.getWaktuTutup() != null) {
                dataPoli.setWaktuTutup(poliModel.getWaktuTutup());
            }

            dataPoli.setId(id);
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
