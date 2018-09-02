package com.fepelus.searchzen.storage;

public class LoadingJsonStorage {

    private final Streams config;

    public LoadingJsonStorage(Streams config)  {
        this.config = config;
    }

    /** Triggers parsing the streams of JSON
     *
     * @return A JsonStorage object .
     * @throws JsonParsingException if the JSON parser could not parse the input
     * @throws FileNotFoundException if the JSON files were not in the directory the system expected them to be in
     */
    public JsonStorage load() throws JsonParsingException, FileNotFoundException {
        var users = new Users(config.usersJsonStream());
        var tickets = new Tickets(config.ticketsJsonStream());
        var organizations = new Organizations(config.organizationsJsonStream());
        return new JsonStorage(users, tickets, organizations);
    }


}
