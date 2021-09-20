package com.banks.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bank_mono")
public class BankMonoEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "time_stamp")
    private Timestamp timeStamp;

    @Column(name = "usd_uan_rate_buy")
    private BigDecimal usdUanRateBuy;

    @Column(name = "eur_uan_rate_buy")
    private BigDecimal eurUanRateBuy;

    @Column(name = "rub_uan_rate_buy")
    private BigDecimal rubUanRateBuy;

    @Column(name = "usd_uan_rate_sell")
    private BigDecimal usdUanRateSell;

    @Column(name = "eur_uan_rate_sell")
    private BigDecimal eurUanRateSell;

    @Column(name = "rub_uan_rate_sell")
    private BigDecimal rubUanRateSell;

/*
    @Column(name = "data_time_usd_uan")
    private Long  dataTimeUsdUan;

    @Column(name = "data_time_eur_uan")
    private Long  dataTimeEurUan;

    @Column(name = "data_time_rub_uan")
    private Long  dataTimeRubUan;

    @Column(name = "data_time_eur_usd")
    private Long  dataTimeEurUsd;

    @Column(name = "data_time_pln_uan")
    private Long  dataTimePlnUan;

    @Column(name = "eur_usd_rate_sell")
    private BigDecimal eurUsdRateSell;

    @Column(name = "pln_uan_rate_sell")
    private BigDecimal plnUanRateSell;

    @Column(name = "eur_usd_rate_buy")
    private BigDecimal eurUsdRateBuy;

    @Column(name = "pln_uan_rate_buy")
    private BigDecimal plnUanRateBuy;
*/

}
