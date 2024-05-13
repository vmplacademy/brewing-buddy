package pl.vm.academy.brewingbuddy.core.errorhandling;

public class RecipeAlreadyExistsException extends RuntimeException{
  public RecipeAlreadyExistsException(String message) {
    super(message);
  }

}
