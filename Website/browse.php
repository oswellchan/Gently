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
				<h1>Browse</h1>
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

$sql = "SELECT * FROM `channel` WHERE `enabled`=1 ORDER BY viewers DESC";
$result = mysqli_query($conn, $sql);

if (mysqli_num_rows($result) > 0) {
	// output data of each row
	while($row = mysqli_fetch_assoc($result)) {
		echo '
				<div class="media">
					<a class="pull-left" href="/channel.php?id='.$row["username"].'"> <img
						class="media-object"
						src="thumbnails/'.$row["thumbnail"].'"
						height="100"
						width="133">
					</a>
					<div class="media-body">
						<h4 class="media-heading">
							<a href="/channel.php?id='.$row["username"].'">'.$row["name"].'</a>
						</h4>
						'.$row["username"].'<br> '.$row["viewers"].' viewers
					</div>
				</div>
		';
	}
} else {
	echo "0 results";
}

mysqli_close($conn);
?>			
			
				<br> <a href="#">&lt;&lt; Previous</a> | <a href="#">Next &gt;&gt; </a>

			</div>
		</div>
	</div>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>