package co.id.telkom.epuskesmas.controller;

import co.id.telkom.epuskesmas.response.HandlerResponse;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Hidden
@RequestMapping(produces={"application/json"})
@RestController
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    public String getErrorPath() {
        return null;
    }

    @RequestMapping("/error")
    public void errorHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer errorCode = Integer.valueOf(status.toString());

            switch (errorCode) {
                case 400:
                    HandlerResponse.responseBadRequest(response, "");
                    break;

                case 401:
                    HandlerResponse.responseUnauthorized(response, "");
                    break;

                case 403:
                    HandlerResponse.responseForbidden(response, "");
                    break;

                case 404:
                    HandlerResponse.responseNotFound(response, "");
                    break;

                case 405:
                    HandlerResponse.responseMethodNotAllowed(response, "");
                    break;

                case 500:
                    HandlerResponse.responseInternalServerError(response, "");
                    break;

                case 502:
                    HandlerResponse.responseBadGateway(response, "");
                    break;

                default:
                    HandlerResponse.responseInternalServerError(response, "ERROR CODE: " + errorCode);
            }
        } else {
            HandlerResponse.responseSuccessOK(response, "NO ERROR");
        }
    }
}
