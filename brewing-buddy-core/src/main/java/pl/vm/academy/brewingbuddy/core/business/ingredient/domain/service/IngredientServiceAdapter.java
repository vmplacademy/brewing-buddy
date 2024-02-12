package pl.vm.academy.brewingbuddy.core.business.ingredient.domain.service;

import lombok.RequiredArgsConstructor;
import pl.vm.academy.brewingbuddy.core.business.ingredient.dto.ExtraIngredientDto;
import pl.vm.academy.brewingbuddy.core.business.ingredient.dto.HopDto;
import pl.vm.academy.brewingbuddy.core.business.ingredient.dto.MaltDto;
import pl.vm.academy.brewingbuddy.core.business.ingredient.dto.YeastDto;
import pl.vm.academy.brewingbuddy.core.business.ingredient.mapper.IngredientCommonMapper;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.model.ExtraIngredient;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.model.Hop;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.model.Malt;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.model.Yeast;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.repository.ExtraIngredientRepository;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.repository.HopRepository;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.repository.MaltRepository;
import pl.vm.academy.brewingbuddy.core.business.ingredient.domain.repository.YeastRepository;


import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
public class IngredientServiceAdapter implements IngredientService{
    private static final  String ERROR_MESSAGE_MALT_ID_NOT_FOUND = "Malt with ID = %s not found in database";
    private static final String ERROR_MESSAGE_HOP_ID_NOT_FOUND = "Hop with ID = %s not found in database";
    private static final String ERROR_MESSAGE_EXTRA_INGREDIENT_ID_NOT_FOUND = "Extra ingredient with ID = %s not found in database";
    private static final String ERROR_MESSAGE_YEAST_ID_NOT_FOUND = "Yeast with ID = %s not found in database";
    private final MaltRepository maltRepository;
    private final HopRepository hopRepository;
    private final ExtraIngredientRepository extraIngredientRepository;
    private final YeastRepository yeastRepository;
    private final IngredientCommonMapper ingredientCommonMapper;

    @Override
    public MaltDto getMaltById(UUID maltId) {
        return ingredientCommonMapper.maltMapper().mapMaltToDto(findMaltById(maltId));
    }

    @Override
    public Set<MaltDto> getAllMalts() {
        return ingredientCommonMapper.maltMapper().mapMaltsToDtos(maltRepository.findAll());
    }

    @Override
    public HopDto getHopById(UUID hopId) {
        return ingredientCommonMapper.hopMapper().mapHopToDto(findHopById(hopId));
    }

    @Override
    public Set<HopDto> getAllHops() {
        return ingredientCommonMapper.hopMapper().mapHopsToDtos(hopRepository.findAll());
    }

    @Override
    public ExtraIngredientDto getExtraIngredientById(UUID extraIngredientId) {
        return ingredientCommonMapper.extraIngredientMapper().mapExtraIngredientToDto(findExtraIngredientById(extraIngredientId));
    }

    @Override
    public Set<ExtraIngredientDto> getAllExtraIngredients() {
        return ingredientCommonMapper.extraIngredientMapper().mapExtraIngredientsToDtos(extraIngredientRepository.findAll());
    }

    @Override
    public YeastDto getYeastById(UUID yeastId) {
        return ingredientCommonMapper.yeastMapper().mapYeastToDto(findYeastById(yeastId));
    }

    @Override
    public Set<YeastDto> getAllYeasts() {
        return ingredientCommonMapper.yeastMapper().mapYeastsToDtos(yeastRepository.findAll());
    }

    private Malt findMaltById (UUID id) {
        return maltRepository.findById(id).orElseThrow(() ->
                new IllegalStateException(String.format(ERROR_MESSAGE_MALT_ID_NOT_FOUND, id)));
    }

    private Hop findHopById (UUID id) {
        return hopRepository.findById(id).orElseThrow(() ->
                new IllegalStateException(String.format(ERROR_MESSAGE_HOP_ID_NOT_FOUND, id)));
    }

    private ExtraIngredient findExtraIngredientById (UUID id) {
        return extraIngredientRepository.findById(id).orElseThrow(() ->
                new IllegalStateException(String.format(ERROR_MESSAGE_EXTRA_INGREDIENT_ID_NOT_FOUND, id)));
    }

    private Yeast findYeastById (UUID id) {
        return yeastRepository.findById(id).orElseThrow(() ->
                new IllegalStateException(String.format(ERROR_MESSAGE_YEAST_ID_NOT_FOUND, id)));
    }
}