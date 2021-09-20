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
@Table(name = "bank_privat")
public class BankPrivatEntity {

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

}
