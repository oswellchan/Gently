<?php
$array = array ();

include 'connectsql.php';

$sql = "SELECT `username` FROM `channel` WHERE `enabled`=1";
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