<html>
<head>
<title>Gently down the stream~</title>
<link rel="shortcut icon" type="image/x-icon" href="favicon.ico">
<link href="css/bootstrap.min.css" rel="stylesheet">
<script src="jwplayer/jwplayer.js"></script>
</head>
<body>
<?php include 'navbar.php';?>
<?php
if (isset ( $_SESSION ['username'] )) {
	header ( "Location: ../index.php" );
} else {
}
?>
<?php 
if (isset ( $_POST ['username'] )) {
	
	$servername = "localhost";
	$username = "gently";
	$password = "downthestream";
	$dbname = "gently";
	
	// Create connection
	$conn = mysqli_connect($servername, $username, $password, $dbname);
	// Check connection
	if (!$conn) {
	    die("Connection failed: " . mysqli_connect_error());
	}
	
	//$sql = "SELECT username FROM login WHERE username='".$_POST ['username']."'";
	$stmt = mysqli_prepare($conn, "SELECT username FROM login WHERE username=?");
	mysqli_stmt_bind_param($stmt, 's', $_POST ['username']);
	mysqli_stmt_execute($stmt);
	mysqli_stmt_store_result($stmt);
	
	if (mysqli_stmt_num_rows($stmt) == 0 && $_POST ['password']==$_POST ['password2']) {
		$key = rand(1000000, 9999999);
		$sql = "SELECT streamkey FROM channel WHERE streamkey='".$key."'";
		$result = mysqli_query($conn, $sql);
		while (mysqli_num_rows($result) > 0) {
			$key = rand(1000000, 9999999);
			$sql = "SELECT streamkey FROM channel WHERE streamkey='".$key."'";
			$result = mysqli_query($conn, $sql);
		}
		$salt = uniqid();
		$stmt1 = mysqli_prepare($conn, "INSERT INTO login (username, password, salt) VALUES (?,?,?)");
		mysqli_stmt_bind_param($stmt1, 'sss', $_POST ['username'], md5($_POST ['password'].$salt), $salt);
		
		$stmt2 = mysqli_prepare($conn, "INSERT INTO channel (username, streamkey, name, description) VALUES (?,?,'Untitled Channel', '')");
		mysqli_stmt_bind_param($stmt2, 'ss', $_POST ['username'], $key);
		
		$stmt3 = mysqli_prepare($conn, "INSERT INTO favourites (username, favourites) VALUES (?,'')");
		mysqli_stmt_bind_param($stmt3, 's', $_POST ['username']);
		
		if (mysqli_stmt_execute($stmt1) && mysqli_stmt_execute($stmt2) && mysqli_stmt_execute($stmt3)) {
		    echo '<div class="alert alert-success" role="success"><center>Account successfully created. You can now sign in above!</center></div>';
		} else {
		    echo "Error: ". mysqli_error($conn);
		}
	} else if ($_POST ['password']!=$_POST ['password2']){
		echo '<div class="alert alert-warning" role="warning"><center>Password does not match.</center></div>';
	} else {
		echo '<div class="alert alert-warning" role="warning"><center>Username already taken.</center></div>';
	}
	mysqli_stmt_close($stmt);
	mysqli_stmt_close($stmt1);
	mysqli_stmt_close($stmt2);
	mysqli_stmt_close($stmt3);
	mysqli_close($conn);
	
	
}
?>		


<div class="container">
	<div class="row">
		<div class="col-md-12">
		<br>
			<form class="form" method="post" action="register.php">
			<fieldset>
			
			<!-- Form Name -->
			<legend>Register</legend>
			
			<!-- Text input-->
			<div class="form-group">
			  	<label for="username">Username <small>(5-32 characters)</small></label>
			  	<input class="form-control" id="username" name="username" type="text" required pattern="^[a-zA-Z0-9]{5,32}$" title="5-32 alphanumeric characters">
			</div>
			
			<!-- Text input-->
			<div class="form-group">
			  	<label for="password">Password <small>(7-32 characters)</small></label>
			  	<input class="form-control" id="password" name="password" type="password" required pattern=".{7,32}" title="7-32 characters">
			</div>
			
			<!-- Text input-->
			<div class="form-group">
			  	<label for="password2">Repeat password</label>
			  	<input class="form-control" id="password2" name="password2" type="password" required pattern=".{7,32}" title="7-32 characters">
			</div>
			
			<!-- Button -->
			<div class="form-group">
				<button class="btn btn-success">Register</button>
			</div>
			
			</fieldset>
			</form>
		</div>
	</div>

	
    
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	
</body>
</html>