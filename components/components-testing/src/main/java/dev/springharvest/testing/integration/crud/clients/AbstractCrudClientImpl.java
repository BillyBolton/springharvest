package dev.springharvest.testing.integration.crud.clients;

import dev.springharvest.testing.integration.shared.clients.RestClientImpl;
import dev.springharvest.testing.integration.shared.uri.IUriFactory;
import dev.springharvest.testing.integration.shared.uri.UriFactory;
import dev.springhavest.common.contracts.IClazzAware;
import dev.springhavest.common.models.dtos.BaseDTO;
import dev.springhavest.common.models.entities.BaseEntity;
import io.restassured.response.ValidatableResponse;
import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractCrudClientImpl<D extends BaseDTO<K>, E extends BaseEntity<K>, K extends Serializable>
    implements ICrudClient<D, E, K>, IClazzAware<D> {

  @Getter
  protected Class<D> clazz;
  protected RestClientImpl clientHelper;
  protected IUriFactory uriFactory;

  @Autowired(required = true)
  protected AbstractCrudClientImpl(RestClientImpl clientHelper, UriFactory uriFactory, Class<D> clazz) {
    this.clientHelper = clientHelper;
    this.uriFactory = uriFactory;
    this.clazz = clazz;
  }

  @Override
  public ValidatableResponse findAll() {
    return clientHelper.getAndThen(uriFactory.getFindAllUri());
  }

  @Override
  public List<D> findAllAndExtract() {
    return extractObjects(findAll());
  }

  @Override
  public ValidatableResponse findById(K id) {
    return clientHelper.getAndThen(uriFactory.getFindByIdUri(), id);
  }

  public D findByIdAndExtract(K id) {
    return extractObject(findById(id));
  }

  @Override
  public ValidatableResponse create(D dto) {
    return clientHelper.postAndThen(uriFactory.getPostUri(), dto);
  }

  @Override
  public D createAndExtract(D dto) {
    return extractObject(create(dto));
  }

  @Override
  public ValidatableResponse createAll(List<D> dtos) {
    return clientHelper.postAndThen(uriFactory.getPostAllUri(), dtos);
  }

  @Override
  public List<D> createAllAndExtract(List<D> dtos) {
    return extractObjects(createAll(dtos));
  }

  @Override
  public ValidatableResponse update(K id, D dto) {
    return clientHelper.patchAndThen(uriFactory.getPatchUri(), dto, id);
  }

  @Override
  public D updateAndExtract(K id, D dto) {
    return extractObject(update(id, dto));
  }

  @Override
  public ValidatableResponse updateAll(List<D> dtos) {
    return clientHelper.patchAndThen(uriFactory.getPatchAllUri(), dtos);
  }

  @Override
  public List<D> updateAllAndExtract(List<D> dtos) {
    return extractObjects(updateAll(dtos));
  }

  @Override
  public ValidatableResponse deleteById(K id) {
    return clientHelper.deleteAndThen(uriFactory.getDeleteByIdUri(), id);
  }

  @Override
  public void deleteByIdAndExtract(K id) {
    validateStatus(deleteById(id), 204);
  }

  @Override
  public ValidatableResponse deleteAllByIds(List<K> ids) {
    return clientHelper.deleteAllAndThen(uriFactory.getDeleteAllByIdsUri(), ids);
  }

  @Override
  public void deleteByIdsAndExtract(List<K> ids) {
    validateStatus(deleteAllByIds(ids), 204);
  }

  private void validateStatus(ValidatableResponse response, int expectedStatusCode) {
    response.statusCode(expectedStatusCode);
  }

  private D extractObject(ValidatableResponse response) {
    return response.statusCode(200)
        .extract()
        .body()
        .jsonPath()
        .getObject("", getClazz());
  }

  private List<D> extractObjects(ValidatableResponse response) {
    return response.statusCode(200)
        .extract()
        .body()
        .jsonPath()
        .getList("", getClazz());
  }

}
