<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	Yeah ${name}
</body>

<script type="text/javascript">
	window.onload = function(){
		var socket = new WebSocket("ws://localhost:8080/pong/waitingroom");
		console.log(socket);
	}
</script>

</html>