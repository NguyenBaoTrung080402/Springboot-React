package DSTA.Security.LoginSecurity.Controller;

import DSTA.Security.LoginSecurity.Entity.Shoes;
import DSTA.Security.LoginSecurity.Entity.response.DataResponse;
import DSTA.Security.LoginSecurity.Service.ShoesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shoes")
public class ShoesController {
    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    ShoesService shoseService;

    @GetMapping("list-shoes")
    public List<Shoes> getAllShoes(){
        log.debug("List Shoes");
        return shoseService.getAllShoes();
    }
    @PostMapping("create-shoes")
    public DataResponse createShoes(@RequestBody Shoes shoes){
        log.debug("Create shoes");
        DataResponse res = shoseService.createShoes(shoes);
        return res;
    }

}