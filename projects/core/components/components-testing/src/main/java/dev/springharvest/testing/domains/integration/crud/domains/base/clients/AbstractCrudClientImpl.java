package dev.springharvest.testing.domains.integration.crud.domains.base.clients;

import dev.springharvest.shared.contracts.IClazzAware;
import dev.springharvest.shared.domains.base.models.dtos.BaseDTO;
import dev.springharvest.testing.domains.integration.crud.domains.base.clients.uri.CrudUriFactory;
import dev.springharvest.testing.domains.integration.crud.domains.base.clients.uri.ICrudUriFactory;
import dev.springharvest.testing.domains.integration.shared.domains.base.clients.RestClientImpl;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.ValidatableResponse;
import jakarta.annotation.Nullable;
import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

public abstract class AbstractCrudClientImpl<D extends BaseDTO<K>, K extends Serializable>
    implements ICrudClient<D, K>, IClazzAware<D> {

  @Getter
  protected Class<D> clazz;

  @Getter
  protected Class<Page<D>> pageClazz;
  protected RestClientImpl clientHelper;
  protected ICrudUriFactory uriFactory;

  @Autowired(required = true)
  protected AbstractCrudClientImpl(RestClientImpl clientHelper, CrudUriFactory uriFactory, Class<D> clazz) {
    this.clientHelper = clientHelper;
    this.uriFactory = uriFactory;
    this.clazz = clazz;
  }

  @Override
  public ValidatableResponse findAll() {
    return findAll(null, null, null);
  }

  @Override
  public ValidatableResponse findAll(@Nullable Integer pageNumber, @Nullable Integer pageSize, @Nullable String sorts) {
    return clientHelper.getAndThen(uriFactory.getFindAllUri(pageNumber, pageSize, sorts));
  }

  @Override
  public List<D> findAllAndExtract(@Nullable Integer pageNumber, @Nullable Integer pageSize, @Nullable String sorts) {
    return extractObjectsFromPage(findAll());
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
  public ValidatableResponse count() {
    return clientHelper.getAndThen(uriFactory.getCountUri());
  }

  @Override
  public int countAndExtract() {
    return count().extract().as(Integer.class);
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

  protected void validateStatus(ValidatableResponse response, int expectedStatusCode) {
    response.statusCode(expectedStatusCode);
  }

  protected List<D> extractObjects(ValidatableResponse response) {
    return response.statusCode(200)
        .extract()
        .body()
        .jsonPath()
        .getList("", getClazz());
  }

  protected D extractObject(ValidatableResponse response) {
    return response.statusCode(200)
        .extract()
        .body()
        .jsonPath()
        .getObject("", getClazz());
  }

  protected List<D> extractObjectsFromPage(ValidatableResponse response) {
    return response.statusCode(200)
        .extract()
        .body()
        .jsonPath()
        .getList("content", getClazz());
  }


}
