package dev.springhavest.common.utils;

public class StringUtils {

  public static String capitalizeFirstLetters(String str) {
    String[] words = str.split(" ");
    StringBuilder sb = new StringBuilder();
    for (String word : words) {
      sb.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
    }
    return sb.toString().trim();
  }

}
