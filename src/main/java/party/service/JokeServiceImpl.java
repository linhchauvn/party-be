package party.service;

import org.eclipse.jetty.http.HttpStatus;
import party.api.JokeService;
import party.client.ChuckNorrisClient;
import party.model.ChuckNorrisGetJokeResponse;
import party.model.JokeResponse;
import party.util.CacheUtils;

public class JokeServiceImpl implements JokeService {

    private ChuckNorrisClient chuckNorrisClient;
    private CacheUtils cache;

    public JokeServiceImpl(ChuckNorrisClient chuckNorrisClient, CacheUtils cache) {
        this.chuckNorrisClient = chuckNorrisClient;
        this.cache = cache;
    }

    @Override
    public JokeResponse getJoke(String query) {
        if (!cache.addDataToCache(query)) {
            JokeResponse response = new JokeResponse();
            response.setHttpStatus(HttpStatus.TOO_MANY_REQUESTS_429);
            response.setMessage("Keyword \""+ query + "\" is requested too much. Please try another word :)");
            return response;
        }
        ChuckNorrisGetJokeResponse clientRes = chuckNorrisClient.getJoke(query);
        return convertObject(clientRes);
    }

    private JokeResponse convertObject(ChuckNorrisGetJokeResponse from) {
        if (from == null) {
            return null;
        }
        JokeResponse to = new JokeResponse();
        to.setHttpStatus(HttpStatus.OK_200);
        to.setResult(from.getResult());
        to.setTotal(from.getTotal());
        return to;
    }
}
