package co.id.telkom.epuskesmas.service;

import co.id.telkom.epuskesmas.model.UserModel;
import co.id.telkom.epuskesmas.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private ModelMapper modelMapper;

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserModel createUser(UserModel userModel) {
        // TODO
        // - Password Should Be Encrypted Using BCrypt
        return userRepository.save(userModel);
    }

    public Iterable<UserModel> getAllUser() {
        return userRepository.findAll();
    }

    public Iterable<UserModel> getAllUserByTelepon(String telepon) {
        return userRepository.findByTeleponContains(telepon);
    }

    public Optional<UserModel> getUserById(int id) {
        return userRepository.findById(id);
    }

    public Optional<UserModel> getUserByTelepon(String telepon) {
        return userRepository.findByTelepon(telepon);
    }

    public UserModel updateUserById(int id, UserModel userModel) {
        Optional<UserModel> currentUser = userRepository.findById(id);

        if (currentUser.isPresent()) {
            // TODO
            // - Password Should Be Encrypted Using BCrypt
            userModel.setId(id);
            return userRepository.save(userModel);
        }

        return null;
    }

    public UserModel patchUserById(int id, UserModel userModel) {
        Optional<UserModel> currentUser = userRepository.findById(id);

        if (currentUser.isPresent()) {
            UserModel dataUser = currentUser.get();

            if (userModel.getNama() != null && !userModel.getNama().isEmpty()) {
                dataUser.setNama(userModel.getNama());
            }

            if (userModel.getProvinsi() != null && !userModel.getProvinsi().isEmpty()) {
                dataUser.setProvinsi(userModel.getProvinsi());
            }

            if (userModel.getKabupaten() != null && !userModel.getKabupaten().isEmpty()) {
                dataUser.setKabupaten(userModel.getKabupaten());
            }

            if (userModel.getTelepon() != null && !userModel.getTelepon().isEmpty()) {
                dataUser.setTelepon(userModel.getTelepon());
            }

            if (userModel.getBpjs() != null && !userModel.getBpjs().isEmpty()) {
                dataUser.setBpjs(userModel.getBpjs());
            }

            if (userModel.getBpjs() != null && !userModel.getBpjs().isEmpty()) {
                dataUser.setBpjs(userModel.getBpjs());
            }

            if (userModel.getPassword() != null && !userModel.getPassword().isEmpty()) {
                // TODO
                // - Password Should Be Encrypted Using BCrypt
                dataUser.setPassword(userModel.getPassword());
            }

            if (userModel.getKelamin() != null && !userModel.getKelamin().isEmpty()) {
                dataUser.setKelamin(userModel.getKelamin());
            }

            if (userModel.getTanggalLahir() != null) {
                dataUser.setTanggalLahir(userModel.getTanggalLahir());
            }

            if (userModel.getLon() != null && !userModel.getLon().isNaN()) {
                dataUser.setLon(userModel.getLon());
            }

            if (userModel.getLat() != null && !userModel.getLat().isNaN()) {
                dataUser.setLat(userModel.getLat());
            }

            return userRepository.save(dataUser);
        }

        return null;
    }

    public boolean deleteUserById(int id) {
        Optional<UserModel> currentUser = userRepository.findById(id);

        if (currentUser.isPresent()) {
            userRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
