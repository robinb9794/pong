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
	<canvas id="canvas"></canvas>
	<div id="info">
		<table id="table">
			<thead>
				<tr>
					<th>Name</th>
					<th>Lifes</th>
					<th>Position</th>
				</tr>
			</thead>
			<tbody id="table-body"></tbody>				
		</table>
	</div>
	<div id="log"></div>
</body>

<script src="/js/objects.js" type="text/javascript"></script>
<script src="/js/init.js" type="text/javascript"></script>
<script src="/js/countdown.js" type="text/javascript"></script>
<script src="/js/update.js" type="text/javascript"></script>
<script src="/js/reset.js" type="text/javascript"></script>
<script type="text/javascript">
	var socket;
	var canvas;
	var ctx;
	var field = {};
	var self = {};
	var ball;
	var players = [];
	var log;
	
	var countdownExpired = false;
	
	window.onload = function(){
		socket = new WebSocket('ws://localhost:8081/pong/sportshall/hall/${hallId}/${numberOfRegisteredPlayers}');
		self.name = '${name}';
		self.racket = '${racket}';
		initSocket();	
		canvas = document.getElementById('canvas');	
		log = document.getElementById('log');
	}
	
	document.body.onkeydown = function(event){
		let keyCode = event.keyCode;
		var direction = '';
		switch(keyCode){
		case 87:
			direction = 'UP';
			break;
		case 68:
			direction = 'RIGHT';
			break;
		case 83:
			direction = 'DOWN';
			break;
		case 65:
			direction = 'LEFT';
			break;
		}
		if(direction != '' && countdownExpired && directionIsCorrect(direction))
			sendToServer('UPDATE', direction);
	}
	
	function handleServerMessage(json){
		switch(json.type){
		case 'INIT':
			handleInitMessage(json);
			break;
		case 'COUNTDOWN':
			handleCountdownMessage(json);
			break;
		case 'UPDATE':
			handleUpdateMessage(json);
			break;
		case 'RESET':
			handleResetMessage(json);
			break;
		}
	}
	
	function directionIsCorrect(direction){
		if(selfIsHorizontal()){
			if(direction == 'LEFT' || direction == 'RIGHT')
				return true;
		}else if(selfIsVertical()){
			if(direction == 'UP' || direction == 'DOWN')
				return true;
		}
		return false;
	}
	
	function selfIsHorizontal(){
		return self.position == 'TOP' || self.position == 'BOTTOM';
	}
	
	function selfIsVertical(){
		return self.position == 'LEFT' || self.position == 'RIGHT';
	}
	
	function sendToServer(type, direction){
		socket.send(JSON.stringify({
			type: type,
			name: self.name,
			direction: direction
		}));
	}
	
	function clearField(){
		ctx.clearRect(0, 0, canvas.width, canvas.height);
	}
	
	function drawObjects(){
		ball.draw();
		for(var i = 0; i < players.length; i++){
			players[i].draw();				
		}	
	}	
</script>
</html>