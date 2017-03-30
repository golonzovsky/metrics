package golonzovsky;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.prometheus.client.spring.boot.EnablePrometheusEndpoint;
import io.prometheus.client.spring.boot.EnableSpringBootMetricsCollector;

@SpringBootApplication
@EnablePrometheusEndpoint
@EnableSpringBootMetricsCollector
public class MetricsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MetricsApplication.class, args);
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
