package org.revestoria.core.shared.domain;

import java.math.BigDecimal;

public class Money {
    private static final int ISO_CURRENCY_CODE_LENGTH = 3;

    private final BigDecimal amount;
    private final String currency;

    public Money(BigDecimal amount, String currency) {
        validateAmount(amount);
        validateCurrency(currency);

        this.amount = amount;
        this.currency = currency;
    }

    private void validateCurrency(String currency){
        if(currency == null || currency.isBlank()){
            throw new IllegalArgumentException("Currency is required");
        }

        if(currency.length() != ISO_CURRENCY_CODE_LENGTH){
            throw new IllegalArgumentException("Currency must be a 3-letter ISO code");
        }
    }

    private void validateAmount(BigDecimal amount){
        if(amount == null || amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Product price must be greater than zero");
        }
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }
}
