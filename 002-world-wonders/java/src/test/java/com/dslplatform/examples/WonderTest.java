// package com.dslplatform.examples;
// 
// import static org.junit.Assert.assertEquals;
// 
// import com.dslplatform.examples.AggregateCrud.PeriodicElement;
// import com.dslplatform.patterns.ServiceLocator;
// import java.io.IOException;
// import org.junit.AfterClass;
// import org.junit.BeforeClass;
// import org.junit.Test;
// 
// public class PeriodicElementTest {
//   public static ServiceLocator locator;
// 
//   @BeforeClass
//   public static void init() throws IOException {
//     locator = Model.init();
//   }
// 
//   @AfterClass
//   public static void shutdown() throws IOException {
//     Model.shutdown(locator);
//   }
// 
//   /**
//    * Full CRUD test.
//    * Demonstrates a CREATE / READ / UPDATE / READ / DELETE cycle.
//    */
//   @Test
//   public void fullCRUD() throws IOException {
//     // Initialize an element.
//     //final PeriodicElement element = new PeriodicElement(94, "Plutonium");
//     final PeriodicElement element = new PeriodicElement(94, "Plutonium");
// 
//     // Alternative way to initialize a PeriodicElement:
//     new PeriodicElement()
//         .setNumber(94)
//         .setName("Plutonium");
// 
//     // CREATE.
//     element.create();
// 
//     // READ, and compare result with original.
//     final PeriodicElement readElement = PeriodicElement.find("32");
//     assertEquals(readElement, element);
// 
//     // Construct a new number, and UPDATE created element with it.
//     element.setNumber(92);
//     element.setName("Uranium");
//     element.update();
// 
//     // READ updated element.
//     final PeriodicElement updatedElement = PeriodicElement.find("92");
//     assertEquals(updatedElement, element);
// 
//     // DELETE updated element.
//     updatedElement.delete();
//   }
// 
// 
//   /**
//    * Demonstrates a violation of unique constraint for a primary key.
//    */
//   @Test (expected = IOException.class)
//   public void duplicateKey() throws IOException {
//     // Initialize two elements with the same number.
//     final PeriodicElement element1 = new PeriodicElement(8, "Oxygen");
//     final PeriodicElement element2 = new PeriodicElement(8, "Unobtanium");
// 
//     // Try to CREATE them both.
//     element1.create();
//     element2.create();
//   }
// 
//   /**
//    * Demonstrates an attempt to READ a non-existent PeriodicElement.
//    */
//   @Test (expected = IOException.class)
//   public void readMissing() throws IOException {
//     // Try to READ an element that doesn't exist.
//     PeriodicElement.find(String.valueOf("-200"));
//   }
// 
//   /**
//    * Demonstrates an attempt to UPDATE a non-existent PeriodicElement.
//    */
//   @Test (expected = IOException.class)
//   public void updateMissing() throws IOException {
//     // Initialize an element, but do not CREATE it.
//     final PeriodicElement element = new PeriodicElement(-200, "Weirdium");
// 
//     // Try to UPDATE it.
//     element.update();
//   }
// 
//   /**
//    * Demonstrates an attempt to DELETE a non-existent PeriodicElement.
//    */
//   @Test (expected = IOException.class)
//   public void deleteMissing() throws IOException {
//     // Initialize an element, but do not CREATE it.
//     final PeriodicElement element = new PeriodicElement(-200, "Nonexistium");
// 
//     // Try to DELETE it.
//     element.delete();
//   }
// }
// 