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
	if (isset($_GET['search'])) {
		$searchkey = sanitizeHTML($_GET['search']);
	} else {
		header("Location: ../browse.php");
	}
	include 'navbar.php';
	include 'connectsql.php'; 
	echo '
	<div class="container">
		<div class="row">
			<div class="col-md-10 col-md-offset-1">
				<h1>Search results for "'.$searchkey.'"</h1>
			</div>
			<div class="col-md-8 col-md-offset-2">
	';
	$searchkey = "%".$searchkey."%";
	$stmt = mysqli_prepare($conn, "SELECT `username`, `name`, `viewers`, `thumbnail` FROM `channel` WHERE `username` LIKE ? AND `enabled`=1 UNION SELECT `username`, `name`, `viewers`, `thumbnail` FROM `channel` WHERE `name` LIKE ? AND `enabled`=1 UNION SELECT `username`, `name`, `viewers`, `thumbnail` FROM `channel` WHERE `description` LIKE ? AND `enabled`=1 ORDER BY `viewers` DESC");
	mysqli_stmt_bind_param($stmt, 'sss', $searchkey, $searchkey, $searchkey);
	mysqli_stmt_execute($stmt);
	mysqli_stmt_bind_result($stmt, $sqlusername, $sqlname, $sqlviewers, $thumbnail);
	mysqli_stmt_store_result($stmt);
	
	if (mysqli_stmt_num_rows($stmt) > 0) {
		while(mysqli_stmt_fetch($stmt)) {
			echo '
				<div class="media">
					<a class="pull-left" href="/channel.php?id='.$sqlusername.'"> <img
						class="media-object"
						src="thumbnails/'.$thumbnail.'"
						height="100"
						width="133">
					</a>
					<div class="media-body">
						<h4 class="media-heading">
							<a href="/channel.php?id='.$sqlusername.'">'.$sqlname.'</a>
						</h4>
						'.$sqlusername.'<br> '.$sqlviewers.' viewers
					</div>
				</div>
			';
		}
		echo '<br><a href="#">&lt;&lt; Previous</a> | <a href="#">Next &gt;&gt; </a>';
	} else {
		echo "0 results";
	}
	mysqli_stmt_close($stmt);
	mysqli_close($conn);
	
	function sanitizeHTML($string) {
		// to be completed
		return $string;
	}
	?>
	
			</div>
		</div>
	</div>
</body>
</html>