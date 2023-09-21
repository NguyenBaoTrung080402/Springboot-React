package DSTA.Security.LoginSecurity.Service;

import DSTA.Security.LoginSecurity.Entity.Shoes;
import DSTA.Security.LoginSecurity.Entity.response.DataResponse;

import java.util.List;

public interface ShoesService {

    List<Shoes> getAllShoes();

    DataResponse createShoes(Shoes shoes);
}
