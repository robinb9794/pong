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
		<canvas id="field"></canvas>
	</div>
</body>

<script type="text/javascript">
	var socket;
	var field;
	var ctx;
	
	window.onload = function(){
		socket = new WebSocket('ws://localhost:8081/pong/sportshall/hall/${hallId}/${numberOfRegisteredPlayers}');
		initSocket();
		field = document.getElementById('field');
		ctx = field.getContext('2d');
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
			var json = JSON.parse(serverMessage.data);
			console.log(json);
			handleServerMessage(json);
		}
		
		socket.onerror = function(error){
			console.log(error);
		}
	}
	
	function handleServerMessage(json){
		switch(json.type){
		case 'INIT':
			handleInitMessage(json);
			break;
		}
	}
	
	function handleInitMessage(json){
		var serverField = json.field;
		var ball = json.ball;
		var players = json.players;
		drawField(serverField);
		drawBall(ball);
		drawPlayers(players);
	}
	
	function drawField(serverField){
		field.width = serverField.width;
		field.height = serverField.height;
	}
	
	function drawBall(ball){
		var x = ball.position.x;
		var y = ball.position.y;
		var size = ball.size;
		ctx.beginPath();
		ctx.arc(x, y, size, 0, 2*Math.PI);
		ctx.fill();
	}
	
	function drawPlayers(players){
		var racket = players[0].racket;
		ctx.rect(racket.x, racket.y, racket.x + racket.width, racket.y + racket.height);
		ctx.fill();
	}
</script>
</html>