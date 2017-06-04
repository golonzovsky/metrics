package golonzovsky.prometheus;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import org.springframework.boot.actuate.metrics.Metric;
import org.springframework.boot.actuate.metrics.writer.Delta;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class PrometheusMetricWriter implements MetricWriter {

    private CollectorRegistry registry;

    private final ConcurrentMap<String, Counter> counters = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Gauge> gauges = new ConcurrentHashMap<>();

    public PrometheusMetricWriter(CollectorRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void increment(Delta<?> delta) {
        counter(delta.getName()).inc(delta.getValue().longValue());
    }

    @Override
    public void reset(String metricName) {
        counter(metricName).clear();
    }

    @Override
    public void set(Metric<?> value) {
        gauge(value.getName()).set(value.getValue().doubleValue());
    }

    private Counter counter(String name) {
        String key = sanitizeName(name);
        return counters.computeIfAbsent(key, k -> Counter.build().name(k).help(k).register(registry));
    }

    private Gauge gauge(String name) {
        String key = sanitizeName(name);
        return gauges.computeIfAbsent(key, k -> Gauge.build().name(k).help(k).register(registry));
    }

    private String sanitizeName(String name) {
        return name.replaceAll("[^a-zA-Z0-9_]", "_");
    }

}