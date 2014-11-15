<?php
session_start();
if(file_exists("chat/".$_SESSION ['username'].".html") && filesize("chat/".$_SESSION ['username'].".html") > 0){
	$fp = fopen("chat/".$_SESSION ['username'].".html", "w");
	fwrite($fp, "<div class='msgln'>There doesn't seem to be anything here. Start chatting!<br></div>");
	fclose($fp);
}
header ( "Location: channelsettings.php?deleted=true" );
?>