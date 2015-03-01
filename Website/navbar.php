<?php
session_start ();
if (isset ( $_GET ['logout'] )) {
	logout();
}
if (isset ( $_POST ['usernameInput'] )) {
	include 'connectsql.php';
	
	processLogin($conn);
}

// left half of navbar
echo '
	<div class="navbar navbar-default navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-responsive-collapse">
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="/"><img class="navbar-brand-img" src="/images/gentlylogo.png">Gently</a>
			</div>
			<div class="navbar-collapse collapse navbar-responsive-collapse">
				<ul class="nav navbar-nav">
					<li><a href="/browse.php">Browse</a></li>
					<li><a href="/random.php">Random</a></li>
					<li><a href="/applet.php">Applet</a></li>
				</ul>
				<form id="form" class="navbar-form navbar-right" method="get" action="search.php">
					<input id="search" name="search" type="text" class="form-control col-lg-8 navsearch" placeholder="Search">
				</form>
';

// right half of navbar
if (isset ( $_SESSION ['username'] )) {
	// if logged in
	echo '
				<ul class="nav navbar-nav navbar-right">
					<li><a href="/channel.php?id='.$_SESSION ["username"].'">' . $_SESSION ["username"] . '</a></li>
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">Account <b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><a href="/accountsettings.php">Account settings</a></li>
							<li><a href="/channelsettings.php">Channel settings</a></li>
							<li><a href="managefavourites.php">Manage favourites</a></li>
							<li class="divider"></li>
							<li><a href="
	';
							if (isset ( $_GET ['id'] )) {
								$id = str_replace('#', '', $_GET ['id']);
								echo '
									?id='.$id.'&logout=true
								';
							} else {
								echo '
									?logout=true
								';
							}

	echo
							'">Sign out</a></li>
						</ul>
					</li>
				</ul>
	';
} else {
	// if not logged in
	echo '
				<ul class="nav navbar-nav navbar-right">
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">Sign in <b class="caret"></b></a>
						<ul class="dropdown-menu" style="padding: 15px;min-width: 250px;">
							<li>
								<div class="row">
									<div class="col-md-12">
										<form class="form" role="form" method="post" action="#" accept-charset="UTF-8" id="login-nav">
											<div class="form-group">
												<label class="sr-only" for="usernameInput">Username</label>
												<input type="text" class="form-control" name="usernameInput" placeholder="Username" required>
											</div>
											<div class="form-group">
												<label class="sr-only" for="passwordInput">Password</label>
												<input type="password" class="form-control" name="passwordInput" placeholder="Password" required>
											</div>
											<div class="form-group">
												<button type="submit" class="btn btn-success btn-block">Sign in</button>
											</div>
										</form>
									</div>
								</div>
							</li>
						</ul>
					</li>
					<li><a href="/register.php">Sign up</a></li>
				</ul>
	';
}

echo '
			</div>
		</div>
	</div>
';

// footer
echo '
	<div class="footer">
		<div class="container">
			<p class="text-muted">Copyright &copy; 2015 Gently</p>
		</div>
	</div>
';

function logout() {
	session_destroy ();
	echo '
		<script type="text/javascript">
			var url = location.href;
			url=url.replace("?logout=true","");
			url=url.replace("&logout=true","");
			location.href =url;
		</script>
	';
}

function processLogin($conn) {
	$stmt = mysqli_prepare($conn, "SELECT username,password,salt FROM login WHERE username=?");
	mysqli_stmt_bind_param($stmt, 's', $_POST['usernameInput']);
	mysqli_stmt_execute($stmt);
	mysqli_stmt_bind_result($stmt, $acctusername, $acctpassword, $acctsalt);
	mysqli_stmt_store_result($stmt);
	
	if (mysqli_stmt_num_rows($stmt) > 0) {
		// output data of each row
		mysqli_stmt_fetch($stmt);
		if ($acctusername  == $_POST['usernameInput'] && $acctpassword==md5($_POST['passwordInput'].$acctsalt)){
			$_SESSION ['username'] = $_POST ['usernameInput'];
		} else {
			// wrong password
			echo '<div class="alert alert-warning" role="warning"><center>Incorrect username or password</center></div>';
		}
	} else {
		// no such user
		echo '<div class="alert alert-warning" role="warning"><center>Incorrect username or password</center></div>';
	}
	
	mysqli_stmt_close($stmt);
	mysqli_close($conn);
}
?>