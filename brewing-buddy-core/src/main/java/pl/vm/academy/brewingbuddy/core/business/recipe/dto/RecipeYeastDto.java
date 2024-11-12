package pl.vm.academy.brewingbuddy.core.business.recipe.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.NoArgsConstructor;

public record RecipeYeastDto(
        UUID id,
        @NotNull
        @NotBlank
        UUID recipeId,
        @NotNull
        @NotBlank
        UUID yeastId,
        BigDecimal yeastQuantity
) {
        /**
         * Builder has been implemented manually to train and to better understand Builder pattern.
         */
        @NoArgsConstructor
        public static final class Builder {
                private UUID id;
                private UUID recipeId;
                private UUID yeastId;
                private BigDecimal yeastQuantity;

                /**
                 * Sets the ID for the RecipeYeastDto.
                 *
                 * @param id the UUID of the RecipeYeastDto
                 * @return the Builder instance
                 */
                public Builder id(UUID id) {
                        this.id = id;
                        return this;
                }

                /**
                 * Sets the recipe ID for the RecipeYeastDto.
                 *
                 * @param recipeId the UUID of the recipe
                 * @return the Builder instance
                 */
                public Builder recipeId(UUID recipeId) {
                        this.recipeId = recipeId;
                        return this;
                }

                /**
                 * Sets the yeast ID for the RecipeYeastDto.
                 *
                 * @param yeastId the UUID of the yeast
                 * @return the Builder instance
                 */
                public Builder yeastId(UUID yeastId) {
                        this.yeastId = yeastId;
                        return this;
                }

                /**
                 * Sets the yeast quantity for the RecipeYeastDto.
                 *
                 * @param yeastQuantity the quantity of yeast
                 * @return the Builder instance
                 */
                public Builder yeastQuantity(BigDecimal yeastQuantity) {
                        this.yeastQuantity = yeastQuantity;
                        return this;
                }

                /**
                 * Builds and returns a RecipeYeastDto instance.
                 *
                 * @return a new RecipeYeastDto instance
                 */
                public RecipeYeastDto build() {
                        return new RecipeYeastDto(id, recipeId, yeastId, yeastQuantity);
                }
        }

        public static Builder builder() {
                return new Builder();
        }

}