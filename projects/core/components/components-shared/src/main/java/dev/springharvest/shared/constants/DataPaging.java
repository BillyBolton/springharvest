package dev.springharvest.shared.constants;

import java.util.List;

/**
 * This record is used to represent data paging
 * It contains various fields to represent different paging conditions
 *
 * @param page
 * @param size
 * @param sortOrders
 *
 * @author Gilles Djawa (NeroNemesis)
 */
public record DataPaging(
        int page, // Represents the current page number
        int size, // Represents the number of items per page
        List<Sort> sortOrders // Represents the order of sorting (e.g., "name,age")
) {}


