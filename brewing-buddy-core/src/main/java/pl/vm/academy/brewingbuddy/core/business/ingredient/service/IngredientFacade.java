package pl.vm.academy.brewingbuddy.core.business.ingredient.service;

import pl.vm.academy.brewingbuddy.core.business.ingredient.dto.ExtraIngredientDto;
import pl.vm.academy.brewingbuddy.core.business.ingredient.dto.HopDto;
import pl.vm.academy.brewingbuddy.core.business.ingredient.dto.MaltDto;
import pl.vm.academy.brewingbuddy.core.business.ingredient.dto.YeastDto;

import java.util.Set;
import java.util.UUID;

public interface IngredientFacade {

    /**
     * Returns MaltDto with given ID
     *
     * @param maltId  Given malt ID
     * @return MaltDto
     */
    public MaltDto getMaltById(UUID maltId);

    /**
     * Returns Set with all Malts
     *
     * @return Set<MaltDto>
     */
    public Set<MaltDto> getAllMalts();

    /**
     * Returns HopDto with given ID
     *
     * @param hopId  Given hop ID
     * @return HopDto
     */
    public HopDto getHopById(UUID hopId);

    /**
     * Returns Set with all Hops
     *
     * @return Set<HopDto>
     */
    public Set<HopDto> getAllHops();

    /**
     * Returns ExtraIngredientDto with given ID
     *
     * @param extraIngredientId Given extra ingredient ID
     * @return ExtraIngredientDto
     */
    public ExtraIngredientDto getExtraIngredientById(UUID extraIngredientId);

    /**
     * Returns Set with all extra ingredients
     *
     * @return Set<ExtraIngredientDto>
     */
    public Set<ExtraIngredientDto> getAllExtraIngredients();

    /**
     * Returns YeastDto with given ID
     *
     * @param yeastId Given extra ingredient ID
     * @return YeastDto
     */
    public YeastDto getYeastById(UUID yeastId);

    /**
     * Returns Set with all yeasts
     *
     * @return Set<YeastDto>
     */
    public Set<YeastDto> getAllYeasts();
}