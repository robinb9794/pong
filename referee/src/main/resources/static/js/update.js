function handleUpdateMessage(json){
	let updateFunctions = () => {
		updateBall(json.ball);
		updatePlayers(json.players);
		clearField();
		drawObjects();
	}
	updateFunctions();
}

function updateBall(serverBall){
	ball.coordinate.x = serverBall.coordinate.x;
	ball.coordinate.y = serverBall.coordinate.y;
}

function updatePlayers(serverPlayers){
	for(var i = 0; i < serverPlayers.length; i++){
		let playerToUpdate = players.find(function (player){
			return player.name == serverPlayers[i].name;
		});
		playerToUpdate.lifes = serverPlayers[i].lifes;
		playerToUpdate.racket = serverPlayers[i].racket;	
	}
}