package testCron;

import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.field.CronField;
import com.cronutils.model.time.ExecutionTime;
import com.cronutils.parser.CronParser;

import java.sql.Date;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;

/**
 * author: fuliang
 * date: 2017/1/19
 */
public class TestCrontab {
    public static void main(String[] args) {
        CronDefinition cronDefinition = CronDefinitionBuilder.instanceDefinitionFor(CronType.UNIX);
        CronParser parser = new CronParser(cronDefinition);
        Cron quartzCron = parser.parse("00 */2 * * *");
        System.out.println(quartzCron.asString());
        System.out.println(quartzCron.validate());
        ZonedDateTime now = ZonedDateTime.now();
        ExecutionTime executionTime = ExecutionTime.forCron(parser.parse("00 */2 * * *"));
        ZonedDateTime lastExecution = executionTime.nextExecution(now);
        ZonedDateTime lastExecution2 = executionTime.nextExecution(lastExecution);
        System.out.println(executionTime);
        System.out.println(lastExecution);
        System.out.println(lastExecution2);
        System.out.println(lastExecution2.isBefore(now));
        System.out.println(Date.from(lastExecution.toInstant()));

    }
}
