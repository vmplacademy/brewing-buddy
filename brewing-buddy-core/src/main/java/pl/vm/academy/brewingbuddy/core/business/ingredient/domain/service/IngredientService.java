package pl.vm.academy.brewingbuddy.core.business.ingredient.domain.service;

import pl.vm.academy.brewingbuddy.core.business.ingredient.dto.HopDto;
import pl.vm.academy.brewingbuddy.core.business.ingredient.dto.MaltDto;
import pl.vm.academy.brewingbuddy.core.business.ingredient.dto.YeastDto;

import java.util.Set;
import java.util.UUID;
import pl.vm.academy.brewingbuddy.core.business.ingredient.dto.ingredient.ExtraIngredientDto;
import pl.vm.academy.brewingbuddy.core.business.ingredient.dto.ingredient.JuiceDto;
import pl.vm.academy.brewingbuddy.core.business.ingredient.dto.ingredient.LactoseDto;

public interface IngredientService {
    MaltDto getMaltById(UUID maltId);

    Set<MaltDto> getAllMalts();

    HopDto getHopById(UUID hopId);

    Set<HopDto> getAllHops();

    YeastDto getYeastById(UUID yeastId);

    Set<YeastDto> getAllYeasts();

    ExtraIngredientDto getExtraIngredientById(UUID extraIngredientId);

    Set<ExtraIngredientDto> getAllExtraIngredients();

    Set<JuiceDto> getAllJuices();

    Set<LactoseDto> getAllLactose();
}