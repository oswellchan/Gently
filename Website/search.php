<?php
if (isset ( $_GET ['search'] )) {
	$searchkey = $_GET ['search'];
} else {
	header ( "Location: ../browse.php" );
}
?>

<html>
<head>
<title>Gently down the stream~</title>
<link rel="shortcut icon" type="image/x-icon" href="favicon.ico">
<link href="css/bootstrap.min.css" rel="stylesheet">
<script src="jwplayer/jwplayer.js"></script>
</head>
<body>
		<?php include 'navbar.php';?>
		<div class="container">
		<div class="row">

			<div class="col-md-10 col-md-offset-1">
				<h1>Search result: <?php echo "$searchkey"; ?></h1>
			</div>
			<div class="col-md-8 col-md-offset-2">
			
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
$searchkey = "%".$searchkey."%";
$stmt = mysqli_prepare($conn, "SELECT username, name, viewers FROM `channel` WHERE `username` LIKE ? AND `enabled`=1 UNION SELECT username, name, viewers FROM `channel` WHERE `name` LIKE ? AND `enabled`=1 UNION SELECT username, name, viewers FROM `channel` WHERE `description` LIKE ? AND `enabled`=1 ORDER BY viewers DESC");
mysqli_stmt_bind_param($stmt, 'sss', $searchkey, $searchkey, $searchkey);
mysqli_stmt_execute($stmt);
mysqli_stmt_bind_result($stmt, $sqlusername, $sqlname, $sqlviewers);
mysqli_stmt_store_result($stmt);

if (mysqli_stmt_num_rows($stmt) > 0) {
	// output data of each row
	while(mysqli_stmt_fetch($stmt)) {
		echo '
				<div class="media">
					<a class="pull-left" href="/channel.php?id='.$sqlusername.'"> <img
						class="media-object"
						src="images/320px-Placeholder.jpg"
						height="100px">
					</a>
					<div class="media-body">
						<h4 class="media-heading">
							<a href="/channel.php?id='.$sqlusername.'">'.$sqlname.'</a>
						</h4>
						'.$sqlusername.'<br> '.$sqlviewers.' viewers
					</div>
				</div>
		';
	}
	echo '<br> <a href="#">&lt;&lt; Previous</a> | <a href="#">Next &gt;&gt; </a>';
} else {
	echo "0 results";
}
mysqli_stmt_close($stmt);
mysqli_close($conn);
?>			
			
				

			</div>
		</div>
	</div>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>