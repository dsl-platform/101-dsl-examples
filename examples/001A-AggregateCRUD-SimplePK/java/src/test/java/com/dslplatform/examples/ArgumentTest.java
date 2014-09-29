package com.dslplatform.examples;

import com.dslplatform.examples.AggregateCrud.Asteroid;
import java.io.IOException;
import org.junit.Test;

public class ArgumentTest {
  @Test (expected = IllegalArgumentException.class)
  public void createNullName() throws IOException {
    // Try to create an asteroid with a NULL name.
    new Asteroid(null);
  }

  @Test (expected = IllegalArgumentException.class)
  public void createLongName() {
    // Try to create an asteroid with a too long name.
    new Asteroid("A THIRTY-SEVEN CHARACTERS LONG NAME");
  }
}
