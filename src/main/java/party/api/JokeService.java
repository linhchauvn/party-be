package party.api;

import party.model.JokeResponse;

public interface JokeService {
    JokeResponse getJoke(String query);
}
