UPDATE t_recipe SET recipe_calculated_parameter_recipe_id = NULL WHERE recipe_calculated_parameter_recipe_id IS NOT NULL;

DELETE FROM t_recipe_calculated_parameter;

DELETE FROM t_recipe;