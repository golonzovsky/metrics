package golonzovsky.prometheus;

import org.springframework.boot.actuate.autoconfigure.ExportMetricWriter;
import org.springframework.boot.actuate.autoconfigure.ManagementContextConfiguration;
import org.springframework.boot.actuate.condition.ConditionalOnEnabledEndpoint;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;

import io.prometheus.client.CollectorRegistry;

@ManagementContextConfiguration
public class PrometheusEndpointContextConfiguration {

    @Bean
    PrometheusEndpoint prometheusEndpoint(CollectorRegistry registry) {
        return new PrometheusEndpoint(registry);
    }

    @Bean
    @ConditionalOnBean(PrometheusEndpoint.class)
    @ConditionalOnEnabledEndpoint("prometheus")
    PrometheusMvcEndpoint prometheusMvcEndpoint(PrometheusEndpoint prometheusEndpoint) {
        return new PrometheusMvcEndpoint(prometheusEndpoint);
    }

    @Bean
    CollectorRegistry collectorRegistry() {
        return CollectorRegistry.defaultRegistry;
    }

    @Bean
    @ExportMetricWriter
    MetricWriter prometheusMetricWriter(CollectorRegistry registry) {
        return new PrometheusMetricWriter(registry);
    }

}