<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Pong - Login</title>		
		<link href="/css/login.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div style="text-align: center;">
			<h1>Pong - Login</h1>
		</div>
		<hr/>
		<div class="container">
			<div>
				<label for="nickname">Name</label>
			</div>
			<div>
				<input type="text" id="nickname" name="nickname" placeholder="Your nickname..." />
			</div>
			<div>
				<label for="racket">Racket</label>
			</div>
			<div>
				<select id="racket" name="racket">
					<option value="system">System</option>
					<option value="individual">Individual</option>
				</select>
			</div>
			<hr/>
			<strong><a href="http://localhost:8082/pong/stats/get/xml">Stats</a></strong>
			<button type="submit" onclick="checkForm()">GO!</button>				
		</div>
	</body>
	
	<script type="text/javascript">
		function checkForm(){
			if(nicknameIsValid()){
				toLobby();	
			}else{
				alert('Nickname is not valid!');
			}
		}
		
		function nicknameIsValid(){
			var value = document.getElementById('nickname').value;
			return value.length >= 4 && value.length <= 16 ? true : false;
		}
		
		function toLobby(){
			window.location.href = '/pong/lobby/' + document.getElementById('racket').value + '/' + document.getElementById('nickname').value;
		}
	</script>
</html>

