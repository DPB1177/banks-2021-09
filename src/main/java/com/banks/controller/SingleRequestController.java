package com.banks.controller;

import com.banks.config.AppProperties;
import com.banks.dto.in.DtoBankMinfin;
import com.banks.dto.in.DtoBankMono;
import com.banks.dto.in.DtoBankPrivat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

import static com.banks.constants.CurrencyCodes.*;
import static com.banks.constants.CurrencyCodes.UAH;

@Api(tags = "Checking requests for third party services")
@RestController
@RequestMapping(value = "/single", produces = "application/json")
public class SingleRequestController {

    private final AppProperties appProperties;

    private WebClient webClient;

    public SingleRequestController(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @PostConstruct
    private void setUpWebClient() {
        webClient = WebClient.create();
    }

    @ApiOperation(value = "Get a response from Privat", notes = "Get a response from Privat according to the url")
    @GetMapping(value = "/private")
    public List<DtoBankPrivat> getPrivateList() {
        return webClient
                .get()
                .uri(appProperties.getPrivat())
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, error -> Mono.error(new RuntimeException("API not found")))
                .onStatus(HttpStatus::is5xxServerError, error -> Mono.error(new RuntimeException("Server is not responding")))
                .bodyToFlux(DtoBankPrivat.class)
                .collectList()
                .block()
                .stream()
                .filter(dto -> (!dto.getCcy().equals("BTC")))
                .collect(Collectors.toList());

    }

    @ApiOperation(value = "Get a response from Mono", notes = "Get a response from Mono according to the url")
    @GetMapping(value = "/mono")
    public List<DtoBankMono> getMonoList() {
        return webClient
                .get()
                .uri(appProperties.getMono())
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, error -> Mono.error(new RuntimeException("API not found")))
                .onStatus(HttpStatus::is5xxServerError, error -> Mono.error(new RuntimeException("Server is not responding")))
                .bodyToFlux(DtoBankMono.class)
                .collectList()
                .block()
                .stream()
                .filter(dto -> (dto.getCurrencyCodeA() == USD || dto.getCurrencyCodeA() == EUR || dto.getCurrencyCodeA() == RUB) &&
                        (dto.getCurrencyCodeB() == UAH))
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "Get a response from Minfin", notes = "Get a response from Minfin according to the url")
    @GetMapping(value = "/minfin")
    public List<DtoBankMinfin> getMinfinList() {
        return webClient
                .get()
                .uri(appProperties.getMinfin() + "/" + appProperties.getKey_minfin())
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, error -> Mono.error(new RuntimeException("API not found")))
                .onStatus(HttpStatus::is5xxServerError, error -> Mono.error(new RuntimeException("Server is not responding")))
                .bodyToFlux(DtoBankMinfin.class)
                .collectList()
                .block();
    }
}
