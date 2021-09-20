package com.banks.customquery;

import com.banks.dto.out.DtoAverage;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class CustomQueries {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Map<String, Object>> getSimpleNativeQueryResultInMap() {

        String querySimpleString = "SELECT avg(usd_uan_rate_buy) usd_uan_buy, avg(eur_uan_rate_buy) eur_uan_buy, avg(rub_uan_rate_buy) rub_uan_buy, avg(usd_uan_rate_sell) usd_uan_sell, avg(eur_uan_rate_sell) eur_uan_sell, avg(rub_uan_rate_sell) rub_uan_sell  \n" +
                "FROM (SELECT * FROM bank_mono WHERE time_stamp IN (SELECT max(time_stamp) FROM bank_mono) UNION \n" +
                "      SELECT * FROM bank_privat WHERE time_stamp IN (SELECT max(time_stamp) FROM bank_privat) UNION \n" +
                "      SELECT * FROM bank_privat WHERE time_stamp IN (SELECT max(time_stamp) FROM bank_minfin)\n" +
                "     ) banks;\n";


        Query query = entityManager.createNativeQuery(querySimpleString);
        NativeQueryImpl nativeQuery = (NativeQueryImpl) query;
        nativeQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        List<Map<String, Object>> result = query.getResultList();
        for (Map<String, Object> map : result) {
            log.info("after request  ::: " + map);
        }
        return result;
    }

    public List<Map<String, Object>> getIntervalNativeQueryResultInMap(String start, String end) {

        String queryString = "SELECT avg(usd_uan_rate_buy) usd_uan_buy, avg(eur_uan_rate_buy) eur_uan_buy, avg(rub_uan_rate_buy) rub_uan_buy, avg(usd_uan_rate_sell) usd_uan_sell, avg(eur_uan_rate_sell) eur_uan_sell, avg(rub_uan_rate_sell) rub_uan_sell  \n" +
                "FROM\n" +
                "(SELECT * FROM bank_mono WHERE time_stamp >= TO_TIMESTAMP('" + start + "', 'yyyy-MM-dd HH:mi:ss') and  time_stamp <= TO_TIMESTAMP('" + end + "', 'yyyy-MM-dd HH:mi:ss')\n" +
                "UNION\n" +
                "SELECT * FROM bank_privat WHERE time_stamp >= TO_TIMESTAMP('" + start + "', 'yyyy-MM-dd HH:mi:ss') and  time_stamp <= TO_TIMESTAMP('" + end + "', 'yyyy-MM-dd HH:mi:ss')\n" +
                "UNION\n" +
                "SELECT * FROM bank_minfin WHERE time_stamp >= TO_TIMESTAMP('" + start + "', 'yyyy-MM-dd HH:mi:ss') and  time_stamp <= TO_TIMESTAMP('" + end + "', 'yyyy-MM-dd HH:mi:ss')\n" +
                ") banks;";

        Query query = entityManager.createNativeQuery(queryString);
        NativeQueryImpl nativeQuery = (NativeQueryImpl) query;
        nativeQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        List<Map<String, Object>> result = query.getResultList();
        for (Map<String, Object> map : result) {
            log.info("after request  ::: " + map);
        }
        return result;
    }

    public List<DtoAverage> getSimpleCustomQuery() {

        String querySimpleString = "SELECT avg(usd_uan_rate_buy) usd_uan_buy, avg(eur_uan_rate_buy) eur_uan_buy, avg(rub_uan_rate_buy) rub_uan_buy, avg(usd_uan_rate_sell) usd_uan_sell, avg(eur_uan_rate_sell) eur_uan_sell, avg(rub_uan_rate_sell) rub_uan_sell  \n" +
                "FROM (SELECT * FROM bank_mono WHERE time_stamp IN (SELECT max(time_stamp) FROM bank_mono) UNION \n" +
                "      SELECT * FROM bank_privat WHERE time_stamp IN (SELECT max(time_stamp) FROM bank_privat) UNION \n" +
                "      SELECT * FROM bank_privat WHERE time_stamp IN (SELECT max(time_stamp) FROM bank_minfin)\n" +
                "     ) banks;\n";

        Query nativeQuery = entityManager.createNativeQuery(querySimpleString);
        return (List<DtoAverage>) nativeQuery.getResultList();
    }

    public List<DtoAverage> getInterval(String start, String end) {

        String queryString = "SELECT avg(usd_uan_rate_buy) usd_uan_buy, avg(eur_uan_rate_buy) eur_uan_buy, avg(rub_uan_rate_buy) rub_uan_buy, avg(usd_uan_rate_sell) usd_uan_sell, avg(eur_uan_rate_sell) eur_uan_sell, avg(rub_uan_rate_sell) rub_uan_sell  \n" +
                "FROM\n" +
                "(SELECT * FROM bank_mono WHERE time_stamp >= TO_TIMESTAMP('" + start + "', 'yyyy-MM-dd HH:mi:ss') and  time_stamp <= TO_TIMESTAMP('" + end + "', 'yyyy-MM-dd HH:mi:ss')\n" +
                "UNION\n" +
                "SELECT * FROM bank_privat WHERE time_stamp >= TO_TIMESTAMP('" + start + "', 'yyyy-MM-dd HH:mi:ss') and  time_stamp <= TO_TIMESTAMP('" + end + "', 'yyyy-MM-dd HH:mi:ss')\n" +
                "UNION\n" +
                "SELECT * FROM bank_minfin WHERE time_stamp >= TO_TIMESTAMP('" + start + "', 'yyyy-MM-dd HH:mi:ss') and  time_stamp <= TO_TIMESTAMP('" + end + "', 'yyyy-MM-dd HH:mi:ss')\n" +
                ") banks;";

        Query nativeQuery = entityManager.createNativeQuery(queryString);
        return nativeQuery.getResultList();
    }


}
