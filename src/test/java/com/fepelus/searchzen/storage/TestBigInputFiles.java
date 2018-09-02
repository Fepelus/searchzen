package com.fepelus.searchzen.storage;

import com.fepelus.searchzen.UserInputBuilder;
import com.fepelus.searchzen.commandline.UserInput;
import com.fepelus.searchzen.search.SearchResults;
import com.google.common.io.Resources;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestBigInputFiles {
    @Test
    void shouldLoadTheBigFiles() throws JsonParsingException, FileNotFoundException {
        UserInput config = new UserInputBuilder().directory(hugePath()).searchTerm("West").build();
        JsonStorage storage = new LoadingJsonStorage(config).load();
        SearchResults results = storage.search(config);
        assertEquals(192, results.size());
    }

    // Return the absolute pathname of the test/resources/directoryFlag directory
    private String hugePath() {
        return Resources.getResource("larger").getPath();
    }

}
