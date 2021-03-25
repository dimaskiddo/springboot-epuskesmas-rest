package co.id.telkom.epuskesmas.controller;

import co.id.telkom.epuskesmas.model.PuskesmasModel;
import co.id.telkom.epuskesmas.repository.PuskesmasRepository;
import co.id.telkom.epuskesmas.response.DataResponse;
import co.id.telkom.epuskesmas.response.HandlerResponse;
import co.id.telkom.epuskesmas.service.PuskesmasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RequestMapping(value="/api/v1/clinics", produces={"application/json"})
@RestController
public class PuskesmasController {

    @Autowired
    private PuskesmasService puskesmasService;

    @PostMapping
    public void createPuskesmas(HttpServletRequest request, HttpServletResponse response,
                                @RequestParam("nama") String nama,
                                @RequestParam("alamat") String alamat,
                                @RequestParam("phone") String phone,
                                @RequestParam("lon") Double lon,
                                @RequestParam("lat") Double lat) throws IOException {
        PuskesmasModel puskesmasModel = new PuskesmasModel();

        // Fill in Puskesmas Data
        puskesmasModel.setNama(nama);
        puskesmasModel.setAlamat(alamat);
        puskesmasModel.setPhone(phone);
        puskesmasModel.setLon(lon);
        puskesmasModel.setLat(lat);

        // Insert the Puskesmas Data
        if (puskesmasService.createPuskesmas(puskesmasModel) != null) {
            HandlerResponse.responseSuccessCreated(response, "PUSKESMAS CREATED");
        } else {
            HandlerResponse.responseInternalServerError(response, "FAILED TO CREATE PUSKESMAS");
        }
    }

    @GetMapping
    public void getPuskesmas(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DataResponse<Iterable<PuskesmasModel>> dataResponse = new DataResponse<>();

        dataResponse.setCode(HttpServletResponse.SC_OK);
        dataResponse.setData(puskesmasService.getPuskesmas());

        HandlerResponse.responseSuccessWithData(response, dataResponse);
    }

    @GetMapping("/{id}")
    public void getPuskemasById(HttpServletRequest request, HttpServletResponse response,
                                @PathVariable("id") int id) throws IOException {
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
                                    @PathVariable("id") int id,
                                    @RequestParam("nama") String nama,
                                    @RequestParam("alamat") String alamat,
                                    @RequestParam("phone") String phone,
                                    @RequestParam("lon") Double lon,
                                    @RequestParam("lat") Double lat) throws IOException {
        PuskesmasModel puskesmasModel = new PuskesmasModel();

        // Fill in Puskesmas Data
        puskesmasModel.setNama(nama);
        puskesmasModel.setAlamat(alamat);
        puskesmasModel.setPhone(phone);
        puskesmasModel.setLon(lon);
        puskesmasModel.setLat(lat);

        // Update the Puskesmas Data
        if (puskesmasService.updatePuskesmasById(id, puskesmasModel) != null) {
            HandlerResponse.responseSuccessOK(response, "PUSKESMAS UPDATED");
        } else {
            HandlerResponse.responseNotFound(response, "PUSKESMAS NOT FOUND");
        }
    }

    @PatchMapping("/{id}")
    public void patchPuskesmasById(HttpServletRequest request, HttpServletResponse response,
                                   @PathVariable(value = "id", required = false) int id,
                                   @RequestParam(value = "nama", required = false) String nama,
                                   @RequestParam(value = "alamat", required = false) String alamat,
                                   @RequestParam(value = "phone", required = false) String phone,
                                   @RequestParam(value = "lon",required = false) Double lon,
                                   @RequestParam(value = "lat", required = false) Double lat) throws IOException {
        PuskesmasModel puskesmasModel = new PuskesmasModel();

        // Fill in Puskesmas Data
        if (nama != null && !nama.isEmpty()) {
            // If the 'nama' field is not empty then
            // Try to update User 'nama'
            puskesmasModel.setNama(nama);
        }
        if (alamat != null && !alamat.isEmpty()) {
            // If the 'alamat' field is not empty then
            // Try to update User 'alamat'
            puskesmasModel.setAlamat(alamat);
        }
        if (phone != null && !phone.isEmpty()) {
            // If the 'phone' field is not empty then
            // Try to update User 'phone'
            puskesmasModel.setPhone(phone);
        }
        if (lon != null && !lon.isNaN()) {
            // If the 'lon' field is not empty then
            // Try to update User 'lon'
            puskesmasModel.setLon(lon);
        }
        if (lat != null && !lat.isNaN()) {
            // If the 'lat' field is not empty then
            // Try to update User 'lat'
            puskesmasModel.setLon(lat);
        }

        // Patch the Puskesmas Data
        if (puskesmasService.patchPuskesmasById(id, puskesmasModel) != null) {
            HandlerResponse.responseSuccessOK(response, "PUSKESMAS UPDATED");
        } else {
            HandlerResponse.responseNotFound(response, "PUSKESMAS NOT FOUND");
        }
    }

    @DeleteMapping("/{id}")
    public void deletePuskesmasById(HttpServletRequest request, HttpServletResponse response,
                                    @PathVariable("id") int id) throws IOException {
        if (puskesmasService.deletePuskesmasById(id)) {
            HandlerResponse.responseSuccessOK(response, "PUSKESMAS DELETED");
        } else {
            HandlerResponse.responseNotFound(response, "PUSKESMAS NOT FOUND");
        }
    }

    @GetMapping("/nama")
    public void getPuskesmasByName(HttpServletRequest request, HttpServletResponse response,
                                   @RequestParam("nama") String nama) throws IOException {
        Iterable<PuskesmasModel> dataPuskesmas = puskesmasService.findPuskesmasByNama(nama);

        if (dataPuskesmas != null) {
            DataResponse <Iterable<PuskesmasModel>> dataResponse = new DataResponse<>();

            dataResponse.setCode(HttpServletResponse.SC_OK);
            dataResponse.setData(dataPuskesmas);

            HandlerResponse.responseSuccessWithData(response, dataResponse);
        } else {
            HandlerResponse.responseNotFound(response, "PUSKESMAS NOT FOUND");
        }
    }
}
