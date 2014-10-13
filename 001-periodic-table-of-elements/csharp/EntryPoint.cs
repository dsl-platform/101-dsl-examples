using NGS.Client;

namespace PeriodicTable
{
    class EntryPoint
    {
        public static int Main(string[] args)
        {
            var locator = Platform.Start();
            REPL repl = new REPL();
            repl.RunREPL();
            return 0;
        }
    }
}
