<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Pong - Waiting Room</title>
	<link href="/css/waitingroom.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div style="text-align: center;">
		<h3>
			Hi ${name}, we are currently searching for players with the same racket. Please wait...
		</h3>
	</div>	
	<hr/>
	<div id="output"></div>
	<div  style="text-align: center;">
		<p id="lobby-size"></p>
		<ul id="lobby-member"></ul>
		<h1 id="countdown"></h1>
	</div>
</body>

<script type="text/javascript">
	var socket;
	var output;
	var lobbySize;
	var lobbyMember;
	var countdown;
	
	window.onload = function(){
		output = document.getElementById('output');
		lobbySize = document.getElementById('lobby-size');
		lobbyMember = document.getElementById('lobby-member');
		countdown = document.getElementById('countdown');
		socket = new WebSocket("ws://localhost:8080/pong/waitingroom/${racket}/${name}");
		initSocket(socket);
	}
	
	function initSocket(socket){
		socket.onopen = function(){
			socket.send('CONNECTED, ${name}');	
		}
		
		socket.onmessage = function(serverMessage){
			handleIncomingMessage(serverMessage);
		}
		
		socket.onerror = function(error){
			output.innerHTML += error + '<br>';	
		}
	}
	
	function handleIncomingMessage(serverMessage){
		var json = JSON.parse(serverMessage.data);
		var waitingPlayers = json.waitingPlayers;
		console.log(json);
		if(json.type == 'LOBBY')
			goToGameHall(json.message);
		else{
			updateOutput(json);
			updateLobbyInfo(json.waitingPlayers);
			updateCountdown(json);
		}		
	}
	
	function updateOutput(json){
		if(json.type != 'COUNTDOWN'){
			output.innerHTML += addMessage(json.message);		
			output.scrollTop = output.scrollHeight;
		}		
	}
	
	function addMessage(message){
		var date = new Date();
		var time = date.getHours() + ':' + (date.getMinutes() < 10 ? '0' : '') + date.getMinutes();
		var html;
		html = 	'<div class="chat-container">';
		html += '	<img src="/img/robot.png" alt="Robot">';
		html += '	<p>' + message + '</p>';
		html += '	<span class="time-right">' + time + '</span>';
		html += '</div>';
		return html;
	}
	
	function updateLobbyInfo(waitingPlayers){
		lobbyMember.innerHTML = '';
		for(var i = 0; i < waitingPlayers.length; i++){
			var waitingPlayer = waitingPlayers[i];
			lobbyMember.innerHTML += '<li>' + waitingPlayer.name + '</li>';
		}		
		lobbySize.innerHTML = 'Currently waiting players (' + waitingPlayers.length + '/4):';
	}
	
	function updateCountdown(json){
		if(json.type == 'COUNTDOWN'){
			countdown.innerHTML = json.message;
			if(json.message == '0')
				goToGameHall();
		}else
			countdown.innerHTML = '';
	}
	
	function goToGameHall(lobbyId){
		window.location.href = 'http://localhost:8081/pong/gamehall/' + lobbyId + '/${name}';
	}
</script>

</html>