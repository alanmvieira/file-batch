package br.com.iurymarques.filebatch.config;

import br.com.iurymarques.filebatch.batch.FileProcessor;
import br.com.iurymarques.filebatch.batch.FileReader;
import br.com.iurymarques.filebatch.batch.FileWriter;
import br.com.iurymarques.filebatch.domain.SaleReport;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.nio.file.Path;

import static java.lang.String.valueOf;
import static java.lang.System.currentTimeMillis;

@Configuration
@EnableScheduling
@EnableBatchProcessing
@EnableAutoConfiguration(
        exclude = {
                DataSourceAutoConfiguration.class,
                HibernateJpaAutoConfiguration.class
        }
)
public class BatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final JobLauncher jobLauncher;

    private JobExecution jobExecution;

    public BatchConfiguration(JobBuilderFactory jobBuilderFactory,
                              StepBuilderFactory stepBuilderFactory,
                              JobLauncher jobLauncher) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.jobLauncher = jobLauncher;
    }

    @Scheduled(cron = "${cron.interval}")
    public void perform() throws Exception {
        JobParameters param = new JobParametersBuilder()
                .addString("JobID", valueOf(currentTimeMillis()))
                .toJobParameters();

        if (jobExecution == null || !jobExecution.isRunning()) {
            jobExecution = jobLauncher.run(job(), param);
        }
    }

    @Bean
    protected FileReader reader() {
        return new FileReader();
    }

    @Bean
    protected FileProcessor processor() {
        return new FileProcessor();
    }

    @Bean
    protected FileWriter writer() {
        return new FileWriter();
    }

    @Bean
    protected Job job() {
        return jobBuilderFactory.get("job")
                .incrementer(new RunIdIncrementer())
                .flow(step())
                .end()
                .build();
    }

    @Bean
    protected Step step() {
        return stepBuilderFactory.get("step")
                .<Path, SaleReport>chunk(1)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }
}
