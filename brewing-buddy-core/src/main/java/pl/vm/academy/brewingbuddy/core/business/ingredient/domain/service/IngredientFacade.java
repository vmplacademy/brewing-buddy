package pl.vm.academy.brewingbuddy.core.business.ingredient.domain.service;

import pl.vm.academy.brewingbuddy.core.business.ingredient.dto.HopDto;
import pl.vm.academy.brewingbuddy.core.business.ingredient.dto.MaltDto;
import pl.vm.academy.brewingbuddy.core.business.ingredient.dto.YeastDto;

import java.util.Set;
import java.util.UUID;
import pl.vm.academy.brewingbuddy.core.business.ingredient.dto.ingredient.ExtraIngredientDto;
import pl.vm.academy.brewingbuddy.core.business.ingredient.dto.ingredient.JuiceDto;
import pl.vm.academy.brewingbuddy.core.business.ingredient.dto.ingredient.LactoseDto;

public interface IngredientFacade {

    /**
     * Returns MaltDto with given ID
     *
     * @param maltId  Given malt ID
     * @return MaltDto
     */
    MaltDto getMaltById(UUID maltId);

    /**
     * Returns Set with all Malts
     *
     * @return Set<MaltDto>
     */
    Set<MaltDto> getAllMalts();

    /**
     * Returns HopDto with given ID
     *
     * @param hopId  Given hop ID
     * @return HopDto
     */
    HopDto getHopById(UUID hopId);

    /**
     * Returns Set with all Hops
     *
     * @return Set<HopDto>
     */
    Set<HopDto> getAllHops();

    /**
     * Returns YeastDto with given ID
     *
     * @param yeastId Given extra ingredient ID
     * @return YeastDto
     */
    YeastDto getYeastById(UUID yeastId);

    /**
     * Returns Set with all yeasts
     *
     * @return Set<YeastDto>
     */
    Set<YeastDto> getAllYeasts();

    /**
     * Returns ExtraIngredientDto with given ID
     *
     * @param extraIngredientId Given extra ingredient ID
     * @return ExtraIngredientDto
     */
    ExtraIngredientDto getExtraIngredientById(UUID extraIngredientId);

    /**
     * Returns Set with all extra ingredients
     *
     * @return Set<ExtraIngredientDto>
     */
    Set<ExtraIngredientDto> getAllExtraIngredients();

    /**
     * Returns Set with all juices
     *
     * @return  Set<JuiceDto>
     */
    Set<JuiceDto> getAllJuices();

    /**
     * Returns Set with all lactose
     *
     * @return  Set<LactoseDto>
     */
    Set<LactoseDto> getAllLactose();


}