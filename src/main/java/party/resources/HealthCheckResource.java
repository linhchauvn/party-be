package party.resources;

import com.codahale.metrics.health.HealthCheck;

public class HealthCheckResource extends HealthCheck {
    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}
