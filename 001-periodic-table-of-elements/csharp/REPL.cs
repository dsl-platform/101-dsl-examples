using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using Newtonsoft.Json;

namespace PeriodicTable
{
    public class REPL
    {
        public void PrintElements(IList<Element> elements)
        {
            IList<IList<String>> table = new List<IList<String>>();
            table.Add(new List<String> { "Number", "Name" });

            foreach (Element element in elements)
            {
                List<String> row = new List<String>();
                row.Add(element.number.ToString());
                row.Add(element.name);
                table.Add(row);
            }

            Console.WriteLine(AsciiTable.Make(table));
        }


        public void RunREPL()
        {
            bool end = false;
            while (!end)
            {
                try
                {
                    Console.Write("Enter command (h for help): ");
                    String line = Console.ReadLine();
                    String command = line.Trim();

                    long startAt = Environment.TickCount;

                    switch (command)
                    {
                        case "h":
                            startAt = ExecHelp(startAt);
                            break;

                        case "q":
                            end = true;
                            break;

                        case "c":
                            startAt = ExecCount(startAt);
                            break;

                        case "l":
                            startAt = ExecList(startAt);
                            break;

                        case "a":
                            startAt = ExecAdd(startAt);
                            break;

                        case "e":
                            startAt = ExecEdit(startAt);
                            break;

                        case "x":
                            startAt = ExecDeleteAll(startAt);
                            break;

                        case "i":
                            startAt = ExecInsert(startAt);
                            break;

                        default:
                            Console.WriteLine("Invalid command: " + command);
                            continue;
                    }

                    long endAt = Environment.TickCount;
                    Console.WriteLine("Took: " + (endAt - startAt) + " ms");
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.ToString());
                }
            }
        }


        private long ExecHelp(long startAt)
        {
            Console.WriteLine("Usage:");
            Console.WriteLine("  h = display this help");
            Console.WriteLine("  q = quit program");
            Console.WriteLine();
            Console.WriteLine("Querying the catalogue:");
            Console.WriteLine("  c = count number of elements");
            Console.WriteLine("  l = list elements using ASCII art");
            Console.WriteLine();
            Console.WriteLine("Adding and editing:");
            Console.WriteLine("  a = add an element");
            Console.WriteLine("  e = edit an element");
            Console.WriteLine();
            Console.WriteLine("Import / cleanup:");
            Console.WriteLine("  x = delete all elements");
            Console.WriteLine("  i = import elements from resources");
            Console.WriteLine();
            return startAt;
        }


        private long ExecCount(long startAt)
        {
            Element[] ee = Element.FindAll();
            Console.WriteLine(ee);
            long count = Element.FindAll().Count();
            if (count == 1)
            {
                Console.WriteLine("There is only 1 element");
            }
            else
            {
                Console.WriteLine("There are " + count + " elements");
            }
            return startAt;
        }


        private long ExecList(long startAt)
        {
            IList<Element> elements = Element.FindAll();
            PrintElements(elements);
            return startAt;
        }


        private long ExecAdd(long startAt)
        {
            int number;
            /* This loop verifies that entered number is an integer, and
             * that it does not already exist in the database. */
            while (true)
            {
                /* This loop verifies that entered number is an integer. */
                while (true)
                {
                    Console.Write("Enter the new element number: ");
                    String numberRaw = Console.ReadLine().Trim();
                    try
                    {
                        number = int.Parse(numberRaw);
                        break;
                    }
                    catch (FormatException)
                    {
                        Console.WriteLine("\"" + numberRaw + "\" does not seem to look like a number, please try again!");
                    }
                    catch (OverflowException)
                    {
                        Console.WriteLine("\"" + numberRaw + "\" seem to be too large, please try again!");
                    }
                }

                Console.WriteLine("Checking if that element already exists...");
                /*
                 * Find on a collection will return an empty collection if all of those elements could not be found.
                 * Find on a single element will throw an exception if that element could not be found.
                 * Thus, we issue a find on a collection of just one element, expecting to get 0 or 1 results.
                 */
                Element[] lookup = Element.Find(new List<String> { number.ToString() });
                if (lookup.Count() == 0)
                {
                    break;
                }
                else
                {
                    Console.WriteLine("That element already exist, please enter a new one!");
                }
            }

            Console.Write("Enter name for element " + number + ": ");
            String name = Console.ReadLine().Trim();

            Element newElement = new Element();
            newElement.number = number;
            newElement.name = name;

            startAt = Environment.TickCount;
            newElement.Create();
            PrintElements(new List<Element> { newElement });
            return startAt;
        }


