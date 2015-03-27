</html>
<head>
	<title>Gently down the stream~</title>
	<link rel="shortcut icon" type="image/x-icon" href="favicon.ico"/>
	<link rel="stylesheet" href="css/bootstrap.min.css" />
	<script src="jwplayer/jwplayer.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="https://www.java.com/js/deployJava.js"></script>
</head>
</body>
	<?php include 'navbar.php';?>
	<div class="container">
		<div class="row">
			<div class="col-md-10 col-md-offset-1">
				<h1>Web Streamer</h1>
			</div>
			<div class="col-md-10 col-md-offset-1">
				<div id="start" ></div>	
				<div class="btn-group" role="group" aria-label="...">
					<button type="button" class="btn btn-default" id="startAppletButton">Start</button>
				</div>
			</div>
		</div>
	</div>
	
	<script>
		$('#startAppletButton').on('click', function () {
			$('#start').html("<applet code='StartApplet.class' archive='GentlyRecorder.jar' width='320' height='120'></applet>");
		});	
	</script>
	
	<script>
// 		var attributes = {code:'StartApplet',  width:320, height:120} ; 
// 	 	var parameters = {jnlp_href: 'recorder.jnlp'} ; 
// 	   	deployJava.runApplet(attributes, parameters, '1.7'); 
	</script>
	
	<script>
        // using JavaScript to get location of JNLP
        // file relative to HTML page
        var dir = location.href.substring(0, location.href.lastIndexOf('/')+1);
        var url = dir + "GentlyRecorderJNLP.jnlp";
        deployJava.createWebStartLaunchButton(url, '1.7.0');
    </script>
	
</body>
</html>