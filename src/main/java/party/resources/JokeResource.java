package party.resources;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;
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
@OpenAPIDefinition(
        info = @Info(
                title = "Joke API",
                version = "1.0",
                description = "Joke API"
        ),
        servers = {
                @Server(
                        description = "local",
                        url = "http://localhost:8080")
        }
)
public class JokeResource {

    private JokeService jokeService;

    public JokeResource(JokeService jokeService) {
        this.jokeService = jokeService;
    }

    @GET
    @Operation(summary = "Get jokes by keyword", tags = {"jokes"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of jokes", content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JokeResponse.class))),
                    @ApiResponse(responseCode = "429", description = "Keyword requested too much")})
    public Response getJoke(@Parameter(description = "keyword to find jokes", required = true, schema = @Schema(type = "string"))
                            @QueryParam("keyword") @NotEmpty String keyword) {
        JokeResponse response = jokeService.getJoke(keyword);
        return Response.status(response.getHttpStatus())
                .entity(response)
                .build();
    }
}

