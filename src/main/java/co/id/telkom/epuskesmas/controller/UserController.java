package co.id.telkom.epuskesmas.controller;

import co.id.telkom.epuskesmas.model.UserModel;
import co.id.telkom.epuskesmas.response.DataResponse;
import co.id.telkom.epuskesmas.response.HandlerResponse;
import co.id.telkom.epuskesmas.service.UserService;
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

@Tag(name = "Users", description = "Endpoints for Users")
@SecurityRequirement(name = "Login")
@RequestMapping(value="/api/v1/users", produces={"application/json"})
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public void createUser(HttpServletRequest request, HttpServletResponse response,
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
            HandlerResponse.responseBadRequest(response, "USER ALREADY CREATED");
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
                HandlerResponse.responseSuccessCreated(response, "USER CREATED");
            } else {
                HandlerResponse.responseInternalServerError(response, "FAILED TO CREATE USER");
            }
        }
    }

    @GetMapping
    public void getAllUser(HttpServletRequest request, HttpServletResponse response,
                           @RequestParam(required = false) String telepon,
                           @RequestParam(required = false) String nama) throws IOException {
        if (telepon == null) {
            telepon = "";
        }
        if (nama == null) {
            nama = "";
        }

        DataResponse<Iterable<UserModel>> dataResponse = new DataResponse<>();

        dataResponse.setCode(HttpServletResponse.SC_OK);
        if (!telepon.isEmpty() || !nama.isEmpty()) {
            dataResponse.setData(userService.getAllUserBySearch(telepon, nama));
        } else {
            dataResponse.setData(userService.getAllUser());
        }

        HandlerResponse.responseSuccessWithData(response, dataResponse);
    }

    @GetMapping("/{id}")
    public void getUserById(HttpServletRequest request, HttpServletResponse response,
                            @PathVariable int id) throws IOException {
        Optional<UserModel> currentUser = userService.getUserById(id);

        if (currentUser.isPresent()) {
            UserModel dataUser = currentUser.get();
            DataResponse<UserModel> dataResponse = new DataResponse<>();

            dataResponse.setCode(HttpServletResponse.SC_OK);
            dataResponse.setData(dataUser);

            HandlerResponse.responseSuccessWithData(response, dataResponse);
        } else {
            HandlerResponse.responseNotFound(response, "USER NOT FOUND");
        }
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public void updateUserById(HttpServletRequest request, HttpServletResponse response,
                               @PathVariable int id,
                               @Valid @NotNull @ModelAttribute String nama,
                               @Valid @NotNull @ModelAttribute String provinsi,
                               @Valid @NotNull @ModelAttribute String kabupaten,
                               @Valid @NotNull @ModelAttribute String telepon,
                               @Valid @NotNull @ModelAttribute String bpjs,
                               @Valid @ModelAttribute String password,
                               @Valid @NotNull @ModelAttribute String kelamin,
                               @Valid @NotNull @ModelAttribute String tanggalLahir,
                               @Valid @NotNull @ModelAttribute Double lon,
                               @Valid @NotNull @ModelAttribute Double lat) throws IOException {
        UserModel userModel = new UserModel();

        // Fill in User Data
        userModel.setNama(nama);
        userModel.setProvinsi(provinsi);
        userModel.setKabupaten(kabupaten);
        userModel.setTelepon(telepon);
        userModel.setBpjs(bpjs);
        if (password != null && !password.isEmpty()) {
            // If the 'password' field is not empty then
            // Try to update User 'password'
            userModel.setPassword(password);
        }
        userModel.setKelamin(kelamin);
        userModel.setTanggalLahir(tanggalLahir);
        userModel.setLon(lon);
        userModel.setLat(lat);

        // Update the User Data
        if (userService.updateUserById(id, userModel) != null) {
            HandlerResponse.responseSuccessOK(response, "USER UPDATED");
        } else {
            HandlerResponse.responseNotFound(response, "USER NOT FOUND");
        }
    }

    @PatchMapping(value = "/{id}", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public void patchUserById(HttpServletRequest request, HttpServletResponse response,
                              @PathVariable int id,
                              @Valid @ModelAttribute String nama,
                              @Valid @ModelAttribute String provinsi,
                              @Valid @ModelAttribute String kabupaten,
                              @Valid @ModelAttribute String telepon,
                              @Valid @ModelAttribute String bpjs,
                              @Valid @ModelAttribute String password,
                              @Valid @ModelAttribute String kelamin,
                              @Valid @ModelAttribute String tanggalLahir,
                              @Valid @ModelAttribute Double lon,
                              @Valid @ModelAttribute Double lat) throws IOException {
        UserModel userModel = new UserModel();

        // Fill in User Data
        if (nama != null && !nama.isEmpty()) {
            // If the 'nama' field is not empty then
            // Try to update User 'nama'
            userModel.setNama(nama);
        }
        if (provinsi != null && !provinsi.isEmpty()) {
            // If the 'provinsi' field is not empty then
            // Try to update User 'provinsi'
            userModel.setProvinsi(provinsi);
        }
        if (kabupaten != null && !kabupaten.isEmpty()) {
            // If the 'kabupaten' field is not empty then
            // Try to update User 'kabupaten'
            userModel.setKabupaten(kabupaten);
        }
        if (telepon != null && !telepon.isEmpty()) {
            // If the 'telepon' field is not empty then
            // Try to update User 'telepon'
            userModel.setTelepon(telepon);
        }
        if (bpjs != null && !bpjs.isEmpty()) {
            // If the 'bpjs' field is not empty then
            // Try to update User 'bpjs'
            userModel.setBpjs(bpjs);
        }
        if (password != null && !password.isEmpty()) {
            // If the 'password' field is not empty then
            // Try to update User 'password'
            userModel.setPassword(password);
        }
        if (kelamin != null && !kelamin.isEmpty()) {
            // If the 'kelamin' field is not empty then
            // Try to update User 'kelamin'
            userModel.setPassword(kelamin);
        }
        if (tanggalLahir != null) {
            // If the 'tanggalLahir' field is not empty then
            // Try to update User 'tanggalLahir'
            userModel.setTanggalLahir(tanggalLahir);
        }
        if (lon != null && !lon.isNaN()) {
            // If the 'lon' field is not empty then
            // Try to update User 'lon'
            userModel.setLon(lon);
        }
        if (lat != null && !lat.isNaN()) {
            // If the 'lat' field is not empty then
            // Try to update User 'lat'
            userModel.setLat(lat);
        }

        // Patch the User Data
        if (userService.patchUserById(id, userModel) != null) {
            HandlerResponse.responseSuccessOK(response, "USER UPDATED");
        } else {
            HandlerResponse.responseNotFound(response, "USER NOT FOUND");
        }
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(HttpServletRequest request, HttpServletResponse response,
                               @PathVariable int id) throws IOException {
        if (userService.deleteUserById(id)) {
            HandlerResponse.responseSuccessOK(response, "USER DELETED");
        } else {
            HandlerResponse.responseNotFound(response, "USER NOT FOUND");
        }
    }

    @GetMapping("/by/phone/{telepon}")
    public void getUserById(HttpServletRequest request, HttpServletResponse response,
                            @PathVariable String telepon) throws IOException {
        Optional<UserModel> currentUser = userService.getUserByTelepon(telepon);

        if (currentUser.isPresent()) {
            UserModel dataUser = currentUser.get();
            DataResponse<UserModel> dataResponse = new DataResponse<>();

            dataResponse.setCode(HttpServletResponse.SC_OK);
            dataResponse.setData(dataUser);

            HandlerResponse.responseSuccessWithData(response, dataResponse);
        } else {
            HandlerResponse.responseNotFound(response, "USER NOT FOUND");
        }
    }
}
