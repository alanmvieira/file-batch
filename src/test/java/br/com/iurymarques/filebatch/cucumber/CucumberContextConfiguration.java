package br.com.iurymarques.filebatch.cucumber;

import br.com.iurymarques.filebatch.Application;
import cucumber.api.java.Before;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = Application.class, loader = SpringBootContextLoader.class)
public class CucumberContextConfiguration {

    @Before
    public void setup_cucumber_spring_context() {

    }
}
