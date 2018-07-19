function Ball(x, y, size){
	this.x = x;
	this.y = y;
	this.size = size;
	
	this.draw = function(){
		ctx.beginPath();
		ctx.arc(this.x, this.y, this.size, 0, 2*Math.PI);
		ctx.fill();
	}
}

function Player(name, lifes, racket){
	this.name = name;
	this.lifes = lifes;
	this.racket = racket;
	
	this.draw = function(){
		console.log(racket);
		ctx.rect(this.racket.startPos.x, this.racket.startPos.y, this.racket.width, this.racket.height);
		ctx.fill();
	}
}

function Racket(startPos, width, height){
	this.startPos = startPos;
	this.width = width;
	this.height = height;
}

function Position(x, y){
	this.x = x;
	this.y = y;
}