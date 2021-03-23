package co.id.telkom.epuskesmas.controller;

import co.id.telkom.epuskesmas.response.HandlerResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping(value="/", produces={"application/json"})
@RestController
public class IndexController {

    @GetMapping
    public void index(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HandlerResponse.responseSuccessOK(response, "e-Puskemas REST is running!");
    }
}
