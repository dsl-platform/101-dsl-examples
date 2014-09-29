package com.dslplatform.examples;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Random;

import com.dslplatform.examples.AggregateCrud.Asteroid;
import com.dslplatform.examples.AggregateCrud.repositories.AsteroidRepository;
import com.dslplatform.patterns.ServiceLocator;
import java.io.IOException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class CrudTest {
  public static ServiceLocator locator;
  public static AsteroidRepository repo;

  @BeforeClass
  public static void init() throws IOException {
    locator = Model.init();
    repo    = locator.resolve(AsteroidRepository.class);
  }

  @AfterClass
  public static void shutdown() throws IOException {
    Model.shutdown(locator);
  }

  public static int rand(
      final Random r,
      final int min,
      final int max) {
    return r.nextInt(max - min) + min;
  }

  public static String getRandomAsteroidName() {
    final Random r = new Random();
    return
        String.format("%04d", rand(r, 0, 10000)) + "-" +
        (char)rand(r, 65, 91) +
        (char)rand(r, 65, 91) +
        (char)rand(r, 65, 91) +
        (char)rand(r, 65, 91) + "-" +
        String.format("%04d", rand(r, 0, 10000));
  }

  /**
   */
  @Test
  public void fullCRUD() throws IOException {
    // Initialize an asteroid we wish to persist and read.
    final String   name     = getRandomAsteroidName();
    final Asteroid asteroid = new Asteroid(name);

    // CREATE.
    asteroid.create();

    // READ, and compare result with original.
    final Asteroid readAsteroid = Asteroid.find(name);
    assertEquals(readAsteroid.getName(), name);

    // Create new name, and UPDATE.
    final String updatedName = name + "-NEW";
    asteroid.setName(updatedName);
    asteroid.update();

    // READ updated asteroid.
    final Asteroid updatedAsteroid = Asteroid.find(updatedName);
    assertEquals(updatedAsteroid.getName(), updatedName);

    // DELETE updated asteroid.
    updatedAsteroid.delete();
  }

  @Test (expected = IOException.class)
  public void duplicateKey() throws IOException {
    // Initialize two asteroids wtih the same name.
    final String   name     = getRandomAsteroidName();
    final Asteroid asteroid1 = new Asteroid(name);
    final Asteroid asteroid2 = new Asteroid(name);

    // Try to persist them both.
    asteroid1.create();
    asteroid2.create();
  }
}
