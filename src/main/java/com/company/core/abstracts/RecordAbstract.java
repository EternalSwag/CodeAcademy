package com.company.core.abstracts;

import com.company.core.enums.PaymentMethod;
import com.company.core.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public abstract class RecordAbstract {

    private UUID id;
    private LocalDateTime dateTime;
    private BigDecimal sum;
    private TransactionType transactionType;
    private PaymentMethod paymentMethod;
    private String additionalInfo;

}