function handleCountdownMessage(json){
	if(json.countdown > 0)
		log.innerHTML = '<h1>' + json.countdown + '</h1>';
	else{
		countdownExpired = true;
		log.innerHTML = '';
	}		
}