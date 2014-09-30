package com.dslplatform.examples;

import java.util.Random;

public final class Util {
  private Util() {}

  public static int rand(
      final Random r,
      final int min,
      final int max) {
    return r.nextInt(max - min) + min;
  }

  public static String getRandomAsteroidCode() {
    final Random r = new Random();
    return
        String.format("%04d", rand(r, 0, 10000)) + "-" +
        (char)rand(r, 65, 91) +
        (char)rand(r, 65, 91) +
        (char)rand(r, 65, 91) +
        (char)rand(r, 65, 91) + "-" +
        String.format("%04d", rand(r, 0, 10000));
  }
}
