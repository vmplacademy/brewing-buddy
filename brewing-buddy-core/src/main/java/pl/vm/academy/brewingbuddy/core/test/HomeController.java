package pl.vm.academy.brewingbuddy.core.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.model.Hop;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.repository.HopRepository;

import java.math.BigDecimal;


@Controller
public class HomeController {
    HopRepository hopRepository;
    public HomeController(HopRepository hopRepository) {
        this.hopRepository = hopRepository;
    }

    @GetMapping("/")
    @ResponseBody
    public String hello() {

        Hop hop = new Hop();
        hop.setName("nowy chmiel");
        hop.setAlfaAcidInPercentage(BigDecimal.valueOf(10));
        hopRepository.save(hop);
        return "<h1>Hello, champ!<h1>";

    }

}
