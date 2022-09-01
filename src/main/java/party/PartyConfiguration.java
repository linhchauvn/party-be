package party;

import com.fasterxml.jackson.annotation.JsonProperty;
import in.vectorpro.dropwizard.swagger.SwaggerBundleConfiguration;
import io.dropwizard.Configuration;
import party.config.ClientConfig;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class PartyConfiguration extends Configuration {
    @JsonProperty("swagger")
    public SwaggerBundleConfiguration swaggerBundleConfiguration;
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

}
