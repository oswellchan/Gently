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

	$sql = "SELECT * FROM `favourites` WHERE username='".$_SESSION['username']."'";
	$result = mysqli_query($conn, $sql);

	if (mysqli_num_rows($result) > 0) {
		$row = mysqli_fetch_assoc($result);
		$fav = $row['favourites'];
		$fav = $fav.$_POST['id']." ";
		$sql = "UPDATE favourites SET favourites='".$fav."' WHERE username='".$_SESSION['username']."'";
		$conn->query($sql);
	}

	mysqli_close($conn);
	
}
?>