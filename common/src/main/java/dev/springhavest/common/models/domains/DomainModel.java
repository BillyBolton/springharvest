package dev.springhavest.common.models.domains;

import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@AllArgsConstructor
public abstract class DomainModel {

    public abstract boolean isEmpty();

}

