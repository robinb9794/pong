function handleCountdownMessage(json){
	countdownExpired = false;
	if(json.countdown >= 1)
		log.innerHTML = '<h1>' + json.countdown + '</h1>';
	else{
		countdownExpired = true;
		log.innerHTML = '';
	}		
}