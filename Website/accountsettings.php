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
} else {
	header ( "Location: ../index.php" );
}
?>
<?php 
if (isset ( $_POST ['oldPassword'] )) {
	
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
	
	$stmt = mysqli_prepare($conn, "SELECT password FROM login WHERE username=?");
	mysqli_stmt_bind_param($stmt, 's', $_SESSION['username']);
	mysqli_stmt_execute($stmt);
	mysqli_stmt_bind_result($stmt, $oldpassword);
    mysqli_stmt_fetch($stmt);
    mysqli_stmt_close($stmt);
    
    if ($oldpassword==$_POST['oldPassword'] && $_POST['newPassword']==$_POST['newPassword2']){
    	$stmt = mysqli_prepare($conn, "UPDATE login SET password=? WHERE username=?");
    	mysqli_stmt_bind_param($stmt, 'ss', $_POST ['newPassword'], $_SESSION['username']);
    	if (mysqli_stmt_execute($stmt)) {
    		echo '<div class="alert alert-success" role="success"><center>Password changed.</center></div>';
    	} else {
    		echo '<div class="alert alert-warning" role="warning"><center>Error updating record: '.$conn->error.'</center></div>';
    	}
    	mysqli_stmt_close($stmt);
    } else if ($_POST['newPassword']!=$_POST['newPassword2']){
    	echo '<div class="alert alert-warning" role="warning"><center>New password does not match.</center></div>';
    } else {
    	echo '<div class="alert alert-warning" role="warning"><center>Incorrect password.</center></div>';
    }
	
	mysqli_close($conn);
	
	
}
?>		


<div class="container">
	<div class="row">
		<div class="col-md-12">
		<br>
			<form class="form" method="post" action="accountsettings.php">
			<fieldset>
			
			<!-- Form Name -->
			<legend>Account Settings</legend>
			
			<!-- Text input-->
			<div class="form-group">
			  	<label for="oldPassword">Old password</label>
			  	<input class="form-control" id="oldPassword" name="oldPassword" type="password" required pattern=".{7,32}">
			</div>
			
			<!-- Text input-->
			<div class="form-group">
			  	<label for="newPassword">New password <small>(minimum 7-32 characters)</small></label>
			  	<input class="form-control" id="newPassword" name="newPassword" type="password" required pattern=".{7,32}">
			</div>
			
			<!-- Text input-->
			<div class="form-group">
			  	<label for="newPassword2">Repeat new password</label>
			  	<input class="form-control" id="newPassword2" name="newPassword2" type="password" required pattern=".{7,32}">
			</div>
			
			<!-- Button -->
			<div class="form-group">
				<button class="btn btn-success">Change</button>
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