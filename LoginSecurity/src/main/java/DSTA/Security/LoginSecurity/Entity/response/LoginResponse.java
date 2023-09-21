package DSTA.Security.LoginSecurity.Entity.response;

import java.io.Serializable;

public class LoginResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String token;
    private final String username;
    private final String name;
    private final String authority;

    public LoginResponse(String token, String username, String name, String authority) {
        this.token = token;
        this.username = username;
        this.name = name;
        this.authority = authority;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getAuthority() {
        return authority;
    }
}
