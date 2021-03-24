package co.id.telkom.epuskesmas.repository;

import co.id.telkom.epuskesmas.model.UserModel;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<UserModel, Integer> {

    Optional<UserModel> getUserByPhone(String phone);

}
