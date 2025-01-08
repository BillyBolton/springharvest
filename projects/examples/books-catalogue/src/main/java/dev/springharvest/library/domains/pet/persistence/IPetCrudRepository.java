package dev.springharvest.library.domains.pet.persistence;

import dev.springharvest.crud.domains.base.persistence.ICrudRepository;
import dev.springharvest.library.domains.pet.models.entities.PetEntity;

import java.util.UUID;

public interface IPetCrudRepository extends ICrudRepository<PetEntity, UUID>{

}
