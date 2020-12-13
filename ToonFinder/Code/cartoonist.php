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
    <title>ToonFinder</title>
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
  

<?php

	
	if (isset($_POST["cartoonist"])) {
		$_SESSION['cid'] = intval(get_post($conn, "cartoonist"));
	}
	if(isset($_SESSION['cid'])) {
		$cid = $_SESSION['cid'];
		$query = "SELECT * FROM Cartoonist WHERE cid = $cid";
			
		$result = $conn->query($query);
		if (!$result) die ("SELECT failed: " . $conn->error);
	
		$row = mysqli_fetch_assoc($result);
		$name = $row['name'];
		$pos = findPos($row['position']);
		$query = "SELECT tid FROM Member_of WHERE cid = $cid";
		$result = $conn->query($query);
		if (!$result) die ("SELECT failed: " . $conn->error);
		$row = mysqli_fetch_assoc($result);
		$tid = $row['tid'];
	}
else {
		$_POST["cartoonist"] = $_SESSION['cid'];
} 


	function get_post($conn, $var) {
		return $conn->real_escape_string($_POST[$var]);
	}
	
	function findPos($position) {
		$pos = '';
		if($position == 'B') {
            $pos = 'Story/Drawing';
        }
        else if($position == 'W') {
            $pos = 'Story';
        }
        else if($position == 'D') {
            $pos = 'Drawing';
        }
		return $pos;
	}
	
	
?>



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
	
	<div class="site-blocks-cover inner-page-cover overlay" style = "background-image:url(images/000.png);"  data-aos="fade" data-stellar-background-ratio="0.5">
      <div class="container">
        <div class="row align-items-center justify-content-center text-center">
          <div class="col-md-10">
		  <?php
            echo '<h1 class="mb-2">' . $name .'</h1>';
		  ?>
          </div>
        </div>
      </div>
    </div>
    
    </div>
	
<?php
	$randomImg = '000';
	$query = "SELECT W.wid FROM Webtoon W, Written_by B WHERE W.wid = B.wid AND B.tid = $tid;";					
	$result = $conn->query($query);
	if (!$result) die ("SELECT failed: " . $conn->error);
	if(mysqli_num_rows($result)!=0) {
		$rows = $result->num_rows;
		$j = rand(0, mysqli_num_rows($result)-1);
		$result->data_seek($j);
		$row = $result->fetch_row();
		$randomImg = $row[0];
	}
	$query = "SELECT name, wage FROM Team, Team_Pay WHERE Team.wnumber = Team_Pay.wnumber AND tid = $tid";
	$result = $conn->query($query);
	if (!$result) die ("SELECT failed: " . $conn->error);
	$row = mysqli_fetch_assoc($result);
	$team = $row['name'];
	$wage = $row['wage'];
	
	$query = "SELECT name, position, C.cid FROM Cartoonist C, Member_of M WHERE C.cid = M.cid AND M.tid = $tid AND C.cid != $cid";					
	$result = $conn->query($query);
	if (!$result) die ("SELECT failed: " . $conn->error);

	$rows_Members = $result ->num_rows;
	$row_Member = $result;
	
	$query = "SELECT name, genre, W.wid FROM Webtoon W, Written_by B WHERE W.wid = B.wid AND B.tid = $tid;";					
	$result = $conn->query($query);
	if (!$result) die ("SELECT failed: " . $conn->error);
	
	$rows_Works = $result ->num_rows;
	$row_Works = $result;
	
