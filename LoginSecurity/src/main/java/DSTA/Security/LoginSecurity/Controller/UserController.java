package DSTA.Security.LoginSecurity.Controller;

import DSTA.Security.LoginSecurity.Common.Constants;
import DSTA.Security.LoginSecurity.Dto.UserLogin;
import DSTA.Security.LoginSecurity.Dto.UserRegisterDto;
import DSTA.Security.LoginSecurity.Entity.UserEntity;
import DSTA.Security.LoginSecurity.Entity.response.DataResponse;
import DSTA.Security.LoginSecurity.Entity.response.LoginResponse;
import DSTA.Security.LoginSecurity.Sercurity.CustomUserDetails;
import DSTA.Security.LoginSecurity.Sercurity.JwtTokenProvider;
import DSTA.Security.LoginSecurity.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("register")
    public DataResponse register(@RequestBody UserRegisterDto userRegisterDto) throws Exception {
        log.debug("Request Register");
        DataResponse res = userService.register(userRegisterDto);
        return res;
    }
    @PostMapping("login")
    public DataResponse login(@RequestBody UserLogin userLogin) throws Exception{
        log.debug("Request Login");
        DataResponse res = new DataResponse();
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLogin.getUsername(), userLogin.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
            res.setStatus(Constants.SUCCESS);
            res.setMessage(Constants.LOGIN_SUCCESS);
            UserEntity user = userService.getAccountLogin();
            res.setResult(new LoginResponse(jwt, user.getUsername(), user.getName(),user.getAuthority()));
            return res;
        }catch (Exception ex){
            res.setMessage(Constants.LOGIN_FAIL);
            res.setStatus(Constants.ERROR);
            return res;
        }
    }
    @PutMapping("update-user/{id}")
    public DataResponse updateInformation(@PathVariable Long id, @RequestBody UserEntity user){
        log.debug("update information");
        DataResponse res = userService.updateInformationUser(id, user);
        return res;
    }
    @GetMapping("find-user/{id}")
    public DataResponse findID(@PathVariable Long id){
        log.debug("Find ID");
        DataResponse res = userService.getUserById(id);
        return res;
    }
}
