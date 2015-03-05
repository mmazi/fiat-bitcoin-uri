<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create QR code</title>
    <%--<link href="bootstrap/css/bootstrap-responsive.min.css"/>--%>
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
        input[type=submit] {
            font-size: 100%;
            background-color: lightgreen;
        }
        form {
            font-size: 130%;
            padding: 1ex;
            background-color: antiquewhite;
            width: 50ex;
        }
        body {
            font-family: sans-serif;
            margin: 2em 20%;
        }
    </style>
</head>
<body>
<h1>Fiat-denominated QR-codes for Bitcoin payments</h1>
<p>
    Using this tool, you can create static (printable) QR-codes for payments denominated in fiat currency.
    The QR-code will always work, no matter how the Bitcoin price changes.
</p>
<p>
    Just stick these QR-codes to your products and have your customers pay you directly - no BitPay needed!
</p>
<p>
    Enter the recipient Bitcoin address, fiat amount and currency:
</p>

<form action="/qr.png/" method="get">
  <input type="text" name="address" value="13QkQMHcyXfAJ9RWQ68Brfh9bFhfLGRU4U"/>
  <input type="text" name="amount" value="0.50" class="inline"/>
  <input type="text" name="currency" value="EUR" class="inline"/>
  <input type="submit" value="Get the QR-code!"/>
</form>

<p>
    A QR-code will be generated. Scanning this QR-code with a general QR-scanner (eg.
    <a href="https://play.google.com/store/apps/details?id=com.google.zxing.client.android">Barcode Scanner</a>
    or <a href="https://play.google.com/store/apps/details?id=com.google.android.apps.unveil">Google Goggles</a>
    for Android) will open your mobile wallet with the correct amount in BTC, taking int account the
    current BTC price.
</p>
<h2>Notes</h2>
<p>
    Note that this QR-code does not work if scanned using a bitcoin wallet; it should be scanned by a general-purpose
    barcode scanner. It works simply by opening a Web URL in the browser that then redirects the system to a
    <code>bitcoin:</code> URI.
</p>
<p>
    The app can also be used for NFC-tags. Using NFC is perhaps an even better use case since NFC tags
    are usually scanned by the system, while the users might attempt to scan QR-codes from within Bitcoin
    wallets which won't work.
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
    If you like this small app, you can donate via the default address 13QkQMHcyXfAJ9RWQ68Brfh9bFhfLGRU4U in the form above - and test how it works at the same time :)
</p>

</body>
</html>
