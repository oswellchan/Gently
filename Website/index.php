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
	<?php
		if (isset ( $_SESSION ['username'] )) {
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
				
				$sql = "SELECT * FROM `channel` WHERE `username` IN (".$fav.") ORDER BY `viewers` DESC";
				$result = mysqli_query($conn, $sql);
				echo '<div class="row">
				<h1>Favourites</h1>';
				if (mysqli_num_rows($result) > 0) {
					
					for ($i=0;$i<min(3,mysqli_num_rows($result));$i++){
					$row = mysqli_fetch_assoc($result);
					
					echo '<div class="col-md-4">
					<a href="channel.php?id='.$row["username"].'"><img
					src="images/320px-Placeholder.jpg"><br>
					<h4>'.$row["name"].'</h4></a>'.$row["viewers"].' viewers watching '.$row["username"].'
							</div>
					';
					}
					
				} else {
					echo 'Start adding some channels to your favourites!';
				}
				echo '</div>';
			}

			mysqli_close($conn);
			
			}
	?>
		<div class="row">
			<h1>Popular</h1>
			
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
			
			$sql = "SELECT * FROM `channel` ORDER BY viewers DESC";
			$result = mysqli_query($conn, $sql);
			
			if (mysqli_num_rows($result) > 0) {
				// output data of each row
				for ($i=0;$i<min(3,mysqli_num_rows($result));$i++){
					$row = mysqli_fetch_assoc($result);
					
					echo '
						<div class="col-md-4">
							<a href="channel.php?id='.$row["username"].'"><img
								src="images/320px-Placeholder.jpg"><br>
								<h4>'.$row["name"].'</h4></a>'.$row["viewers"].' viewers watching '.$row["username"].'
						</div>
						';
				}
			} else {
				echo "0 results";
			}
			
			mysqli_close($conn);
			
			?>
		</div>
	</div>

	
    
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	
</body>
</html>