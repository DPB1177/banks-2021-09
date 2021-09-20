package com.banks.dto.in;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DtoBankMinfin {
    private Long id;
    private String pointDate;
    private String date;
    private BigDecimal ask;
    private BigDecimal bid;
    private BigDecimal trendAsk;
    private BigDecimal trendBid;
    private String currency;
}
