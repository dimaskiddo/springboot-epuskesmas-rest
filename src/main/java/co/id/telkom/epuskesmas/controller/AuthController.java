package co.id.telkom.epuskesmas.controller;

import co.id.telkom.epuskesmas.model.AuthModel;
import co.id.telkom.epuskesmas.response.DataResponse;
import co.id.telkom.epuskesmas.response.HandlerResponse;
import co.id.telkom.epuskesmas.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Base64;

@Tag(name = "Authentications", description = "Endpoints for Authentications")
@RequestMapping(value="/login", produces={"application/json"})
@RestController
public class AuthController {

    @Autowired
    UserService userService;

    @PostMapping(consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public void generateToken(HttpServletRequest request, HttpServletResponse response,
                              @Valid @NotNull @ModelAttribute("telepon") String telepon,
                              @Valid @NotNull @ModelAttribute("password") String password) {
        if (userService.authUserByTeleponAndPassword(telepon, password)) {
            String token = telepon + ":" + password;

            AuthModel authModel = new AuthModel();
            DataResponse<AuthModel> dataResponse = new DataResponse<>();

            authModel.setToken(Base64.getEncoder().encodeToString(token.getBytes()));

            dataResponse.setCode(HttpServletResponse.SC_OK);
            dataResponse.setData(authModel);

            HandlerResponse.responseSuccessWithData(response, dataResponse);
        } else {
            HandlerResponse.responseUnauthorized(response, "");
        }
    }
}
