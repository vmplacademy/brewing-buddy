package pl.vm.academy.brewingbuddy.core.business.ingredient.domain.service;

import org.hibernate.type.YesNoConverter;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.dto.ExtraIngredientDto;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.dto.HopDto;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.dto.MaltDto;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.dto.YeastDto;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.model.ExtraIngredient;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.model.Yeast;

import java.util.Set;
import java.util.UUID;

public interface IngredientFacade {

    MaltDto getMaltById(UUID maltId);

    Set<MaltDto> getAllMalts();

    HopDto getHopById(UUID hopId);

    Set<HopDto> getAllHops();

    ExtraIngredientDto getExtraIngredientById(UUID extraIngredientId);

    Set<ExtraIngredientDto> getAllExtraIngredients();

    YeastDto getYeastById(UUID yeastId);

    Set<YeastDto> getAllYeasts();
}