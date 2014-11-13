<?php
session_start();
if(isset($_SESSION['username'])){
	$text = $_POST['text'];
	 
	$fp = fopen("chat/".$_POST['id'].".html", 'a');
	fwrite($fp, "<div class='msgln'><abbr title='".date("D, d M Y, g:i A")."'><b>".$_SESSION['username']."</b></abbr>: ".stripslashes(htmlspecialchars($text))."<br></div>");
	fclose($fp);
}
?>