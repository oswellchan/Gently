<?php echo '
	<div class="navbar navbar-default navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-responsive-collapse">
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="/">Gently</a>
			</div>
			<div class="navbar-collapse collapse navbar-responsive-collapse">
				<ul class="nav navbar-nav">
					<li><a href="/search.php?search=user">Browse</a></li>
					<li><a href="/channel/random.php">Random</a></li>
				</ul>
				<form id="form" class="navbar-form navbar-right">
					<input id="search" type="text" class="form-control col-lg-8" placeholder="Search">
				</form>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="/index.php">Log in</a></li>
					<li><a href="/index.php">Sign up</a></li>
				</ul>
			</div>
		</div>
	</div>
';?>