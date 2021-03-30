package co.id.telkom.epuskesmas.controller;

import co.id.telkom.epuskesmas.model.DokterModel;
import co.id.telkom.epuskesmas.model.QueueModel;
import co.id.telkom.epuskesmas.response.DataResponse;
import co.id.telkom.epuskesmas.response.HandlerResponse;
import co.id.telkom.epuskesmas.service.DokterService;
import co.id.telkom.epuskesmas.service.QueueService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Optional;

@Tag(name = "Queues", description = "Endpoints for Queues")
@SecurityRequirement(name = "Login")
@RequestMapping(value = "/api/v1/queues", produces = {"application/json"})
@RestController
public class QueueController {

    @Autowired
    private QueueService queueService;

    @Autowired
    private DokterService dokterService;

    @PostMapping(consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public void createQueue(HttpServletRequest request, HttpServletResponse response,
                            @Valid @NotNull @ModelAttribute("idUser") Integer idUser,
                            @Valid @NotNull @ModelAttribute("idPoli") Integer idPoli,
                            @Valid @NotNull @ModelAttribute("idDokter") Integer idDokter,
                            @Valid @NotNull @ModelAttribute("noAntrian") Integer noAntrian,
                            @Valid @NotNull @ModelAttribute("waktuAmbilAntri") String waktuAmbilAntri) throws IOException {

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
                            @RequestParam(value = "idUser", required = false) Integer idUser,
                            @RequestParam(value = "idPoli", required = false) Integer idPoli,
                            @RequestParam(value = "idDokter", required = false) Integer idDokter,
                            @RequestParam(value = "noAntrian", required = false) Integer noAntrian) throws IOException {
        DataResponse<Iterable<QueueModel>> dataResponse = new DataResponse<>();

        dataResponse.setCode(HttpServletResponse.SC_OK);
        if (idUser != null && idUser > 0) {
            dataResponse.setData(queueService.getQueueByIdUser(idUser));
        } else if (idPoli != null && idPoli > 0) {
            dataResponse.setData(queueService.getQueueByIdPoli(idPoli));
        } else if (idDokter != null && idDokter > 0) {
            dataResponse.setData(queueService.getQueueByIdDokter(idDokter));
        } else if (noAntrian != null && noAntrian > 0) {
            dataResponse.setData(queueService.getQueueByNoAntrian(noAntrian));
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
                                @PathVariable Integer id,
                                @Valid @NotNull @ModelAttribute("idUser") Integer idUser,
                                @Valid @NotNull @ModelAttribute("idPoli") Integer idPoli,
                                @Valid @NotNull @ModelAttribute("idDokter") Integer idDokter,
                                @Valid @NotNull @ModelAttribute("noAntrian") Integer noAntrian,
                                @Valid @NotNull @ModelAttribute("waktuAmbilAntri") String waktuAmbilAntri) throws IOException {
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
                               @PathVariable Integer id,
                               @Valid @ModelAttribute("idUser") Integer idUser,
                               @Valid @ModelAttribute("idPoli") Integer idPoli,
                               @Valid @ModelAttribute("idDokter") Integer idDokter,
                               @Valid @ModelAttribute("noAntrian") Integer noAntrian,
                               @Valid @ModelAttribute("waktuAmbilAntri") String waktuAmbilAntri) throws IOException {
        Optional<DokterModel> dokterModel = dokterService.getDokterById(idDokter);

        // Check if Dokter is Exist
        if (dokterModel.isPresent()) {
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
                // If the 'idDokter' field is not empty then
                // Try to update Queue 'idDokter'
                queueModel.setIdDokter(idDokter);
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
        } else {
            HandlerResponse.responseBadRequest(response, "INVALID DOKTER");
        }
    }

    @DeleteMapping("/{id}")
    public void deleteQueueById(HttpServletRequest request, HttpServletResponse response,
                                @PathVariable Integer id) throws IOException {
        if (queueService.deleteQueueById(id)) {
            HandlerResponse.responseSuccessOK(response, "QUEUE DELETED");
        } else {
            HandlerResponse.responseNotFound(response, "QUEUE NOT FOUND");
        }
    }
}
