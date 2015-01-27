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
<link rel="shortcut icon" type="image/x-icon" href="favicon.ico">
<link href="/css/bootstrap.min.css" rel="stylesheet">
<script src="/jwplayer/jwplayer.js"></script>
</head>
<body>
<?php
	include 'navbar.php';
	include 'connect.php';
?>
        
<?php 
if (isset ( $_SESSION['username'] )) {
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
	
	$sql = "SELECT * FROM `favourites` WHERE username='".$_SESSION['username']."'";
	$result = mysqli_query($conn, $sql);
	
	if (mysqli_num_rows($result) > 0) {
		$row = mysqli_fetch_assoc($result);
		$fav = $row['favourites'];
		if (strpos($fav,$_GET['id']) !== false || $_SESSION['username']==$_GET['id']) {
			$faved = true;
		} else {
			$faved = false;
		}
	}
	
	mysqli_close($conn);
}
?>
        
		<div class="container-fluid">
		<div class="row">
			<div id="video" class="col-md-9" onresize="resizeScript">
				<div id="myElement">Loading the player...</div>
				<script>
				var source = null;
				var serverstr = <?php echo json_encode($serverstr); ?>;
				var esList = [];
				var ipList = serverstr.split(" ");
				for (var i = 0; i < ipList.length; i++) {
				    esList.push({
				        name: ipList[i],
				        ping: -1,
				        starttime: 0,
				        status: 'nil'
				    });
				}

				for (var i = 0; i < esList.length; i++) {
				    ping(esList[i]);
				}

				function ping(server) {
				    this.img = new Image();

				    this.img.onload = function () {
				        server.ping = Date.now() - server.starttime;
				        server.status = 'responded';
				        if (source == null) {
				            source = server.name;
							console.log("SELECTED: " + source);
							
				            jwplayer("myElement").setup({
				                file: source,
				                width: "100%",
				                aspectratio: "16:9",
				                autostart: true,
				                mute: true
				            });
				        }
				        console.log(server.ping);
				    };

				    this.img.onerror = function (e) {
				        server.ping = Date.now() - server.starttime;
				        server.status = 'responded';
				        if (source == null) {
				            source = server.name;
							console.log("SELECTED: " + source);

				            jwplayer("myElement").setup({
				                file: source,
				                width: "100%",
				                aspectratio: "16:9",
				                autostart: true,
				                mute: true
				            });
				        }
				        console.log(server.ping + " " + server.name);
				    };

				    server.starttime = Date.now();
				    if (server.name.lastIndexOf("http://", 0) === 0) {
				        this.img.src = server.name;
				    } else {
				        this.img.src = "http://" + server.name;
				    }
				}
				</script>
			</div>

			<div id="chat">
					<div id="chatbox">
						<?php
						if(file_exists("chat/".$_GET ['id'].".html") && filesize("chat/".$_GET ['id'].".html") > 0){
						    $handle = fopen("chat/".$_GET ['id'].".html", "r");
						    $contents = fread($handle, filesize("chat/".$_GET ['id'].".html"));
						    fclose($handle);
						     
						    //echo $contents;
						} else {
							$fp = fopen("chat/".$_GET ['id'].".html", "w");
							fwrite($fp, "<div class='msgln'>There doesn't seem to be anything here. Start chatting!<br></div>");
							fclose($fp);
						}
						?>
					</div>
	     			<div id="msgbox">
					    <form class="form-inline" name="message" action="#">
					        <input class="form-control" name="usermsg" type="text" id="usermsg" size="63" <?php if (!isset($_SESSION['username'])) echo 'disabled value="Sign in to chat"';?>/>
					        <button class="btn btn-primary " name="submitmsg" type="submit"  id="submitmsg" <?php if (!isset($_SESSION['username'])) echo 'disabled';?>>Send</button>
					    </form>
				    </div>
			</div>
		</div>
		
		<div class="row">
			<div class="col-md-10 col-md-offset-1">
				<h1 style="float: left"><?php echo $chnrow["name"]; ?> <button class="btn btn-success btn-xs" id="favbtn" style="margin-left: 10px" <?php if (!isset($faved) || $faved == true) echo 'disabled';?>>+ Favourite</button></h1>
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
			
			$(window).load(function() {
				$("#chat").height($("#video").height()-20);
				$('#chatbox').animate({ scrollTop: $('#chatbox')[0].scrollHeight}, 0);
			});
			
			$(window).resize(function(){
				$("#chat").height($("#video").height()-20);
			});
			
			//If user submits the form
			$("#submitmsg").click(function(){
				var clientmsg = $("#usermsg").val();
				clientmsg = clientmsg.trim();
				if (clientmsg != ""){
					var board = getQueryVariable("id");
					$.post("post.php", {text: clientmsg, id: board});
					loadLog();
				}
				
				$("#usermsg").val("");
				return false;
			});

			$("#favbtn").click(function(){
				var chn = getQueryVariable("id");
				$.post("addfav.php", {id: chn});
				$("#favbtn").attr('disabled','disabled');
				return false;
			});

			//Load the file containing the chat log
			function loadLog(){		
				//$("#chatbox").width($("#chatbox").width());
				$("#chatbox").width($(window).width()*0.25 - 73);
				var oldscrollHeight = $('#chatbox')[0].scrollHeight; //Scroll height before the request
				var path = "chat/";
				path += getQueryVariable("id");
				path += ".html";
				$.ajax({
					url: path,
					cache: false,
					success: function(html){		
						$("#chatbox").html(html); //Insert chat log into the #chatbox div	
						
						//Auto-scroll			
						var newscrollHeight = $('#chatbox')[0].scrollHeight; //Scroll height after the request
						if(newscrollHeight > oldscrollHeight){
							$("#chatbox").animate({ scrollTop: $('#chatbox')[0].scrollHeight}, 0);
						}
				  	},
				});
				$("#chat").height($("#video").height()-20);
			}
			
			setInterval (loadLog, 500);
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