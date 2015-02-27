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

	$sql = "SELECT * FROM `channel` WHERE `username`='".$_SESSION['username']."'";
	$result = mysqli_query($conn, $sql);
	
	if (mysqli_num_rows($result) > 0) {
		$row = mysqli_fetch_assoc($result);
		unlink("thumbnails/".$row['thumbnail']);
	}
	
	$sql = "UPDATE channel SET thumbnail='default.jpg' WHERE `username`='".$_SESSION['username']."'";
	echo $sql;
	$result = mysqli_query($conn, $sql);

	mysqli_close($conn);

}
header ( "Location: channelsettings.php?thumbdeleted=true" );
?>