package co.id.telkom.epuskesmas.controller;

import co.id.telkom.epuskesmas.model.PoliModel;
import co.id.telkom.epuskesmas.model.PuskesmasModel;
import co.id.telkom.epuskesmas.response.DataResponse;
import co.id.telkom.epuskesmas.response.HandlerResponse;
import co.id.telkom.epuskesmas.service.PoliService;
import co.id.telkom.epuskesmas.service.PuskesmasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

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
                           @RequestParam("waktuBuka") Date waktuBuka,
                           @RequestParam("waktuTutup") Date waktuTutup) throws IOException {
        Optional<PuskesmasModel> puskesmasModel = puskesmasService.getPuskesmasById(idPuskesmas);

        // Check if Puskemas is Exist
        if (puskesmasModel.isPresent()) {
            // Fill in Poli Data
            PoliModel poliModel = new PoliModel();

            poliModel.setIdPuskesmas(idPuskesmas);
            poliModel.setNama(nama);
            poliModel.setWaktuBuka(waktuBuka);
            poliModel.setWaktuTutup(waktuTutup);

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
    public void getPoli(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DataResponse<Iterable<PoliModel>> dataResponse = new DataResponse<>();

        dataResponse.setCode(HttpServletResponse.SC_OK);
        dataResponse.setData(poliService.getPoli());

        HandlerResponse.responseSuccessWithData(response, dataResponse);
    }

    @GetMapping("/{id}")
    public void getPoliById(HttpServletRequest request, HttpServletResponse response,
                            @PathVariable("id") int id) throws IOException {
        Optional<PoliModel> dataPoli = poliService.getPoliById(id);

        if (dataPoli.isPresent()) {
            PoliModel currentPoli = dataPoli.get();
            DataResponse<PoliModel> dataResponse = new DataResponse<>();

            dataResponse.setCode(HttpServletResponse.SC_OK);
            dataResponse.setData(currentPoli);

            HandlerResponse.responseSuccessWithData(response, dataResponse);
        } else {
            HandlerResponse.responseNotFound(response, "POLI NOT FOUND");
        }
    }

    @DeleteMapping("/{id}")
    public void deletePoliById(HttpServletRequest request, HttpServletResponse response,
                               @PathVariable("id") int id) throws IOException {
        if (poliService.deletePoliById(id)) {
            HandlerResponse.responseSuccessOK(response, "POLI DELETED");
        } else {
            HandlerResponse.responseNotFound(response, "POLI NOT FOUND");
        }
    }

}
