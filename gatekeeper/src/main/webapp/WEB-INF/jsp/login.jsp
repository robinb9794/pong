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
			<h1>Pong</h1>
		</div>
		<hr/>
		<div class="container">
			<form>
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
				<button type="button">Stats</button>
				<input type="submit" value="GO!">
			</form>			
		</div>
	</body>
</html>