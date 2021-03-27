package co.id.telkom.epuskesmas.controller;

import co.id.telkom.epuskesmas.model.DokterModel;
import co.id.telkom.epuskesmas.model.PoliModel;
import co.id.telkom.epuskesmas.response.DataResponse;
import co.id.telkom.epuskesmas.response.HandlerResponse;
import co.id.telkom.epuskesmas.service.DokterService;
import co.id.telkom.epuskesmas.service.PoliService;
import co.id.telkom.epuskesmas.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@RequestMapping(value="/api/v1/doctors", produces={"application/json"})
@RestController
public class DokterController {

    // Dokter Photo Directory
    private final Path dirFotoDokter = Paths.get("static//doctors");

    @Autowired
    PoliService poliService;

    @Autowired
    DokterService dokterService;

    @PostMapping
    public void createDokter(HttpServletRequest request, HttpServletResponse response,
                             @RequestParam("idPoli") int idPoli,
                             @RequestParam("nama") String nama,
                             @RequestParam("kelamin") String kelamin,
                             @RequestParam("foto") MultipartFile foto) throws IOException {
        try {
            Optional<PoliModel> poliModel = poliService.getPoliById(idPoli);

            if (poliModel.isPresent()) {
                // Create Dokter Photo Directory
                if (!Files.exists(dirFotoDokter)) {
                    Files.createDirectories(dirFotoDokter);
                }

                // Generate Dokter Photo File Name
                String fileFotoDokter = UUID.randomUUID().toString() +
                        FileUtils.getFileExtension(foto.getOriginalFilename());

                // Upload Dokter Photo File to Puskesmas Photo Directory
                Files.copy(foto.getInputStream(), dirFotoDokter.resolve(fileFotoDokter));

                DokterModel dokterModel = new DokterModel();

                // Fill in Dokter Data
                dokterModel.setIdPoli(idPoli);
                dokterModel.setNama(nama);
                dokterModel.setKelamin(kelamin);
                dokterModel.setFoto(fileFotoDokter);

                // Insert the Dokter Data
                if (dokterService.createDokter(dokterModel) != null) {
                    HandlerResponse.responseSuccessCreated(response, "DOKTER CREATED");
                } else {
                    HandlerResponse.responseInternalServerError(response, "FAILED TO CREATE DOKTER");
                }
            } else {
                HandlerResponse.responseBadRequest(response, "INVALID POLI");
            }
        } catch (Exception e) {
            HandlerResponse.responseInternalServerError(response, e.getMessage().toUpperCase());
        }
    }

    @GetMapping
    public void getAllDokter(HttpServletRequest request, HttpServletResponse response,
                             @RequestParam("nama") String nama) throws IOException {
        DataResponse<Iterable<DokterModel>> dataResponse = new DataResponse<>();

        dataResponse.setCode(HttpServletResponse.SC_OK);
        if (nama != null && !nama.isEmpty()) {
            dataResponse.setData(dokterService.getAllDokterByNama(nama));
        } else {
            dataResponse.setData(dokterService.getAllDokter());
        }

        HandlerResponse.responseSuccessWithData(response, dataResponse);
    }

    @GetMapping("/{id}")
    public void getDokterById(HttpServletRequest request, HttpServletResponse response,
                              @PathVariable("id") int id) throws IOException {
        Optional<DokterModel> currentDokter = dokterService.getDokterById(id);

        if (currentDokter.isPresent()) {
            DokterModel dataDokter = currentDokter.get();
            DataResponse<DokterModel> dataResponse = new DataResponse<>();

            dataResponse.setCode(HttpServletResponse.SC_OK);
            dataResponse.setData(dataDokter);

            HandlerResponse.responseSuccessWithData(response, dataResponse);
        } else {
            HandlerResponse.responseNotFound(response, "DOKTER NOT FOUND");
        }
    }

    @PutMapping("/{id}")
    public void updateDokterById(HttpServletRequest request, HttpServletResponse response,
                                 @PathVariable("id") int id,
                                 @RequestParam("idPoli") int idPoli,
                                 @RequestParam("nama") String nama,
                                 @RequestParam("kelamin") String kelamin,
                                 @RequestParam("foto") MultipartFile foto) throws IOException {

    }

    @PatchMapping("/{id}")
    public void patchDokterById(HttpServletRequest request, HttpServletResponse response,
                                @PathVariable("id") int id,
                                @RequestParam(value = "idPoli", required = false) int idPoli,
                                @RequestParam(value = "nama", required = false) String nama,
                                @RequestParam(value = "kelamin", required = false) String kelamin,
                                @RequestParam(value = "foto", required = false) MultipartFile foto) throws IOException {

    }

    @DeleteMapping("/{id}")
    public void deleteDokterById(HttpServletRequest request, HttpServletResponse response,
                                 @PathVariable("id") int id) throws IOException {
        try {
            Optional<DokterModel> currentDokter = dokterService.getDokterById(id);

            if (currentDokter.isPresent()) {
                DokterModel dataDokter = currentDokter.get();

                // Delete OLD Puskesmas Photo File from Puskesmas Photo Directory
                Files.delete(dirFotoDokter.resolve(dataDokter.getFoto()));

                dokterService.deleteDokterById(id);
                HandlerResponse.responseSuccessOK(response, "DOKTER DELETED");
            } else {
                HandlerResponse.responseNotFound(response, "DOKTER NOT FOUND");
            }
        } catch (Exception e) {
            HandlerResponse.responseInternalServerError(response, e.getMessage().toUpperCase());
        }
    }
}
