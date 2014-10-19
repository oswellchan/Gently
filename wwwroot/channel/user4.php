<html>
	<head>
		<title>Gently down the stream~</title>
		<link href="/css/bootstrap.min.css" rel="stylesheet">
		<script src="/jwplayer/jwplayer.js" ></script>
	</head>
	<body>
		<?php
			function getBestSource() {
				$ip = $_SERVER['REMOTE_ADDR'];
                $channel = 1;
                // insert magic fairy here
                return "rtmp://fms.12E5.edgecastcdn.net/0012E5/mp4:videos/8Juv1MVa-485.mp4";
			}

			$source = getBestSource();
		?>
		<?php include '../navbar.php';?>
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<h1>Untitled Channel</h1>
				</div>
			</div>
			<div class="row">
				<div id="video" class="col-md-9">
					<div id="myElement">Loading the player...</div>
				</div>

				<div id="chat" class="col-md-3">
					<iframe frameborder="0" scrolling="no" src="http://twitch.tv/twitchplayspokemon/chat?popout=" height=476px width=100%></iframe>
				</div>
			</div>
			<br>
			<div class="row">
				<div class="col-md-12">
					Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec ultrices eu nunc vitae gravida. Interdum et malesuada fames ac ante ipsum primis in faucibus. Curabitur sagittis dapibus tellus vitae vulputate. Praesent placerat enim vitae lorem accumsan, vitae iaculis leo lacinia. Cras commodo velit non leo convallis bibendum. Nullam elementum nunc eu quam tempor vulputate. Quisque egestas sagittis dapibus. Mauris aliquet non leo a dignissim. Quisque vitae sem eget dui placerat cursus bibendum a leo. Vivamus blandit sodales mauris. Phasellus hendrerit ipsum quis auctor vehicula. Duis vel tellus erat. Duis venenatis metus quis quam cursus, a elementum mi tincidunt. Quisque sodales mattis pharetra.

					In egestas, lacus at vulputate condimentum, nisl nisl lacinia diam, in laoreet nulla mi eget purus. Aliquam varius eros ac elit euismod viverra eu ac tortor. Nam tempus magna magna, vitae suscipit ipsum convallis nec. Proin eu dolor at lectus venenatis congue sit amet id dui. Pellentesque iaculis mi nec elit maximus, non lacinia turpis convallis. Interdum et malesuada fames ac ante ipsum primis in faucibus. Pellentesque lobortis interdum massa, at luctus arcu. Praesent vehicula orci eget urna iaculis, a cursus felis malesuada.
				</div>
			</div>
		</div>

		<script type="text/javascript">

		</script>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
		<script src="js/bootstrap.min.js"></script>
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