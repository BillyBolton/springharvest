package dev.springharvest.library.domains.pet.models.entities;

import dev.springharvest.errors.constants.ExceptionMessages;
import dev.springharvest.library.domains.authors.models.entities.AuthorEntity;
import dev.springharvest.library.domains.authors.models.entities.AuthorEntityMetadata;
import dev.springharvest.search.domains.base.models.entities.EntityMetadata;
import dev.springharvest.shared.domains.base.models.entities.BaseEntity_;
//import jdk.internal.jimage.decompressor.StringSharingDecompressorFactory;
//import dev.springharvest.library.domains.pet.models.entities.PetEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;

@Component
public class PetEntityMetadata extends EntityMetadata<PetEntity> {


    @Autowired
    protected PetEntityMetadata(AuthorEntityMetadata authorEntityMetadata){

        super(PetEntity.class,
                Constants.Paths.DOMAIN_SINGULAR,
                Constants.Paths.DOMAIN_PLURAL,
                Constants.Paths.Maps.ROOTS,
                Constants.Paths.Maps.ROOT_PATH_CLAZZ_MAP,
                Constants.Paths.Maps.ROOT_MAPPING_FUNCTIONS,
                authorEntityMetadata.getPathClazzMap());
    }
    private static class Constants {

        private Constants() {
            throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
        }

        private static class Paths {

            private static final String DOMAIN_SINGULAR = "pet";
            private static final String DOMAIN_PLURAL = "pets";
            private static final String PET_ID = DOMAIN_SINGULAR + "." + BaseEntity_.ID;
            //private static StringSharingDecompressorFactory PetEntity_;
            private static final String PET_NAME = DOMAIN_SINGULAR + "." + PetEntity_.NAME;

            private Paths() {
                throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
            }

            private static class Maps {

                private static final Map<String, Class<?>> ROOTS = Map.of(DOMAIN_SINGULAR, PetEntity.class);
                private static final Map<String, Class<?>> ROOT_PATH_CLAZZ_MAP = Map.of(
                        PET_ID, UUID.class,
                        PET_NAME, String.class);

                private static final Map<String, BiConsumer<PetEntity, Object>> ROOT_MAPPING_FUNCTIONS = Map.of(
                        PET_ID, (entity, value) -> entity.setId((UUID) value),
                        PET_NAME, (entity, value) -> entity.setName((String) value)
                );

                private Maps() {
                    throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
                }
            }
        }
    }
}
