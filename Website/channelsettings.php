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

if (isset ( $_POST ['channelName'] )) {
	$sql1 = "UPDATE channel SET name='".$_POST ['channelName']."' WHERE username='".$_SESSION['username']."'";
	$sql2 = "UPDATE channel SET description='".$_POST ['channelDescription']."' WHERE username='".$_SESSION['username']."'";
	
	if ($conn->query($sql1) === TRUE && $conn->query($sql2) === TRUE) {
		echo '<div class="alert alert-success" role="success"><center>Settings saved.</center></div>';
	} else {
		echo '<div class="alert alert-warning" role="warning"><center>Error updating record: '.$conn->error.'</center></div>';
			
	}
}




$sql = "SELECT * FROM `channel` WHERE username='".$_SESSION['username']."'";
$result = mysqli_query($conn, $sql);

if (mysqli_num_rows($result) > 0) {
	$row = mysqli_fetch_assoc($result); 
} else {
	echo "0 results";
}

mysqli_close($conn);
?>		


<div class="container">
	<div class="row">
		<div class="col-md-12">
		<br>
			<form class="form" method="post" action="channelsettings.php">
			<fieldset>
			
			<!-- Form Name -->
			<legend>Channel Settings</legend>
			
			<!-- Text input-->
			<div class="form-group">
			  	<label for="channelName">Channel Name</label>
			  	<input class="form-control" id="channelName" name="channelName" type="text" required="" value="<?php echo $row["name"];?>">
			</div>
			
			<!-- Textarea -->
			<div class="form-group">
				<label for="channelDescription">Channel Description</label>             
				<textarea class="form-control" id="channelDescription" name="channelDescription" rows="11"><?php echo $row["description"];?></textarea>
				<span class="help-block">HTML is supported.</span>
			</div>
			
			<!-- Text input-->
			<div class="form-group">
			  	<label for="channelName">Stream key <small>for setting up OBS</small></label>
			  	<input class="form-control" id="channelName" name="channelName" type="text" required="" value="<?php echo $row["streamkey"];?>" disabled>
			</div>
			
			<!-- Button -->
			<div class="form-group">
				<button class="btn btn-success">Save</button>
			</div>
			
			</fieldset>
			</form>
		</div>
	</div>

	
    
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	
</body>
</html>