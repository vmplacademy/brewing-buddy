package pl.vm.academy.brewingbuddy.core.business.ingredient.domain.service;

import pl.vm.academy.brewingbuddy.core.business.ingredient.dto.ExtraIngredientDto;
import pl.vm.academy.brewingbuddy.core.business.ingredient.dto.HopDto;
import pl.vm.academy.brewingbuddy.core.business.ingredient.dto.MaltDto;
import pl.vm.academy.brewingbuddy.core.business.ingredient.dto.YeastDto;

import java.util.Set;
import java.util.UUID;

public interface IngredientService {
    MaltDto getMaltById(UUID maltId);
    Set<MaltDto> getAllMalts();
    HopDto getHopById(UUID hopId);
    Set<HopDto> getAllHops();
    ExtraIngredientDto getExtraIngredientById(UUID extraIngredientId);
    Set<ExtraIngredientDto> getAllExtraIngredients();
    YeastDto getYeastById(UUID yeastId);
    Set<YeastDto> getAllYeasts();
}
