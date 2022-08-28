package party;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import party.config.ClientConfig;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class PartyConfiguration extends Configuration {
    @Valid
    @NotNull
    private ClientConfig clientConfig = new ClientConfig();

    @JsonProperty("client")
    public ClientConfig getClientConfig() {
        return clientConfig;
    }

    @JsonProperty("client")
    public void setClientConfig(ClientConfig factory) {
        this.clientConfig = factory;
    }

    @JsonProperty("swagger")
    public SwaggerBundleConfiguration swaggerBundleConfiguration;
}
