package jokes.resources;

import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import party.api.JokeService;
import party.model.JokeDetail;
import party.model.JokeResponse;
import party.resources.JokeResource;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(DropwizardExtensionsSupport.class)
public class JokeResourceTest {
    private JokeService service = mock(JokeService.class);
    private ResourceExtension resourceExt = ResourceExtension.builder()
            .addResource(new JokeResource(service))
            .build();

    @Test
    public void getJoke_Success() {
        JokeResponse response = new JokeResponse();
        response.setHttpStatus(200);
        response.setTotal(1L);
        JokeDetail detail = new JokeDetail();
        detail.setValue("This is chuck norris testing.");
        response.setResult(Arrays.asList(detail));
        when(service.getJoke("testing")).thenReturn(response);

        JokeResponse jokeRes = resourceExt.target("/joke").queryParam("keyword", "testing").request().get(JokeResponse.class);
        Assertions.assertEquals(1L, jokeRes.getTotal());
        Assertions.assertEquals(detail.getValue(), jokeRes.getResult().get(0).getValue());
    }

    @Test
    public void getJoke_NotFound() {
        JokeResponse response = new JokeResponse();
        response.setTotal(0L);
        response.setResult(Collections.emptyList());
        when(service.getJoke(any())).thenReturn(response);

        JokeResponse jokeRes = resourceExt.target("/joke").queryParam("keyword", "testing").request().get(JokeResponse.class);
        Assertions.assertEquals(0L, jokeRes.getTotal());
        Assertions.assertTrue(jokeRes.getResult().isEmpty());
    }
}
