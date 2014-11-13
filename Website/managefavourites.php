<html>
<head>
<title>Gently down the stream~</title>
<link rel="shortcut icon" type="image/x-icon" href="favicon.ico">
<link href="css/bootstrap.min.css" rel="stylesheet">
<script src="jwplayer/jwplayer.js"></script>
</head>
<body>
<?php include 'navbar.php';?>
<?php
if (isset ( $_SESSION ['username'] )) {
} else {
	header ( "Location: ../index.php" );
}
?>
<?php 
if (isset ( $_GET ['remove'] )) {

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

	$sql = "SELECT * FROM `favourites` WHERE username='".$_SESSION['username']."'";
	$result = mysqli_query($conn, $sql);

	if (mysqli_num_rows($result) > 0) {
		$row = mysqli_fetch_assoc($result);
		$fav = $row['favourites'];
		$fav = str_replace($_GET ['remove']." ", "", $fav);
		$sql = "UPDATE favourites SET favourites='".$fav."' WHERE username='".$_SESSION['username']."'";
		$conn->query($sql);
	}

	mysqli_close($conn);


}
?>


<?php 
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

$sql = "SELECT * FROM `favourites` WHERE username='".$_SESSION['username']."'";
$result = mysqli_query($conn, $sql);

if (mysqli_num_rows($result) > 0) {
	$row = mysqli_fetch_assoc($result);
	$fav = trim($row['favourites']);
	$fav = str_replace(' ',"', '",$fav);
	$fav = "'".$fav."'";

	$sql = "SELECT * FROM `channel` WHERE `username` IN (".$fav.") ORDER BY `username`";
	$result = mysqli_query($conn, $sql);
	echo '
<div class="container">
	<div class="row">
		<div class="col-md-12">
		<h1>Manage favourites</h1>
		<br>
			<table class="table table-hover">
		      <thead>
		        <tr>
		          <th>Username</th>
		          <th>Channel Name</th>
		          <th></th>
		        </tr>
		      </thead>
		      <tbody>
	';
	while($row = mysqli_fetch_assoc($result)) {
		echo '
				<tr class="'.$row["username"].'">
		          <td>'.$row["username"].'</td>
		          <td>'.$row["name"].'</td>
		          <td><a href="managefavourites.php?remove='.$row["username"].'" onclick="return confirm(\'Are you sure?\')"><button class="btn btn-danger btn-xs">Remove</button></a></td>
		        </tr>
		';
	}
	echo '		      </tbody>
		    </table>
		</div>
	</div>
</div>
	';
} else {
	echo "No favourites";
}

mysqli_close($conn);
?>


	
    
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	
</body>
</html>