<html>
	<head>
		<title>Gently down the stream~</title>
		<link href="css/bootstrap.min.css" rel="stylesheet">
		<script src="jwplayer/jwplayer.js" ></script>
	</head>
	<body>
		<?php
			function getBestSource() {
				// OSWELL DO THIS SHIT
				return "rtmp://fms.12E5.edgecastcdn.net/0012E5/mp4:videos/8Juv1MVa-485.mp4";
			}

			$source = getBestSource();
		?>
		
		<?php include 'navbar.php';?>
		<div class="container">
			<div class="row">
				<h1>Favourites</h1>
				<div class="col-md-4">
					<a href="channel/user1.php"><img src="http://static-cdn.jtvnw.net/previews-ttv/live_user_fragbitelive-320x240.jpg"><br><h4>User1's cool channel</h2></a>
				</div>
				<div class="col-md-4">
					<a href="channel/user2.php"><img src="http://static-cdn.jtvnw.net/previews-ttv/live_user_amazhs-320x240.jpg"><br><h4>User2's awesome channel</h2></a>
				</div>
				<div class="col-md-4">
					<a href="channel/user3.php"><img src="http://static-cdn.jtvnw.net/previews-ttv/live_user_versuta-320x240.jpg"><br><h4>User3's fun channel</h2></a>
				</div>
			</div>
			<div class="row">
				<h1>Popular</h1>
				<div class="col-md-4">
					<a href="channel/user4.php"><img src="http://static-cdn.jtvnw.net/previews-ttv/live_user_bdutch-320x200.jpg"><br><h4>User4's happy channel</h2></a>
				</div>
				<div class="col-md-4">
					<a href="channel/user5.php"><img src="http://static-cdn.jtvnw.net/previews-ttv/live_user_billyboyfrombel-320x200.jpg"><br><h4>User5's boring channel</h2></a>
				</div>
				<div class="col-md-4">
					<a href="channel/user6.php"><img src="http://static-cdn.jtvnw.net/previews-ttv/live_user_gbxlcartman61-320x200.jpg"><br><h4>User6's dead channel</h2></a>
				</div>
			</div>
		</div>

		<script type="text/javascript">

		</script>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
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