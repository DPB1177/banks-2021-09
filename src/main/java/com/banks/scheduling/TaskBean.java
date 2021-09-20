package com.banks.scheduling;

import com.banks.service.BankMinfinService;
import com.banks.service.BankMonoService;
import com.banks.service.BankPrivatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@Service
public class TaskBean implements Runnable {

//    @Autowired
//    private IBankService bankMonoService;
//    @Autowired
//    private IBankService bankPrivatService;
//    @Autowired
//    private IBankService bankMinfinService;

    @Autowired
    private BankMonoService bankMonoService;
    @Autowired
    private BankPrivatService bankPrivatService;
    @Autowired
    private BankMinfinService bankMinfinService;

    @Override
    public void run() {
        Date date = new Date();
        SimpleDateFormat formatForDateTime = new SimpleDateFormat("hh:mm:ss");
        SimpleDateFormat formatForDate = new SimpleDateFormat("yyyy.MM.dd");
        log.info("With Data: " + formatForDate.format(date));
        log.info("Running action: " + formatForDateTime.format(date));
        LocalDateTime localDateTime = LocalDateTime.now();
        bankMonoService.save(localDateTime);
        bankPrivatService.save(localDateTime);
        bankMinfinService.save(localDateTime);
    }
}