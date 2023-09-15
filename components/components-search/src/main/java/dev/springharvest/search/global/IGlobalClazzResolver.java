package dev.springharvest.search.global;

import dev.springharvest.search.model.entities.IEntityMetadata;

import java.util.Map;

public interface IGlobalClazzResolver {

    Map<String, IEntityMetadata<?>> getEntityMetadataMap();

    default IEntityMetadata<?> getEntityMetadata(String domain) {
        return getEntityMetadataMap().get(domain);
    }

    default Class<?> getClazz(String path) {

        String domain = "";
        String[] pathContexts = path.split("\\.");
        for (int i = pathContexts.length - 1; i >= 0; i--) {
            String pathContext = pathContexts[i];
            if (getEntityMetadataMap().containsKey(pathContext)) {
                domain = pathContext;
                path = path.substring(path.indexOf(domain));
                break;
            }
        }

        IEntityMetadata<?> entityMetadata = getEntityMetadata(domain);
        Class<?> clazz = entityMetadata.getClazz(path);
        return entityMetadata.getClazz(path);

    }

}
