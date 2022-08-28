package jokes.client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import party.client.ChuckNorrisClient;
import party.config.ClientConfig;
import party.model.ChuckNorrisGetJokeResponse;
import party.model.JokeDetail;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ChuckNorrisClientTest {
    private Client client = mock(Client.class);
    private ClientConfig clientConfig = mock(ClientConfig.class);
    private ChuckNorrisClient serviceClient = new ChuckNorrisClient(client, clientConfig);

    private WebTarget webTarget = mock(WebTarget.class);
    private Invocation.Builder builder = mock(Invocation.Builder.class);
    private Response mockRes = mock(Response.class);

    @Test
    public void getJoke_Success() {
        ChuckNorrisGetJokeResponse response = new ChuckNorrisGetJokeResponse();
        response.setTotal(1L);
        JokeDetail detail = new JokeDetail();
        detail.setValue("This is chuck norris testing.");
        response.setResult(Arrays.asList(detail));
        when(clientConfig.getHost()).thenReturn("hosturl");
        when(client.target(anyString())).thenReturn(webTarget);
        when(webTarget.queryParam(anyString(), anyString())).thenReturn(webTarget);
        when(webTarget.request()).thenReturn(builder);
        when(builder.get()).thenReturn(mockRes);
        when(mockRes.readEntity(ChuckNorrisGetJokeResponse.class)).thenReturn(response);

        ChuckNorrisGetJokeResponse jokeRes = serviceClient.getJoke("testing");
        Assertions.assertEquals(1L, jokeRes.getTotal());
        Assertions.assertEquals(detail.getValue(), jokeRes.getResult().get(0).getValue());
    }

    @Test
    public void getJoke_NotFound() {
        ChuckNorrisGetJokeResponse response = new ChuckNorrisGetJokeResponse();
        response.setTotal(0L);
        response.setResult(Collections.emptyList());
        when(clientConfig.getHost()).thenReturn("hosturl");
        when(client.target(anyString())).thenReturn(webTarget);
        when(webTarget.queryParam(anyString(), anyString())).thenReturn(webTarget);
        when(webTarget.request()).thenReturn(builder);
        when(builder.get()).thenReturn(mockRes);
        when(mockRes.readEntity(ChuckNorrisGetJokeResponse.class)).thenReturn(response);

        ChuckNorrisGetJokeResponse jokeRes = serviceClient.getJoke("testing");
        Assertions.assertEquals(0L, jokeRes.getTotal());
        Assertions.assertTrue(jokeRes.getResult().isEmpty());
    }
}
