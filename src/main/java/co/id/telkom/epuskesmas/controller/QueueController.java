package co.id.telkom.epuskesmas.controller;

import co.id.telkom.epuskesmas.model.DokterModel;
import co.id.telkom.epuskesmas.model.QueueModel;
import co.id.telkom.epuskesmas.response.DataResponse;
import co.id.telkom.epuskesmas.response.HandlerResponse;
import co.id.telkom.epuskesmas.service.DokterService;
import co.id.telkom.epuskesmas.service.QueueService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@Tag(name = "Queues", description = "Endpoints for Queues")
@SecurityRequirement(name = "Login")
@RequestMapping(value = "/api/v1/queues", produces = {"application/json"})
@RestController
public class QueueController {

    @Autowired
    private DokterService dokterService;

    @Autowired
    private QueueService queueService;

    @PostMapping(consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public void createQueue(HttpServletRequest request, HttpServletResponse response,
                            @Valid @NotNull @ModelAttribute Integer idUser,
                            @Valid @NotNull @ModelAttribute Integer idPoli,
                            @Valid @NotNull @ModelAttribute Integer idDokter,
                            @Valid @NotNull @ModelAttribute Integer noAntrian,
                            @Valid @NotNull @ModelAttribute String waktuAmbilAntri) throws IOException {
        Optional<DokterModel> dokterModel = dokterService.getDokterById(idDokter);

        if (dokterModel.isPresent()) {
            QueueModel queueModel = new QueueModel();

            // Fill in Queue Data
            queueModel.setIdUser(idUser);
            queueModel.setIdPoli(idPoli);
            queueModel.setIdDokter(idDokter);
            queueModel.setNoAntrian(noAntrian);
            queueModel.setWaktuAntrian(waktuAmbilAntri);

            // Insert the Queue Data
            if (queueService.createQueue(queueModel) != null) {
                HandlerResponse.responseSuccessCreated(response, "QUEUE CREATED");
            } else {
                HandlerResponse.responseInternalServerError(response, "FAILED TO CREATE QUEUE");
            }
        } else {
            HandlerResponse.responseBadRequest(response, "INVALID DOKTER");
        }
    }

    @GetMapping
    public void getAllQueue(HttpServletRequest request, HttpServletResponse response,
                            @RequestParam(required = false) Integer idUser,
                            @RequestParam(required = false) Integer idPoli,
                            @RequestParam(required = false) Integer idDokter) throws IOException {
        if (idUser == null) {
            idUser = 0;
        }
        if (idPoli == null) {
            idPoli = 0;
        }
        if (idDokter == null) {
            idDokter = 0;
        }

        DataResponse<Iterable<QueueModel>> dataResponse = new DataResponse<>();

        dataResponse.setCode(HttpServletResponse.SC_OK);
        if (idUser > 0 || idPoli > 0 || idDokter > 0) {
            dataResponse.setData(queueService.getQueueBySearch(idUser, idPoli, idDokter));
        } else {
            dataResponse.setData(queueService.getAllQueue());
        }

        HandlerResponse.responseSuccessWithData(response, dataResponse);
    }

    @GetMapping("/{id}")
    public void getQueueById(HttpServletRequest request, HttpServletResponse response,
                             @PathVariable int id) throws IOException {
        Optional<QueueModel> queueModel = queueService.getQueueById(id);

        if (queueModel.isPresent()) {
            QueueModel dataPoli = queueModel.get();
            DataResponse<QueueModel> dataResponse = new DataResponse<>();

            dataResponse.setCode(HttpServletResponse.SC_OK);
            dataResponse.setData(dataPoli);

            HandlerResponse.responseSuccessWithData(response, dataResponse);
        } else {
            HandlerResponse.responseNotFound(response, "QUEUE NOT FOUND");
        }
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public void updateQueueById(HttpServletRequest request, HttpServletResponse response,
                                @PathVariable int id,
                                @Valid @NotNull @ModelAttribute Integer idUser,
                                @Valid @NotNull @ModelAttribute Integer idPoli,
                                @Valid @NotNull @ModelAttribute Integer idDokter,
                                @Valid @NotNull @ModelAttribute Integer noAntrian,
                                @Valid @NotNull @ModelAttribute String waktuAmbilAntri) throws IOException {
        Optional<DokterModel> dokterModel = dokterService.getDokterById(idDokter);

        if (dokterModel.isPresent()) {
            QueueModel queueModel = new QueueModel();

            // Fill in Queue Data
            queueModel.setIdUser(idUser);
            queueModel.setIdPoli(idPoli);
            queueModel.setIdDokter(idDokter);
            queueModel.setNoAntrian(noAntrian);
            queueModel.setWaktuAntrian(waktuAmbilAntri);

            // Update the Queue Data
            if (queueService.updateQueueById(id, queueModel) != null) {
                HandlerResponse.responseSuccessCreated(response, "QUEUE UPDATED");
            } else {
                HandlerResponse.responseInternalServerError(response, "FAILED TO CREATE QUEUE");
            }
        } else {
            HandlerResponse.responseBadRequest(response, "INVALID DOKTER");
        }
    }

    @PatchMapping(value = "/{id}", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public void patchQueueById(HttpServletRequest request, HttpServletResponse response,
                               @PathVariable int id,
                               @Valid @NotNull @ModelAttribute Integer idUser,
                               @Valid @NotNull @ModelAttribute Integer idPoli,
                               @Valid @NotNull @ModelAttribute Integer idDokter,
                               @Valid @ModelAttribute Integer noAntrian,
                               @Valid @ModelAttribute String waktuAmbilAntri) throws IOException {
        // Fill in Queue Data
        QueueModel queueModel = new QueueModel();

        // Fill in Queue Data
        if (idUser != null && idUser > 0) {
            // If the 'idUser' field is not empty then
            // Try to update Queue 'idUser'
            queueModel.setIdUser(idUser);
        }
        if (idPoli != null && idPoli > 0) {
            // If the 'idPoli' field is not empty then
            // Try to update Queue 'idPoli'
            queueModel.setIdPoli(idPoli);
        }
        if (idDokter != null && idDokter > 0) {
            Optional<DokterModel> dokterModel = dokterService.getDokterById(idDokter);

            // Check if Dokter is Exist
            if (dokterModel.isPresent()) {
                // If the 'idDokter' field is not empty then
                // Try to update Queue 'idDokter'
                queueModel.setIdDokter(idDokter);
            } else {
                HandlerResponse.responseBadRequest(response, "INVALID DOKTER");
                return;
            }
        }
        if (noAntrian != null && noAntrian > 0) {
            // If the 'noAntrian' field is not empty then
            // Try to update Queue 'noAntrian'
            queueModel.setNoAntrian(noAntrian);
        }
        if (waktuAmbilAntri != null && !waktuAmbilAntri.isEmpty()) {
            // If the 'waktuAmbilAntri' field is not empty then
            // Try to update Queue 'waktuAmbilAntri'
            queueModel.setWaktuAntrian(waktuAmbilAntri);
        }

        // Patch the Queue Data
        if (queueService.patchQueueById(id, queueModel) != null) {
            HandlerResponse.responseSuccessOK(response, "QUEUE UPDATED");
        } else {
            HandlerResponse.responseInternalServerError(response, "QUEUE NOT FOUND");
        }
    }

    @DeleteMapping("/{id}")
    public void deleteQueueById(HttpServletRequest request, HttpServletResponse response,
                                @PathVariable int id) throws IOException {
        if (queueService.deleteQueueById(id)) {
            HandlerResponse.responseSuccessOK(response, "QUEUE DELETED");
        } else {
            HandlerResponse.responseNotFound(response, "QUEUE NOT FOUND");
        }
    }
}
