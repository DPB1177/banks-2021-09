package com.banks.controller;

import com.banks.converter.LocalDateTimeAttributeConverter;
import com.banks.customquery.CustomQueries;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@Api(tags = " API - the solution of the task")
@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class ApiController {

    @Autowired
    private CustomQueries customQueries;

    @Autowired
    private LocalDateTimeAttributeConverter converter;

    @ApiOperation(value = "Request for a list of exchange rates for all sources", notes = "Request for a list of exchange rates for all sources")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Неверный параметр запроса"),
            @ApiResponse(code = 401, message = "несанкционированный"),
            @ApiResponse(code = 403, message = "Нет доступа"),
            @ApiResponse(code = 404, message = "Путь запроса не существует"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервера")
    })
    @GetMapping(value = "/simple")
    List<Map<String, Object>> getSimple() {
        return customQueries.getSimpleNativeQueryResultInMap();
    }

    @ApiOperation(value = "Request for a list of exchange rates for all sources for the period", notes = "Request for a list of exchange rates for all sources for the period")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "Interval start", name = "start", dataType = "String", paramType = "path", required = false, defaultValue = "2021-09-19 02:05:00"),
            @ApiImplicitParam(value = "Interval end", name = "end", dataType = "String", paramType = "path", required = false, defaultValue = "2021-09-19 02:15:00")
    })
    @ApiResponses({
            @ApiResponse(code = 400, message = "Неверный параметр запроса"),
            @ApiResponse(code = 401, message = "несанкционированный"),
            @ApiResponse(code = 403, message = "Нет доступа"),
            @ApiResponse(code = 404, message = "Путь запроса не существует"),
            @ApiResponse(code = 500, message = "Внутренняя ошибка сервера")
    })
    @RequestMapping(value = "/interval", method = RequestMethod.GET)
    @ResponseBody
    List<Map<String, Object>> getInterval(@RequestParam(value = "start", required = false, defaultValue = "2021-09-19 02:05:00") String start,
                                 @RequestParam(value = "end", required = false, defaultValue = "2021-09-19 02:15:00") String end) {
        log.info(start);
        log.info(end);

        return customQueries.getIntervalNativeQueryResultInMap(start, end);
    }
}
