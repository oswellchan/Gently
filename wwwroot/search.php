<?php 
$searchkey = $_GET['search'];
?>

<html>
	<head>
		<title>Gently down the stream~</title>
		<link href="css/bootstrap.min.css" rel="stylesheet">
		<script src="jwplayer/jwplayer.js" ></script>
	</head>
	<body>
		<?php include 'navbar.php';?>
		<div class="container">
			<div class="row">
				
				<div class="col-md-10 col-md-offset-1">
					<h1>Search result: <?php echo "$searchkey"; ?></h1>
				</div>
				<div class="col-md-8 col-md-offset-2">
					<div class="media">
						<a class="pull-left" href="/channel/user1.php">
							<img class="media-object" src="http://static-cdn.jtvnw.net/previews-ttv/live_user_taketvred-320x240.jpg" height="100px">
						</a>
						<div class="media-body">
								<h4 class="media-heading"><a href="#">MEGAMAN SPEED RUN!</a></h4>
								User1<br>
								69 viewers
						</div>
					</div>
					<div class="media">
						<a class="pull-left" href="/channel/user2.php">
							<img class="media-object" src="http://static-cdn.jtvnw.net/previews-ttv/live_user_riotgames-320x240.jpg" height="100px">
						</a>
						<div class="media-body">
								<h4 class="media-heading"><a href="#">2014 World Championship</a></h4>
								User2<br>
								10 viewers
						</div>
					</div>
					<div class="media">
						<a class="pull-left" href="/channel/user3.php">
							<img class="media-object" src="http://static-cdn.jtvnw.net/previews-ttv/live_user_weplaydota2tv_2-320x240.jpg" height="100px">
						</a>
						<div class="media-body">
								<h4 class="media-heading"><a href="#">Watch me play blindfolded!</a></h4>
								User3<br>
								39 viewers
						</div>
					</div>
					<div class="media">
						<a class="pull-left" href="/channel/user4.php">
							<img class="media-object" src="http://static-cdn.jtvnw.net/previews-ttv/live_user_nocturnalsmoker-320x240.jpg" height="100px">
						</a>
						<div class="media-body">
								<h4 class="media-heading"><a href="#">Untitled Channel</a></h4>
								User4<br>
								1 viewer
						</div>
					</div>
					<div class="media">
						<a class="pull-left" href="/channel/user5.php">
							<img class="media-object" src="http://static-cdn.jtvnw.net/previews-ttv/live_user_starladder1-320x240.jpg" height="100px">
						</a>
						<div class="media-body">
								<h4 class="media-heading"><a href="#">sitting around doing nothing</a></h4>
								User5<br>
								729 viewers
						</div>
					</div>
					<div class="media">
						<a class="pull-left" href="/channel/user6.php">
							<img class="media-object" src="http://static-cdn.jtvnw.net/previews-ttv/live_user_beyondthesummit-320x240.jpg" height="100px">
						</a>
						<div class="media-body">
								<h4 class="media-heading"><a href="#">Worst Channel Ever</a></h4>
								User6<br>
								9283 viewers
						</div>
					</div>
					<br>
					<a href="#">&lt;&lt; Previous</a> | <a href="#">Next &gt;&gt; </a>
				
				</div>
			</div>
		</div>
	</body>
</html>