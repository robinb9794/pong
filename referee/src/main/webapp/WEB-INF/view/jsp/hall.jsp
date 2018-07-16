<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Pong - Gamehall</title>
	<link href="/css/hall.css" type="text/css" rel="stylesheet" />
</head>
<body>
	<div>
		<canvas id="field" width="800" height="700"></canvas>
	</div>
</body>

<script type="text/javascript">
	var socket;
	var canvas
	var ctx;
	
	window.onload = function(){
		socket = new WebSocket('ws://localhost:8081/pong/sportshall/hall/${hallId}/${numberOfRegisteredPlayers}');
		initSocket();
		canvas = document.getElementById('field');
		ctx = canvas.getContext('2d');
		ctx.fillStyle = '#FFF';		
	}
	
	function initSocket(){
		socket.onopen = function(){
			socket.send(JSON.stringify({
				type: 'INIT',
				name: '${name}',
				racket: '${racket}'
			}));
		}
		
		socket.onmessage = function(serverMessage){
			console.log(serverMessage);
		}
		
		socket.onerror = function(error){
			console.log(error);
		}
	}
</script>
</html>