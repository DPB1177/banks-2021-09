package com.banks.service;

import com.banks.config.AppProperties;
import com.banks.converter.LocalDateTimeAttributeConverter;
import com.banks.dto.in.DtoBankMinfin;
import com.banks.entity.BankMinfinEntity;
import com.banks.repository.BankMinfinRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class BankMinfinService implements IBankService {
    private final AppProperties appProperties;

    private final LocalDateTimeAttributeConverter converter;

    private final WebClient webClient;

    private final BankMinfinRepository bankMinfinRepository;

    BankMinfinService(AppProperties appProperties, LocalDateTimeAttributeConverter converter, BankMinfinRepository bankMinfinRepository) {
        webClient = WebClient.create();
        this.appProperties = appProperties;
        this.converter = converter;
        this.bankMinfinRepository = bankMinfinRepository;
    }

    @Override
    public void save(LocalDateTime localDateTime) {

        List<DtoBankMinfin> list = webClient
                .get()
                .uri(appProperties.getMinfin() + "/" + appProperties.getKey_minfin())
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, error -> Mono.error(new RuntimeException("API not found")))
                .onStatus(HttpStatus::is5xxServerError, error -> Mono.error(new RuntimeException("Server is not responding")))
                .bodyToFlux(DtoBankMinfin.class)
                .collectList()
                .block();

        BankMinfinEntity bankMinfin = new BankMinfinEntity();
        bankMinfin.setTimeStamp(converter.convertToDatabaseColumn(localDateTime));
        long idForUsd = Long.MIN_VALUE;
        long idForEur = Long.MIN_VALUE;
        long idForRub = Long.MIN_VALUE;
        BigDecimal usdAsk = null;
        BigDecimal eurAsk = null;
        BigDecimal rubAsk = null;
        BigDecimal usdBid = null;
        BigDecimal eurBid = null;
        BigDecimal rubBid = null;

        for (DtoBankMinfin dto : Objects.requireNonNull(list)) {
            if (dto.getCurrency().equals("usd") && (dto.getId() >= idForUsd)) {
                usdAsk = dto.getAsk();
                usdBid = dto.getBid();
                idForUsd = dto.getId();
            } else if (dto.getCurrency().equals("eur") && (dto.getId() >= idForEur)) {
                eurAsk = dto.getAsk();
                eurBid = dto.getBid();
                idForEur = dto.getId();
            } else if (dto.getCurrency().equals("rub") && (dto.getId() >= idForRub)) {
                rubAsk = dto.getAsk();
                rubBid = dto.getBid();
                idForRub = dto.getId();
            }
            log.info(dto.toString());
        }

        bankMinfin.setUsdUanRateBuy(usdAsk);
        bankMinfin.setUsdUanRateSell(usdBid);
        bankMinfin.setEurUanRateBuy(eurAsk);
        bankMinfin.setEurUanRateSell(eurBid);
        bankMinfin.setRubUanRateBuy(rubAsk);
        bankMinfin.setRubUanRateSell(rubBid);

        bankMinfinRepository.save(bankMinfin);
    }
}
