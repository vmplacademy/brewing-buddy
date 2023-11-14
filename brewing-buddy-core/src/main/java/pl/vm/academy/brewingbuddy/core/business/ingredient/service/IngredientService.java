package pl.vm.academy.brewingbuddy.core.business.ingredient.service;

import pl.vm.academy.brewingbuddy.core.business.ingredient.dto.ExtraIngredientDto;
import pl.vm.academy.brewingbuddy.core.business.ingredient.dto.HopDto;
import pl.vm.academy.brewingbuddy.core.business.ingredient.dto.MaltDto;
import pl.vm.academy.brewingbuddy.core.business.ingredient.dto.YeastDto;

import java.util.Set;
import java.util.UUID;

public interface IngredientService {

    public MaltDto getMaltById(UUID maltId);
    public Set<MaltDto> getAllMalts();
    public HopDto getHopById(UUID hopId);
    public Set<HopDto> getAllHops();
    public ExtraIngredientDto getExtraIngredientById(UUID extraIngredientId);
    public Set<ExtraIngredientDto> getAllExtraIngredients();
    public YeastDto getYeastById(UUID yeastId);
    public Set<YeastDto> getAllYeasts();

}
