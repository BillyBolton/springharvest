package dev.springharvest.library.domains.publishers.models.entities;

import dev.springharvest.errors.constants.ExceptionMessages;
import dev.springharvest.search.model.entities.IEntityMetadata;
import dev.springharvest.shared.domains.publishers.models.entities.PublisherEntity;
import java.util.Set;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class PublisherEntityMetadata implements IEntityMetadata<PublisherEntity> {

  public static class Paths {

    public static final String PUBLISHERS = "publishers";

    public static final String PUBLISHER = "publisher";

    public static final String PUBLISHER_ID = PUBLISHER + ".id";
    public static final String PUBLISHER_NAME = PUBLISHER + ".name";

    private Paths() {
      throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
    }

  }  @Override
  public String getDomainName() {
    return Paths.PUBLISHER;
  }

  @Override
  public String getDomainName(boolean isPlural) {
    return isPlural ? Paths.PUBLISHERS : getDomainName();
  }

  @Override
  public Class<?> getClazz(String path) {
    switch (path) {
      case Paths.PUBLISHER_ID -> {
        return UUID.class;
      }
      case Paths.PUBLISHER_NAME -> {
        return String.class;
      }
      default -> {
        return null;
      }
    }
  }

  @Override
  public Set<String> getRootPaths() {
    return Set.of(Paths.PUBLISHER);
  }

  @Override
  public Set<String> getNestedPaths() {
    return Set.of();
  }



}
