package co.id.telkom.epuskesmas.controller;

import co.id.telkom.epuskesmas.model.AuthModel;
import co.id.telkom.epuskesmas.response.DataResponse;
import co.id.telkom.epuskesmas.response.HandlerResponse;
import co.id.telkom.epuskesmas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

@RequestMapping(value="/auth", produces={"application/json"})
@RestController
public class AuthController {

    @Autowired
    UserService userService;

    @PostMapping
    public void generateToken(HttpServletRequest request, HttpServletResponse response,
                              @RequestParam("telepon") String telepon,
                              @RequestParam("password") String password) throws IOException {
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
