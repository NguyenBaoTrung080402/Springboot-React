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
                res.setResult(optionalShoes.get());
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

    @Override
    public DataResponse updateShoes(Long id, Shoes shoes) {
        log.debug("update shoes");
        DataResponse res = new DataResponse();
        try {
            if(!shoesRepository.existsById(id)){
                res.setMessage(Constants.DATA_EMPTY);
                res.setStatus(Constants.ERROR);
                return res;
            }
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
                res.setResult(optionalShoes.get());
                return res;
            }

            Shoes updateShoes = shoesRepository.getShoesById(id);
            updateShoes.setNameProduct(shoes.getNameProduct());
            updateShoes.setBrand(shoes.getBrand());
            updateShoes.setColor(shoes.getColor());
            try {
                Integer.parseInt(String.valueOf(shoes.getSize()));
            } catch (NumberFormatException ex) {
                res.setMessage(Constants.VALUE_INVALID);
                res.setStatus(Constants.ERROR);
                return res;
            }
            updateShoes.setSize(shoes.getSize());
            try {
                Float.parseFloat(String.valueOf(shoes.getPrice()));
            } catch (NumberFormatException ex) {
                res.setMessage(Constants.VALUE_INVALID);
                res.setStatus(Constants.ERROR);
                return res;
            }
            updateShoes.setPrice(shoes.getPrice());

            shoesRepository.save(shoes);
            res.setMessage(Constants.UPDATE_SUCCESS);
            res.setStatus(Constants.SUCCESS);
            res.setResult(shoes);
            return res;
        }catch (Exception ex){
            res.setMessage(Constants.UPDATE_FAIL);
            res.setStatus(Constants.ERROR);
            return res;
        }

    }

    @Override
    public DataResponse findShoesByID(Long id) {
        log.debug("Find Shoes By Name");
        DataResponse res = new DataResponse();
        try{
            if(!shoesRepository.existsById(id)){
                res.setMessage(Constants.DATA_ERROR);
                res.setStatus(Constants.ERROR);
                return res;
            }
            Optional<Shoes> shoesByID = shoesRepository.findById(id);
            res.setMessage(Constants.SUCCESS);
            res.setStatus(Constants.SUCCESS);
            res.setResult(shoesByID);
            return res;
        }catch (Exception ex){
            res.setMessage(Constants.DATA_ERROR);
            res.setStatus(Constants.ERROR);
            return res;
        }
    }

    @Override
    public DataResponse deleteShoes(Long id) {
        log.debug("Delete Shoes");
        DataResponse res = new DataResponse();
        try {
            if(!shoesRepository.existsById(id)){
                res.setMessage(Constants.DELETE_FAIL);
                res.setStatus(Constants.ERROR);
                return res;
            }
            shoesRepository.deleteById(id);
            res.setMessage(Constants.DELETE_SUCCESS);
            res.setStatus(Constants.SUCCESS);
            res.setResult(Constants.SUCCESS);
            return res;
        }catch (Exception ex){
            res.setMessage(Constants.DELETE_FAIL);
            res.setStatus(Constants.ERROR);
            return res;
        }
    }

}
