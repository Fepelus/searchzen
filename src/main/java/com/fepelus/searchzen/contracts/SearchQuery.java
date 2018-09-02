package com.fepelus.searchzen.contracts;

import java.util.List;

public interface SearchQuery {
    String searchTerm();
    boolean searchAllAttributes();
    List<String> limitToAttributes();
}