?>

	<div class="site-section">
      <div class="container">
        <div class="row">
          <div class="col-md-6" data-aos="fade-up" data-aos-delay="100">
            <img src="images/<?php echo $randomImg ?>.png" alt="Image" class="img-fluid">
			<form action="cartoonist.php" method="post">
				<button type="submit" name="favorite" class="btn btn-success text-white btn-block rounded-0">Favorite</button>
			</form>
          </div>
		  
		 <?php 
			if (isset($_POST['favorite'])) {
				if (isset($_SESSION['username'])) {
					$username = $_SESSION['username'];
					$query  = "SELECT* FROM Reader WHERE uname = '$username';";
					$result = $conn->query($query);
					$result->data_seek(0);
					$row = $result->fetch_array(MYSQLI_NUM);
					$rid = $row[0];
					$query  = "INSERT Favorite_of (cid, rid) VALUES ($cid, $rid);";
					$result2 = $conn->query($query);
					if($result2) echo "<script>alert(\"Favorite!\");</script>";
				}
				else {
					echo "<script>if (confirm(\"Please Login\")) {location.replace(\"userlogin.php\");}</script>";
				}
			}
		?>
		  
		  
		  
          <div class="col-md-5 ml-auto"  data-aos="fade-up" data-aos-delay="200">

            <div class="site-section-title">
            <?php
				echo '<h1 class="mb-2">' . $name .'</h1>';
				echo "<h4>$pos</h4>";
				
			?>
            </div>
            <p class="lead">
			<?php
				echo "Member of $team <br>";
				echo "Wage $wage Million Won per Month";
		    ?>			
			</p>
            
          </div>
        </div>
      </div>
    </div>

    <div class="site-section">
    <div class="container">
      <div class="row mb-5 justify-content-center"  data-aos="fade-up" >
        <div class="col-md-7">
          <div class="site-section-title text-center">
            <h2>Team Members of Team: <?php echo "$team" ?></h2>
			<?php 
				if ($rows_Members < 1) {
					echo '<p>There is no other team member.</p>';
				}     
			?>
		  </div>
        </div>
      </div>
	  
	  
		
	  <?php 
	  
		while (($row = $row_Member -> fetch_assoc())) {
			
		echo '<div class="row">';
		  echo '<div class="col-md-6 col-lg-4 mb-5 mb-lg-5"  data-aos="fade-up" data-aos-delay="200">';
            echo '<div class="team-member">';
              echo '<div class="text">';	
				echo '<h2 class="mb-2 font-weight-light text-black h4">' . $row['name'] .'</h2>';
				echo '<span class="d-block mb-3 text-white-opacity-05">' . findPos($row['position']) .'</span>';
				echo "<form action =\"cartoonist.php\" method=\"post\">
					<input type='hidden' name='cartoonist' value=".$row['cid']."><input type=\"submit\" class = \"btn btn-outline-primary rounded-0 py-2 px-4\" value=\"View Info\"></form>";
			    echo '</div>';
            echo '</div>';
          echo '</div>';
			
        
		if (!$row = $row_Member -> fetch_assoc()) {
			break;
		}
		
		
          
		  echo '<div class="col-md-6 col-lg-4 mb-5 mb-lg-5"  data-aos="fade-up" data-aos-delay="200">';
            echo '<div class="team-member">';
              echo '<div class="text">';	
				echo '<h2 class="mb-2 font-weight-light text-black h4">' . $row['name'] .'</h2>';
				echo '<span class="d-block mb-3 text-white-opacity-05">' . findPos($row['position']) .'</span>';
				echo "<form action =\"cartoonist.php\" method=\"post\">
					<input type='hidden' name='cartoonist' value=".$row['cid']."><input type=\"submit\" class = \"btn btn-outline-primary rounded-0 py-2 px-4\" value=\"View Info\"></form>";
			    echo '</div>';
            echo '</div>';
          echo '</div>';
			
          
		  
		if (!$row = $row_Member -> fetch_assoc()) {
			break;
		}
  
		echo '<div class="col-md-6 col-lg-4 mb-5 mb-lg-5"  data-aos="fade-up" data-aos-delay="200">';
            echo '<div class="team-member">';
              echo '<div class="text">';	
				echo '<h2 class="mb-2 font-weight-light text-black h4">' . $row['name'] .'</h2>';
				echo '<span class="d-block mb-3 text-white-opacity-05">' . findPos($row['position']) .'</span>';
				echo "<form action =\"cartoonist.php\" method=\"post\">
					<input type='hidden' name='cartoonist' value=".$row['cid']."><input type=\"submit\" class = \"btn btn-outline-primary rounded-0 py-2 px-4\"value=\"View Info\"></form>";
			    echo '</div>';
            echo '</div>';
          echo '</div>';
		  
        echo '</div>';
				
		}
		
		?>
		
		
    </div>
    </div>
	<div class="site-section">
	<div class="container">
      <div class="row mb-5 justify-content-center"  data-aos="fade-up" >
        <div class="col-md-7">
          <div class="site-section-title text-center">
            <h2>Works</h2>
			<?php 
				if ($rows_Works < 1) {
					echo '<p>There is no Work done.</p>';
				}     
			?>
		  </div>
        </div>
      </div>
	  
	  
		
	<?php 
	  
		while (($r = $row_Works -> fetch_assoc())) {
			$id = $r['wid'];
			$name = $r['name'];
			$genre = $r['genre'];
			
			echo "<div class=\"col-md-6 col-lg-4 mb-5 mb-lg-5\"  data-aos=\"fade-up\" data-aos-delay=\"200\">";
					echo '<div>';
						echo "<img src=\"images/$id.png\" alt=\"Image\" class=\"img-fluid rounded mb-4\"></img>";
						echo "<div class=\"text\">
							<h2 class=\"mb-2 font-weight-light text-black h4\">$name</h2>
							<span class=\"d-block mb-3 text-white-opacity-05\">$genre</span>";
						echo "<p>
								<form action =\"webtoon.php\" method=\"post\">
								<input type='hidden' name='webtoon' value=".$id."><input type=\"submit\" class = \"btn btn-outline-primary rounded-0 py-2 px-4\" value=\"View Info\"></form>
							</p>
						</div>
					</div>
				</div>";
			
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

