function initSocket(name){
	socket.onopen = function(){
		socket.send(JSON.stringify({
			type: 'INIT',
			name: self.name,
			racket: self.racket
		}));
	}
	
	socket.onmessage = function(serverMessage){
		let json = JSON.parse(serverMessage.data);
		console.log(json);
		handleServerMessage(json);
	}
	
	socket.onerror = function(error){
		console.log(error);
	}
}

function handleInitMessage(json){	
	let initFunctions = () => {
		initField(json.field);
		initBall(json.ball);
		initPlayers(json.players);
		drawObjects();
	}		
	initFunctions();
}

function initField(serverField){
	let width = serverField.width;
	let height = serverField.height;
	let spaceHorizontal = serverField.spaceHorizontal;
	let spaceVertical = serverField.spaceVertical;
	field = new Field(width, height, spaceHorizontal, spaceVertical);
	canvas.width = field.width;
	canvas.height = field.height;
	ctx = canvas.getContext('2d');		
	ctx.fillStyle = '#FFF';
}

function initBall(serverBall){
	let x = serverBall.coordinate.x;
	let y = serverBall.coordinate.y;
	let coordinate = new Coordinate(x, y);
	let radius = serverBall.radius;
	ball = new Ball(coordinate, radius);
}

function initPlayers(serverPlayers){
	for(let i = 0; i < serverPlayers.length; i++){	
		let player = initNewPlayer(serverPlayers[i]);
		if(player.name == self.name)
			self = player;
		initTableRow(player);
		players.push(player);
	}
}

function initNewPlayer(player){
	let startPosX = player.racket.coordinate.x;
	let startPosY = player.racket.coordinate.y;
	
	let startPos = new Coordinate(startPosX, startPosY);
	let width = player.racket.width;
	let height = player.racket.height;
	
	let racket = new Racket(startPos, width, height);
	
	let name = player.name;
	let lifes = player.lifes;
	let color = player.color;
	let position = player.position;
	
	return new Player(name, lifes, color, position, racket);
}

function initTableRow(player){
	let tableBody = document.getElementById('table-body');
	let html = '';
	html += '<tr' + (player.name == self.name ? ' style="background-color: #747474;"' : '') + '>';
	html += '	<td>' + player.name + '</td>';	
	html += '	<td id="' + player.name + '-life">' + player.lifes + '</td>';
	html += '	<td>' + player.position + '</td>';
	html += '</tr>';
	tableBody.innerHTML += html;
}