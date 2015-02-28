<?php
include 'connectsql.php';

if ($_POST['visit']==1){
	$sql = "UPDATE `channel` SET viewers = viewers + 1 WHERE `username`='".$_POST['id']."'";
} else if ($_POST['visit']==0){
	$sql = "UPDATE `channel` SET viewers = viewers - 1 WHERE `username`='".$_POST['id']."'";
}
	
$result = mysqli_query($conn, $sql);

mysqli_close($conn);
?>