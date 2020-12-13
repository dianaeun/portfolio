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
    <title>Webtoon</title>
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

	
	if (isset($_POST["webtoon"])) {
		$_SESSION['wid'] = intval(get_post($conn, "webtoon"));
	}
	if(isset($_SESSION['wid'])) {
		$wid = $_SESSION['wid'];
		$query = "SELECT * FROM Webtoon WHERE wid = $wid";
			
		$result = $conn->query($query);
		if (!$result) die ("SELECT failed: " . $conn->error);
	
		$row = mysqli_fetch_assoc($result);
		$name = $row['name'];
		$genre = $row['genre'];
		$score = $row['score'];
		$views = $row['views'];
		$price = $row['price'];
		$summary = $row['summary'];

		$query = "SELECT T.name FROM Team T, Written_by W WHERE T.tid = W.tid AND W.wid = $wid;";
		$result = $conn->query($query);
		if (!$result) die ("SELECT failed: " . $conn->error);
		$row = mysqli_fetch_assoc($result);
		$team = $row['name'];
		$query = "SELECT since, _to, comid FROM Duration D, Serialize S WHERE D.did = S.did AND S.wid = $wid;";
		$result = $conn->query($query);
		if (!$result) die ("SELECT failed: " . $conn->error);
		$row = mysqli_fetch_assoc($result);
		$from = $row['since'];
		$to = $row['_to'];
		if($to == '-1') $to = 'Current';
		$company = 'KAKAO';
		if($row['comid'] == 1) $company = 'NAVER';
		else if($row['comid'] == 2) $company = 'DAUM';
		
		$query = "SELECT * FROM Content C, Related_to R WHERE C.content_id = R.content_id AND R.wid = $wid;";					
		$result = $conn->query($query);
		if (!$result) die ("SELECT failed: " . $conn->error);
	
		$rows_content = $result ->num_rows;
	
	}
