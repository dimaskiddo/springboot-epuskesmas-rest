package co.id.telkom.epuskesmas.controller;

import co.id.telkom.epuskesmas.response.HandlerResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Tag(name = "Index", description = "Endpoints for Index")
@RequestMapping(value="/", produces={"application/json"})
@RestController
public class IndexController {

    @GetMapping
    public void index(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HandlerResponse.responseSuccessOK(response, "e-Puskesmas REST is running!");
    }
}
