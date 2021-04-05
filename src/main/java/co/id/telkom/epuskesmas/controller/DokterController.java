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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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

    private FileUtils fileUtils;

    @Autowired
    PoliService poliService;

    @Autowired
    DokterService dokterService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void createDokter(HttpServletRequest request, HttpServletResponse response,
                             @Valid @NotNull @ModelAttribute("idPoli") Integer idPoli,
                             @Valid @NotNull @ModelAttribute("nama") String nama,
                             @Valid @NotNull @ModelAttribute("kelamin") String kelamin,
                             @Valid @NotNull @RequestPart("foto") MultipartFile foto) {
        try {
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
        } catch (Exception e) {
            HandlerResponse.responseInternalServerError(response, e.getMessage().toUpperCase());
        }
    }

    @GetMapping
    public void getAllDokter(HttpServletRequest request, HttpServletResponse response,
                             @RequestParam(value = "nama", required = false) String nama,
                             @RequestParam(value = "idPoli", required = false) Integer idPoli) {
        DataResponse<Iterable<DokterModel>> dataResponse = new DataResponse<>();

        dataResponse.setCode(HttpServletResponse.SC_OK);
        if (nama != null && !nama.isEmpty()) {
            dataResponse.setData(dokterService.getAllDokterByNama(nama));
        } else if (idPoli != null && idPoli > 0) {
            dataResponse.setData(dokterService.getAllDokterByIdPoli(idPoli));
        } else {
            dataResponse.setData(dokterService.getAllDokter());
        }

        HandlerResponse.responseSuccessWithData(response, dataResponse);
    }

    @GetMapping("/{id}")
    public void getDokterById(HttpServletRequest request, HttpServletResponse response,
                              @PathVariable("id") int id) {
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
                                 @PathVariable("id") int id,
                                 @Valid @NotNull @ModelAttribute("idPoli") Integer idPoli,
                                 @Valid @NotNull @ModelAttribute("nama") String nama,
                                 @Valid @NotNull @ModelAttribute("kelamin") String kelamin,
                                 @Valid @NotNull @RequestPart("foto") MultipartFile foto) {
        try {
            Optional<PoliModel> currentPoli = poliService.getPoliById(id);

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
        } catch (Exception e) {
            HandlerResponse.responseInternalServerError(response, e.getMessage().toUpperCase());
        }
    }

    @PatchMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void patchDokterById(HttpServletRequest request, HttpServletResponse response,
                                @PathVariable("id") int id,
                                @Valid @ModelAttribute("idPoli") Integer idPoli,
                                @Valid @ModelAttribute("nama") String nama,
                                @Valid @ModelAttribute("kelamin") String kelamin,
                                @Valid @RequestPart(value = "foto", required = false) MultipartFile foto) {
        try {
            Optional<PoliModel> currentPoli = poliService.getPoliById(id);

            if (currentPoli.isPresent()) {
                Optional<DokterModel> currentDokter = dokterService.getDokterById(id);

                if (currentDokter.isPresent()) {
                    DokterModel dokterModel = new DokterModel();

                    // Fill in Dokter Data
                    if (idPoli != null && idPoli > 0) {
                        dokterModel.setIdPoli(idPoli);
                    }
                    if (nama != null && !nama.isEmpty()) {
                        dokterModel.setNama(nama);
                    }
                    if (kelamin != null && !kelamin.isEmpty()) {
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
                    dokterService.updateDokterById(id, dokterModel);
                    HandlerResponse.responseSuccessOK(response, "DOKTER UPDATED");
                } else {
                    HandlerResponse.responseNotFound(response, "DOKTER NOT FOUND");
                }
            } else {
                HandlerResponse.responseBadRequest(response, "INVALID POLI");
            }
        } catch (Exception e) {
            HandlerResponse.responseInternalServerError(response, e.getMessage().toUpperCase());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteDokterById(HttpServletRequest request, HttpServletResponse response,
                                 @PathVariable("id") int id) {
        try {
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
        } catch (Exception e) {
            HandlerResponse.responseInternalServerError(response, e.getMessage().toUpperCase());
        }
    }
}
