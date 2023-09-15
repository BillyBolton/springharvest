package dev.springharvest.testing.integration.shared.uri;

import dev.springhavest.common.constants.ControllerEndpoints;

public class UriFactory implements IUriFactory {

    private final String DOMAIN_CONTEXT;

    public UriFactory(String domainContext) {
        DOMAIN_CONTEXT = domainContext;
    }

    public String getDomainContext() {
        return DOMAIN_CONTEXT;
    }

    @Override
    public String getExistsByIdUri() {
        return buildUri(ControllerEndpoints.EXISTS_BY_ID);
    }

    @Override
    public String getFindByIdUri() {
        return buildUri(ControllerEndpoints.FIND_BY_ID);
    }

    @Override
    public String getFindAllUri() {
        return buildUri(ControllerEndpoints.FIND_ALL);
    }

    @Override
    public String getPostUri() {
        return buildUri(ControllerEndpoints.CREATE);
    }

    @Override
    public String getPostAllUri() {
        return buildUri(ControllerEndpoints.CREATE_ALL);
    }

    @Override
    public String getPostSearchUri() {
        return buildUri(ControllerEndpoints.SEARCH);
    }

    @Override
    public String getPostSearchMapUri() {
        return buildUri(ControllerEndpoints.SEARCH_MAP);
    }

    @Override
    public String getPatchUri() {
        return buildUri(ControllerEndpoints.UPDATE_BY_ID);
    }

    @Override
    public String getPatchAllUri() {
        return buildUri(ControllerEndpoints.UPDATE_ALL);
    }

    @Override
    public String getDeleteByIdUri() {
        return buildUri(ControllerEndpoints.DELETE_BY_ID);
    }

    @Override
    public String getDeleteAllByIdsUri() {
        return buildUri(ControllerEndpoints.DELETE_ALL);
    }

    /**
     * It takes a URI and returns a URI
     *
     * @param uri The URI to be called.
     *
     * @return A string that is the domain and the uri.
     */
    protected String buildUri(String uri) {
        return String.format("%s%s", getDomainContext(), uri);
    }

}