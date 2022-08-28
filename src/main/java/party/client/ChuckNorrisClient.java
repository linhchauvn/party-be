package party.client;

import party.config.ClientConfig;
import party.model.ChuckNorrisGetJokeResponse;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;

public class ChuckNorrisClient {
    private Client client;
    private ClientConfig clientConfig;

    public ChuckNorrisClient(Client client, ClientConfig clientConfig) {
        this.client = client;
        this.clientConfig = clientConfig;
    }

    public ChuckNorrisGetJokeResponse getJoke(String keyword) {
        Invocation.Builder request = client.target(clientConfig.getHost()).queryParam("query", keyword).request();
        Response response = request.get();
        return response.readEntity(ChuckNorrisGetJokeResponse.class);
    }
}
