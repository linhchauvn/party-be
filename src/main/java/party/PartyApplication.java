package party;

import io.dropwizard.Application;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.util.Duration;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import party.client.ChuckNorrisClient;
import party.resources.HealthCheckResource;
import party.resources.JokeResource;
import party.service.JokeServiceImpl;
import party.util.CacheUtils;

import javax.ws.rs.client.Client;

public class PartyApplication extends Application<PartyConfiguration> {

    public static void main(final String[] args) throws Exception {
        new PartyApplication().run(args);
    }

    @Override
    public String getName() {
        return "party";
    }

    @Override
    public void initialize(final Bootstrap<PartyConfiguration> bootstrap) {
        bootstrap.addBundle(new SwaggerBundle<PartyConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(PartyConfiguration configuration) {
                return configuration.swaggerBundleConfiguration;
            }
        });
    }

    @Override
    public void run(final PartyConfiguration configuration,
                    final Environment environment) {
        environment.healthChecks().register("healthcheck", new HealthCheckResource());

        JerseyClientConfiguration jerseyClientConfiguration = new JerseyClientConfiguration();
        jerseyClientConfiguration.setTimeout(Duration.seconds(30));
        Client client = new JerseyClientBuilder(environment)
                .using(jerseyClientConfiguration)
                .build("ChuckNorrisClient");
        ChuckNorrisClient chuckNorrisClient = new ChuckNorrisClient(client, configuration.getClientConfig());
        environment.jersey().register(chuckNorrisClient);

        final JokeResource resource = new JokeResource(new JokeServiceImpl(chuckNorrisClient, CacheUtils.getInstance()));
        environment.jersey().register(resource);
    }

}
