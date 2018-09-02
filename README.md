# searchzen

Command-line tool for searching provided JSON data.

## Prerequisites

This program requires _Java 10_.

Go to Oracle’s [Download page](http://www.oracle.com/technetwork/java/javase/downloads/jdk10-downloads-4416644.htm),
accept the license agreement and download the Java SE Development Kit 
appropriate for your system.

I expect this program to work on all systems but it has been tested
only on OSX.

### Installing

```
github clone https://github.com/Fepelus/searchzen.git
cp searchzen/distribution/searchzen.tar /tmp
cd /tmp
tar xf searchzen.tar
cd searchzen/bin
./searchzen "Miss Livingston"
```

## Running the tests

```
./gradlew test
```

## Building the distribution tarfile

```
./gradlew distribution
```

## Usage

```
usage: searchzen [options] searchterm
  -a,--attribute <arg>  Which attribute(s) to search for
  -d,--directory <arg>  Directory that holds the three json files
  -o,--organizations    Match organizations
  -t,--tickets          Match tickets
  -u,--users            Match users
```

The program returns each entity that has an attribute with a value that
case-sensitively matches the search term.

The data searched is by default those attached to the requirements
document. These are hard-coded in the application but if you want to
search other data then specify the parameter `--directory /dir/data`,
giving the path to a directory that has an
`organizations.json`, a `tickets.json`and a `users.json` file. 

By default it will search the organizations, tickets and users but you
may indicate that you only want, say, users by adding the `--users`
parameter.

By default the search term is matched against all attributes of the
three different entities. If you wish to match against only certain
attributes — say, the name of the user — then you may add
 `--attribute name`
 
### Example
```
./searchzen --users --tickets --attribute subject alias -- "Miss Joni"
```
will match all users or tickets (but not organisations) that have
either a subject that equals "Miss Joni" or an alias that equals
"Miss Joni". Note that the search term here includes a space and so
needs quotes around it and also that the attribute parameter accepts
more than attribute and so here the two dashes are needed to let bash
know that the attributes are ended and that the search term follows.

## Development discussion


I saw four tasks:
* Parse the command line 
* Read the files and parse the data
* Filter the data by the search term
* Format the output

You can see that pipeline reflected in the `main` method.

Parsing a command line is a task done simply by a library.
The `CommandLine` class runs the Apache commons CLI library
and the `UserInput` converts the output of that into an
object that represents all of the intentions of the user —
that is, a) whether she wants to read the data from a certain
directory or not, and b) what she wants to search for. This is
two jobs really, and the `UserInput` class implements two
interfaces for that reason.

Parsing the JSON into classes I can later search is not as
simple as I would have liked it. The result of parsing is a
`JsonStorage` which holds `Organizations`, `Tickets` and `Users`
which are themselves encapsulating in turn lists of individual
`Organization`, `Ticket` and `User` objects. If a fourth 
entity type were to be added, then this is where the changes
would bloom throughout the code base. A simpler solution may
be to parse each of the three types of entity into a single
class which holds a hashmap of all the attributes. The filtering
by type that I added — and was not specifically requested in
the requirements — would have been harder in that case and I
do like the ability this application has of designing the output
format of each type separately.

Filtering is passed from `JsonStorage` down to the three typed
collections which themselves query each entity. Each entity checks
its own set of all strings against the search term. Only if it
finds one does it do the finicky check for whether the attributes
have been specified by the user.

The formatting is what it is. Perhaps its mother would love the
output but then again perhaps even she wouldn’t. I am not, as
people say, a designer. The datetime fields were left as strings.
I would have parsed them into `DateTime` objects in the formatter
if I were to display them more elegantly.

The test suite includes a test with input files a couple of
megabytes in size and my running times aren’t obnoxious.