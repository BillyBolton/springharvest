package dev.springharvest.testing.integration.shared.clients;

import dev.springhavest.common.models.dtos.BaseDTO;
import java.io.Serializable;

public interface IDomainClient<D extends BaseDTO<K>, K extends Serializable> {

}
