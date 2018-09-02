package com.fepelus.searchzen.contracts;

import com.fepelus.searchzen.search.SearchResults;

public interface Searchable {
    SearchResults search(SearchQuery query);
}
