package com.mmazi.fbu;

import com.google.appengine.api.search.checkers.Preconditions;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.bitcoinaverage.BitcoinAverageExchange;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Prices {

    private static final Logger log = LoggerFactory.getLogger(Prices.class);
    private static Prices INSTANCE = new Prices();
    private static final Collection<String> SUPPORTED_CURRENCIES;
    private static final int CURR_FIELD_MODIFIERS = Modifier.PUBLIC | Modifier.STATIC | Modifier.FINAL;

    private final Map<String, Map<String, BigDecimal>> priceCache = new HashMap<>();

    private final PollingMarketDataService marketdata;

    static {
        SUPPORTED_CURRENCIES = new HashSet<>();
        final Field[] fields = Currencies.class.getDeclaredFields();
        try {
            for (Field field : fields) {
                if ((field.getModifiers() | CURR_FIELD_MODIFIERS) == CURR_FIELD_MODIFIERS && String.class.isAssignableFrom(field.getType())) {
                    SUPPORTED_CURRENCIES.add((String) field.get(null));
                }
            }
        } catch (IllegalAccessException e) {
            log.error("Error initializing currencies.");
        }
    }

    private Prices() {
        Exchange btcavg = ExchangeFactory.INSTANCE.createExchange(BitcoinAverageExchange.class.getName());
        marketdata = btcavg.getPollingMarketDataService();

        priceCache.put("BTC", new HashMap<String, BigDecimal>());
    }

    public static Prices instance() {
        return INSTANCE;
    }

    public BigDecimal getAmountBTC(String currency, Number amount) throws IOException {
        return new BigDecimal(amount.doubleValue() / getExchangeRate("BTC", currency)).setScale(8, BigDecimal.ROUND_HALF_UP);
    }

    static void checkCurrency(String currency) {
        Preconditions.checkArgument(SUPPORTED_CURRENCIES.contains(currency), "Unsupported currency: " + currency);
    }

    public void reloadBTC(String currency) throws IOException {
        checkCurrency(currency);
        priceCache.get("BTC").put(currency, loadRate("BTC", currency));
    }

    private BigDecimal loadRate(String base, String counter) throws IOException {
        return marketdata.getTicker(new CurrencyPair(base, counter)).getLast();
    }

    public void reloadAll() throws IOException {
        for (String base : priceCache.keySet()) {
            if (base.equals("BTC")) {
                final Map<String, BigDecimal> prices = priceCache.get(base);
                for (String counter : prices.keySet()) {
                    reloadBTC(counter);
                }
            }
        }
    }

    public double getExchangeRate(String base, String counter) throws IOException {
        final Map<String, BigDecimal> baseMap = priceCache.get(base);
        final BigDecimal rate;
        if (baseMap.containsKey(counter)) {
            rate = baseMap.get(counter);
        } else {
            rate = loadRate(base, counter);
            baseMap.put(counter, rate);
        }

        return rate.doubleValue();
    }
}
