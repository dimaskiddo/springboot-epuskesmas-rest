package co.id.telkom.epuskesmas.controller;

import co.id.telkom.epuskesmas.model.PuskesmasModel;
import co.id.telkom.epuskesmas.response.DataResponse;
import co.id.telkom.epuskesmas.response.HandlerResponse;
import co.id.telkom.epuskesmas.service.PuskesmasService;
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

@RequestMapping(value="/api/v1/clinics", produces={"application/json"})
@RestController
public class PuskesmasController {

    // Puskesmas Photo Directory
    private final Path dirFotoPuskesmas = Paths.get("static//clinics");

    @Autowired
    private PuskesmasService puskesmasService;

    @PostMapping
    public void createPuskesmas(HttpServletRequest request, HttpServletResponse response,
                                @RequestParam("nama") String nama,
                                @RequestParam("alamat") String alamat,
                                @RequestParam("telepon") String telepon,
                                @RequestParam("lon") Double lon,
                                @RequestParam("lat") Double lat,
                                @RequestParam("foto") MultipartFile foto) throws IOException {
        try {
            // Create Puskesmas Photo Directory
            if (!Files.exists(dirFotoPuskesmas)) {
                Files.createDirectories(dirFotoPuskesmas);
            }

            // Generate Puskesmas Photo File Name
            String fileFotoPuskesmas = UUID.randomUUID().toString() +
                    FileUtils.getFileExtension(foto.getOriginalFilename());

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
        } catch (Exception e) {
            HandlerResponse.responseInternalServerError(response, e.getMessage().toUpperCase());
        }
    }

    @GetMapping
    public void getAllPuskesmas(HttpServletRequest request, HttpServletResponse response,
                                @RequestParam(value = "nama", required = false) String nama) throws IOException {
        DataResponse<Iterable<PuskesmasModel>> dataResponse = new DataResponse<>();

        dataResponse.setCode(HttpServletResponse.SC_OK);
        if (nama != null) {
            dataResponse.setData(puskesmasService.getAllPuskesmasByNama(nama));
        } else {
            dataResponse.setData(puskesmasService.getAllPuskesmas());
        }

        HandlerResponse.responseSuccessWithData(response, dataResponse);
    }

    @GetMapping("/{id}")
    public void getPuskemasById(HttpServletRequest request, HttpServletResponse response,
                                @PathVariable int id) throws IOException {
        Optional<PuskesmasModel> dataPuskesmas = puskesmasService.getPuskesmasById(id);

        if (dataPuskesmas.isPresent()) {
            PuskesmasModel currentPuskesmas = dataPuskesmas.get();
            DataResponse<PuskesmasModel> dataResponse = new DataResponse<>();

            dataResponse.setCode(HttpServletResponse.SC_OK);
            dataResponse.setData(currentPuskesmas);

            HandlerResponse.responseSuccessWithData(response, dataResponse);
        } else {
            HandlerResponse.responseNotFound(response, "PUSKESMAS NOT FOUND");
        }
    }

    @PutMapping("/{id}")
    public void updatePuskesmasById(HttpServletRequest request, HttpServletResponse response,
                                    @PathVariable int id,
                                    @RequestParam("nama") String nama,
                                    @RequestParam("alamat") String alamat,
                                    @RequestParam("telepon") String telepon,
                                    @RequestParam("lon") Double lon,
                                    @RequestParam("lat") Double lat,
                                    @RequestParam("foto") MultipartFile foto) throws IOException {
        try {
            Optional<PuskesmasModel> currentPuskesmas = puskesmasService.getPuskesmasById(id);

            if (currentPuskesmas.isPresent()) {
                PuskesmasModel dataPuskesmas = currentPuskesmas.get();

                // Create Puskesmas Photo Directory
                if (!Files.exists(dirFotoPuskesmas)) {
                    Files.createDirectories(dirFotoPuskesmas);
                }

                // Generate Puskesmas Photo File Name
                String fileFotoPuskesmas = UUID.randomUUID().toString() +
                        FileUtils.getFileExtension(foto.getOriginalFilename());

                // Upload Puskesmas Photo File to Puskesmas Photo Directory
                Files.copy(foto.getInputStream(), dirFotoPuskesmas.resolve(fileFotoPuskesmas));

                // Delete OLD Puskesmas Photo File from Puskesmas Photo Directory
                Files.delete(dirFotoPuskesmas.resolve(dataPuskesmas.getFoto()));

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
        } catch (Exception e) {
            HandlerResponse.responseInternalServerError(response, e.getMessage().toUpperCase());
        }
    }

    @PatchMapping("/{id}")
    public void patchPuskesmasById(HttpServletRequest request, HttpServletResponse response,
                                   @PathVariable int id,
                                   @RequestParam(value = "nama", required = false) String nama,
                                   @RequestParam(value = "alamat", required = false) String alamat,
                                   @RequestParam(value = "telepon", required = false) String telepon,
                                   @RequestParam(value = "lon",required = false) Double lon,
                                   @RequestParam(value = "lat", required = false) Double lat,
                                   @RequestParam(value = "foto", required = false) MultipartFile foto) throws IOException {
        try {
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
                            FileUtils.getFileExtension(foto.getOriginalFilename());

                    // Upload Puskesmas Photo File to Puskesmas Photo Directory
                    Files.copy(foto.getInputStream(), dirFotoPuskesmas.resolve(fileFotoPuskesmas));

                    // Delete OLD Puskesmas Photo File from Puskesmas Photo Directory
                    Files.delete(dirFotoPuskesmas.resolve(dataPuskesmas.getFoto()));

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
        } catch (Exception e) {
            HandlerResponse.responseInternalServerError(response, e.getMessage().toUpperCase());
        }
    }

    @DeleteMapping("/{id}")
    public void deletePuskesmasById(HttpServletRequest request, HttpServletResponse response,
                                    @PathVariable int id) throws IOException {
        try {
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
        } catch (Exception e) {
            HandlerResponse.responseInternalServerError(response, e.getMessage().toUpperCase());
        }
    }
}
