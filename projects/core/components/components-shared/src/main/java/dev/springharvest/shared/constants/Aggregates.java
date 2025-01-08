package dev.springharvest.shared.constants;

import java.util.List;

public record Aggregates(
        List<String> count,
        List<String> sum,
        List<String> avg,
        List<String> min,
        List<String> max,
        List<String> groupBy
) {
}
