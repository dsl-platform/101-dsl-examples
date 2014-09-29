package com.dslplatform.examples;

import static org.junit.Assert.assertEquals;

import com.dslplatform.examples.AggregateCrud.Asteroid;
import com.dslplatform.examples.AggregateCrud.repositories.AsteroidRepository;
import com.dslplatform.patterns.ServiceLocator;
import java.io.IOException;
import java.util.Random;
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
   * Full CRUD test.
   * Demonstrates a CREATE / READ / UPDATE / READ / DELETE cycle.
   */
  @Test
  public void fullCRUD() throws IOException {
    // Initialize an asteroid..
    final String   name     = getRandomAsteroidName();
    final Asteroid asteroid = new Asteroid(name);

    // CREATE.
    asteroid.create();

    // READ, and compare result with original.
    final Asteroid readAsteroid = Asteroid.find(name);
    assertEquals(readAsteroid.getName(), name);

    // Construct a new name, and UPDATE created asteroid with it.
    final String updatedName = getRandomAsteroidName();
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
    // Initialize two asteroids with the same name.
    final String   name      = getRandomAsteroidName();
    final Asteroid asteroid1 = new Asteroid(name);
    final Asteroid asteroid2 = new Asteroid(name);

    // Try to CREATE them both.
    asteroid1.create();
    asteroid2.create();
  }

  @Test (expected = IOException.class)
  public void readMissing() throws IOException {
    // Get a random asteroid name.
    final String name = getRandomAsteroidName();

    // Try to READ an asteroid with that name.
    Asteroid.find(name);
  }

  @Test (expected = IOException.class)
  public void updateMissing() throws IOException {
    // Initialize an asteroid, but do not CREATE it.
    final String   name     = getRandomAsteroidName();
    final Asteroid asteroid = new Asteroid(name);

    // Try to UPDATE it.
    asteroid.update();
  }

  @Test (expected = IOException.class)
  public void deleteMissing() throws IOException {
    // Initialize an asteroid, but do not CREATE it.
    final String   name     = getRandomAsteroidName();
    final Asteroid asteroid = new Asteroid(name);

    // Try to DELETE it.
    asteroid.delete();
  }
}
