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
	$stmt1 = mysqli_prepare($conn, "UPDATE channel SET name=? WHERE username=?");
	mysqli_stmt_bind_param($stmt1, 'ss', $_POST ['channelName'], $_SESSION['username']);
	
	$stmt2 = mysqli_prepare($conn, "UPDATE channel SET description=? WHERE username=?");
	mysqli_stmt_bind_param($stmt2, 'ss', $_POST ['channelDescription'], $_SESSION['username']);

	$stmt3 = mysqli_prepare($conn, "UPDATE channel SET enabled=? WHERE username=?");
	mysqli_stmt_bind_param($stmt3, 'is', $_POST ['enable'], $_SESSION['username']);
	
	if (mysqli_stmt_execute($stmt1) === TRUE && mysqli_stmt_execute($stmt2) === TRUE && mysqli_stmt_execute($stmt3) === TRUE) {
		echo '<div class="alert alert-success" role="success"><center>Settings saved.</center></div>';
	} else {
		echo '<div class="alert alert-warning" role="warning"><center>Error updating record: '.$conn->error.'</center></div>';
	}
	mysqli_stmt_close($stmt1);
	mysqli_stmt_close($stmt2);
	mysqli_stmt_close($stmt3);
}

if (isset ( $_GET ['deleted'] )) {
	if ($_GET ['deleted'] == "true") {
		echo '<div class="alert alert-success" role="success"><center>Chat log deleted.</center></div>';
	} else {
		echo '<div class="alert alert-warning" role="warning"><center>Error deleting chat log.</center></div>';	
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
			<label for="channelName">Channel</label><br>
			<div class="btn-group" data-toggle="buttons">
			  <label class="btn btn-primary <?php if ($row["enabled"]==1) {echo 'active';}?>">
			    <input type="radio" name="enable" id="enable" value="1" autocomplete="off" <?php if ($row["enabled"]==1) {echo 'checked';}?>>Enable
			  </label>
			  <label class="btn btn-primary <?php if ($row["enabled"]==0) {echo 'active';}?>">
			    <input type="radio" name="enable" id="disable" value="0" autocomplete="off" <?php if ($row["enabled"]==0) {echo 'checked';}?>>Disable
			  </label>
			</div>
			<br><br>
			
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
			
			<fieldset>
			<legend>Chatbox</legend>
			<div class="form-group">
			  	<label for="channelName">Delete chat log</label><br>
			  	<button class="btn btn-danger" onclick="deleteConfirm()">Delete</button>
			</div>
			</fieldset>
		</div>
	</div>
</div>
	
    
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script>
		function deleteConfirm() {
		    if (confirm("Chat log deletion is permanent!\nProceed?") == true) {
		    	location.href = "deletechat.php";
		    } else {
		    }
		}
	</script>
	
</body>
</html>