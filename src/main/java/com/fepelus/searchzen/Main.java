package com.fepelus.searchzen;

import com.fepelus.searchzen.commandline.CommandLine;
import com.fepelus.searchzen.commandline.ParseCommandLineException;
import com.fepelus.searchzen.commandline.UserInput;
import com.fepelus.searchzen.storage.FileNotFoundException;
import com.fepelus.searchzen.format.Format;
import com.fepelus.searchzen.search.SearchResults;
import com.fepelus.searchzen.storage.JsonParsingException;
import com.fepelus.searchzen.storage.JsonStorage;
import com.fepelus.searchzen.storage.LoadingJsonStorage;

public class Main {
    public static void main(String[] args) {
        try {
            UserInput userInput = new CommandLine(args).parse();
            JsonStorage storage = new LoadingJsonStorage(userInput).load();
            SearchResults results = storage.search(userInput);
            Format format = new Format(storage);
            System.out.println(format.format(results));
        } catch (ParseCommandLineException | JsonParsingException | FileNotFoundException e) {
            System.err.println(e.getMessage());
            System.err.println(e.getCause().getMessage());
            System.exit(1);
        }
    }
}
