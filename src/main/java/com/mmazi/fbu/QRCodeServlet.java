package com.mmazi.fbu;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

public class QRCodeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String address = req.getParameter("address");
        final String currency = req.getParameter("currency");
        final BigDecimal amount = new BigDecimal(req.getParameter("amount"));

        Utils.redirect(resp, String.format(
                "https://chart.googleapis.com/chart?cht=qr&chs=300x300&chl=%s",
                Utils.urlEncode(getUri(address, amount, currency))
        ));
    }

    public static String getUri(String address, BigDecimal amount, String currency) {
        return String.format("https://fiat-bitcoin-uri.appspot.com/fbu/%s?fbu-c=%s&fbu-a=%s", address, currency, amount.toPlainString());
    }
}
