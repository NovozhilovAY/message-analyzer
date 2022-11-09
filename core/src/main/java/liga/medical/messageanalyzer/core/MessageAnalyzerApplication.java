package liga.medical.messageanalyzer.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"liga.medical.messageanalyzer", "liga.commonmodule.core"})
public class MessageAnalyzerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessageAnalyzerApplication.class, args);
    }

}
