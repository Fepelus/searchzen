package com.fepelus.searchzen.storage;

import java.util.List;

public interface SearchQuery {
    String searchTerm();
    boolean searchAllAttributes();
    List<String> limitToAttributes();
}
