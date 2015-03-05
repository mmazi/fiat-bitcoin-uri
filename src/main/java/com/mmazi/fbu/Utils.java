package com.mmazi.fbu;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Utils {

    static void redirect(HttpServletResponse resp, String uri) {
        resp.setHeader("Location", uri);
        resp.setStatus(301);
    }

    static String urlEncode(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("This shouldn't happen, check the source code.", e);
        }
    }
}
