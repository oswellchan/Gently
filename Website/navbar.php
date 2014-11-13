<?php
session_start ();
if (isset ( $_GET ['logout'] )) {
	session_destroy ();
	echo '
	<script type="text/javascript">
	var url = location.href;
	url=url.replace("?logout=true","");
	url=url.replace("&logout=true","");
	location.href =url;
	</script>
	';
	
	//header ( "Location: ../index.php" );
}
if (isset ( $_POST ['usernameInput'] )) {
	
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
	
	$sql = "SELECT * FROM login WHERE username='".$_POST['usernameInput']."'";
	$result = mysqli_query($conn, $sql);
	
	if (mysqli_num_rows($result) > 0) {
	    // output data of each row
	    $row = mysqli_fetch_assoc($result);
	    if ($row["username"] == $_POST['usernameInput'] && $row["password"]==$_POST['passwordInput']){
	        $_SESSION ['username'] = stripslashes ( htmlspecialchars ( $_POST ['usernameInput'] ) );
	    } else {
	    	echo '<div class="alert alert-warning" role="warning"><center>Incorrect username or password</center></div>';
	    }
	} else {
	    echo '<div class="alert alert-warning" role="warning"><center>Incorrect username or password</center></div>';
	}
	
	mysqli_close($conn);
	
	
}

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
				</ul>
				<form id="form" class="navbar-form navbar-right" method="get" action="search.php">
					<input id="search" name="search" type="text" class="form-control col-lg-8 navsearch" placeholder="Search">
				</form>
';

if (isset ( $_SESSION ['username'] )) {
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
							<li><a href="';
	if (isset ( $_GET ['id'] )) {
		$id = str_replace('#', '', $_GET ['id']);
		echo '?id='.$id.'&logout=true';
	} else {
		echo '?logout=true';
	}
							
	echo	
							'">Sign out</a></li>
						</ul>
					</li>
				</ul>
	';
} else {
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
;

echo '
			</div>
		</div>
	</div>
';


echo '
	<div class="footer">
    	<div class="container">
        	<p class="text-muted">Copyright &copy; 2014 Gently</p>
    	</div>
    </div>';
?>