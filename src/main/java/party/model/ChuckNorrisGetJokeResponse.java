package party.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.dropwizard.jackson.JsonSnakeCase;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSnakeCase
public class ChuckNorrisGetJokeResponse {
    private Long total;
    private List<JokeDetail> result;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<JokeDetail> getResult() {
        return result;
    }

    public void setResult(List<JokeDetail> result) {
        this.result = result;
    }
}
