<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create QR code</title>
</head>
<body>
<form action="/qr.png/" method="get">
  <input type="text" name="address" value="1FcLf8p7Wp9Tkmve7FetFXarvrPhHUtEri"/>
  <input type="text" name="amount" value="12.34"/>
  <input type="text" name="currency" value="USD"/>
  <input type="submit" value="Get QR-code"/>
</form>
</body>
</html>
