package pl.vm.academy.brewingbuddy.core.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.vm.academy.brewingbuddy.core.hop.model.entity.Hop;
import pl.vm.academy.brewingbuddy.core.hop.repository.HopRepository;
import pl.vm.academy.brewingbuddy.core.recipe.model.entity.Recipe;
import pl.vm.academy.brewingbuddy.core.recipe.repository.RecipeRepository;

import java.math.BigDecimal;


@Controller
public class HomeController {
    HopRepository hopRepository;
    public HomeController(HopRepository hopRepository) {
        this.hopRepository = hopRepository;
    }

    @RequestMapping("/")
    @ResponseBody
    public String hello() {

        Hop hop = new Hop();
        hop.setName("nowy chmiel");
        hop.setAlfaAcid(BigDecimal.valueOf(10));
        hopRepository.save(hop);
        return "<h1>Hello, champ!<h1>";

    }

}
