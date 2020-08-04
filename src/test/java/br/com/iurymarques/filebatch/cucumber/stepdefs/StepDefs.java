package br.com.iurymarques.filebatch.cucumber.stepdefs;

import br.com.iurymarques.filebatch.config.BatchConfiguration;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static br.com.iurymarques.filebatch.util.TestUtil.deleteDirectory;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StepDefs {

    @Autowired
    BatchConfiguration batchConfiguration;

    @Value("${path.input}")
    private String inputDirectory;

    @Value("${path.output}")
    private String outputDirectory;

    @Value("${path.error}")
    private String errorDirectory;

    @Value("${path.success}")
    private String successDirectory;

    @Before
    public void setUp() throws IOException {
        Path path = Paths.get(inputDirectory);
        Files.createDirectories(path);
    }

    @After
    public void tearDown() throws IOException {
        deleteDirectory(outputDirectory);
        deleteDirectory(errorDirectory);
        deleteDirectory(successDirectory);
    }

    @Given("^a sales file \"([^\"]*)\" with the following content exists$")
    public void aSalesFileWithTheFollowingContentExists(String filename, String fileContent) throws Throwable {
        Path path = Paths.get(inputDirectory).resolve(filename);
        byte[] content = fileContent.getBytes();

        Files.write(path, content);
    }

    @When("the application runs")
    public void theApplicationRuns() throws Exception {
        batchConfiguration.perform();
    }

    @Then("^a sales report \"([^\"]*)\" with the following content should exist$")
    public void aSalesReportWithTheFollowingContentShouldExist(String filename, String expected) throws Throwable {
        Path output = Paths.get(outputDirectory).resolve(filename);
        String actual = Files.readString(output);

        assertThat(actual).isEqualTo(expected);
    }

    @Then("^a file \"([^\"]*)\" should exist on the error folder$")
    public void aFileShouldExistOnTheErrorFolder(String filename) throws Throwable {
        Path output = Paths.get(errorDirectory).resolve(filename);
        boolean exists = Files.exists(output);

        assertTrue(exists);
    }

    @And("^a file \"([^\"]*)\" should exist on the success folder$")
    public void aFileShouldExistOnTheSuccessFolder(String filename) throws Throwable {
        Path output = Paths.get(successDirectory).resolve(filename);
        boolean exists = Files.exists(output);

        assertTrue(exists);
    }
}
