<?php
session_start();
if(file_exists("chat/".$_SESSION ['username'].".html") && filesize("chat/".$_SESSION ['username'].".html") > 0){
	$handle = fopen("chat/".$_SESSION ['username'].".html", "w");
	fwrite($handle, '');
	fclose($handle);
}
header ( "Location: channelsettings.php?deleted=true" );
?>