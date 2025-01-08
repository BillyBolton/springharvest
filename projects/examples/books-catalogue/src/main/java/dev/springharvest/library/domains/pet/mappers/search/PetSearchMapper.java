package dev.springharvest.library.domains.pet.mappers.search;

//import ch.qos.logback.core.model.ComponentModel;
import dev.springharvest.library.domains.pet.models.entities.PetEntity;
import dev.springharvest.library.domains.pet.models.entities.PetEntityMetadata;
import dev.springharvest.library.domains.pet.models.queries.PetFilterBO;
import dev.springharvest.library.domains.pet.models.queries.PetFilterDTO;
import dev.springharvest.library.domains.pet.models.queries.PetFilterRequestBO;
import dev.springharvest.library.domains.pet.models.queries.PetFilterRequestDTO;
import dev.springharvest.library.global.GlobalClazzResolver;
import dev.springharvest.search.domains.base.mappers.queries.ISearchMapper;
//import lombok.Builder;
import org.mapstruct.Builder;
import lombok.Getter;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Getter
@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public abstract class PetSearchMapper implements ISearchMapper<PetEntity, UUID, PetFilterRequestDTO, PetFilterRequestBO, PetFilterDTO, PetFilterBO>{

    @Autowired
    private GlobalClazzResolver globalClazzResolver;


    @Autowired
    private PetEntityMetadata entityMetadata;
}
