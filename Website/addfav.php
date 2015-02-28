<?php
session_start();
if(isset($_SESSION['username'])){
	include 'connectsql.php';
	
	$sql = "INSERT INTO `favourites` (username, favourites) VALUES ('".$_SESSION['username']."', '".$_POST['id']."')";
	$result = mysqli_query($conn, $sql);

	mysqli_close($conn);
}
?>