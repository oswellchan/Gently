<?php
session_start ();
if (isset ( $_GET ['logout'] )) {
	session_destroy ();
	echo '
	<script type="text/javascript">
	var url = location.href;
	url=url.split("?")[0];
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
';

if (isset ( $_SESSION ['username'] )) {
	echo '
				<ul class="nav navbar-nav navbar-right">
					<li><a href="/channel/user1.php">' . $_SESSION ["username"] . '</a></li>
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">Account <b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><a href="#">Account settings</a></li>
							<li><a href="#">Channel settings</a></li>
							<li><a href="#">Manage favourites</a></li>
							<li class="divider"></li>
							<li><a href="?logout=true">Sign out</a></li>
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
				  <li><a href="#">Sign up</a></li>
				</ul>
	';
}
;

echo '
			</div>
		</div>
	</div>
';
?>