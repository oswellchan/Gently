<?php
	$numUser = 6;
	$array = array();
	for ($i = 1; $i <= $numUser; $i++) {
		array_push($array, "user".$i.".php");
	}
	$path = $array[rand(0,count($array)-1)];
	header("Location: $path");
?>