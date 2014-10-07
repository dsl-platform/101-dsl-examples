package com.dslplatform.examples;

import com.dslplatform.client.Bootstrap;
import com.dslplatform.client.JsonSerialization;
import com.dslplatform.examples.WorldWonders.Wonder;
import com.dslplatform.examples.WorldWonders.repositories.WonderRepository;
import com.dslplatform.patterns.PersistableRepository;
import com.dslplatform.patterns.ServiceLocator;
import org.slf4j.Logger;

import java.io.*;
import java.util.*;

public class Main {
    private final ServiceLocator locator;
    private final Logger logger;
    private final PersistableRepository<Wonder> wonderRepository;
    private final JsonSerialization jsonSerialization;

    public Main(final ServiceLocator locator) {
        this.locator = locator;
        this.logger = locator.resolve(Logger.class);
        this.wonderRepository = locator.resolve(WonderRepository.class);
        this.jsonSerialization = locator.resolve(JsonSerialization.class);
    }

    public void printWonders(final List<Wonder> wonders) {
        final List<List<String>> table = new ArrayList<List<String>>();
        table.add(Arrays.asList("Type", "English name", "Native names"));

        for (final Wonder wonder : wonders) {
            final List<String> row = new ArrayList<String>();
            row.add(wonder.getIsAncient() ? "Ancient" : "New");
            row.add(wonder.getEnglishName());
            final String nativeNames = wonder.getNativeNames().toString();
            row.add(nativeNames.substring(1, nativeNames.length() - 1));
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
                    System.out.println("  c = count number of world wonders");
                    System.out.println("  l = list world wonders using ASCII art");
                    System.out.println();
                    System.out.println("Adding wonders:");
                    System.out.println("  a = add an \"ancient world\" wonder");
                    System.out.println("  n = add a \"new world\" wonder");
                    System.out.println();
                    System.out.println("Import / cleanup:");
                    System.out.println("  x = delete all wonders");
                    System.out.println("  i = import wonders from resources");
                    System.out.println();
                }

                else if (command.equals("q")) {
                    break;
                }

                else if (command.equals("c")) {
                    /* count() on Repositories returns a Future, so we need to block here */
                    final long count = wonderRepository.count().get();
                    System.out.println("There are " + count + " world wonder(s)");
                }

                else if (command.equals("l")) {
                    /* search() on Repositories returns a Future, so we need to block here */
                    final List<Wonder> wonders = wonderRepository.search().get();
                    printWonders(wonders);
                }

                else if (command.matches("[an]")) {
                    final boolean isAncient = command.equals("a");
                    final String type = isAncient ? "ancient" : "new";

                    String englishName;
                    while (true) {
                        System.out.print("Enter the English name of the " + type + " world wonder: ");
                        englishName = br.readLine().trim();

                        System.out.println("Checking if such wonder already exists ... ");
                        /*
                         * Found on a collection will return an empty collection if all of those elements could not be found.
                         * Find on a single element will throw an exception if that element could not be found.
                         * Thus, we issue a find on a collection of just one element, expecting to get 0 or 1 results.
                         * Find returns a Future, so we need to block here in order to inspect it.
                         */
                        final List<Wonder> lookup = wonderRepository.find(Arrays.asList(englishName)).get();
                        if (lookup.isEmpty()) {
                            break;
                        }
                        System.out.println("That wonder already exist, please enter a new one!");
                    }

                    System.out.print("Enter native name(s) for " + englishName + ": ");

                    final List<String> nativeNames = new ArrayList<String>();
                    for (final String nativeName : br.readLine().split(",+")) {
                        final String trimName = nativeName.trim();
                        if (!trimName.isEmpty()) nativeNames.add(trimName);
                    }

                    final Wonder newWonder = new Wonder()
                            .setEnglishName(englishName)
                            .setNativeNames(nativeNames)
                            .setIsAncient(isAncient);

                    startAt = System.currentTimeMillis();
                    /* Insert on Repositories returns a Future, so we need to block here */
                    wonderRepository.insert(newWonder).get();
                    printWonders(Arrays.asList(newWonder));
                }

                else if (command.equals("x")) {
                    /* In order to delete all entries, we simply do a search for all of them and then delete them */
                    final List<Wonder> wonders = wonderRepository.search().get();
                    /* Node that since both of these operations return Futures, we are awaiting on them via get() */
                    wonderRepository.delete(wonders).get();
                    printWonders(Collections.<Wonder> emptyList());
                }

                else if (command.equals("i")) {
                    final List<Wonder> wonders = jsonSerialization.deserialize(
                            JsonSerialization.buildCollectionType(ArrayList.class, Wonder.class),
                            Main.class.getResourceAsStream("/wonders.json"));
                    try {
                        /*
                         * Since insert() returns a Future, we need to block here to insure an Exception is thrown
                         * in case of duplicate entries. If we did not block, exception would be hidden & lost.
                         */
                        wonderRepository.insert(wonders).get();
                        System.out.println("Imported " + wonders.size() + " wonders!");
                        printWonders(wonders);
                    } catch (final Exception e) {
                        System.out.println("Could not import wonders due to duplicates.");
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
                e.printStackTrace();
            }
        }
    }

    public static void main(final String[] args) throws IOException {
        final ServiceLocator locator = Bootstrap.init(Main.class.getResourceAsStream("/dsl-project.props"));
        new Main(locator).runREPL();
        locator.resolve(java.util.concurrent.ExecutorService.class).shutdown();
    }
}
