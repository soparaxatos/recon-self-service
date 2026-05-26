package com.recon.selfservice.config;

import com.recon.selfservice.model.entity.Action;
import com.recon.selfservice.model.entity.Recon;
import com.recon.selfservice.model.enums.ActionType;
import com.recon.selfservice.repository.ReconRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ReconRepository reconRepository;

    @Override
    public void run(String... args) {
        if (reconRepository.count() == 0) {
            // Sample REST Recon
            Action restAction = Action.builder()
                    .application("db-extractor")
                    .type(ActionType.REST)
                    .parameters("{\n" +
                            "  \"name\": \"001_SAMPLE_RECON\",\n" +
                            "  \"driver\": \"oracle.jdbc.OracleDriver\",\n" +
                            "  \"dbUrl\": \"jdbc:oracle:thin:@host:1521/service\",\n" +
                            "  \"port\": 1521,\n" +
                            "  \"username\": \"user\",\n" +
                            "  \"useCloakware\": true,\n" +
                            "  \"collumnSeparator\": \";\",\n" +
                            "  \"sql\": \"SELECT * FROM TABLE WHERE DATE = '{date}'\",\n" +
                            "  \"sheetName\": \"Sheet1\"\n" +
                            "}")
                    .build();

            Recon restRecon = Recon.builder()
                    .name("001_SAMPLE_REST_RECON")
                    .description("Sample recon using db-extractor via REST")
                    .action(restAction)
                    .build();

            // Sample Command Line Recon
            Action cmdAction = Action.builder()
                    .application("app-file-exporter")
                    .type(ActionType.COMMAND_LINE)
                    .parameters("java -jar app-file-exporter.jar bradesco PARAM1 PARAM2 {date}")
                    .build();

            Recon cmdRecon = Recon.builder()
                    .name("002_SAMPLE_CMD_RECON")
                    .description("Sample recon using app-file-exporter via Command Line")
                    .action(cmdAction)
                    .build();

            reconRepository.save(restRecon);
            reconRepository.save(cmdRecon);
        }
    }
}
