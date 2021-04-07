package co.id.telkom.epuskesmas.controller;

import co.id.telkom.epuskesmas.model.PuskesmasModel;
import co.id.telkom.epuskesmas.response.DataResponse;
import co.id.telkom.epuskesmas.response.HandlerResponse;
import co.id.telkom.epuskesmas.service.PuskesmasService;
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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Tag(name = "Clinics", description = "Endpoints for Clinics")
@SecurityRequirement(name = "Login")
@RequestMapping(value="/api/v1/clinics", produces={"application/json"})
@RestController
public class PuskesmasController {

    // Puskesmas Photo Directory
    private final Path dirFotoPuskesmas = Paths.get("static//clinics");

    private FileUtils fileUtils;

    @Autowired
    private PuskesmasService puskesmasService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void createPuskesmas(HttpServletRequest request, HttpServletResponse response,
                                @Valid @NotNull @ModelAttribute("nama") String nama,
                                @Valid @NotNull @ModelAttribute("alamat") String alamat,
                                @Valid @NotNull @ModelAttribute("telepon") String telepon,
                                @Valid @NotNull @ModelAttribute("lon") Double lon,
                                @Valid @NotNull @ModelAttribute("lat") Double lat,
                                @Valid @NotNull @RequestPart("foto") MultipartFile foto) throws IOException {
        // Create Puskesmas Photo Directory
        if (!Files.exists(dirFotoPuskesmas)) {
            Files.createDirectories(dirFotoPuskesmas);
        }

        // Generate Puskesmas Photo File Name
        String fileFotoPuskesmas = UUID.randomUUID().toString() +
                fileUtils.getFileExtension(foto.getOriginalFilename());

        // Upload Puskesmas Photo File to Puskesmas Photo Directory
        Files.copy(foto.getInputStream(), dirFotoPuskesmas.resolve(fileFotoPuskesmas));

        PuskesmasModel puskesmasModel = new PuskesmasModel();

        // Fill in Puskesmas Data
        puskesmasModel.setNama(nama);
        puskesmasModel.setAlamat(alamat);
        puskesmasModel.setTelepon(telepon);
        puskesmasModel.setLon(lon);
        puskesmasModel.setLat(lat);
        puskesmasModel.setFoto(fileFotoPuskesmas);

        // Insert the Puskesmas Data
        if (puskesmasService.createPuskesmas(puskesmasModel) != null) {
            HandlerResponse.responseSuccessCreated(response, "PUSKESMAS CREATED");
        } else {
            HandlerResponse.responseInternalServerError(response, "FAILED TO CREATE PUSKESMAS");
        }
    }

    @GetMapping
    public void getAllPuskesmas(HttpServletRequest request, HttpServletResponse response,
                                @RequestParam(value = "nama", required = false) String nama) throws IOException {
        DataResponse<Iterable<PuskesmasModel>> dataResponse = new DataResponse<>();

        dataResponse.setCode(HttpServletResponse.SC_OK);
        if (nama != null && !nama.isEmpty()) {
            dataResponse.setData(puskesmasService.getAllPuskesmasByNama(nama));
        } else {
            dataResponse.setData(puskesmasService.getAllPuskesmas());
        }

        HandlerResponse.responseSuccessWithData(response, dataResponse);
    }

