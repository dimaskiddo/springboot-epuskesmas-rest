package co.id.telkom.epuskesmas.controller;

import co.id.telkom.epuskesmas.model.PoliModel;
import co.id.telkom.epuskesmas.model.PuskesmasModel;
import co.id.telkom.epuskesmas.response.DataResponse;
import co.id.telkom.epuskesmas.response.HandlerResponse;
import co.id.telkom.epuskesmas.service.PoliService;
import co.id.telkom.epuskesmas.service.PuskesmasService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@SecurityRequirement(name = "Login")
@RequestMapping(value="/api/v1/polies", produces={"application/json"})
@RestController
public class PoliController {

    @Autowired
    private PuskesmasService puskesmasService;

    @Autowired
    private PoliService poliService;

    @PostMapping
    public void createPoli(HttpServletRequest request, HttpServletResponse response,
                           @RequestParam("idPuskesmas") int idPuskesmas,
                           @RequestParam("nama") String nama,
                           @RequestParam("waktuBuka") String waktuBuka,
                           @RequestParam("waktuTutup") String waktuTutup) throws IOException {
        Optional<PuskesmasModel> puskesmasModel = puskesmasService.getPuskesmasById(idPuskesmas);

        // Check if Puskemas is Exist
        if (puskesmasModel.isPresent()) {
            // Fill in Poli Data
            PoliModel poliModel = new PoliModel();

            poliModel.setIdPuskesmas(idPuskesmas);
            poliModel.setNama(nama);
            poliModel.setWaktuBuka(waktuBuka);
            poliModel.setWaktuTutup(waktuTutup);

            // Insert the Poli Data
            if (poliService.createPoli(poliModel) != null) {
                HandlerResponse.responseSuccessCreated(response, "POLI CREATED");
            } else {
                HandlerResponse.responseInternalServerError(response, "FAILED TO CREATE POLI");
            }
        } else {
            HandlerResponse.responseBadRequest(response, "INVALID PUSKESMAS");
        }
    }

    @GetMapping
    public void getAllPoli(HttpServletRequest request, HttpServletResponse response,
                           @RequestParam(value = "nama", required = false) String nama) throws IOException {
        DataResponse<Iterable<PoliModel>> dataResponse = new DataResponse<>();

        dataResponse.setCode(HttpServletResponse.SC_OK);
        if (nama != null && !nama.isEmpty()) {
            dataResponse.setData(poliService.getAllPoliByNama(nama));
        } else {
            dataResponse.setData(poliService.getAllPoli());
        }

        HandlerResponse.responseSuccessWithData(response, dataResponse);
    }

    @GetMapping("/{id}")
    public void getPoliById(HttpServletRequest request, HttpServletResponse response,
                            @PathVariable int id) throws IOException {
        Optional<PoliModel> currentPoli = poliService.getPoliById(id);

        if (currentPoli.isPresent()) {
            PoliModel dataPoli = currentPoli.get();
            DataResponse<PoliModel> dataResponse = new DataResponse<>();

            dataResponse.setCode(HttpServletResponse.SC_OK);
            dataResponse.setData(dataPoli);

            HandlerResponse.responseSuccessWithData(response, dataResponse);
        } else {
            HandlerResponse.responseNotFound(response, "POLI NOT FOUND");
        }
    }

    @PutMapping("/{id}")
    public void updatePoliById(HttpServletRequest request, HttpServletResponse response,
                               @PathVariable int id,
                               @RequestParam("idPuskesmas") int idPuskesmas,
                               @RequestParam("nama") String nama,
                               @RequestParam("waktuBuka") String waktuBuka,
                               @RequestParam("waktuTutup") String waktuTutup) throws IOException {
        Optional<PuskesmasModel> puskesmasModel = puskesmasService.getPuskesmasById(idPuskesmas);

        // Check if Puskemas is Exist
        if (puskesmasModel.isPresent()) {
            // Fill in Poli Data
            PoliModel poliModel = new PoliModel();

            poliModel.setIdPuskesmas(idPuskesmas);
            poliModel.setNama(nama);
            poliModel.setWaktuBuka(waktuBuka);
            poliModel.setWaktuTutup(waktuTutup);

            // Update the Poli Data
            if (poliService.updatePoliById(id, poliModel) != null) {
                HandlerResponse.responseSuccessOK(response, "POLI UPDATED");
            } else {
                HandlerResponse.responseInternalServerError(response, "POLI NOT FOUND");
            }
        } else {
            HandlerResponse.responseBadRequest(response, "INVALID PUSKESMAS");
        }
    }

    @PatchMapping("/{id}")
    public void patchPoliById(HttpServletRequest request, HttpServletResponse response,
                              @PathVariable int id,
                              @RequestParam(value = "idPuskesmas", required = false) int idPuskesmas,
                              @RequestParam(value = "nama", required = false) String nama,
                              @RequestParam(value = "waktuBuka", required = false) String waktuBuka,
                              @RequestParam(value = "waktuTutup", required = false) String waktuTutup) throws IOException {
        Optional<PuskesmasModel> puskesmasModel = puskesmasService.getPuskesmasById(idPuskesmas);

        // Check if Puskemas is Exist
        if (puskesmasModel.isPresent()) {
            // Fill in Poli Data
            PoliModel poliModel = new PoliModel();

            if (idPuskesmas != 0) {
                // If the 'idPuskesmas' field is not empty then
                // Try to update Poli 'idPuskesmas'
                poliModel.setIdPuskesmas(idPuskesmas);
            }
            if (nama != null && !nama.isEmpty()) {
                // If the 'nama' field is not empty then
                // Try to update Poli 'nama'
                poliModel.setNama(nama);
            }
            if (waktuBuka != null && !waktuBuka.isEmpty()) {
                // If the 'waktuBuka' field is not empty then
                // Try to update Poli 'waktuBuka'
                poliModel.setWaktuBuka(waktuBuka);
            }
            if (waktuTutup != null && !waktuTutup.isEmpty()) {
                // If the 'waktuTutup' field is not empty then
                // Try to update Poli 'waktuTutup'
                poliModel.setWaktuTutup(waktuTutup);
            }

            // Patch the Poli Data
            if (poliService.patchPoliById(id, poliModel) != null) {
                HandlerResponse.responseSuccessOK(response, "POLI UPDATED");
            } else {
                HandlerResponse.responseInternalServerError(response, "POLI NOT FOUND");
            }
        } else {
            HandlerResponse.responseBadRequest(response, "INVALID PUSKESMAS");
        }
    }

    @DeleteMapping("/{id}")
    public void deletePoliById(HttpServletRequest request, HttpServletResponse response,
                               @PathVariable int id) throws IOException {
        if (poliService.deletePoliById(id)) {
            HandlerResponse.responseSuccessOK(response, "POLI DELETED");
        } else {
            HandlerResponse.responseNotFound(response, "POLI NOT FOUND");
        }
    }
}
