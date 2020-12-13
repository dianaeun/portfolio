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
    <title>My Page</title>
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
						echo '<li class="active"><a href="userDisplay.php">My Page</a></li>';
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
	
<?php
	$msg = 'Welcome!<br>Please login or Create Account to Continue the wonderful experience!';
	$background = 'url(images/000.png)';
	if(isset($_SESSION['username'])) {
		$username = $_SESSION['username'];
		$query  = "SELECT rid FROM Reader WHERE uname = '$username';";
		$result = $conn->query($query);
		$row = $result->fetch_array(MYSQLI_NUM);
		$rid = $row[0];
		$msg = 'Welcome '.$username.'!<br>Enjoy wonderful experience of ToonFinder!';
		$query  = "SELECT wid FROM Subscribes WHERE rid = '$rid';";
		$result = $conn->query($query);
		if(mysqli_num_rows($result)!=0) {
			$rows = $result->num_rows;
			$j = rand(0, mysqli_num_rows($result)-1);
			$result->data_seek($j);
			$row = $result->fetch_row();
			$wid = $row[0];
			$background = 'url(images/'.$wid.'.png)';
		}
	}
?>

    <div class="site-blocks-cover inner-page-cover overlay" style="background-image: <?php echo $background ?>;" data-aos="fade" data-stellar-background-ratio="0.5">
      <div class="container">
        <div class="row align-items-center justify-content-center text-center">
          <div class="col-md-10">
            <h1 class="mb-2"><?php echo $msg ?></h1>
          </div>
        </div>
      </div>
    </div>
    
	<div class="site-section">
      <div class="container">
          <div class="col-md-7" data-aos="fade-up" data-aos-delay="100">
			  <div class="site-section-title">
				  <h2>Personal Information</h2>
				</div>
				<?php
					if(isset($_SESSION['username'])) {
						$query  = "SELECT* FROM Reader WHERE uname = '$username';";
						$result = $conn->query($query);
						$result->data_seek(0);
						$row = $result->fetch_array(MYSQLI_NUM);
						$rid = $row[0];
						$age = $row[1];
						$gender = $row[2];
						if ($gender == 'F') $gender = 'Female';
						else if ($gender == 'M') $gender = 'Male';
						else $gender = 'N/A';
						$ccode = $row[3];
						$username = $row[4];
						$query = "SELECT C.country FROM Reader_Country C WHERE C.ccode = '$ccode';";
						$result = $conn->query($query);
						$row = $result->fetch_array(MYSQLI_NUM);
						$country = $row[0];
						$query = "SELECT payamount FROM Reader_age_pay WHERE age = $age;";
						$result = $conn->query($query);
						$row = $result->fetch_array(MYSQLI_NUM);
						$payamount = $row[0];
						echo "<p class=\"lead\">User Name:			 $username</p>
							  <p class=\"lead\">Age:				 $age</p>
							  <p class=\"lead\">Gender:				 $gender</p>
							  <p class=\"lead\">Country:			 $country</p>
							  <p class=\"lead\">Possible Pay Amount: $payamount Won/per Month</p>
							  <p class=\"lead\"><a href = \"userlogout.php\" title = \"Logout\">Logout</a></p>";
						$result->close();
					}
					else {
						echo "<p class=\"lead\"><a href = \"userlogin.php\" title = \"Login\">Login</a></p>";
					}	
				?>
			  </div>
      </div>
    </div>

    <div class="site-section">
    <div class="container">
      <div class="row mb-5 justify-content-center"  data-aos="fade-up" >
        <div class="col-md-7">
          <div class="site-section-title text-center">
            <h2>Subscribed Webtoons</h2>
          </div>
        </div>
      </div>
      <div class="row">
		<?php
			$delayNum = 200;
			if(isset($_SESSION['username'])) {
				
				$query  = "SELECT W.name, W.wid, W.genre FROM Subscribes S, Webtoon W WHERE rid = '$rid' AND S.wid = W.wid;";
				$result = $conn->query($query);
				$genre = array();
				if(mysqli_num_rows($result)==0) {
					echo "<p class=\"lead\">There is no Subscribed Webtoon!</p>";
				}
				else {
					$rows = $result->num_rows;
					for ($j = 0 ; $j < $rows ; ++$j) {
							$result->data_seek($j);
							$row = $result->fetch_row();
							$wid = $row[1];
							$genre[] = $row[2];
							echo "<div class=\"col-md-6 col-lg-4 mb-5 mb-lg-5\"  data-aos=\"fade-up\" data-aos-delay=\"$delayNum\">
									<div>
									  <img src=\"images/$wid.png\" alt=\"Image\" class=\"img-fluid rounded mb-4\">
									  <div class=\"text\">
										<h2 class=\"mb-2 font-weight-light text-black h4\">$row[0]</h2>
										<span class=\"d-block mb-3 text-white-opacity-05\">$row[2]</span>
										<p>
											<form action =\"webtoon.php\" method=\"post\" style='display: inline-block;'>
											<input type='hidden' name='webtoon' value=".$wid."><input type=\"submit\" class = \"btn btn-outline-primary rounded-0 py-2 px-4\" value=\"View Info\"></form>
											<form name='subDelete' method='post' style='display: inline-block;'> 
											<input type='hidden' name='wid' value='$row[1]'>
											<input type='submit' name='delSubscribe' class = \"btn btn-outline-danger rounded-0 py-2 px-4\" value='Delete'>
											</form>
										</p>
									  </div>
									</div>
								  </div>";
							$delayNum = $delayNum + 50;
						}
					$distinctGenre = array_unique($genre);
					$genreCount = array_count_values($genre);
					echo "<div>";
					echo "<h3>Subscribed Webtoons Genre Stat</h3><table style=\"width:100%\"><tr><th>Genre</th><th>Count</th><tr>";
					foreach ($distinctGenre as $c) {
						echo "<tr><td>$c</td><td>$genreCount[$c]</td></tr>";
					}
					echo "</table></div>";
				}
				
				
			}
			else {
				echo "<p>Please login to see more information!</p>";
			}	
			if(isset($_POST['delSubscribe']))
			{
				$del = $_POST['wid'];
				$query  = "DELETE FROM Subscribes WHERE rid = '$rid' AND wid = '$del';";
				$result = $conn->query($query);
				header("Refresh:0");
			}
		?>
		</div>
    </div>
    </div>

    <div class="site-section">
    <div class="container">
      <div class="row mb-5 justify-content-center"  data-aos="fade-up" >
        <div class="col-md-7">
          <div class="site-section-title text-center">
            <h2>Favorite Webtoons</h2>
          </div>
        </div>
      </div>
      <div class="row">
		<?php
			if(isset($_SESSION['username'])) {
				$query  = "SELECT W.name, W.wid, W.genre FROM Likes L, Webtoon W WHERE L.rid = '$rid' AND L.wid = W.wid;";
				$result = $conn->query($query);
				if(mysqli_num_rows($result)==0) {
					echo "<p class=\"lead\">There is no Favorite Webtoon!</p>";
				}
				else {
					$genre = array();
					$rows = $result->num_rows;
					for ($j = 0 ; $j < $rows ; ++$j) {
							$result->data_seek($j);
							$row = $result->fetch_row();
							$wid = $row[1];
							$genre[] = $row[2];
							echo "<div class=\"col-md-6 col-lg-4 mb-5 mb-lg-5\"  data-aos=\"fade-up\" data-aos-delay=\"$delayNum\">
									<div>
									  <img src=\"images/$wid.png\" alt=\"Image\" class=\"img-fluid rounded mb-4\">
									  <div class=\"text\">
										<h2 class=\"mb-2 font-weight-light text-black h4\">$row[0]</h2>
										<span class=\"d-block mb-3 text-white-opacity-05\">$row[2]</span>
										<p>
											<form action =\"webtoon.php\" method=\"post\" style='display: inline-block;'>
											<input type='hidden' name='webtoon' value=".$wid."><input type=\"submit\" class = \"btn btn-outline-primary rounded-0 py-2 px-4\" value=\"View Info\"></form>
											<form name='subDelete' method='post' style='display: inline-block;'> 
											<input type='hidden' name='wid' value='$row[1]'>
											<input type='submit' name='delLikes' class = \"btn btn-outline-danger rounded-0 py-2 px-4\" value = 'Delete'>
											</form>
										</p>
									  </div>
									</div>
								  </div>";
							$delayNum = $delayNum + 50;
						}
					$distinctGenre = array_unique($genre);
					$genreCount = array_count_values($genre);
					echo "<div>";
					echo "<h3>Favorite Webtoons Genre Stat</h3><table style=\"width:100%\"><tr><th>Genre</th><th>Count</th><tr>";
					foreach ($distinctGenre as $c) {
						echo "<tr><td>$c</td><td>$genreCount[$c]</td></tr>";
					}
					echo "</table></div>";
				}
			}
			else {
				echo "<p>Please login to see more information!</p>";
			}	
				if(isset($_POST['delLikes']))
				{
					$del = $_POST['wid'];
					$query  = "DELETE FROM Likes WHERE rid = '$rid' AND wid = '$del';";
					$result = $conn->query($query);
					header("Refresh:0");
				}
		?>
		</div>
    </div>
    </div>
	
    
    <div class="site-section">
    <div class="container">
      <div class="row mb-5 justify-content-center"  data-aos="fade-up" >
        <div class="col-md-7">
          <div class="site-section-title text-center">
            <h2>Favorite Cartoonists</h2>
          </div>
        </div>
      </div>
      <div class="row">
		<?php
			if(isset($_SESSION['username'])) {			
				$query  = "SELECT C.name, C.position, C.cid FROM Favorite_of R, Cartoonist C WHERE R.rid = '$rid' AND R.cid = C.cid;";
				$result = $conn->query($query);
				if(mysqli_num_rows($result)==0) {
					echo "<p class=\"lead\">There is no Favorite Cartoonist!</p>";
				}
				else {
					$rows = $result->num_rows;
					for ($j = 0 ; $j < $rows ; ++$j) {
							$result->data_seek($j);
							$row = $result->fetch_row();
							$cid = $row[2];
							if($row[1]='B') $pos = 'Story, Drawing';
							else if($row[1]='W') $pos = 'Story';
							else $pos = 'Drawing';
							$k = rand(1, 3);
							echo "<div class=\"col-md-6 col-lg-4 mb-5 mb-lg-5\"  data-aos=\"fade-up\" data-aos-delay=\"$delayNum\">
									<div>
									  <img src=\"images/cartoonist$k.png\" alt=\"Image\" class=\"img-fluid rounded mb-4\">
									  <div class=\"text\">
										<h2 class=\"mb-2 font-weight-light text-black h4\">$row[0]</h2>
										<span class=\"d-block mb-3 text-white-opacity-05\">$pos</span>
										<p>
											<form action =\"cartoonist.php\" method=\"post\" style='display: inline-block;'>
											<input type='hidden' name='cartoonist' value=".$cid."><input type=\"submit\" class = \"btn btn-outline-primary rounded-0 py-2 px-4\" value=\"View Info\"></form>
											<form name='subDelete' method='post' style='display: inline-block;'>
											<input type='hidden' name='cid' value='$row[2]'><input type='submit' class = \"btn btn-outline-danger rounded-0 py-2 px-4\" name='delFavorite' value = 'Delete'>
											</form>
										</p>
									  </div>
									</div>
								  </div>";
							$delayNum = $delayNum + 50;
						}
				}
			}
			else {
				echo "<p>Please login to see more information!</p>";
			}
			if(isset($_POST['delFavorite']))
			{
				$del = $_POST['cid'];
				$query  = "DELETE FROM Favorite_of WHERE rid = '$rid' AND cid = '$del';";
				$result = $conn->query($query);
				header("Refresh:0");
			}
		?>
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