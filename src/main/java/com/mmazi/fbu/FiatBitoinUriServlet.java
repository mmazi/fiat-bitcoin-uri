package com.mmazi.fbu;


import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FiatBitoinUriServlet extends HttpServlet {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(FiatBitoinUriServlet.class);

    private static final Joiner AMPERSAND = Joiner.on("&");
    private static final Pattern URL_ADDRESS = Pattern.compile("^/?([13][123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz]{25,33})/?$");
    private static final Function<Map.Entry<String, String>, String> ENTRY_FORMAT = new Function<Map.Entry<String,String>, String>() {
        @Override public String apply(Map.Entry<String, String> input) {
            return String.format("%s=%s", input.getKey(), encode(input));
        } };

    private Prices prices = Prices.instance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String address = getAddress(req.getPathInfo());
        log.debug("address = {}", address);

        @SuppressWarnings("unchecked")
        final Map<String, String> params = translate(req.getParameterMap());

        final String uri = String.format("bitcoin:%s?%s", address, format(params));
        resp.setHeader("Location", uri);
        resp.setStatus(301);
    }

    static String getAddress(String pathInfo) {
        final Matcher matcher = URL_ADDRESS.matcher(pathInfo);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Bitcoin address not found in URL path.");
        }
        return matcher.group(1);
    }

    Map<String, String> translate(Map<String, Object> originals) throws IOException {
        final Map<String, String> translated = new LinkedHashMap<>(originals.size());
        String currency = null;
        Double amount = null;
        for (Map.Entry<String, Object> param : originals.entrySet()) {
            final String key = param.getKey();
            final String value = ((String[])param.getValue())[0];
            switch (key) {
                case "fbu-c":
                    currency = value;
                    break;
                case "fbu-a":
                    amount = Double.parseDouble(value);
                    break;
                default:
                    translated.put(key, value);
                    break;
            }
        }
        Preconditions.checkNotNull(currency, "Currency missing.");
        Preconditions.checkNotNull(amount, "Amount missing.");
        translated.put("amount", formatAmount(prices.getAmountBTC(currency, amount)));

        return translated;
    }

    // todo: test for locale (decimal point/comma), thousands separator, scale etc.
    private static String formatAmount(BigDecimal amount) {
        return amount.toPlainString();
    }

    static String format(Map<String, String> map) {
        return AMPERSAND.join(Iterables.transform(map.entrySet(), ENTRY_FORMAT));
    }

    private static String encode(Map.Entry<String, String> input) {
        try {
            return URLEncoder.encode(input.getValue(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("This shouldn't happen, check the source code.", e);
        }
    }
}
