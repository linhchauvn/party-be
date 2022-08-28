package jokes.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import party.model.JokeDetail;
import party.model.JokeResponse;

import java.util.Arrays;

public class JokeResponseTest {
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void serializesToJSON() throws Exception {
        final JokeResponse response = new JokeResponse();
        response.setTotal(1L);
        JokeDetail jokeDetail = new JokeDetail();
        jokeDetail.setCreatedAt("2020-01-05 13:42:25.905626");
        jokeDetail.setIconUrl("https://assets.chucknorris.host/img/avatar/chuck-norris.png");
        jokeDetail.setId("J_GHTvahTuaNlqw9w4v3oQ");
        jokeDetail.setUrl("https://api.chucknorris.io/jokes/J_GHTvahTuaNlqw9w4v3oQ");
        jokeDetail.setValue("Chuck Norris likes Apples for dessert ... especially Macbook Pros and Airbooks");
        response.setResult(Arrays.asList(jokeDetail));

        final String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(getClass().getResource("/fixtures/jokeResponse.json"), JokeResponse.class));

        Assertions.assertEquals(expected, MAPPER.writeValueAsString(response));
    }

    @Test
    public void deSerializesToJSON() throws Exception {
        final JokeResponse response = new JokeResponse();
        response.setTotal(1L);
        JokeDetail jokeDetail = new JokeDetail();
        jokeDetail.setCreatedAt("2020-01-05 13:42:25.905626");
        jokeDetail.setIconUrl("https://assets.chucknorris.host/img/avatar/chuck-norris.png");
        jokeDetail.setId("J_GHTvahTuaNlqw9w4v3oQ");
        jokeDetail.setUrl("https://api.chucknorris.io/jokes/J_GHTvahTuaNlqw9w4v3oQ");
        jokeDetail.setValue("Chuck Norris likes Apples for dessert ... especially Macbook Pros and Airbooks");
        response.setResult(Arrays.asList(jokeDetail));

        JokeResponse actual = MAPPER.readValue(getClass().getResource("/fixtures/jokeResponse.json"), JokeResponse.class);
        Assertions.assertEquals(response.getTotal(), actual.getTotal());
        Assertions.assertEquals(response.getResult().size(), actual.getResult().size());
    }
}
