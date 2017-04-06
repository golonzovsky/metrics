package golonzovsky;

import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.metrics.dropwizard.DropwizardMetricServices;
import org.springframework.boot.actuate.metrics.dropwizard.ReservoirFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Slf4jReporter;
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;

@SpringBootApplication
public class MetricsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MetricsApplication.class, args);
    }
}

@Configuration
@EnableMetrics
class SpringConfiguringClass extends MetricsConfigurerAdapter {

    @Bean
    DropwizardMetricServices dropwizardMetricServices(MetricRegistry metricRegistry,
                                                      ObjectProvider<ReservoirFactory> resFactoryProvider) {
        return new DropwizardMetricServices(metricRegistry, resFactoryProvider.getIfAvailable()) {
            @Override
            public void submit(String name, double value) {
                if (name.startsWith("response.")) {
                    super.submit("timer." + name, value);
                } else {
                    super.submit(name, value);
                }
            }
        };
    }

    @Override
    public void configureReporters(MetricRegistry metricRegistry) {
        // registerReporter allows the MetricsConfigurerAdapter to
        // shut down the reporter when the Spring context is closed
        registerReporter(ConsoleReporter
                .forRegistry(metricRegistry)//.outputTo(Logger)
                .build())
                .start(1, TimeUnit.MINUTES);
    }

}

@RestController
class RestEndpoints {

    private Random random = new SecureRandom();

    @GetMapping("one")
    String one() throws InterruptedException {
        int millis = random.nextInt(1000);
        Thread.sleep(millis);
        return String.valueOf(millis);
    }

    @GetMapping("half")
    String half() throws InterruptedException {
        int millis = random.nextInt(500);
        Thread.sleep(millis);
        return String.valueOf(millis);
    }

    @GetMapping("instant")
    String instant() throws InterruptedException {
        return "instant";
    }
}
