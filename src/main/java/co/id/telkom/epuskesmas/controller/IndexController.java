package co.id.telkom.epuskesmas.controller;

import co.id.telkom.epuskesmas.response.HandlerResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Tag(name = "Index", description = "Endpoints for Index")
@RequestMapping(value="/", produces={"application/json"})
@RestController
public class IndexController {

    @GetMapping
    public void index(HttpServletRequest request, HttpServletResponse response) {
        HandlerResponse.responseSuccessOK(response, "e-Puskesmas REST is running!");
    }
}
