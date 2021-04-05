package co.id.telkom.epuskesmas.controller;

import co.id.telkom.epuskesmas.model.AuthModel;
import co.id.telkom.epuskesmas.model.UserModel;
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
import java.util.Optional;

@Tag(name = "Authentications", description = "Endpoints for Authentications")
@RequestMapping(produces={"application/json"})
@RestController
public class AuthController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/login", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public void authLogin(HttpServletRequest request, HttpServletResponse response,
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

    @PostMapping(value = "/register", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public void authRegister(HttpServletRequest request, HttpServletResponse response,
                             @Valid @NotNull @ModelAttribute("nama") String nama,
                             @Valid @NotNull @ModelAttribute("provinsi") String provinsi,
                             @Valid @NotNull @ModelAttribute("kabupaten") String kabupaten,
                             @Valid @NotNull @ModelAttribute("telepon") String telepon,
                             @Valid @NotNull @ModelAttribute("bpjs") String bpjs,
                             @Valid @NotNull @ModelAttribute("password") String password,
                             @Valid @NotNull @ModelAttribute("kelamin") String kelamin,
                             @Valid @NotNull @ModelAttribute("tanggalLahir") String tanggalLahir,
                             @Valid @NotNull @ModelAttribute("lon") Double lon,
                             @Valid @NotNull @ModelAttribute("lat") Double lat) {
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
