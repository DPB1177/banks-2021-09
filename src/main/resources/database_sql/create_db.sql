drop table if exists "bank_mono";
create table "bank_mono"
(
    id UUID not null,
    time_stamp TIMESTAMP,
    usd_uan_rate_buy NUMERIC(10,4),
    eur_uan_rate_buy NUMERIC(10,4),
    rub_uan_rate_buy NUMERIC(10,4),
    usd_uan_rate_sell NUMERIC(10,4),
    eur_uan_rate_sell NUMERIC(10,4),
    rub_uan_rate_sell NUMERIC(10,4),
    primary key (id)
);

drop table if exists "bank_privat";
create table "bank_privat"
(
    id UUID not null,
    time_stamp TIMESTAMP,
    usd_uan_rate_buy NUMERIC(10,4),
    eur_uan_rate_buy NUMERIC(10,4),
    rub_uan_rate_buy NUMERIC(10,4),
    usd_uan_rate_sell NUMERIC(10,4),
    eur_uan_rate_sell NUMERIC(10,4),
    rub_uan_rate_sell NUMERIC(10,4),
    primary key (id)
);

drop table if exists "bank_minfin";
create table "bank_minfin"
(
    id UUID not null,
    time_stamp TIMESTAMP,
    usd_uan_rate_buy NUMERIC(10,4),
    eur_uan_rate_buy NUMERIC(10,4),
    rub_uan_rate_buy NUMERIC(10,4),
    usd_uan_rate_sell NUMERIC(10,4),
    eur_uan_rate_sell NUMERIC(10,4),
    rub_uan_rate_sell NUMERIC(10,4),
    primary key (id)
);

commit;
