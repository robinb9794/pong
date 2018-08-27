var firstCall = true;

function handleResetMessage(json){
	let cell = document.getElementById(json.playerName + '-life');
	if(firstCall){
		cell.innerHTML = json.playerLifes;
		cell.style.border = '2px solid red';
		handleObjects(json);
		firstCall = false;
	}	
	if(json.countdown >= 1)
		log.innerHTML = '<h1>' + json.countdown + '</h1>';
	else{
		log.innerHTML = '';
		cell.style.border = '1px solid white';
		firstCall = true;
	}
}

function handleObjects(json){
	clearField();
	ball.reset();
	updatePlayers(json.players);
	drawObjects();
}