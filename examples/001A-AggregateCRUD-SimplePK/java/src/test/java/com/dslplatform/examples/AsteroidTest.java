package com.dslplatform.examples;

import static org.junit.Assert.assertEquals;

import com.dslplatform.examples.AggregateCrud.Asteroid;
import com.dslplatform.examples.AggregateCrud.repositories.AsteroidRepository;
import com.dslplatform.patterns.ServiceLocator;
import java.io.IOException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class AsteroidTest {
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

  /**
   * Full CRUD test.
   * Demonstrates a CREATE / READ / UPDATE / READ / DELETE cycle.
   */
  @Test
  public void fullCRUD() throws IOException {
    // Initialize an asteroid..
    final String   code     = Util.getRandomAsteroidCode();
    final Asteroid asteroid = new Asteroid(code);

    // CREATE.
    asteroid.create();

    // READ, and compare result with original.
    final Asteroid readAsteroid = Asteroid.find(code);
    assertEquals(readAsteroid.getCode(), code);

    // Construct a new code, and UPDATE created asteroid with it.
    final String updatedCode = Util.getRandomAsteroidCode();
    asteroid.setCode(updatedCode);
    asteroid.update();

    // READ updated asteroid.
    final Asteroid updatedAsteroid = Asteroid.find(updatedCode);
    assertEquals(updatedAsteroid.getCode(), updatedCode);

    // DELETE updated asteroid.
    updatedAsteroid.delete();
  }


  @Test (expected = IOException.class)
  public void duplicateKey() throws IOException {
    // Initialize two asteroids with the same code.
    final String   code      = Util.getRandomAsteroidCode();
    final Asteroid asteroid1 = new Asteroid(code);
    final Asteroid asteroid2 = new Asteroid(code);

    // Try to CREATE them both.
    asteroid1.create();
    asteroid2.create();
  }

  @Test (expected = IOException.class)
  public void readMissing() throws IOException {
    // Get a random asteroid code.
    final String code = Util.getRandomAsteroidCode();

    // Try to READ an asteroid with that code.
    Asteroid.find(code);
  }

  @Test (expected = IOException.class)
  public void updateMissing() throws IOException {
    // Initialize an asteroid, but do not CREATE it.
    final String   code     = Util.getRandomAsteroidCode();
    final Asteroid asteroid = new Asteroid(code);

    // Try to UPDATE it.
    asteroid.update();
  }

  @Test (expected = IOException.class)
  public void deleteMissing() throws IOException {
    // Initialize an asteroid, but do not CREATE it.
    final String   code     = Util.getRandomAsteroidCode();
    final Asteroid asteroid = new Asteroid(code);

    // Try to DELETE it.
    asteroid.delete();
  }

  @Test (expected = IllegalArgumentException.class)
  public void createNullCode() throws IOException {
    // Try to create an asteroid with a NULL code.
    new Asteroid(null);
  }

  @Test (expected = IllegalArgumentException.class)
  public void createLongCode() {
    // Try to create an asteroid with a too long code.
    new Asteroid("A THIRTY-FOUR CHARACTERS LONG CODE");
  }
}
