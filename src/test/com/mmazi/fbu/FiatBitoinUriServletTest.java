package com.mmazi.fbu;

import org.testng.Assert;
import org.testng.annotations.Test;

public class FiatBitoinUriServletTest {

    @Test
    public void testGetAddress() throws Exception {
        Assert.assertEquals(FiatBitoinUriServlet.getAddress("1FcLf8p7Wp9Tkmve7FetFXarvrPhHUtEri"), "1FcLf8p7Wp9Tkmve7FetFXarvrPhHUtEri");
        Assert.assertEquals(FiatBitoinUriServlet.getAddress("/1FcLf8p7Wp9Tkmve7FetFXarvrPhHUtEri"), "1FcLf8p7Wp9Tkmve7FetFXarvrPhHUtEri");
        Assert.assertEquals(FiatBitoinUriServlet.getAddress("/1FcLf8p7Wp9Tkmve7FetFXarvrPhHUtEri/"), "1FcLf8p7Wp9Tkmve7FetFXarvrPhHUtEri");
    }
}