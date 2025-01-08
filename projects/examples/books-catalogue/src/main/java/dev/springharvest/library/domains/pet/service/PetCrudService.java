package dev.springharvest.library.domains.pet.service;

import dev.springharvest.crud.domains.base.services.AbstractCrudService;
import dev.springharvest.library.domains.pet.models.entities.PetEntity;
import dev.springharvest.library.domains.pet.persistence.IPetCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class PetCrudService extends AbstractCrudService<PetEntity, UUID> {
    @Autowired
    public PetCrudService(IPetCrudRepository baseRepository){
        super(baseRepository);
    }
}
