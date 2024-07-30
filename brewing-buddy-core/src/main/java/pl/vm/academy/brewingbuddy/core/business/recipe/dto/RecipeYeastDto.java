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
        @NoArgsConstructor
        public static final class Builder {
                UUID id;
                UUID recipeId;
                UUID yeastId;
                BigDecimal yeastQuantity;

                public Builder id(UUID id) {
                        this.id = id;
                        return this;
                }

                public Builder recipeId(UUID recipeId) {
                        this.recipeId = recipeId;
                        return this;
                }

                public Builder yeastId(UUID yeastId) {
                        this.yeastId = yeastId;
                        return this;
                }

                public Builder yeastQuantity(BigDecimal yeastQuantity) {
                        this.yeastQuantity = yeastQuantity;
                        return this;
                }


                public RecipeYeastDto build() {
                        return new RecipeYeastDto(id, recipeId, yeastId, yeastQuantity);
                }

        }

        public static Builder builder() {
             return new Builder();
        }

}
