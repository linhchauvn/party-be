package party.resources;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import party.api.JokeService;
import party.model.JokeResponse;

import javax.validation.constraints.NotEmpty;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/joke")
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "/joke", produces = "application/json")
public class JokeResource {

    private JokeService jokeService;

    public JokeResource(JokeService jokeService) {
        this.jokeService = jokeService;
    }

    @GET
    @ApiOperation(value = "Finds jokes by keyword",
            notes = "Multiple status values can be provided with comma seperated strings",
            response = JokeResponse.class)
    @ApiParam(name = "keyword", required = true)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 429, message = "TOO MANY REQUEST") })
    public Response getJoke(@QueryParam("keyword")
                                @NotEmpty String keyword) {
        JokeResponse response = jokeService.getJoke(keyword);
        return Response.status(response.getHttpStatus())
                .entity(response)
                .build();
    }
}

