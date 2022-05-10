package in.co.sout.cricktoscheduler.config;

import in.co.sout.cricktoscheduler.service.EbcdicParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@Slf4j
public class SchedulerConfiguration {

    @Autowired
    private EbcdicParser ebcdicParser;

    @Scheduled(fixedRate = 30000)
    public void scheduleByFixedRate() {
        log.info("scheduler is running");
        ebcdicParser.convert();

    }

}
