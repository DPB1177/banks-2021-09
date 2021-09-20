package com.banks.service;

import com.banks.config.AppProperties;
import com.banks.converter.LocalDateTimeAttributeConverter;
import com.banks.dto.in.DtoBankPrivat;
import com.banks.entity.BankPrivatEntity;
import com.banks.repository.BankPrivatRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BankPrivatService implements IBankService {

    private final AppProperties appProperties;

    private final LocalDateTimeAttributeConverter converter;

    private final WebClient webClient;

    private final BankPrivatRepository bankMonoRepository;

    public BankPrivatService(LocalDateTimeAttributeConverter converter, BankPrivatRepository bankMonoRepository, AppProperties appProperties) {
        webClient = WebClient.create();
        this.converter = converter;
        this.bankMonoRepository = bankMonoRepository;
        this.appProperties = appProperties;
    }

    @Override
    public void save(LocalDateTime localDateTime) {
        List<DtoBankPrivat> list = webClient
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

        BankPrivatEntity bankPrivat = new BankPrivatEntity();
        bankPrivat.setTimeStamp(converter.convertToDatabaseColumn(localDateTime));
        for (DtoBankPrivat dto : Objects.requireNonNull(list)) {
            if (dto.getCcy().equals("USD") && dto.getBase_ccy().equals("UAH")) {
                bankPrivat.setUsdUanRateBuy(dto.getBuy());
                bankPrivat.setUsdUanRateSell(dto.getSale());
            } else if (dto.getCcy().equals("EUR") && dto.getBase_ccy().equals("UAH")) {
                bankPrivat.setEurUanRateBuy(dto.getBuy());
                bankPrivat.setEurUanRateSell(dto.getSale());
            } else if (dto.getCcy().equals("RUR") && dto.getBase_ccy().equals("UAH")) {
                bankPrivat.setRubUanRateBuy(dto.getBuy());
                bankPrivat.setRubUanRateSell(dto.getSale());
            }
            log.info(dto.toString());
        }

        bankMonoRepository.save(bankPrivat);
    }
}
