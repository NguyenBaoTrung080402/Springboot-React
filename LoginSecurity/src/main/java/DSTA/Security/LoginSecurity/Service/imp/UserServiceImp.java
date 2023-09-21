package DSTA.Security.LoginSecurity.Service.imp;

import DSTA.Security.LoginSecurity.Common.Constants;
import DSTA.Security.LoginSecurity.Common.Utils;
import DSTA.Security.LoginSecurity.Common.Validate;
import DSTA.Security.LoginSecurity.Dto.UserRegisterDto;
import DSTA.Security.LoginSecurity.Entity.UserEntity;
import DSTA.Security.LoginSecurity.Entity.response.DataResponse;
import DSTA.Security.LoginSecurity.Repository.UserRepository;
import DSTA.Security.LoginSecurity.Service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserServiceImp implements UserService {
    private final Logger log = LoggerFactory.getLogger(UserServiceImp.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserEntity getAccountByUsername(String username) {
        return userRepository.getAccountByUsername(username);
    }

    @Override
    public UserEntity getAccountById(Long id) {
        return userRepository.getAccountById(id);
    }

    @Override
    public DataResponse register(UserRegisterDto userRegisterDto) {
        log.debug("User Register");
        DataResponse res = new DataResponse();
        try {
            UserEntity user = mapper.map(userRegisterDto, UserEntity.class);
            if(!Validate.validateEmail(user.getEmail())){
                res.setMessage(Constants.REGISTER_FAIL);
                res.setStatus(Constants.ERROR);
                return res;
            }
            if(!Validate.validateTel(user.getTel())){
                res.setMessage(Constants.REGISTER_FAIL);
                res.setStatus(Constants.ERROR);
                return res;
            }
            if(!(Constants.SEEKER.equals(user.getAuthority()) || Constants.EMPLOYEE.equals(user.getAuthority()))){
                res.setMessage(Constants.REGISTER_FAIL);
                res.setStatus(Constants.ERROR);
                return res;
            }
            user.setAuthority(Constants.ROLE + user.getAuthority());

            String password = user.getPassword();
            user.setPassword(passwordEncoder.encode(password));

            res.setStatus(Constants.SUCCESS);
            res.setMessage(Constants.REGISTER_SUCCESS);
            res.setResult(user);
            userRepository.save(user);
            return res;
        } catch (Exception ex) {
            res.setMessage(Constants.REGISTER_FAIL);
            res.setStatus(Constants.ERROR);
            return res;
        }
    }

    @Override
    public UserEntity getAccountLogin() {
        return this.getAccountByUsername(Utils.getCurrentUserLogin().get());
    }


}
