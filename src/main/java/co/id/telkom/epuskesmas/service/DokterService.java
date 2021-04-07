package co.id.telkom.epuskesmas.service;

import co.id.telkom.epuskesmas.model.DokterModel;
import co.id.telkom.epuskesmas.repository.DokterRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DokterService {

    @Autowired
    private ModelMapper modelMapper;

    private DokterRepository dokterRepository;

    @Autowired
    public DokterService(DokterRepository dokterRepository) {
        this.dokterRepository = dokterRepository;
    }

    public DokterModel createDokter(DokterModel dokterModel) {
        return dokterRepository.save(dokterModel);
    }

    public Iterable<DokterModel> getAllDokter() {
        return dokterRepository.findAll();
    }

    public Iterable<DokterModel> getAllDokterByNama(String nama) {
        return dokterRepository.findByNamaContains(nama);
    }

    public Iterable<DokterModel> getAllDokterByIdPoli(int idPoli) {
        return dokterRepository.findByIdPoli(idPoli);
    }

    public Optional<DokterModel> getDokterById(int id) {
        return dokterRepository.findById(id);
    }

    public DokterModel updateDokterById(int id, DokterModel dokterModel) {
        Optional<DokterModel> currentDokter = dokterRepository.findById(id);

        if (currentDokter.isPresent()) {
            dokterModel.setId(id);
            return dokterRepository.save(dokterModel);
        }

        return null;
    }

    public DokterModel patchDokterById(int id, DokterModel dokterModel) {
        Optional<DokterModel> currentDokter = dokterRepository.findById(id);

        if (currentDokter.isPresent()) {
            DokterModel dataDokter = currentDokter.get();

            if (dokterModel.getIdPoli() > 0) {
                dataDokter.setIdPoli(dokterModel.getIdPoli());
            }

            if (dokterModel.getNama() != null && !dokterModel.getNama().isEmpty()) {
                dataDokter.setNama(dokterModel.getNama());
            }

            if (dokterModel.getKelamin() != null && !dokterModel.getKelamin().isEmpty()) {
                dataDokter.setKelamin(dokterModel.getKelamin());
            }

            if (dokterModel.getFoto() != null && !dokterModel.getFoto().isEmpty()) {
                dataDokter.setFoto(dokterModel.getFoto());
            }

            dataDokter.setId(id);
            return dokterRepository.save(dataDokter);
        }

        return null;
    }

    public boolean deleteDokterById(int id) {
        Optional<DokterModel> currentDokter = dokterRepository.findById(id);

        if (currentDokter.isPresent()) {
            dokterRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
