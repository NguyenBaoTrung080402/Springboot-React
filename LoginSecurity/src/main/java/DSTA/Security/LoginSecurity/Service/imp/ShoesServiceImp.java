package DSTA.Security.LoginSecurity.Service.imp;

import DSTA.Security.LoginSecurity.Common.Constants;
import DSTA.Security.LoginSecurity.Entity.Shoes;
import DSTA.Security.LoginSecurity.Entity.response.DataResponse;
import DSTA.Security.LoginSecurity.Repository.ShoesRepository;
import DSTA.Security.LoginSecurity.Service.ShoesService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoesServiceImp implements ShoesService {
    private final Logger log = LoggerFactory.getLogger(UserServiceImp.class);

    @Autowired
    private ShoesRepository shoesRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<Shoes> getAllShoes() {
        return shoesRepository.findAll();
    }

    @Override
    public DataResponse createShoes(Shoes shoes) {
        log.debug("Create Shoes");
        DataResponse res = new DataResponse();
        try {
            if (shoes.getPrice().equals("") || shoes.getBrand().equals("")
                    || shoes.getColor().equals("") || shoes.getSize().equals("")
                    || shoes.getNameProduct().equals("")){
                res.setMessage(Constants.ERROR);
                res.setStatus(Constants.ERROR);
                return res;
            }

            Optional<Shoes> optionalShoes = shoesRepository.findByName(shoes.getNameProduct());
            if (optionalShoes.isPresent()) {
                res.setMessage(Constants.SHOES_EXIST);
                res.setStatus(Constants.ERROR);
                return res;
            }
            shoesRepository.save(shoes);
            res.setStatus(Constants.SUCCESS);
            res.setMessage(Constants.SUCCESS);
            res.setResult(shoes);
            return res;
        } catch (Exception ex) {
            res.setMessage(Constants.ERROR);
            res.setStatus(Constants.ERROR);
            return res;
        }
    }

}
