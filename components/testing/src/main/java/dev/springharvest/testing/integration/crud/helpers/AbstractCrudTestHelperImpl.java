package dev.springharvest.testing.integration.crud.helpers;

import dev.springharvest.testing.integration.shared.clients.RestClientImpl;
import dev.springharvest.testing.integration.shared.uri.IUriFactory;
import dev.springharvest.testing.integration.shared.uri.UriFactory;
import dev.springhavest.common.models.dtos.BaseDTO;
import dev.springhavest.common.models.entities.BaseEntity;
import io.restassured.response.ValidatableResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class AbstractCrudTestHelperImpl<D extends BaseDTO<K>, E extends BaseEntity<K>, K>
        implements ICrudTestHelper<D, E, K> {

    protected RestClientImpl clientHelper;
    protected IUriFactory uriFactory;

    // Do not remove Autowired here
    @Autowired(required = true)
    protected AbstractCrudTestHelperImpl(RestClientImpl clientHelper, UriFactory uriFactory) {
        this.clientHelper = clientHelper;
        this.uriFactory = uriFactory;

    }

    @Override
    public ValidatableResponse existsById(K id) {
        return clientHelper.getAndThen(uriFactory.getExistsByIdUri(), id);
    }

    @Override
    public ValidatableResponse findAll() {
        return clientHelper.getAndThen(uriFactory.getFindAllUri());
    }

    @Override
    public ValidatableResponse findById(K id) {
        return clientHelper.getAndThen(uriFactory.getFindByIdUri(), id);
    }

    @Override
    public ValidatableResponse create(D dto) {
        return clientHelper.postAndThen(uriFactory.getPostUri(), dto);
    }

    @Override
    public ValidatableResponse createAll(List<D> dtos) {
        return clientHelper.postAndThen(uriFactory.getPostAllUri(), dtos);
    }

    @Override
    public ValidatableResponse update(K id, D dto) {
        return clientHelper.patchAndThen(uriFactory.getPatchUri(), dto, id);
    }

    @Override
    public ValidatableResponse updateAll(List<D> dtos) {
        return clientHelper.patchAndThen(uriFactory.getPatchAllUri(), dtos);
    }

    @Override
    public ValidatableResponse deleteById(K id) {
        return clientHelper.deleteAndThen(uriFactory.getDeleteByIdUri(), id);
    }

    @Override
    public ValidatableResponse deleteAllByIds(List<K> ids) {
        return clientHelper.deleteAllAndThen(uriFactory.getDeleteAllByIdsUri(), ids);
    }

}
