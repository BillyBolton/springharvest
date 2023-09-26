package dev.springharvest.library.domains.authors.models.entities;

import dev.springharvest.errors.constants.ExceptionMessages;
import dev.springharvest.search.model.entities.IEntityMetadata;
import java.util.Set;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class AuthorEntityMetadata implements IEntityMetadata<AuthorEntity> {

  public static class Paths {

    public static final String AUTHORS = "authors";

    public static final String AUTHOR = "author";

    public static final String AUTHOR_ID = AUTHOR + ".id";
    public static final String AUTHOR_NAME = AUTHOR + ".name";

    private Paths() {
      throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
    }

  }

  @Override
  public String getDomainName() {
    return Paths.AUTHOR;
  }

  @Override
  public String getDomainName(boolean isPlural) {
    return isPlural ? Paths.AUTHORS : getDomainName();
  }

  @Override
  public Class<?> getClazz(String path) {
    switch (path) {
      case Paths.AUTHOR_ID -> {
        return UUID.class;
      }
      case Paths.AUTHOR_NAME -> {
        return String.class;
      }
      default -> {
        return null;
      }
    }
  }

  @Override
  public Set<String> getRootPaths() {
    return Set.of(Paths.AUTHOR);
  }

  @Override
  public Set<String> getNestedPaths() {
    return Set.of();
  }


}
