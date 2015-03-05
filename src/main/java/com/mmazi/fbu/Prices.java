package com.mmazi.fbu;

import java.math.BigDecimal;

public class Prices {

    private static Prices INSTANCE = new Prices();

    private Prices() {
    }

    public static Prices instance() {
        return INSTANCE;
    }

    public BigDecimal getAmountBTC(String currency, Number amount) {
        return new BigDecimal(amount.doubleValue() / getExchangeRate("BTC", currency)).setScale(8, BigDecimal.ROUND_HALF_UP);
    }

    public double getExchangeRate(String base, String counter) {
        switch (base) {
            case "BTC":
                switch (counter) {
                    case "EUR": return 246.;
                    case "USD": return 269.;
                    default: throw new IllegalArgumentException("Unsupported counter currency: " + counter);
                }
            default: throw new IllegalArgumentException("Unsupported base currency: " + base);
        }
    }
}
