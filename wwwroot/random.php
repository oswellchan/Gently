<?php
$array = array ();
for($i = 1; $i <= $numUser; $i ++) {
	array_push ( $array, "user" . $i . ".php" );
}
$path = $array [rand ( 0, count ( $array ) - 1 )];
header ( "Location: $path" );




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

$sql = "SELECT `username` FROM `channel` WHERE 1";
$result = mysqli_query($conn, $sql);

if (mysqli_num_rows($result) > 0) {
    // output data of each row
    while($row = mysqli_fetch_assoc($result)) {
        array_push($array, $row[username]);
    }
    $path = "channel.php?id=".$array [rand ( 0, count ( $array ) - 1 )];
    header ( "Location: $path" );
} else {
    echo "0 results";
}

mysqli_close($conn);




?>