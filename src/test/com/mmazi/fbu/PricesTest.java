package com.mmazi.fbu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PricesTest {
    private static final Logger log = LoggerFactory.getLogger(PricesTest.class);

    @Test
    public void testGetPrices() throws Exception {
        final double btceur = Prices.instance().getExchangeRate("BTC", "EUR");
        log.debug("btceur = {}", btceur);

        Assert.assertEquals(btceur, 250., 100.);
    }
}