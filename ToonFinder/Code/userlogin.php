<?php
	ob_start();
	session_start();
   
	require_once 'login.php';
	$conn = new mysqli($hn, $un, $pw, $db);
	if ($conn->connect_error) die($conn->connect_error);
?>

<!DOCTYPE html>
<html lang="en">
  <head>
    <title>Login</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Nunito+Sans:200,300,400,700,900|Roboto+Mono:300,400,500"> 
    <link rel="stylesheet" href="fonts/icomoon/style.css">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/magnific-popup.css">
    <link rel="stylesheet" href="css/jquery-ui.css">
    <link rel="stylesheet" href="css/owl.carousel.min.css">
    <link rel="stylesheet" href="css/owl.theme.default.min.css">
    <link rel="stylesheet" href="css/bootstrap-datepicker.css">
    <link rel="stylesheet" href="css/mediaelementplayer.css">
    <link rel="stylesheet" href="css/animate.css">
    <link rel="stylesheet" href="fonts/flaticon/font/flaticon.css">
    <link rel="stylesheet" href="css/fl-bigmug-line.css">   
    <link rel="stylesheet" href="css/aos.css">
    <link rel="stylesheet" href="css/style.css">
    
  </head>
  <body>
  
    <div class="site-wrap">

    <div class="site-mobile-menu">
      <div class="site-mobile-menu-header">
        <div class="site-mobile-menu-close mt-3">
          <span class="icon-close2 js-menu-toggle"></span>
        </div>
      </div>
      <div class="site-mobile-menu-body"></div>
    </div>

    <div class="site-navbar mt-4">
        <div class="container py-1">
          <div class="row align-items-center">
            <div class="col-8 col-md-8 col-lg-4">
              <h1 class="mb-0"><a href="searchEngine.php" class="text-white h2 mb-0"><strong>ToonFinder<span class="text-danger">.</span></strong></a></h1>
            </div>
            <div class="col-4 col-md-4 col-lg-8">
              <nav class="site-navigation text-right text-md-right" role="navigation">

                <div class="d-inline-block d-lg-none ml-md-0 mr-auto py-3"><a href="#" class="site-menu-toggle js-menu-toggle text-white"><span class="icon-menu h3"></span></a></div>

                <ul class="site-menu js-clone-nav d-none d-lg-block">
                  <li>
                    <a href="searchEngine.php">Search</a>
                  </li>
                  <li><a href="cartoonists.php">Cartoonists</a></li>
                  <li><a href="tableGenerate.php">Webtoons</a></li>
                  <li><a href="relatedContents.php">Related Contents</a></li>
				  <?php
					if(isset($_SESSION['username'])) {
						echo '<li><a href="userDisplay.php">My Page</a></li>';
					}
					else {
						echo '<li><a href="userlogin.php">Login</a></li>';
					}
				  ?>
                </ul>
              </nav>
            </div>
           

          </div>
        </div>
      </div>
    </div>
	
	
	<div class="site-blocks-cover inner-page-cover overlay" style="background-image: url(images/000.png)" data-aos="fade" data-stellar-background-ratio="0.5">
      <div class="container">
        <div class="row align-items-center justify-content-center">
          <div class="col-md-10">
			<div class="site-section">
				<div class="container">
					<div class="col-md-7" data-aos="fade-up" data-aos-delay="100">
						<form class = "form-signin" role = "form" action = "<?php echo htmlspecialchars($_SERVER['PHP_SELF']); ?>" method = "post">
							<p class="mb-2">User Name</p> <input type = "text" class = "form-control" name = "username" required autofocus></br>
							<p class="mb-2">Password</p> <input type = "password" class = "form-control" name = "password" required>
							<br><button class = "btn btn-lg btn-primary btn-block" type = "submit" name = "login">Login</button>
						</form>
						<form>
							<br><button class = "btn btn-lg btn-primary btn-block" type = "submit" formaction="addAccount.php">Create Account</button>			
						</form>
				<?php
					$msg = '';
					
					if (isset($_POST['login']) && !empty($_POST['username']) 
					   && !empty($_POST['password'])) {
						$username	= get_post($conn, 'username');
						$password	= get_post($conn, 'password');
						$query  = "SELECT uname, pw FROM Reader WHERE uname = '$username' AND pw = '$password';";
						$result = $conn->query($query);
						if (!$result or mysqli_num_rows($result)==0) {
							echo "<script>if (confirm(\"Your information does not exist. Try different name or password\")) {location.replace(\"userlogin.php\");}</script>";
						}	
						else {
							$_SESSION['valid'] = true;
							$_SESSION['timeout'] = time();
							$_SESSION['username'] = $username;
							header('Refresh: 0; URL = userDisplay.php');
						}
						$result->close();
					}
					
					// real_escape_string to strip out any characters that a hacker
					// may have inserted.
					function get_post($conn, $var) {
						return $conn->real_escape_string($_POST[$var]);
					}	
				?>
			  </div>
      </div>
    </div>
          </div>
        </div>
      </div>
    </div>
	
    
	

  <script src="js/jquery-3.3.1.min.js"></script>
  <script src="js/jquery-migrate-3.0.1.min.js"></script>
  <script src="js/jquery-ui.js"></script>
  <script src="js/popper.min.js"></script>
  <script src="js/bootstrap.min.js"></script>
  <script src="js/owl.carousel.min.js"></script>
  <script src="js/mediaelement-and-player.min.js"></script>
  <script src="js/jquery.stellar.min.js"></script>
  <script src="js/jquery.countdown.min.js"></script>
  <script src="js/jquery.magnific-popup.min.js"></script>
  <script src="js/bootstrap-datepicker.min.js"></script>
  <script src="js/aos.js"></script>
  <script src="js/circleaudioplayer.js"></script>

  <script src="js/main.js"></script>
  </body>
</html>