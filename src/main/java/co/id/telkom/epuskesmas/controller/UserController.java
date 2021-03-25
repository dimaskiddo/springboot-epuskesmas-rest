package co.id.telkom.epuskesmas.controller;

import co.id.telkom.epuskesmas.model.UserModel;
import co.id.telkom.epuskesmas.response.DataResponse;
import co.id.telkom.epuskesmas.response.HandlerResponse;
import co.id.telkom.epuskesmas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@RequestMapping(value="/api/v1/users", produces={"application/json"})
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public void createUser(HttpServletRequest request, HttpServletResponse response,
                           @RequestParam("nama") String nama,
                           @RequestParam("provinsi") String provinsi,
                           @RequestParam("kabupaten") String kabupaten,
                           @RequestParam("phone") String phone,
                           @RequestParam("bpjs") String bpjs,
                           @RequestParam("password") String password,
                           @RequestParam("kelamin") String kelamin,
                           @RequestParam("tanggalLahir") Date tanggalLahir,
                           @RequestParam("lon") Double lon,
                           @RequestParam("lat") Double lat) throws IOException {
        UserModel userModel = new UserModel();

        // Fill in User Data
        userModel.setNama(nama);
        userModel.setProvinsi(provinsi);
        userModel.setKabupaten(kabupaten);
        userModel.setPhone(phone);
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

    @GetMapping
    public void getUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DataResponse<Iterable<UserModel>> dataResponse = new DataResponse<>();

        dataResponse.setCode(HttpServletResponse.SC_OK);
        dataResponse.setData(userService.getUser());

        HandlerResponse.responseSuccessWithData(response, dataResponse);
    }

    @GetMapping("/{id}")
    public void getUserById(HttpServletRequest request, HttpServletResponse response,
                            @PathVariable int id) throws IOException {
        Optional<UserModel> dataUser = userService.getUserById(id);

        if (dataUser.isPresent()) {
            UserModel currentUser = dataUser.get();
            DataResponse<UserModel> dataResponse = new DataResponse<>();

            dataResponse.setCode(HttpServletResponse.SC_OK);
            dataResponse.setData(currentUser);

            HandlerResponse.responseSuccessWithData(response, dataResponse);
        } else {
            HandlerResponse.responseNotFound(response, "USER NOT FOUND");
        }

    }

    @PutMapping("/{id}")
    public void updateUserById(HttpServletRequest request, HttpServletResponse response,
                               @PathVariable int id,
                               @RequestParam("nama") String nama,
                               @RequestParam("provinsi") String provinsi,
                               @RequestParam("kabupaten") String kabupaten,
                               @RequestParam("phone") String phone,
                               @RequestParam("bpjs") String bpjs,
                               @RequestParam(value = "password", required = false) String password,
                               @RequestParam("kelamin") String kelamin,
                               @RequestParam("tanggalLahir") Date tanggalLahir,
                               @RequestParam("lon") Double lon,
                               @RequestParam("lat") Double lat) throws IOException {
        UserModel userModel = new UserModel();

        // Fill in User Data
        userModel.setNama(nama);
        userModel.setProvinsi(provinsi);
        userModel.setKabupaten(kabupaten);
        userModel.setPhone(phone);
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

    @PatchMapping("/{id}")
    public void patchUserById(HttpServletRequest request, HttpServletResponse response,
                              @PathVariable int id,
                              @RequestParam(value = "nama", required = false) String nama,
                              @RequestParam(value = "provinsi", required = false) String provinsi,
                              @RequestParam(value = "kabupaten", required = false) String kabupaten,
                              @RequestParam(value = "phone",required = false) String phone,
                              @RequestParam(value = "bpjs", required = false) String bpjs,
                              @RequestParam(value = "password", required = false) String password,
                              @RequestParam(value = "kelamin", required = false) String kelamin,
                              @RequestParam(value = "tanggalLahir", required = false) Date tanggalLahir,
                              @RequestParam(value = "lon", required = false) Double lon,
                              @RequestParam(value = "lat", required = false) Double lat) throws IOException {
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
        if (phone != null && !phone.isEmpty()) {
            // If the 'phone' field is not empty then
            // Try to update User 'phone'
            userModel.setPhone(phone);
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
}
