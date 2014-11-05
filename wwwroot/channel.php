<?php 
if (isset ( $_GET ['id'] )) {
	$id = str_replace('#', '', $_GET ['id']);
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
	
	$sql = "SELECT * FROM channel WHERE username='".$id."'";
	$result = mysqli_query($conn, $sql);
	
	if (mysqli_num_rows($result) > 0) {
		// output data of each row
		$chnrow = mysqli_fetch_assoc($result);
	} else {
		echo '<div class="alert alert-warning" role="warning"><center>No user channel found</center></div>';
	}
	
	mysqli_close($conn);
	
	
} else {
	header ( "Location: ../browse.php" );
}
?>
<html>
<head>
<title><?php echo $chnrow["name"]; ?></title>
<link href="/css/bootstrap.min.css" rel="stylesheet">
<script src="/jwplayer/jwplayer.js"></script>
</head>
<body>
		<?php
		include 'navbar.php';
		include 'channel/connect.php';
		?>
        
		<div class="container-fluid">
		<div class="row">
			<div class="col-md-10 col-md-offset-1">
				<h1><?php echo $chnrow["name"]; ?></h1>
			</div>
		</div>
		<div class="row">
			<div id="video" class="col-md-9">
				<div id="myElement">Loading the player...</div>
			</div>

			<div id="chat" class="col-md-3">
				<iframe frameborder="0" scrolling="no"
					src="http://twitch.tv/twitchplayspokemon/chat?popout=" height=476px
					width=100%></iframe>
			</div>
		</div>
		<br>
		<div class="row">
			<div class="col-md-10 col-md-offset-1">
				<?php echo $chnrow["description"]; ?>
			</div>
		</div>
	</div>

	<script type="text/javascript">

		</script>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script src="/js/bootstrap.min.js"></script>
	<script type="text/javascript">
			jwplayer("myElement").setup({
				file: <?php echo json_encode($source); ?>,
				width: "100%",
				aspectratio: "16:9",
				autostart: true,
				mute: true
			});
			
			$('#search').on('input', function() { 
				var mega = "MEGAMAN PORN. Has anyone really been far even as decided to use even go want to do look more like? You've got to be kidding me. I've been further even more decided to use even go need to do look more as anyone can. Can you really be far even as decided half as much to use go wish for that? My guess is that when one really been far even as decided once to use even go want, it is then that he has really been far even as decided to use even go want to do look more like. It's just common sense.";
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