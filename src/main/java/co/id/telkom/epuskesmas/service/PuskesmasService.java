package co.id.telkom.epuskesmas.service;

import co.id.telkom.epuskesmas.model.PuskesmasModel;
import co.id.telkom.epuskesmas.repository.PuskesmasRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PuskesmasService {

    @Autowired
    private ModelMapper modelMapper;

    private PuskesmasRepository puskesmasRepository;

    public PuskesmasService(PuskesmasRepository puskesmasRepository) {
        this.puskesmasRepository = puskesmasRepository;
    }

    public PuskesmasModel createPuskesmas(PuskesmasModel puskesmasModel) {
        return puskesmasRepository.save(puskesmasModel);
    }

    public Iterable<PuskesmasModel> getAllPuskesmas() {
        return puskesmasRepository.findAll();
    }

    public Iterable<PuskesmasModel> getAllPuskesmasBySearch(String nama) {
        return puskesmasRepository.findByNamaContains(nama);
    }

    public Optional<PuskesmasModel> getPuskesmasById(int id) {
        return puskesmasRepository.findById(id);
    }

    public PuskesmasModel updatePuskesmasById(int id, PuskesmasModel puskesmasModel) {
        Optional<PuskesmasModel> currentPuskesmas = puskesmasRepository.findById(id);

        if (currentPuskesmas.isPresent()) {
            puskesmasModel.setId(id);
            return puskesmasRepository.save(puskesmasModel);
        }

        return null;
    }

    public PuskesmasModel patchPuskesmasById(int id, PuskesmasModel puskesmasModel) {
        Optional<PuskesmasModel> currentPuskesmas = puskesmasRepository.findById(id);

        if (currentPuskesmas.isPresent()) {
            PuskesmasModel dataPuskesmas = currentPuskesmas.get();

            if (puskesmasModel.getNama() != null && !puskesmasModel.getNama().isEmpty()) {
                dataPuskesmas.setNama(puskesmasModel.getNama());
            }

            if (puskesmasModel.getAlamat() != null && !puskesmasModel.getAlamat().isEmpty()) {
                dataPuskesmas.setAlamat(puskesmasModel.getAlamat());
            }

            if (puskesmasModel.getTelepon() != null && !puskesmasModel.getTelepon().isEmpty()) {
                dataPuskesmas.setTelepon(puskesmasModel.getTelepon());
            }

            if (puskesmasModel.getLon() != null && !puskesmasModel.getLon().isNaN()) {
                dataPuskesmas.setLon(puskesmasModel.getLon());
            }

            if (puskesmasModel.getLat() != null && !puskesmasModel.getLat().isNaN()) {
                dataPuskesmas.setLat(puskesmasModel.getLat());
            }

            if (puskesmasModel.getFoto() != null && !puskesmasModel.getFoto().isEmpty()) {
                dataPuskesmas.setFoto(puskesmasModel.getFoto());
            }

            dataPuskesmas.setId(id);
            return puskesmasRepository.save(dataPuskesmas);
        }

        return null;
    }

    public boolean deletePuskesmasById(int id) {
        Optional<PuskesmasModel> currentUser = puskesmasRepository.findById(id);

        if (currentUser.isPresent()) {
            puskesmasRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
