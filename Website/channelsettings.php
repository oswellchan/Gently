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

$sql = "SELECT * FROM `channel` WHERE username='".$_SESSION['username']."'";
$result = mysqli_query($conn, $sql);

if (mysqli_num_rows($result) > 0) {
	$row = mysqli_fetch_assoc($result);
} else {
	echo "0 results";
}



if (isset ( $_POST ['channelName'] )) {
	if ($_FILES['thumbnail']['error'] == UPLOAD_ERR_OK){
		$target_dir = "thumbnails/";
		$temp = explode(".",$_FILES["thumbnail"]["name"]);
		$newfilename = $_SESSION ['username'] . '.' .end($temp);
		$target_file = $target_dir . $newfilename;
		$uploadOk = 1;
		$imageFileType = pathinfo($target_file,PATHINFO_EXTENSION);
		// Check if image file is a actual image or fake image
		if(isset($_POST["submit"])) {
			$check = getimagesize($_FILES["thumbnail"]["tmp_name"]);
			if($check !== false) {
				echo "File is an image - " . $check["mime"] . ".";
				$uploadOk = 1;
			} else {
				echo "File is not an image.";
				$uploadOk = 0;
			}
		}
		// Check if file already exists
		//if (file_exists($target_file)) {
		//	echo "Sorry, file already exists.";
		//	$uploadOk = 0;
		//}
		// Check file size
		if ($_FILES["thumbnail"]["size"] > 2000000) {
			echo "Sorry, your file is too large.";
			$uploadOk = 0;
		}
		// Allow certain file formats
		if($imageFileType != "jpg" && $imageFileType != "png" && $imageFileType != "jpeg" && $imageFileType != "gif" ) {
			echo "Sorry, only JPG, JPEG, PNG & GIF files are allowed.";
			$uploadOk = 0;
		}
		// Check if $uploadOk is set to 0 by an error
		if ($uploadOk == 0) {
			echo "Sorry, your file was not uploaded.";
			// if everything is ok, try to upload file
		} else {
			if ($row["thumbnail"]!="default.jpg"){
				unlink($target_dir.$row["thumbnail"]);
			}
			if (move_uploaded_file($_FILES["thumbnail"]["tmp_name"], $target_file)) {
				// echo "The file ". basename( $_FILES["thumbnail"]["name"]). " has been uploaded.";
			} else {
				$uploadOk = 0;
				echo "Sorry, there was an error uploading your file.";
			}
		}
		
		$stmt4 = mysqli_prepare($conn, "UPDATE channel SET thumbnail=? WHERE username=?");
		mysqli_stmt_bind_param($stmt4, 'ss', $newfilename, $_SESSION['username']);
		$row["thumbnail"] = $newfilename;
		
		mysqli_stmt_execute($stmt4);
		mysqli_stmt_close($stmt4);
	} else {
		$uploadOk = 1;
		
	}
	
	$stmt1 = mysqli_prepare($conn, "UPDATE channel SET name=? WHERE username=?");
	mysqli_stmt_bind_param($stmt1, 'ss', $_POST ['channelName'], $_SESSION['username']);
	
	$stmt2 = mysqli_prepare($conn, "UPDATE channel SET description=? WHERE username=?");
	mysqli_stmt_bind_param($stmt2, 'ss', $_POST ['channelDescription'], $_SESSION['username']);

	$stmt3 = mysqli_prepare($conn, "UPDATE channel SET enabled=? WHERE username=?");
	mysqli_stmt_bind_param($stmt3, 'is', $_POST ['enable'], $_SESSION['username']);
	
	if ($uploadOk == 1 && mysqli_stmt_execute($stmt1) === TRUE && mysqli_stmt_execute($stmt2) === TRUE && mysqli_stmt_execute($stmt3) === TRUE) {
		echo '<div class="alert alert-success" role="success"><center>Settings saved.</center></div>';
	} else {
		echo '<div class="alert alert-warning" role="warning"><center>Error updating record: '.$conn->error.'</center></div>';
	}
	mysqli_stmt_close($stmt1);
	mysqli_stmt_close($stmt2);
	mysqli_stmt_close($stmt3);
}

if (isset ( $_GET ['chatdeleted'] )) {
	if ($_GET ['chatdeleted'] == "true") {
		echo '<div class="alert alert-success" role="success"><center>Chat log deleted.</center></div>';
	} else {
		echo '<div class="alert alert-warning" role="warning"><center>Error deleting chat log.</center></div>';	
	}
}

if (isset ( $_GET ['thumbdeleted'] )) {
	if ($_GET ['thumbdeleted'] == "true") {
		echo '<div class="alert alert-success" role="success"><center>Thumbnail deleted.</center></div>';
	} else {
		echo '<div class="alert alert-warning" role="warning"><center>Error deleting thumbnail.</center></div>';
	}
}

mysqli_close($conn);
?>		


<div class="container">
	<div class="row">
		<div class="col-md-12">
		<br>
			<form class="form" method="post" action="channelsettings.php" enctype="multipart/form-data">
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
			
			<!-- File Button -->
			<div class="form-group">
			    <label class="control-label" for="filebutton">Channel Thumbnail</label>
			    <div>
					<img src="thumbnails/<?php echo $row["thumbnail"];?>" height="240" width="320">
			  
				    <?php
				    if ($row["thumbnail"]!="default.jpg"){
				    	echo '<a class="btn btn-danger" href="deletethumb.php" role="button" id="delThumbBtn">Remove thumbnail</a>';
				    }
				    ?>
			 	</div>
			 	<div class="controls">
			    	<input id="filebutton" name="thumbnail" class="input-file" type="file" accept=".jpg,.jpeg,.png,.gif,.bmp">
				</div>
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
			  	<a class="btn btn-danger" href="deletechat.php" role="button" id="delChatBtn">Delete</a>
			</div>
			</fieldset>
		</div>
	</div>
</div>
	
    
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script>
	$("#delChatBtn").click(function(){
	    return confirm("Chat log is permanent!\nProceed?");
	});

	$("#delThumbBtn").click(function(){
	    return confirm("Thumbnail deletion is permanent!\nProceed?");
	});
	</script>
	
</body>
</html>