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
		<canvas id="canvas"></canvas>
	</div>
</body>

<script src="/js/objects.js" type="text/javascript"></script>
<script type="text/javascript">
	var socket;
	var canvas;
	var ctx;
	var ball;
	var players = [];
	
	window.onload = function(){
		socket = new WebSocket('ws://localhost:8081/pong/sportshall/hall/${hallId}/${numberOfRegisteredPlayers}');
		initSocket();	
		canvas = document.getElementById('canvas');	
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
		let initObjects = () => {
			initField(json.field);
			initBall(json.ball);
			initPlayers(json.players);
			drawObjects();
		}		
		initObjects();
	}
	
	function initField(serverField){
		canvas.width = serverField.width;
		canvas.height = serverField.height;
		ctx = canvas.getContext('2d');		
		ctx.fillStyle = '#FFF';
	}
	
	function initBall(serverBall){
		var x = serverBall.position.x;
		var y = serverBall.position.y;
		var size = serverBall.size;
		ball = new Ball(x, y, size);
	}
	
	function initPlayers(serverPlayers){
		for(var i = 0; i < serverPlayers.length; i++){
			var startPosX = serverPlayers[i].racket.startPos.x;
			var startPosY = serverPlayers[i].racket.startPos.y;
			
			var startPos = new Position(startPosX, startPosY);
			var width = serverPlayers[i].racket.width;
			var height = serverPlayers[i].racket.height;
			
			var racket = new Racket(startPos, width, height);
			
			var name = serverPlayers[i].name;
			var lifes = serverPlayers[i].lifes;
			
			var player = new Player(name, lifes, racket);
			
			players.push(player);
		}
	}
	
	function drawObjects(){
		ball.draw();
		for(var i = 0; i < players.length; i++){
			players[i].draw();
		}	
	}
</script>
</html>