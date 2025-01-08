package dev.springharvest.expressions.helpers;

import jakarta.persistence.criteria.Path;

public record PathObject(
        Path<?> path,
        String alias
) {
}
