package org.jcl.life;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author chenglei
 */
@SpringBootApplication
public class NoteWebApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(NoteWebApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(NoteWebApplication.class, args);
    }
}