else {
		//header('Location: tableGenerate.php');
		
		$_POST["webtoon"] = $_SESSION['wid'];
		//header("Refresh:0");
} 


	function get_post($conn, $var) {
		return $conn->real_escape_string($_POST[$var]);
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


	<div class="site-section">
      <div class="container">
        <div class="row">
          <div class="col-md-6" data-aos="fade-up" data-aos-delay="100">
            <img src="images/<?php echo $wid ?>.png" alt="Image" class="img-fluid">
			<form action="webtoon.php" method="post">
				<button type="submit" name="like" class="btn btn-success text-white btn-block rounded-0">Like</button>
			</form>	
			
			<form action="webtoon.php" method="post">
				<button type="submit" name="subscribe" class="btn btn-success text-white btn-block rounded-0">Subscribe</button>
			</form>
          </div>
		  
		 <?php 
			if (isset($_POST['like'])) {
				if (isset($_SESSION['username'])) {
					$username = $_SESSION['username'];
					$query  = "SELECT* FROM Reader WHERE uname = '$username';";
					$result2 = $conn->query($query);
					$result2->data_seek(0);
					$row2 = $result2->fetch_array(MYSQLI_NUM);
					$rid = $row2[0];
					$query  = "INSERT INTO Likes (rid, wid) VALUES ($rid, $wid);";
					$result2 = $conn->query($query);
					if($result2) echo "<script>alert(\"Liked!\");</script>";
				}
				else {
					echo "<script>if (confirm(\"Please Login\")) {location.replace(\"userlogin.php\");}</script>";
				}
				
			}
			
			if (isset($_POST['subscribe'])) {
				if (isset($_SESSION['username'])) {
					$username = $_SESSION['username'];
					$query  = "SELECT* FROM Reader WHERE uname = '$username';";
					$result2 = $conn->query($query);
					$result2->data_seek(0);
					$row2 = $result2->fetch_array(MYSQLI_NUM);
					$rid = $row2[0];
					$query  = "INSERT INTO Subscribes (rid, wid) VALUES ($rid, $wid);";
					$result2 = $conn->query($query);
					if($result2) echo "<script>alert(\"Subscribed!\");</script>";
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
				echo "<h5>by $team  |  $company</h5>";
				echo "<h5>$from - $to</h5>";
			?>
            </div>
            <p class="lead">
			<?php
			echo "";
			echo "<br>";
			echo "Genre: " . $genre;
			echo "<br>";
			echo "Score: " . $score . " / 10";
			echo "<br>";
			echo "Views: " . $views / 10  . "  M";
			echo "<br>";
			echo "Price: $price won";
			echo "<br>";
		    ?>			
			</p>
			<?php echo "<p>$summary</p>"; ?>
            
          </div>
        </div>
      </div>
    </div>

    <div class="site-section">
    <div class="container">
      <div class="row mb-5 justify-content-center"  data-aos="fade-up" >
        <div class="col-md-7">
          <div class="site-section-title text-center">
            <h2>Related Contents</h2>
			<?php 
			if ($rows_content < 1) {
				echo '<p>This webtoon doesn\'t have any related content.</p>';
            }     
			?>
		  </div>
        </div>
      </div>
	  
	  
		
	  <?php 
	  
		while (($row = $result -> fetch_assoc())) {
		
		echo '<div class="row">';
		  echo '<div class="col-md-6 col-lg-4 mb-5 mb-lg-5"  data-aos="fade-up" data-aos-delay="200">';
            echo '<div class="team-member">';
              echo '<img src="images/content_image/' . $row['content_id'] . '.PNG" alt="Image" class="img-fluid rounded mb-4">';
              echo '<div class="text">';	
                echo '<h2 class="mb-2 font-weight-light text-black h4">' . $row['title'] .'</h2>';
                echo '<span class="d-block mb-3 text-white-opacity-05">' . $row['ctype'] .'</span>';
                echo '<p> Age limit: ' . $row['age_range'] . "<br>" . 'Price: '. $row['price'] . ' Won</p>';
              echo '</div>';
            echo '</div>';
          echo '</div>';
			
        
		if (!$row = $result -> fetch_assoc()) {
			break;
		}
		
		
          
		  echo '<div class="col-md-6 col-lg-4 mb-5 mb-lg-5"  data-aos="fade-up" data-aos-delay="200">';
            echo '<div class="team-member">';
              echo '<img src="images/content_image/' . $row['content_id'] . '.PNG" alt="Image" class="img-fluid rounded mb-4">';
              echo '<div class="text">';
                echo '<h2 class="mb-2 font-weight-light text-black h4">' . $row['title'] .'</h2>';
                echo '<span class="d-block mb-3 text-white-opacity-05">' . $row['ctype'] .'</span>';
                echo '<p> Age limit: ' . $row['age_range'] . "<br>" . 'Price: '. $row['price'] . ' Won</p>';
              echo '</div>';
            echo '</div>';
          echo '</div>';
			
          
		  
		  if (!$row = $result -> fetch_assoc()) {
			break;
		}
		  
		  
		
		
		
          
		  echo '<div class="col-md-6 col-lg-4 mb-5 mb-lg-5"  data-aos="fade-up" data-aos-delay="200">';
            echo '<div class="team-member">';
              echo '<img src="images/content_image/' . $row['content_id'] . '.PNG" alt="Image" class="img-fluid rounded mb-4">';
              echo '<div class="text">';	
                echo '<h2 class="mb-2 font-weight-light text-black h4">' . $row['title'] .'</h2>';
                echo '<span class="d-block mb-3 text-white-opacity-05">' . $row['ctype'] .'</span>';
                echo '<p> Age limit: ' . $row['age_range'] . "<br>" . 'Price: '. $row['price'] . ' Won</p>';
              echo '</div>';
            echo '</div>';
          echo '</div>';
		  
        echo '</div>';
		
		}
		
		?>
		
		
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

