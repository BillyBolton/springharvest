package dev.springharvest.shared.constants;


/**
 * Helps to specify the sorting direction in a paging request
 *
 * @author Gilles Djawa (NeroNemesis)
 */
public enum SortDirection {
    ASC("A", "ASCENDING"),
    DESC("D", "DESCENDING");

    private String name;
    private String description;

    SortDirection(String name, String description) {
        this.name = name;
        this.description = description;
    }

}