    @GetMapping("/{id}")
    public void getPuskemasById(HttpServletRequest request, HttpServletResponse response,
                                @PathVariable int id) throws IOException {
        Optional<PuskesmasModel> currentPuskesmas = puskesmasService.getPuskesmasById(id);

        if (currentPuskesmas.isPresent()) {
            PuskesmasModel dataPuskesmas = currentPuskesmas.get();
            DataResponse<PuskesmasModel> dataResponse = new DataResponse<>();

            dataResponse.setCode(HttpServletResponse.SC_OK);
            dataResponse.setData(dataPuskesmas);

            HandlerResponse.responseSuccessWithData(response, dataResponse);
        } else {
            HandlerResponse.responseNotFound(response, "PUSKESMAS NOT FOUND");
        }
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void updatePuskesmasById(HttpServletRequest request, HttpServletResponse response,
                                    @PathVariable int id,
                                    @Valid @NotNull @ModelAttribute("nama") String nama,
                                    @Valid @NotNull @ModelAttribute("alamat") String alamat,
                                    @Valid @NotNull @ModelAttribute("telepon") String telepon,
                                    @Valid @NotNull @ModelAttribute("lon") Double lon,
                                    @Valid @NotNull @ModelAttribute("lat") Double lat,
                                    @Valid @NotNull @RequestPart("foto") MultipartFile foto) throws IOException {
        Optional<PuskesmasModel> currentPuskesmas = puskesmasService.getPuskesmasById(id);

        if (currentPuskesmas.isPresent()) {
            PuskesmasModel dataPuskesmas = currentPuskesmas.get();

            // Create Puskesmas Photo Directory
            if (!Files.exists(dirFotoPuskesmas)) {
                Files.createDirectories(dirFotoPuskesmas);
            }

            // Generate Puskesmas Photo File Name
            String fileFotoPuskesmas = UUID.randomUUID().toString() +
                    fileUtils.getFileExtension(foto.getOriginalFilename());

            // Upload Puskesmas Photo File to Puskesmas Photo Directory
            Files.copy(foto.getInputStream(), dirFotoPuskesmas.resolve(fileFotoPuskesmas));

            if (dataPuskesmas.getFoto() != null && !dataPuskesmas.getFoto().isEmpty()) {
                // Delete OLD Puskesmas Photo File from Puskesmas Photo Directory
                Files.delete(dirFotoPuskesmas.resolve(dataPuskesmas.getFoto()));
            }

            PuskesmasModel puskesmasModel = new PuskesmasModel();

            // Fill in Puskesmas Data
            puskesmasModel.setNama(nama);
            puskesmasModel.setAlamat(alamat);
            puskesmasModel.setTelepon(telepon);
            puskesmasModel.setLon(lon);
            puskesmasModel.setLat(lat);
            puskesmasModel.setFoto(fileFotoPuskesmas);

            // Update the Puskesmas Data
            puskesmasService.updatePuskesmasById(id, puskesmasModel);
            HandlerResponse.responseSuccessOK(response, "PUSKESMAS UPDATED");
        } else {
            HandlerResponse.responseNotFound(response, "PUSKESMAS NOT FOUND");
        }
    }

    @PatchMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void patchPuskesmasById(HttpServletRequest request, HttpServletResponse response,
                                   @PathVariable int id,
                                   @Valid @ModelAttribute("nama") String nama,
                                   @Valid @ModelAttribute("alamat") String alamat,
                                   @Valid @ModelAttribute("telepon") String telepon,
                                   @Valid @ModelAttribute("lon") Double lon,
                                   @Valid @ModelAttribute("lat") Double lat,
                                   @Valid @RequestPart(name = "foto", required = false) MultipartFile foto) throws IOException {
        Optional<PuskesmasModel> currentPuskesmas = puskesmasService.getPuskesmasById(id);

        if (currentPuskesmas.isPresent()) {
            PuskesmasModel puskesmasModel = new PuskesmasModel();

            // Fill in Puskesmas Data
            if (nama != null && !nama.isEmpty()) {
                // If the 'nama' field is not empty then
                // Try to update Puskesmas 'nama'
                puskesmasModel.setNama(nama);
            }
            if (alamat != null && !alamat.isEmpty()) {
                // If the 'alamat' field is not empty then
                // Try to update Puskesmas 'alamat'
                puskesmasModel.setAlamat(alamat);
            }
            if (telepon != null && !telepon.isEmpty()) {
                // If the 'telepon' field is not empty then
                // Try to update Puskesmas 'telepon'
                puskesmasModel.setTelepon(telepon);
            }
            if (lon != null && !lon.isNaN()) {
                // If the 'lon' field is not empty then
                // Try to update Puskesmas 'lon'
                puskesmasModel.setLon(lon);
            }
            if (lat != null && !lat.isNaN()) {
                // If the 'lat' field is not empty then
                // Try to update Puskesmas 'lat'
                puskesmasModel.setLon(lat);
            }
            if (foto != null && !foto.isEmpty()) {
                PuskesmasModel dataPuskesmas = currentPuskesmas.get();

                // Create Puskesmas Photo Directory
                if (!Files.exists(dirFotoPuskesmas)) {
                    Files.createDirectories(dirFotoPuskesmas);
                }

                // Generate Puskesmas Photo File Name
                String fileFotoPuskesmas = UUID.randomUUID().toString() +
                        fileUtils.getFileExtension(foto.getOriginalFilename());

                // Upload Puskesmas Photo File to Puskesmas Photo Directory
                Files.copy(foto.getInputStream(), dirFotoPuskesmas.resolve(fileFotoPuskesmas));

                if (dataPuskesmas.getFoto() != null && !dataPuskesmas.getFoto().isEmpty()) {
                    // Delete OLD Puskesmas Photo File from Puskesmas Photo Directory
                    Files.delete(dirFotoPuskesmas.resolve(dataPuskesmas.getFoto()));
                }

                // If the 'foto' field is not empty then
                // Try to update Puskesmas 'foto'
                puskesmasModel.setFoto(fileFotoPuskesmas);
            }

            // Patch the Puskesmas Data
            puskesmasService.patchPuskesmasById(id, puskesmasModel);
            HandlerResponse.responseSuccessOK(response, "PUSKESMAS UPDATED");
        } else {
            HandlerResponse.responseNotFound(response, "PUSKESMAS NOT FOUND");
        }
    }

    @DeleteMapping("/{id}")
    public void deletePuskesmasById(HttpServletRequest request, HttpServletResponse response,
                                    @PathVariable int id) throws IOException {
        Optional<PuskesmasModel> currentPuskesmas = puskesmasService.getPuskesmasById(id);

        if (currentPuskesmas.isPresent()) {
            PuskesmasModel dataPuskesmas = currentPuskesmas.get();

            // Delete OLD Puskesmas Photo File from Puskesmas Photo Directory
            Files.delete(dirFotoPuskesmas.resolve(dataPuskesmas.getFoto()));

            puskesmasService.deletePuskesmasById(id);
            HandlerResponse.responseSuccessOK(response, "PUSKESMAS DELETED");
        } else {
            HandlerResponse.responseNotFound(response, "PUSKESMAS NOT FOUND");
        }
    }
}
