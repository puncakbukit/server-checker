# server-checker
Checks STEEM public RPC servers (full nodes) whether accessible or not.

Usage: java -jar SteemServerChecker.jar [-hV] [-v]... [-a=<account>] [-o=<outputFile>]
                            [-p=<protocol>] [-s=<serverTimeout>]
                            [-t=<steemitTimeout>] [-u=<url>]
  -a, --account=<account>   STEEM account to use when checking the STEEM server.
  -h, --help                Show this help message and exit.
  -o, --output-file=<outputFile>
                            Output file to save list of STEEM servers in JSON format.
  -p, --protocol=<protocol> URL protocol of the STEEM server.
  -s, --serverTimeout=<serverTimeout>
                            Timeout when checking the STEEM server (in ms).
  -t, --steemitTimeout=<steemitTimeout>
                            Timeout when checking the Steemit site (in ms).
  -u, --url=<url>           URL of site that lists STEEM public servers to check.
  -v, --verbose             Verbose mode. Helpful for troubleshooting. Multiple -v
                              options increase the verbosity.
  -V, --version             Print version information and exit.
