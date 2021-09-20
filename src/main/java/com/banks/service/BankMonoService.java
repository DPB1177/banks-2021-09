package com.banks.service;

import com.banks.config.AppProperties;
import com.banks.converter.LocalDateTimeAttributeConverter;
import com.banks.dto.in.DtoBankMono;
import com.banks.entity.BankMonoEntity;
import com.banks.repository.BankMonoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.banks.constants.CurrencyCodes.*;

@Slf4j
@Service
public class BankMonoService implements IBankService {

    private final AppProperties appProperties;

    private final LocalDateTimeAttributeConverter converter;

    private final WebClient webClient;

    private final BankMonoRepository bankMonoRepository;

    BankMonoService(AppProperties appProperties, LocalDateTimeAttributeConverter converter, BankMonoRepository bankMonoRepository) {
        webClient = WebClient.create();
        this.appProperties = appProperties;
        this.converter = converter;
        this.bankMonoRepository = bankMonoRepository;
    }

    @Override
    public void save(LocalDateTime localDateTime) {

        List<DtoBankMono> list = webClient
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

        BankMonoEntity bankMono = new BankMonoEntity();
        bankMono.setTimeStamp(converter.convertToDatabaseColumn(localDateTime));
        for (DtoBankMono dto : Objects.requireNonNull(list)) {
            if (dto.getCurrencyCodeA() == USD && dto.getCurrencyCodeB() == UAH) {
                bankMono.setUsdUanRateBuy(dto.getRateBuy());
                bankMono.setUsdUanRateSell(dto.getRateSell());
            } else if (dto.getCurrencyCodeA() == EUR && dto.getCurrencyCodeB() == UAH) {
                bankMono.setEurUanRateBuy(dto.getRateBuy());
                bankMono.setEurUanRateSell(dto.getRateSell());
            } else if (dto.getCurrencyCodeA() == RUB && dto.getCurrencyCodeB() == UAH) {
                bankMono.setRubUanRateBuy(dto.getRateBuy());
                bankMono.setRubUanRateSell(dto.getRateSell());
            }
            log.info(dto.toString());
        }

        bankMonoRepository.save(bankMono);
    }
}

