package com.banks.controller;

import com.banks.scheduling.TaskBean;
import com.banks.service.TaskSchedulingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@Api(tags = "Scheduled survey of providers")
@RestController
@RequestMapping(path = "/schedule")
public class JobSchedulingController {

    @Value("${cron.trigger}")
    private String cronTrigger;

    private final TaskSchedulingService taskSchedulingService;

    private final TaskBean taskBean;

    private String uuidAsString = null;

    public JobSchedulingController(TaskSchedulingService taskSchedulingService, TaskBean taskDefinitionBean) {
        this.taskSchedulingService = taskSchedulingService;
        this.taskBean = taskDefinitionBean;
    }

    @ApiOperation(value = "Launch survey providers to fill base according to schedule data", notes = "Launch survey providers to fill base according to schedule data")
    @GetMapping(path = "/start")
    public void scheduleTask() {
        log.info("BEGIN scheduleTask()");
        UUID uuid = UUID.randomUUID();
        uuidAsString = uuid.toString();
        taskSchedulingService.scheduleTask(uuidAsString, taskBean, cronTrigger);
        log.info("END scheduleTask()");
    }

    @ApiOperation(value = "Stopping survey", notes = "Stopping survey")
    @GetMapping(path = "/stop")
    public void removeJob() {
        if (uuidAsString != null) {
            taskSchedulingService.removeScheduledTask(uuidAsString);
        }
    }
}