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
					<a href="channel/user1.php"> <img
						src="images/320px-Placeholder.jpg"><br>
						<h4>MEGAMAN SPEED RUN!</h4></a>69 viewers watching User1
				</div>
				<div class="col-md-4">
					<a href="channel/user2.php"><img
						src="images/320px-Placeholder.jpg"><br>
						<h4>2014 World Championship</h4></a>10 viewers watching User2
				</div>
				<div class="col-md-4">
					<a href="channel/user3.php"><img
						src="images/320px-Placeholder.jpg"><br>
						<h4>Watch me play blindfolded!</h4></a>39 viewers watching User3
				</div>
			</div>
		';}
	?>
		<div class="row">
			<h1>Popular</h1>
			<div class="col-md-4">
				<a href="channel/user4.php"><img
					src="images/320px-Placeholder.jpg"><br>
					<h4>Untitled Channel</h4></a>1 viewer watching User4
			</div>
			<div class="col-md-4">
				<a href="channel/user5.php"><img
					src="images/320px-Placeholder.jpg"><br>
					<h4>sitting around doing nothing</h4></a>729 viewers watching User5
			</div>
			<div class="col-md-4">
				<a href="channel/user6.php"><img
					src="images/320px-Placeholder.jpg"><br>
					<h4>Worst Channel Ever</h4></a>9283 viewers watching User6
			</div>
		</div>
	</div>

	<script type="text/javascript">

</script>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script type="text/javascript">


$('#search').on('input', function() { 
	var mega = "MEGAMAN. Has anyone really been far even as decided to use even go want to do look more like? You've got to be kidding me. I've been further even more decided to use even go need to do look more as anyone can. Can you really be far even as decided half as much to use go wish for that? My guess is that when one really been far even as decided once to use even go want, it is then that he has really been far even as decided to use even go want to do look more like. It's just common sense.";
	var length = $("#search").val().length;
	$("#search").val(mega.substring(0,length));
});
$("#form").submit(function( event ) {
	event.preventDefault();
	alert( "fuck you" );
});

</script>
</body>
</html>