package com.dslplatform.examples;

import com.dslplatform.examples.PeriodicTable.Element;

import com.dslplatform.client.Bootstrap;
import com.dslplatform.client.JsonSerialization;
import com.dslplatform.patterns.ServiceLocator;
import java.io.*;
import java.util.*;

public class Main {
    private final JsonSerialization jsonSerialization;

    public Main(final ServiceLocator locator) {
        this.jsonSerialization = locator.resolve(JsonSerialization.class);
    }

    public void printElements(final List<Element> elements) {
        final List<List<String>> table = new ArrayList<List<String>>();
        table.add(Arrays.asList("Number", "Name"));

        for (final Element element : elements) {
            final List<String> row = new ArrayList<String>();
            row.add(String.valueOf(element.getNumber()));
            row.add(element.getName());
            table.add(row);
        }

        System.out.println(AsciiTable.make(table));
    }

    public void runREPL() {
        final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            try {
                System.out.print("Enter command (h for help): ");
                final String line = br.readLine();
                final String command = line.trim();

                long startAt = System.currentTimeMillis();

                if (command.equals("h")) {
                    System.out.println("Usage:");
                    System.out.println("  h = display this help");
                    System.out.println("  q = quit program");
                    System.out.println();
                    System.out.println("Querying the catalogue:");
                    System.out.println("  c = count number of elements");
                    System.out.println("  l = list elements using ASCII art");
                    System.out.println();
                    System.out.println("Adding elements:");
                    System.out.println("  a = add an element");
                    System.out.println();
                    System.out.println("Import / cleanup:");
                    System.out.println("  x = delete all elements");
                    System.out.println("  i = import elements from resources");
                    System.out.println();
                }

                else if (command.equals("q")) {
                    break;
                }

                else if (command.equals("c")) {
                    final long count = Element.count();
                    if (count == 1) {
                        System.out.println("There is only 1 element");
                    } else {
                        System.out.println("There are " + count + " elements");
                    }
                }

                else if (command.equals("l")) {
                    final List<Element> elements = Element.search();
                    printElements(elements);
                }

                else if (command.equals("a")) {
                    int number;
                    /* This loop verifies that entered number is an integer, and
                     * that it does not already exist in the database. */
                    while (true) {
                        /* This loop verifies that entered number is an integer. */
                        while (true) {
                            System.out.print("Enter the new element number: ");
                            final String numberRaw = br.readLine().trim();
                            try {
                                number = Integer.parseInt(numberRaw);
                                break;
                            } catch (final NumberFormatException e) {
                                System.out.println("\"" + numberRaw + "\" does not seem to look like a number, please try again!");
                            }
                        }

                        System.out.println("Checking if that element already exists...");
                        /*
                         * Find on a collection will return an empty collection if all of those elements could not be found.
                         * Find on a single element will throw an exception if that element could not be found.
                         * Thus, we issue a find on a collection of just one element, expecting to get 0 or 1 results.
                         */
                        final List<Element> lookup = Element.find(Arrays.asList(String.valueOf(number)));
                        if (lookup.isEmpty()) {
                            break;
                        } else {
                            System.out.println("That element already exist, please enter a new one!");
                        }
                    }

                    System.out.print("Enter name for element " + number + ": ");
                    final String name = br.readLine().trim();

                    final Element newElement = new Element()
                            .setNumber(number)
                            .setName(name);

                    startAt = System.currentTimeMillis();
                    newElement.create();
                    printElements(Arrays.asList(newElement));
                }

                else if (command.equals("x")) {
                    /* In order to delete all entries, we simply do a search for all of them and then delete them */
                    final List<Element> elements = Element.search();

                    /* NOTE: This will delete each element individually, and
                     * send a separate query towards the database for each
                     * element. For 112 elements, it can take a while.
                     * See example 2: World Wonders for a better way to delete
                     * multiple aggregates.*/
                    for (final Element element : elements) {
                      element.delete();
                    }
                    printElements(Collections.<Element> emptyList());
                }

                else if (command.equals("i")) {
                    final List<Element> elements = jsonSerialization.deserialize(
                            JsonSerialization.buildCollectionType(ArrayList.class, Element.class),
                            Main.class.getResourceAsStream("/elements.json"));
                    try {
                        /* NOTE: This will insert each element individually,
                         * and send a separate query towards the database for
                         * each element. For 112 elements, it can take a while.
                         * See example 2: World Wonders for a better way to
                         * insert multiple aggregates.*/
                        for (final Element element : elements) {
                          element.create();
                        }
                        System.out.println("Imported " + elements.size() + " elements!");
                        printElements(elements);
                    } catch (final Exception e) {
                        System.out.println("Could not import elements, probably due to duplicates.");
                        System.out.println("Please drop the existing entries first (x).");
                    }
                }

                else {
                    System.out.println("Invalid command: " + command);
                    continue;
                }

                final long endAt = System.currentTimeMillis();
                System.out.println("Took: " + (endAt - startAt) + " ms");
            } catch (final Exception e) {
                e.printStackTrace(System.out);
            }
        }
    }

    public static void main(final String[] args) throws IOException {
        final ServiceLocator locator = Bootstrap.init(Main.class.getResourceAsStream("/dsl-project.props"));
        new Main(locator).runREPL();
        locator.resolve(java.util.concurrent.ExecutorService.class).shutdown();
        System.out.println("Program exiting.");
    }
}
