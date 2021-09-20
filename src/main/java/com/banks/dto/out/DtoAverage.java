package com.banks.dto.out;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoAverage {

    private BigDecimal usd_uan_buy;
    private BigDecimal eur_uan_buy;
    private BigDecimal rub_uan_buy;
    private BigDecimal usd_uan_sell;
    private BigDecimal eur_uan_sell;
    private BigDecimal rub_uan_sell;

}
