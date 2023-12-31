package co.id.telkom.epuskesmas.controller;

import co.id.telkom.epuskesmas.model.DokterModel;
import co.id.telkom.epuskesmas.model.PoliModel;
import co.id.telkom.epuskesmas.response.DataResponse;
import co.id.telkom.epuskesmas.response.HandlerResponse;
import co.id.telkom.epuskesmas.service.DokterService;
import co.id.telkom.epuskesmas.service.PoliService;
import co.id.telkom.epuskesmas.utils.FileUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Tag(name = "Doctors", description = "Endpoints for Doctors")
@SecurityRequirement(name = "Login")
@RequestMapping(value="/api/v1/doctors", produces={"application/json"})
@RestController
public class DokterController {

    // Dokter Photo Directory
    private final Path dirFotoDokter = Paths.get("static//doctors");

    private FileUtils fileUtils = new FileUtils();

    @Autowired
    PoliService poliService;

    @Autowired
    DokterService dokterService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void createDokter(HttpServletRequest request, HttpServletResponse response,
                             @Valid @NotNull @ModelAttribute Integer idPoli,
                             @Valid @NotNull @ModelAttribute String nama,
                             @Valid @NotNull @ModelAttribute String kelamin,
                             @Valid @NotNull @RequestPart("foto") MultipartFile foto) throws IOException {
        Optional<PoliModel> poliModel = poliService.getPoliById(idPoli);

        if (poliModel.isPresent()) {
            // Create Dokter Photo Directory
            if (!Files.exists(dirFotoDokter)) {
                Files.createDirectories(dirFotoDokter);
            }

            // Generate Dokter Photo File Name
            String fileFotoDokter = UUID.randomUUID().toString() +
                    fileUtils.getFileExtension(foto.getOriginalFilename());

            // Upload Dokter Photo File to Dokter Photo Directory
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
    }

    @GetMapping
    public void getAllDokter(HttpServletRequest request, HttpServletResponse response,
                             @RequestParam(required = false) Integer idPoli,
                             @RequestParam(required = false) String nama) throws IOException {
        if (idPoli == null) {
            idPoli = 0;
        }
        if (nama == null) {
            nama = "";
        }

        DataResponse<Iterable<DokterModel>> dataResponse = new DataResponse<>();

        dataResponse.setCode(HttpServletResponse.SC_OK);
        if (idPoli > 0 || !nama.isEmpty()) {
            dataResponse.setData(dokterService.getAllDokterBySearch(idPoli, nama));
        } else {
            dataResponse.setData(dokterService.getAllDokter());
        }

        HandlerResponse.responseSuccessWithData(response, dataResponse);
    }

    @GetMapping("/{id}")
    public void getDokterById(HttpServletRequest request, HttpServletResponse response,
                              @PathVariable int id) throws IOException {
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

    @PutMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void updateDokterById(HttpServletRequest request, HttpServletResponse response,
                                 @PathVariable int id,
                                 @Valid @NotNull @ModelAttribute Integer idPoli,
                                 @Valid @NotNull @ModelAttribute String nama,
                                 @Valid @NotNull @ModelAttribute String kelamin,
                                 @Valid @NotNull @RequestPart("foto") MultipartFile foto) throws IOException {
        Optional<PoliModel> currentPoli = poliService.getPoliById(idPoli);

        if (currentPoli.isPresent()) {
            Optional<DokterModel> currentDokter = dokterService.getDokterById(id);

            if (currentDokter.isPresent()) {
                DokterModel dataDokter = currentDokter.get();

                // Create Dokter Photo Directory
                if (!Files.exists(dirFotoDokter)) {
                    Files.createDirectories(dirFotoDokter);
                }

                // Generate Dokter Photo File Name
                String fileFotoDokter = UUID.randomUUID().toString() +
                        fileUtils.getFileExtension(foto.getOriginalFilename());

                // Upload Dokter Photo File to Dokter Photo Directory
                Files.copy(foto.getInputStream(), dirFotoDokter.resolve(fileFotoDokter));

                if (dataDokter.getFoto() != null && !dataDokter.getFoto().isEmpty()) {
                    // Delete OLD Dokter Photo File from Dokter Photo Directory
                    Files.delete(dirFotoDokter.resolve(dataDokter.getFoto()));
                }

                DokterModel dokterModel = new DokterModel();

                // Fill in Dokter Data
                dokterModel.setIdPoli(idPoli);
                dokterModel.setNama(nama);
                dokterModel.setKelamin(kelamin);
                dokterModel.setFoto(fileFotoDokter);

                // Update the Dokter Data
                dokterService.updateDokterById(id, dokterModel);
                HandlerResponse.responseSuccessOK(response, "DOKTER UPDATED");
            } else {
                HandlerResponse.responseNotFound(response, "DOKTER NOT FOUND");
            }
        } else {
            HandlerResponse.responseBadRequest(response, "INVALID POLI");
        }
    }

    @PatchMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void patchDokterById(HttpServletRequest request, HttpServletResponse response,
                                @PathVariable int id,
                                @Valid @NotNull @ModelAttribute Integer idPoli,
                                @Valid @ModelAttribute String nama,
                                @Valid @ModelAttribute String kelamin,
                                @Valid @RequestPart(value = "foto", required = false) MultipartFile foto) throws IOException {
        Optional<DokterModel> currentDokter = dokterService.getDokterById(id);

        if (currentDokter.isPresent()) {
            DokterModel dokterModel = new DokterModel();

            // Fill in Dokter Data
            if (idPoli != null && idPoli > 0) {
                Optional<PoliModel> currentPoli = poliService.getPoliById(idPoli);

                // Check if Poli is Exist
                if (currentPoli.isPresent()) {
                    // If the 'idPoli' field is not empty then
                    // Try to update Poli 'idPoli'
                    dokterModel.setIdPoli(idPoli);
                } else {
                    HandlerResponse.responseBadRequest(response, "INVALID POLI");
                    return;
                }
            }
            if (nama != null && !nama.isEmpty()) {
                // If the 'nama' field is not empty then
                // Try to update Poli 'nama'
                dokterModel.setNama(nama);
            }
            if (kelamin != null && !kelamin.isEmpty()) {
                // If the 'kelamin' field is not empty then
                // Try to update Poli 'kelamin'
                dokterModel.setKelamin(kelamin);
            }
            if (foto != null && !foto.isEmpty()) {
                DokterModel dataDokter = currentDokter.get();

                // Create Dokter Photo Directory
                if (!Files.exists(dirFotoDokter)) {
                    Files.createDirectories(dirFotoDokter);
                }

                // Generate Dokter Photo File Name
                String fileFotoDokter = UUID.randomUUID().toString() +
                        fileUtils.getFileExtension(foto.getOriginalFilename());

                // Upload Dokter Photo File to Dokter Photo Directory
                Files.copy(foto.getInputStream(), dirFotoDokter.resolve(fileFotoDokter));

                if (dataDokter.getFoto() != null && !dataDokter.getFoto().isEmpty()) {
                    // Delete OLD Dokter Photo File from Dokter Photo Directory
                    Files.delete(dirFotoDokter.resolve(dataDokter.getFoto()));
                }

                // If the 'foto' field is not empty then
                // Try to update Dokter 'foto'
                dokterModel.setFoto(fileFotoDokter);
            }

            // Update the Dokter Data
            dokterService.patchDokterById(id, dokterModel);
            HandlerResponse.responseSuccessOK(response, "DOKTER UPDATED");
        } else {
            HandlerResponse.responseNotFound(response, "DOKTER NOT FOUND");
        }
    }

    @DeleteMapping("/{id}")
    public void deleteDokterById(HttpServletRequest request, HttpServletResponse response,
                                 @PathVariable int id) throws IOException {
        Optional<DokterModel> currentDokter = dokterService.getDokterById(id);

        if (currentDokter.isPresent()) {
            DokterModel dataDokter = currentDokter.get();

            // Delete OLD Dokter Photo File from Dokter Photo Directory
            Files.delete(dirFotoDokter.resolve(dataDokter.getFoto()));

            dokterService.deleteDokterById(id);
            HandlerResponse.responseSuccessOK(response, "DOKTER DELETED");
        } else {
            HandlerResponse.responseNotFound(response, "DOKTER NOT FOUND");
        }
    }
}
