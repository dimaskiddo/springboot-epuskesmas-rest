package co.id.telkom.epuskesmas.controller;

import co.id.telkom.epuskesmas.model.AuthModel;
import co.id.telkom.epuskesmas.model.UserModel;
import co.id.telkom.epuskesmas.response.DataResponse;
import co.id.telkom.epuskesmas.response.HandlerResponse;
import co.id.telkom.epuskesmas.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Tag(name = "Authentications", description = "Endpoints for Authentications")
@RequestMapping(produces={"application/json"})
@RestController
public class AuthController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/login", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public void authLogin(HttpServletRequest request, HttpServletResponse response,
                          @Valid @NotNull @ModelAttribute String telepon,
                          @Valid @NotNull @ModelAttribute String password) throws IOException {
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

    @PostMapping(value = "/register", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public void authRegister(HttpServletRequest request, HttpServletResponse response,
                             @Valid @NotNull @ModelAttribute String nama,
                             @Valid @NotNull @ModelAttribute String provinsi,
                             @Valid @NotNull @ModelAttribute String kabupaten,
                             @Valid @NotNull @ModelAttribute String telepon,
                             @Valid @NotNull @ModelAttribute String bpjs,
                             @Valid @NotNull @ModelAttribute String password,
                             @Valid @NotNull @ModelAttribute String kelamin,
                             @Valid @NotNull @ModelAttribute String tanggalLahir,
                             @Valid @NotNull @ModelAttribute Double lon,
                             @Valid @NotNull @ModelAttribute Double lat) throws IOException {
        Optional<UserModel> currentUser = userService.getUserByTelepon(telepon);

        if (currentUser.isPresent()) {
            HandlerResponse.responseBadRequest(response, "USER ALREADY REGISTERED");
        } else {
            UserModel userModel = new UserModel();

            // Fill in User Data
            userModel.setNama(nama);
            userModel.setProvinsi(provinsi);
            userModel.setKabupaten(kabupaten);
            userModel.setTelepon(telepon);
            userModel.setBpjs(bpjs);
            userModel.setPassword(password);
            userModel.setKelamin(kelamin);
            userModel.setTanggalLahir(tanggalLahir);
            userModel.setLon(lon);
            userModel.setLat(lat);

            // Insert the User Data
            if (userService.createUser(userModel) != null) {
                HandlerResponse.responseSuccessCreated(response, "USER REGISTERED");
            } else {
                HandlerResponse.responseInternalServerError(response, "FAILED TO REGISTER USER");
            }
        }
    }
}
