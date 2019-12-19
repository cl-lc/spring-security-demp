package fun.cllc.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application
 *
 * @author jk
 * @date 2018/07/09
 */
@SpringBootApplication
public class Application {

    /**
     * main
     *
     * @param args params
     */
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.run(args);
    }
}
