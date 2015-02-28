<?php 
include 'navbar.php';
include 'connectsql.php';
include 'connect.php';

//Load channel name and description
if (isset($_GET['id'])) {
	$id = str_replace('#', '', $_GET['id']);
	
	$stmt = mysqli_prepare($conn, "SELECT `name`,`description`,`enabled` FROM `channel` WHERE `username`=?");
	mysqli_stmt_bind_param($stmt, 's', $id);
	mysqli_stmt_execute($stmt);
	mysqli_stmt_bind_result($stmt, $chnname, $chndescription, $enabled);
	mysqli_stmt_store_result($stmt);
	
	if (mysqli_stmt_num_rows($stmt) > 0) {
		mysqli_stmt_fetch($stmt);
	} else {
		echo '<div class="alert alert-warning" role="warning"><center>No user channel found</center></div>';
	}
	mysqli_stmt_close($stmt);
	
	if ($enabled != 1) {
		header ( "Location: ../browse.php" );
	}
	
	// check for favourites if logged in
	if (isset($_SESSION['username'])) {
		$sql = "SELECT * FROM `favourites` WHERE `username`='".$_SESSION['username']."' AND  `favourites`='".$_GET['id']."'";
		$result = mysqli_query($conn, $sql);
		
		if (mysqli_num_rows($result) > 0) {
			$faved = true;
		} else {
			$faved = false;
		}
	} else {
		// true so that button is disabled
		$faved = true;
	}
	
	mysqli_close($conn);
	
} else {
	// no id provided
	header ( "Location: ../browse.php" );
}
?>
<html>
<head>
	<title><?php echo $chnname; ?> - Gently</title>
	<link rel="shortcut icon" type="image/x-icon" href="favicon.ico">
	<link href="/css/bootstrap.min.css" rel="stylesheet">
	<script src="/jwplayer/jwplayer.js"></script>
	<script	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script src="/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div id="video" class="col-md-9" onresize="resizeScript">
				<div id="myElement"><center><h4>Loading the player...</h4></center></div>
			</div>

			<div id="chat">
				<div id="chatbox">
					<?php
					if(file_exists("chat/".$_GET['id'].".html") && filesize("chat/".$_GET['id'].".html") > 0){
					    $handle = fopen("chat/".$_GET['id'].".html", "r");
					    $contents = fread($handle, filesize("chat/".$_GET['id'].".html"));
					    fclose($handle);
					} else {
						$fp = fopen("chat/".$_GET['id'].".html", "w");
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
				<h1 style="float: left"><?php echo $chnname; ?> <button class="btn btn-success btn-xs" id="favbtn" style="margin-left: 10px" <?php if ($faved == true) echo 'disabled';?>>+ Favourite</button></h1>
			</div>
		</div>
		<div class="row">
			<div class="col-md-10 col-md-offset-1">
				<?php echo $chndescription; ?>
			</div>
		</div>
	</div>

	
	<script type="text/javascript">
		// set chatbox size to video height and increment viewer count
		$(document).ready(function() {
			$("#chat").height($("#video").height()-20);
			$('#chatbox').animate({ scrollTop: $('#chatbox')[0].scrollHeight}, 0);
			var chn = getQueryVariable("id");
			$.post("counter.php", {id: chn,visit: 1});
		});

		// decrease viewer count
		$(window).on('beforeunload', function(){
			var chn = getQueryVariable("id");
			$.post("counter.php", {id: chn,visit: 0});
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
	
		//Use addfav.php to add to favourites
		$("#favbtn").click(function(){
			var chn = getQueryVariable("id");
			$.post("addfav.php", {id: chn});
			$("#favbtn").attr('disabled','disabled');
			return false;
		});
	
		var source = chooseVideoSource();
		
		// updates chat board every 500ms
		setInterval (loadLog, 500);

		function chooseVideoSource() {
			var errorCount = 0;
			var source = null;
			var serverstr = <?php echo json_encode($serverstr); ?>;
			var esList = [];
			var ipList = serverstr.split(" ");
			for (var i = 0; i < ipList.length; i++) {
			    esList.push({
			        stream: ipList[i],
			        domain: extractDomain(ipList[i]),
			        ping: -1,
			        starttime: 0,
			        status: 'nil'
			    });
			}
			
			for (var i = 0; i < esList.length; i++) {
			    ping(esList[i]);
			}

			return source;
		}
		
		function extractDomain(url) {
		    var domain;
		    // find & remove protocol (http, ftp, etc.) and get domain
		    if (url.indexOf("://") > -1) {
		        domain = url.split('/')[2];
		    }
		    else {
		        domain = url.split('/')[0];
		    }
	
		    // find & remove port number
		    domain = domain.split(':')[0];
	
		    return domain;
		}
	
		function ping(server) {
		    this.file = new Image();
	
		    this.file.onload = function () {
		        server.ping = Date.now() - server.starttime;
		        server.status = 'Responded';
		        
		        // Set first response as video source
		        if (source == null) {
		            source = server.stream;
					console.log("SELECTED: " + source);
					
		            jwplayer("myElement").setup({
		                file: source,
		                width: "100%",
		                aspectratio: "16:9",
		                autostart: true,
		                mute: true
		            });
		        }
		        console.log(server.ping + " " + server.status + " " + server.stream);
		    };
			
		    this.file.onerror = function (e) {
		        server.ping = Date.now() - server.starttime;
		        server.status = 'Test error';
		        console.log(server.ping + " " + server.status + " " + server.stream);

		        errorCount++;
		        // all servers error
		        if (errorCount == esList.length){
		        	$("#myElement").html("<center><h4>No streaming servers available</h4></center>");
		        }
		    };
	
		    server.starttime = Date.now();
			
		    //this.file.src = "http://" + server.domain + "/speedtest.jpg?no-cache=" + Math.floor(Math.random() * 100000);
		    this.file.src = "http://google.com/images/srpr/logo11w.png";
		}

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
	</script>
</body>
</html>