<?php
session_start();
if(isset($_SESSION['username'])){
	$text = $_POST['text'];
	 
	$fp = fopen("chat/".$_POST['id'].".html", 'a');
	fwrite($fp, "<div class='msgln'><span title='".date("D, d M Y, g:i A")."'><b>".$_SESSION['username']."</b>: ".stripslashes(htmlspecialchars($text))."</span><br></div>");
	fclose($fp);
}
?>