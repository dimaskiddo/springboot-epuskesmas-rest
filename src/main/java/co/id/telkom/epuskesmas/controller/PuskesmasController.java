package co.id.telkom.epuskesmas.controller;

import co.id.telkom.epuskesmas.model.PuskesmasModel;
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
            DataResponse<Optional<PuskesmasModel>> dataResponse = new DataResponse<>();

            dataResponse.setCode(HttpServletResponse.SC_OK);
            dataResponse.setData(dataPuskesmas);

            HandlerResponse.responseSuccessWithData(response, dataResponse);
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
}
