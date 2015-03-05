<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create QR code</title>
    <style>
        input {
            font-size: 100%;
            width: 40ex;
            display: block;
        }
        input.inline {
            width: 20ex;
            display: inline;
        }
        form {
            font-size: 130%;
            padding: 1ex;
            background-color: antiquewhite;
            width: 50ex;
        }
    </style>
</head>
<body>
<p>
    Using this tool, you can create static (printable) QR-codes for payments denominated in fiat currency.
</p>
<p>
    Just stick these QR-codes to your products and have your customers pay you directly - no BitPay needed!
</p>
<p>
    Enter the recipient Bitcoin address, fiat amount and currency:
</p>

<form action="/qr.png/" method="get">
  <input type="text" name="address" value="1FcLf8p7Wp9Tkmve7FetFXarvrPhHUtEri"/>
  <input type="text" name="amount" value="0.50" class="inline"/>
  <input type="text" name="currency" value="EUR" class="inline"/>
  <input type="submit" value="Get QR-code"/>
</form>

<p>
    A QR-code will be generated. Scanning this QR-code with a QR-scanner (eg.
    <a href="https://play.google.com/store/apps/details?id=com.google.zxing.client.android">Barcode Scanner</a> for Android)
    will always open your Android wallet with the correct amount in BTC, regardless of the BTC price changes.
</p>
<p>
    <a href="https://bitcoinaverage.com/">BitcoinAverage</a>
    is used for Bitcoin/fiat exchange rates. Many national currencies are supported.
</p>
<p>
    This is a proof of concept app. Please email any problems and suggestions to
    <a href="mailto:matija.mazi@gmail.com">matija.mazi@gmail.com</a>.
</p>
<p>
    If you like this small app, you can donate via the default address 1FcLf8p7Wp9Tkmve7FetFXarvrPhHUtEri in the form above - and test how it works at the same time :)
</p>

</body>
</html>
