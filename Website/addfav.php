<?php
session_start();
if(isset($_SESSION['username'])){
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
	
	$sql = "INSERT INTO favourites (username, favourites) VALUES ('".$_SESSION['username']."', '".$_POST['id']."')";
	$result = mysqli_query($conn, $sql);

	mysqli_close($conn);
	
}
?>