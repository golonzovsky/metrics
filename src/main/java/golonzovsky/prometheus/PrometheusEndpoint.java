package golonzovsky.prometheus;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.common.TextFormat;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.stereotype.Component;

/**
 * Created by jzietsman on 1/28/16.
 */
@Slf4j
class PrometheusEndpoint extends AbstractEndpoint<String> {

    private CollectorRegistry registry;

    PrometheusEndpoint(CollectorRegistry registry) {
        super("prometheus",false, true);
        this.registry = registry;
    }

    @Override
    public String invoke() {
        Writer writer = new StringWriter();
        writeRegistryMetrics(writer);
        return writer.toString();
    }

    private void writeRegistryMetrics(Writer writer) {
        try {
            TextFormat.write004(writer, registry.metricFamilySamples());
        } catch (IOException e) {
            log.error("error serializing prometheus metrics to endpoint response");
            throw new IllegalStateException("error writing prometheus metrics to endpoint", e);
        }
    }
}
