function Ball(coordinate, size){
	this.coordinate = coordinate;
	this.size = size;
	
	this.draw = function(){
		ctx.beginPath();
		ctx.fillStyle = '#FFF';
		ctx.arc(this.coordinate.x, this.coordinate.y, this.size, 0, 2*Math.PI);
		ctx.fill();
		ctx.closePath();
	}
}

function Player(name, lifes, color, position, racket){
	this.name = name;
	this.lifes = lifes;
	this.color = color;
	this.position = position;
	this.racket = racket;
	
	this.draw = function(){
		ctx.beginPath();
		ctx.translate(0, 0);
		ctx.fillStyle = color;
		ctx.rect(this.racket.coordinate.x, this.racket.coordinate.y, this.racket.width, this.racket.height);
		ctx.fill();
		ctx.closePath();
	}
	
	
}

function Racket(coordinate, width, height){
	this.coordinate = coordinate;
	this.width = width;
	this.height = height;
}

function Coordinate(x, y){
	this.x = x;
	this.y = y;
}