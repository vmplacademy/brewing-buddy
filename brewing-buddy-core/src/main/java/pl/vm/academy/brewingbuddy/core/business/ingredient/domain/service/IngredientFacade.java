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

    public MaltDto getMaltById(UUID maltId);
    public Set<MaltDto> getAllMalts();
    public HopDto getHopById(UUID hopId);
    public Set<HopDto> getAllHops();
    public ExtraIngredientDto getExtraIngredientById(UUID extraIngredientId);
    public Set<ExtraIngredientDto> getAllExtraIngredients();
    public YeastDto getYeastById(UUID yeastId);
    public Set<YeastDto> getAllYeasts();


}
