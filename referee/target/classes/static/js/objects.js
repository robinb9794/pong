function Field(width, height, spaceHorizontal, spaceVertical){
	this.width = width;
	this.height = height;
	this.spaceHorizontal = spaceHorizontal;
	this.spaceVertical = spaceVertical;
	
	this.drawLines = function(){
		ctx.beginPath();
		ctx.strokeStyle = '#FFF';
		ctx.setLineDash([5, 15]);	
		
		ctx.moveTo(0, this.spaceVertical);
		ctx.lineTo(this.width, this.spaceVertical);
		ctx.moveTo(0, this.height - this.spaceVertical);
		ctx.lineTo(this.width, this.height - this.spaceVertical);	
		
		if(players.length == 4){
			ctx.moveTo(this.spaceHorizontal, 0);
			ctx.lineTo(this.spaceHorizontal, this.height);
			ctx.moveTo(this.width - this.spaceHorizontal, 0);
			ctx.lineTo(this.width - this.spaceHorizontal, this.height);
		}		
		ctx.stroke();
		ctx.closePath();
	}
}

function Ball(coordinate, radius){
	this.coordinate = coordinate;
	this.radius = radius;
	
	this.draw = function(){
		ctx.beginPath();
		ctx.fillStyle = '#FFF';
		ctx.arc(this.coordinate.x, this.coordinate.y, this.radius, 0, 2*Math.PI);
		ctx.fill();
		ctx.closePath();
	}
	
	this.reset = function(){
		this.coordinate = new Coordinate(field.width / 2, field.height / 2);	
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
		ctx.fillStyle = this.color;
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