<html>
<head>
<title>Gently down the stream~</title>
<link href="css/bootstrap.min.css" rel="stylesheet">
<script src="jwplayer/jwplayer.js"></script>
</head>
<body>
<?php include 'navbar.php';?>

<div class="container">
	<?php
		if (isset ( $_SESSION ['username'] )) {
			echo'
			<div class="row">
				<h1>Favourites</h1>
				<div class="col-md-4">
					<a href="channel.php?id=user1"> <img
						src="images/320px-Placeholder.jpg"><br>
						<h4>MEGAMAN SPEED RUN!</h4></a>69 viewers watching User1
				</div>
				<div class="col-md-4">
					<a href="channel.php?id=user2"><img
						src="images/320px-Placeholder.jpg"><br>
						<h4>2014 World Championship</h4></a>10 viewers watching User2
				</div>
				<div class="col-md-4">
					<a href="channel.php?id=user3"><img
						src="images/320px-Placeholder.jpg"><br>
						<h4>Watch me play blindfolded!</h4></a>39 viewers watching User3
				</div>
			</div>
		';}
	?>
		<div class="row">
			<h1>Popular</h1>
			<div class="col-md-4">
				<a href="channel.php?id=user4"><img
					src="images/320px-Placeholder.jpg"><br>
					<h4>Untitled Channel</h4></a>1 viewer watching User4
			</div>
			<div class="col-md-4">
				<a href="channel.php?id=user5"><img
					src="images/320px-Placeholder.jpg"><br>
					<h4>sitting around doing nothing</h4></a>729 viewers watching User5
			</div>
			<div class="col-md-4">
				<a href="channel.php?id=user6"><img
					src="images/320px-Placeholder.jpg"><br>
					<h4>Worst Channel Ever</h4></a>9283 viewers watching User6
			</div>
		</div>
	</div>

	
    
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	
</body>
</html>