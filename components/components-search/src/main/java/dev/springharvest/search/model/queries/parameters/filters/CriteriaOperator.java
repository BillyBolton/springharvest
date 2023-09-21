package dev.springharvest.search.model.queries.parameters.filters;

/**
 * This enum is used to represent the criteria operator. The criteria operator is used to determine the type of comparison that will be performed on the field
 * and value for a criteria search query.
 */
public enum CriteriaOperator {
  EQUALS("eq"), NOT_EQUALS("neq"), CONTAINS("contains"), NOT_CONTAINS("not_contains"), STARTS_WITH(
      "starts_with"), ENDS_WITH("ends_with"), LESS_THAN("lt"), LESS_THAN_OR_EQUAL("lte"), GREATER_THAN(
      "gt"), GREATER_THAN_OR_EQUAL("gte"), IN("in"), NOT_IN("not_in"), IS_NULL("is_null"), IS_NOT_NULL(
      "is_not_null"), IS_EMPTY("is_empty"), IS_NOT_EMPTY("is_not_empty"), BEFORE("before"), AFTER("after"), LIKE(
      "like");

  private final String operator;

  CriteriaOperator(String operator) {
    this.operator = operator;
  }

  /**
   * This method is used to get the criteria operator from a string.
   *
   * @param operator The string representation of the criteria operator.
   * @return The criteria operator.
   */
  public static CriteriaOperator fromString(String operator) {
    for (CriteriaOperator op : CriteriaOperator.values()) {
      if (op.operator.equalsIgnoreCase(operator)) {
        return op;
      }
    }
    return null;
  }

  /**
   * This method is used to get the string representation of the criteria operator.
   *
   * @return The string representation of the criteria operator.
   */
  public String getValue() {
    return operator;
  }

}
