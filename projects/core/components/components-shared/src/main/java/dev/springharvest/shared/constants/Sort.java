package dev.springharvest.shared.constants;

/**
 * Allows to specify the fields to sort by and the direction
 *
 * @param field
 * @param sortDirection
 *
 * @author Gilles Djawa (NeroNemesis)
 */
public record Sort(String field, SortDirection sortDirection) {}
