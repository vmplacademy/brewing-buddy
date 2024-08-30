package pl.vm.academy.brewingbuddy.core.business.ingredient.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.vm.academy.brewingbuddy.core.business.ingredient.dto.HopDto;
import pl.vm.academy.brewingbuddy.core.business.ingredient.dto.MaltDto;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.service.IngredientFacade;

import java.util.Set;
import pl.vm.academy.brewingbuddy.core.business.ingredient.dto.ingredient.ExtraIngredientDto;
import pl.vm.academy.brewingbuddy.core.business.ingredient.dto.ingredient.JuiceDto;
import pl.vm.academy.brewingbuddy.core.business.ingredient.dto.ingredient.LactoseDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ingredients")
public class IngredientController {
    private final IngredientFacade ingredientFacade;

    @GetMapping("/malts")
    public Set<MaltDto> getAllMalts() {
        return ingredientFacade.getAllMalts();
    }

    @GetMapping("/hops")
    public Set<HopDto> getAllHop() {
        return ingredientFacade.getAllHops();
    }

    @GetMapping("/extraIngredients")
    public Set<ExtraIngredientDto> getAllExtraIngredients() {
        return ingredientFacade.getAllExtraIngredients();
    }

    @GetMapping("/extraIngredients/juices")
    public Set<JuiceDto> getAllJuices() {
        return ingredientFacade.getAllJuices();
    }

    @GetMapping("/extraIngredients/lactose")
    public Set<LactoseDto> getAllLactose() {
        return ingredientFacade.getAllLactose();
    }
}