        private long ExecEdit(long startAt)
        {
            int number;
            Element element;
            /* This loop verifies that entered number is an integer, and
             * that it exist in the database. */
            while (true)
            {
                /* This loop verifies that entered number is an integer. */
                while (true)
                {
                    Console.Write("Enter the existing element number: ");
                    String numberRaw = Console.ReadLine().Trim();
                    try
                    {
                        number = int.Parse(numberRaw);
                        break;
                    }
                    catch (FormatException)
                    {
                        Console.WriteLine("\"" + numberRaw + "\" does not seem to look like a number, please try again!");
                    }
                    catch (OverflowException)
                    {
                        Console.WriteLine("\"" + numberRaw + "\" seem to be too large, please try again!");
                    }
                }

                Console.WriteLine("Checking if that element already exists...");
                /*
                 * Find on a collection will return an empty collection if all of those elements could not be found.
                 * Find on a single element will throw an exception if that element could not be found.
                 * Thus, we issue a find on a collection of just one element, expecting to get 0 or 1 results.
                 */
                Element[] lookup = Element.Find(new List<String> { number.ToString() });
                if (lookup.Count() == 0)
                {
                    Console.WriteLine("That element does not exist, please enter one that does!");
                }
                else
                {
                    element = lookup[0];
                    break;
                }
            }

            Console.Write("Enter new name for element " + number + " (" + element.name + "): ");
            String newName = Console.ReadLine().Trim();

            element.name = newName;

            startAt = Environment.TickCount;
            element.Update();
            PrintElements(new List<Element> { element });
            return startAt;
        }


        private long ExecDeleteAll(long startAt)
        {
            /* In order to delete all entries, we simply do a search for all of them and then delete them */
            Element[] elements = Element.FindAll();

            /* NOTE: This will delete each element individually, and
             * send a separate query towards the database for each
             * element. For 112 elements, it can take a while.
             * See example 2: World Wonders for a better way to delete
             * multiple aggregates.*/
            foreach (Element element in elements)
            {
                element.Delete();
            }
            PrintElements(new List<Element> { });
            return startAt;
        }


        private long ExecInsert(long startAt)
        {
            FileStream   fs = null;
            StreamReader sr = null;
            try
            {
                fs = new FileStream("elements.json", FileMode.Open);
                sr = new StreamReader(fs);
                Element[] elements = JsonConvert.DeserializeObject<Element[]>(sr.ReadToEnd());

                /* NOTE: This will insert each element individually,
                    * and send a separate query towards the database for
                    * each element. For 112 elements, it can take a while.
                    * See example 2: World Wonders for a better way to
                    * insert multiple aggregates.*/
                foreach (Element element in elements)
                {
                    element.Create();
                }
                Console.WriteLine("Imported " + elements.Count() + " elements!");
                PrintElements(elements);
            }
            catch (Exception)
            {
                Console.WriteLine("Could not import elements, probably due to duplicates.");
                Console.WriteLine("Please drop the existing entries first (x).");
            }
            finally
            {
                if (fs != null)
                {
                    fs.Dispose();
                }

                if (sr != null)
                {
                    sr.Dispose();
                }
            }
            return startAt;
        }
    }
}
