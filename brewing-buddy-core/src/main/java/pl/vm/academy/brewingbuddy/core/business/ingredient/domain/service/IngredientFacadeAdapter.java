package pl.vm.academy.brewingbuddy.core.business.ingredient.domain.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import pl.vm.academy.brewingbuddy.core.business.ingredient.dto.ExtraIngredientDto;
import pl.vm.academy.brewingbuddy.core.business.ingredient.dto.HopDto;
import pl.vm.academy.brewingbuddy.core.business.ingredient.dto.MaltDto;
import pl.vm.academy.brewingbuddy.core.business.ingredient.dto.YeastDto;

import java.util.Set;
import java.util.UUID;

@Transactional
@RequiredArgsConstructor
public class IngredientFacadeAdapter implements IngredientFacade{

    private final IngredientService ingredientService;

    @Override
    public MaltDto getMaltById(UUID maltId) {
        return ingredientService.getMaltById(maltId);
    }

    @Override
    public Set<MaltDto> getAllMalts() {
        return ingredientService.getAllMalts();
    }

    @Override
    public HopDto getHopById(UUID hopId) {
        return ingredientService.getHopById(hopId);
    }

    @Override
    public Set<HopDto> getAllHops() {
        return ingredientService.getAllHops();
    }

    @Override
    public ExtraIngredientDto getExtraIngredientById(UUID extraIngredientId) {
        return ingredientService.getExtraIngredientById(extraIngredientId);
    }

    @Override
    public Set<ExtraIngredientDto> getAllExtraIngredients() {
        return ingredientService.getAllExtraIngredients();
    }

    @Override
    public YeastDto getYeastById(UUID yeastId) {
        return ingredientService.getYeastById(yeastId);
    }

    @Override
    public Set<YeastDto> getAllYeasts() {
        return ingredientService.getAllYeasts();
    }
}
