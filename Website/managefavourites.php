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
	
	if (!isset($_SESSION['username'])) {
		header ( "Location: ../index.php" );
	}
	
	if (isset ($_GET['remove'])) {
		$sql = "DELETE FROM `favourites` WHERE `username`='".$_SESSION['username']."' AND `favourites`='".$_GET ['remove']."'";
		$result = mysqli_query($conn, $sql);
	
		mysqli_close($conn);
	}
	
	$sql = "SELECT * FROM `favourites` WHERE `username`='".$_SESSION['username']."'";
	$result = mysqli_query($conn, $sql);
	
	if (mysqli_num_rows($result) > 0) {
		$fav = "";
		while ($row = mysqli_fetch_assoc($result)){
			$fav = $fav."','".$row['favourites'];
		}
		$fav = substr($fav, 2)."'";
	
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
		
		while ($row = mysqli_fetch_assoc($result)) {
			echo '
								<tr class="'.$row["username"].'">
									<td>'.$row["username"].'</td>
									<td>'.$row["name"].'</td>
									<td><a href="managefavourites.php?remove='.$row["username"].'" onclick="return confirm(\'Are you sure?\')"><button class="btn btn-danger btn-xs">Remove</button></a></td>
								</tr>
			';
		}
		
		echo '
							</tbody>
						</table>
					</div>
				</div>
			</div>
		';
	} else {
		echo '
			<div class="container">
				<div class="row">
					<div class="col-md-12">
						<h1>Manage favourites</h1>
						No channels in favourites
					</div>
				</div>	
			</div>
		';
	}
	
	mysqli_close($conn);
	?>
</body>
</html>