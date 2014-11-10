<?php
$ip = $_SERVER ['REMOTE_ADDR'];
$channel = "user1";
$address = 'mediatech-i.comp.nus.edu.sg';
$port = 9001;

$input = '';
$input .= $ip;
$input .= ' ';
$input .= $channel;

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

$source = $out;

//$source = 'rtmp://mediatech-i.comp.nus.edu.sg:1935/live1/flv:123';
?>