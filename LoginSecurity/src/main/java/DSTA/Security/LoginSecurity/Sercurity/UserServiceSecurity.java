package DSTA.Security.LoginSecurity.Sercurity;

import DSTA.Security.LoginSecurity.Entity.UserEntity;
import DSTA.Security.LoginSecurity.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceSecurity implements UserDetailsService {
    @Lazy
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userService.getAccountByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetails(user);
    }
    public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
        UserEntity user = userService.getAccountById(id);
        if (user == null) {
            throw new UsernameNotFoundException(String.valueOf(id));
        }
        return new CustomUserDetails(user);
    }


}
