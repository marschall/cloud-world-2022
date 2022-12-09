package com.github.marschall.openworld2022;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

class RegexTests {
  
  private static final Pattern TRIM_SPACES = Pattern.compile("^[\\s\\u200c]+|[\\s\\u200c]+$");

  @Test
  void shortInput() {
    String input = "  aa  ";
    String trimed = TRIM_SPACES.matcher(input).replaceAll("");
    assertEquals("aa", trimed);
  }
  
  @Test
  void longInput() {
//    String input = "-- play happy sound for player to enjoy" + " ".repeat(20_000);
    String input = "-- play happy sound for player to enjoy" + repeat(' ', 20_000);
    String trimed = TRIM_SPACES.matcher(input).replaceAll("");
    assertEquals("-- play happy sound for player to enjoy", trimed);
  }
  
  private static String repeat(char c, int repetitions) {
    StringBuilder buffer = new StringBuilder(repetitions);
    for (int i = 0; i < repetitions; i++) {
      buffer.append(c);
    }
    return buffer.toString();
  }

}
