package pl.vm.academy.brewingbuddy.core.business.ingredient.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.vm.academy.brewingbuddy.core.business.ingredient.dto.HopDto;
import pl.vm.academy.brewingbuddy.core.business.ingredient.dto.MaltDto;
import pl.vm.academy.brewingbuddy.core.business.ingredient.service.IngredientFacade;

import java.util.Set;

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
}
