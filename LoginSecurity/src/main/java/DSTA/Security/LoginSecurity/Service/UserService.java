package DSTA.Security.LoginSecurity.Service;

import DSTA.Security.LoginSecurity.Dto.UserRegisterDto;
import DSTA.Security.LoginSecurity.Entity.UserEntity;
import DSTA.Security.LoginSecurity.Entity.response.DataResponse;

public interface UserService {
    UserEntity getAccountByUsername(String username);

    UserEntity getAccountById(Long id);

    DataResponse register(UserRegisterDto userRegisterDto);

    UserEntity getAccountLogin();
}
