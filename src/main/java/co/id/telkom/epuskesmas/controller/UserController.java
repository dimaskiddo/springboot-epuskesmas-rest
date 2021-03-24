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
import java.util.Optional;

@RequestMapping(value="/api/v1/users", produces={"application/json"})
@RestController
public class UserController {

    @Autowired
    private UserService userService;

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
            DataResponse<Optional<UserModel>> dataResponse = new DataResponse<>();

            dataResponse.setCode(HttpServletResponse.SC_OK);
            dataResponse.setData(dataUser);

            HandlerResponse.responseSuccessWithData(response, dataResponse);
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
