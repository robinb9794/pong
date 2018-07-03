<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div>
		Hi ${name}, we are currently searching for players with the same racket (${racket}). Please wait...
	</div>
	<hr/>
	<div id="output"></div>
</body>

<script type="text/javascript">
	var output;
	var firstMessage;
	
	window.onload = function(){
		output = document.getElementById('output');
		firstMessage = true;
		var socket = new WebSocket("ws://localhost:8080/pong/waitingroom/${racket}");
		initSocket(socket);
	}
	
	function initSocket(socket){
		socket.onopen = function(){
			socket.send(createInitMessage());			
		}
		
		socket.onmessage = function(serverMessage){
			var json = JSON.parse(serverMessage.data);
			console.log(json);
			output.innerHTML += json.message + ' Currently waiting players with same racket: ' + json.waitingPlayers + '<br>';
		}
		
		socket.onerror = function(error){
			output.innerHTML += error + '<br>';	
		}
	}
	
	function createInitMessage(){
		return JSON.stringify({
			name: '${name}',
			racket: '${racket}'
		});
	}
</script>

</html>