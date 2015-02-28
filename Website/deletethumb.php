<?php
session_start();
if(isset($_SESSION['username'])){
	include 'connectsql.php';

	$sql = "SELECT * FROM `channel` WHERE `username`='".$_SESSION['username']."'";
	$result = mysqli_query($conn, $sql);
	
	if (mysqli_num_rows($result) > 0) {
		$row = mysqli_fetch_assoc($result);
		unlink("thumbnails/".$row['thumbnail']);
	}
	
	$sql = "UPDATE channel SET thumbnail='default.jpg' WHERE `username`='".$_SESSION['username']."'";
	$result = mysqli_query($conn, $sql);

	mysqli_close($conn);

}
header ( "Location: channelsettings.php?thumbdeleted=true" );
?>