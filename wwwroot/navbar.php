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
					<li><a href="#">Browse</a></li>
					<li><a href="/channel/random.php">Random</a></li>
				</ul>
				<form id="form" class="navbar-form navbar-right">
					<input id="search" type="text" class="form-control col-lg-8" placeholder="Search">
				</form>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="/channel/user1.php">User1</a></li>
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">Account <b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><a href="#">Settings</a></li>
							<li class="divider"></li>
							<li><a href="#">Sign out</a></li>
						</ul>
					</li>
				</ul>
			</div>
		</div>
	</div>
';?>