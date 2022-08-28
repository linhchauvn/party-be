package jokes.service;

import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import party.api.JokeService;
import party.client.ChuckNorrisClient;
import party.model.ChuckNorrisGetJokeResponse;
import party.model.JokeDetail;
import party.model.JokeResponse;
import party.resources.JokeResource;
import party.service.JokeServiceImpl;
import party.util.CacheUtils;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JokeServiceImplTest {
    private ChuckNorrisClient chuckNorrisClient = mock(ChuckNorrisClient.class);
    private CacheUtils cache = mock(CacheUtils.class);
    private JokeService service = new JokeServiceImpl(chuckNorrisClient, cache);

    @Test
    public void getJoke_Success() {
        String keyword = "testing";
        when(cache.addDataToCache(keyword)).thenReturn(true);
        ChuckNorrisGetJokeResponse response = new ChuckNorrisGetJokeResponse();
        response.setTotal(1L);
        JokeDetail detail = new JokeDetail();
        detail.setValue("This is chuck norris testing.");
        response.setResult(Arrays.asList(detail));
        when(chuckNorrisClient.getJoke("testing")).thenReturn(response);

        JokeResponse jokeRes = service.getJoke(keyword);
        Assertions.assertEquals(1L, jokeRes.getTotal());
        Assertions.assertEquals(200, jokeRes.getHttpStatus());
        Assertions.assertEquals(detail.getValue(), jokeRes.getResult().get(0).getValue());
    }

    @Test
    public void getJoke_KeywordLimit() {
        String keyword = "testing";
        when(cache.addDataToCache(keyword)).thenReturn(false);

        JokeResponse jokeRes = service.getJoke(keyword);
        Assertions.assertEquals(429, jokeRes.getHttpStatus());
        Assertions.assertNotNull(jokeRes.getMessage());
    }

    @Test
    public void getJoke_NotFound() {
        String keyword = "testing";
        when(cache.addDataToCache(keyword)).thenReturn(true);
        ChuckNorrisGetJokeResponse response = new ChuckNorrisGetJokeResponse();
        response.setTotal(0L);
        response.setResult(Collections.emptyList());
        when(chuckNorrisClient.getJoke("testing")).thenReturn(response);

        JokeResponse jokeRes = service.getJoke(keyword);
        Assertions.assertEquals(0L, jokeRes.getTotal());
        Assertions.assertEquals(200, jokeRes.getHttpStatus());
        Assertions.assertTrue(jokeRes.getResult().isEmpty());
    }
}
