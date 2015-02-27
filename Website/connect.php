<?php
$channel = str_replace('#', '', $_GET ['id']);
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

$stmt = mysqli_prepare($conn, "SELECT streamkey FROM channel WHERE username=?");
mysqli_stmt_bind_param($stmt, 's', $channel);
mysqli_stmt_execute($stmt);
mysqli_stmt_bind_result($stmt, $streamkey);
mysqli_stmt_store_result($stmt);

if (mysqli_stmt_num_rows($stmt) > 0) {
	mysqli_stmt_fetch($stmt);
} else {
	echo '<div class="alert alert-warning" role="warning"><center>No user channel found</center></div>';
}
mysqli_stmt_close($stmt);


$ip = $_SERVER ['REMOTE_ADDR'];
$address = 'mediatech-i.comp.nus.edu.sg';
$port = 9001;

$input = '';
$input .= $streamkey;
$input .= ' ';
$input .= $ip;

$socket = socket_create ( AF_INET, SOCK_STREAM, SOL_TCP );
if ($socket === false) {
	echo "socket_create() failed: reason: " . socket_strerror ( socket_last_error () ) . "\n";
}

$result = socket_connect ( $socket, $address, $port );
if ($result === false) {
	echo "socket_connect() failed.\nReason: ($result) " . socket_strerror ( socket_last_error ( $socket ) ) . "\n";
}

$input .= "\n";
$out = '';

socket_write ( $socket, $input );

$out = socket_read ( $socket, 2048, PHP_NORMAL_READ );
socket_close ( $socket );
// expecting "http://www.w3schools.com imgur.com mediatech-i.comp.nus.edu.sg google.com" format for list of ES
$serverstr = substr($out, 0, strlen($out)-1);



//$source = 'rtmp://mediatech-i.comp.nus.edu.sg:1935/live1/flv:123';

?>
