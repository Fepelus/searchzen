package com.fepelus.searchzen.commandline;

import org.apache.commons.cli.*;

import java.io.*;


public class CommandLine {
    private final String[] args;

    public CommandLine(String[] args) {
        this.args = args;
    }

    public UserInput parse() throws ParseCommandLineException {

        Options options = specifyTheCommandLineOptions();

        CommandLineParser parser = new DefaultParser();
        try {
            org.apache.commons.cli.CommandLine cmd = parser.parse(options, args);
            if (cmd.getArgList().size() < 1) {
                throw new ParseException("Requires a search term");
            }
            return new UserInput(
                    cmd.hasOption("o"),
                    cmd.hasOption("t"),
                    cmd.hasOption("u"),
                    cmd.getOptionValue("d"),
                    cmd.getOptionValues("a"),
                    cmd.getArgList().get(0) //
            );
        } catch ( ParseException e ) {
            throw new ParseCommandLineException(helpMessage(options), e);
        }
    }

    private Options specifyTheCommandLineOptions() {
        var options = new Options();
        options.addOption("o", "organizations", false, "Match organizations");
        options.addOption("t", "tickets", false, "Match tickets");
        options.addOption("u", "users", false, "Match users");
        options.addOption("d", "directory", true, "Directory that holds the three json files");

        Option attributesOption = new Option("a", "attribute", true, "Which attribute(s) to search for");
        attributesOption.setArgs(Option.UNLIMITED_VALUES);
        options.addOption(attributesOption);

        return options;
    }

    private String helpMessage(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        formatter.printHelp(writer, 70, "searchzen [options] searchterm",
                "", options, 2, 2, "");
        return stringWriter.toString();
    }

}

