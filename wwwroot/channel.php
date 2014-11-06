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
<title><?php echo $chnrow["name"]; ?> - Gently</title>
<link href="/css/bootstrap.min.css" rel="stylesheet">
<script src="/jwplayer/jwplayer.js"></script>
</head>
<body>
		<?php
		include 'navbar.php';
		include 'connect.php';
		?>
        
		<div class="container-fluid">
		<div class="row">
			<div id="video" class="col-md-9" onresize="resizeScript">
				<div id="myElement">Loading the player...</div>
			</div>

			<div id="chat">
					<div id="chatbox">
						<?php
						if(file_exists("chat/".$_GET ['id'].".html") && filesize("chat/".$_GET ['id'].".html") > 0){
						    $handle = fopen("chat/".$_GET ['id'].".html", "r");
						    $contents = fread($handle, filesize("chat/".$_GET ['id'].".html"));
						    fclose($handle);
						     
						    echo $contents;
						} else {
							fopen("chat/".$_GET ['id'].".html", "w");
						}
						?>
					</div>
	     			<div id="msgbox">
					    <form class="form-inline" name="message" action="#">
					        <input class="form-control" name="usermsg" type="text" id="usermsg" size="63" />
					        <button class="btn btn-primary " name="submitmsg" type="submit"  id="submitmsg">Send</button>
					    </form>
				    </div>
			</div>
		</div>
		
		<div class="row">
			<div class="col-md-10 col-md-offset-1">
				<h1><?php echo $chnrow["name"]; ?></h1>
			</div>
		</div>
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
			
			$(window).load(function() {
				$("#chat").height($("#video").height()-10);
			});
			
			$(window).resize(function(){
				$("#chat").height($("#video").height()-10);
			});
			
			//If user submits the form
			$("#submitmsg").click(function(){	
				var clientmsg = $("#usermsg").val();
				var board = getQueryVariable("id");
				$.post("post.php", {text: clientmsg, id: board});				
				$("#usermsg").val("");
				loadLog();
				return false;
			});

			//Load the file containing the chat log
			function loadLog(){		
				var oldscrollHeight = $("#chatbox").attr("scrollHeight") - 20; //Scroll height before the request
				var path = "chat/";
				path += getQueryVariable("id");
				path += ".html";
				$.ajax({
					url: path,
					cache: false,
					success: function(html){		
						$("#chatbox").html(html); //Insert chat log into the #chatbox div	
						
						//Auto-scroll			
						var newscrollHeight = $("#chatbox").attr("scrollHeight") - 20; //Scroll height after the request
						if(newscrollHeight > oldscrollHeight){
							$("#chatbox").animate({ scrollTop: newscrollHeight }, 'normal'); //Autoscroll to bottom of div
						}
				  	},
				});
			}
			
			setInterval (loadLog, 1000);
			function getQueryVariable(variable)
			{
			       var query = window.location.search.substring(1);
			       var vars = query.split("&");
			       for (var i=0;i<vars.length;i++) {
			               var pair = vars[i].split("=");
			               if(pair[0] == variable){return pair[1];}
			       }
			       return(false);
			}
		
			jwplayer().onMeta(function(event) {
				console.log(event.metadata);
			});
		</script>
</body>
</html>