<html>
<head>
	<title>Gently down the stream~</title>
	<link rel="shortcut icon" type="image/x-icon" href="favicon.ico">
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<script src="jwplayer/jwplayer.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
</head>
<body>
	<?php 
	include 'navbar.php';
	include 'connectsql.php';
	
	echo '
		<div class="container">
	';	
	if (isset ( $_SESSION['username'] )) {
		outputFavourites($conn);
	}
	echo '
			<div class="row">
				<h1>Popular</h1>
	';
	outputPopular($conn);
	
	mysqli_close($conn);
	
	echo '
			</div>
		</div>		
	';
	
	function outputFavourites($conn) {
		$sql = "SELECT * FROM `favourites` WHERE `username`='".$_SESSION['username']."'";
		$result = mysqli_query($conn, $sql);
		
		if (mysqli_num_rows($result) > 0) {
			$fav = "";
			while ($row = mysqli_fetch_assoc($result)){
				$fav = $fav."','".$row['favourites'];
			}
			$fav = substr($fav, 2)."'";
				
			$sql = "SELECT * FROM `channel` WHERE `username` IN (".$fav.") AND `enabled`=1 ORDER BY `viewers` DESC";
			$result = mysqli_query($conn, $sql);
				
			echo '
				<div class="row">
				<h1>Favourites</h1>
			';
				
			if (mysqli_num_rows($result) > 0) {
				// display up to 3 channels
				for ($i=0; $i < min(3, mysqli_num_rows($result)); $i++) {
					$row = mysqli_fetch_assoc($result);
						
					echo '
						<div class="col-md-4">
							<a href="channel.php?id='.$row["username"].'">
								<img src="thumbnails/'.$row["thumbnail"].'" height="240" width="320"><br>
								<h4>'.$row["name"].'</h4>
							</a>'.$row["viewers"].' viewers watching '.$row["username"].'
						</div>
					';
				}
		
			} else {
				echo 'Start adding some channels to your favourites!';
			}
				
			echo '</div>';
		}
	}
	
	function outputPopular($conn) {
		$sql = "SELECT * FROM `channel` ORDER BY `viewers` DESC";
		$result = mysqli_query($conn, $sql);
		
		if (mysqli_num_rows($result) > 0) {
			// display up to 3 channels
			for ($i=0; $i < min(3, mysqli_num_rows($result)); $i++) {
				$row = mysqli_fetch_assoc($result);
					
				echo '
				<div class="col-md-4">
					<a href="channel.php?id='.$row["username"].'">
						<img src="thumbnails/'.$row["thumbnail"].'" height="240" width="320"><br>
						<h4>'.$row["name"].'</h4>
					</a>'.$row["viewers"].' viewers watching '.$row["username"].'
				</div>
			';
			}
		} else {
			echo "0 results";
		}
	}
	?>
	
</body>
</html>