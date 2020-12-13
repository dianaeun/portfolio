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
    <title>Search Webtoons</title>
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
                  <li class="active">
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
		<div class = "container">
				  <form action="searchEngine.php" method="post"  class="form-search col-md-12" style="margin-top: 120px;">
					<p class="mb-2">Title</p>	<input type="text" name="title"><br>
					<p class="mb-2">Company</p>
					<?php	$query = "SELECT comid, name FROM Company;";
							$result = $conn->query($query);
							$rows = $result->num_rows;
							for ($j = 0 ; $j < $rows ; ++$j) {
								$result->data_seek($j);
								$row = $result->fetch_row();
								echo "<input type=\"checkbox\" name=\"company[]\" value='$row[0]'><label style=\"padding: 10px; color: white\">$row[1]</label>";
							}
							echo "<br>";
					?>
					<p class="mb-2">Genre</p>
					<?php	$query = "SELECT DISTINCT genre FROM Webtoon ORDER BY genre ASC;";
							$result = $conn->query($query);
							$rows = $result->num_rows;
							for ($j = 0 ; $j < $rows ; ++$j) {
								$result->data_seek($j);
								$row = $result->fetch_row();
								echo "<input type=\"checkbox\" name=\"genre[]\" value=\"$row[0]\"><label style=\"padding-right: 10px; padding-left: 3px; color: white\">$row[0]</label>";
							}
							echo "<br>";
					?>
													
					<p class="mb-2">Worth</p>
					
					<?php	$query = "SELECT DISTINCT worth FROM Webtoon_Worth ORDER BY worth ASC;";
							$result = $conn->query($query);
							$rows = $result->num_rows;
							for ($j = 0 ; $j < $rows ; ++$j) {
								$result->data_seek($j);
								$row = $result->fetch_row();
								echo "<input type=\"checkbox\" name=\"worth[]\" value=\"$row[0]\"><label style=\"padding: 10px; color: white\">$row[0]</label>";
							}
							echo '<br><br><button class = "btn btn-primary btn-lg btn-block" type = "submit" name = "search">Search</button></form>';

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
	<div>
	<div class="site-section">
    <div class="container">
      <div class="row mb-5 justify-content-center"  data-aos="fade-up" >
        <div class="col-md-7">
          <div class="site-section-title text-center">
            <h2>Result</h2>
          </div>
        </div>
      </div>
	  <div class="row">
			<?php
					
				if (isset($_POST['search'])) {
					$title 	= get_post($conn, 'title');
					$titleop = '';
					if($title != '') $titleop = "W.name LIKE '%$title%'";
					$companylist = get_options('S.comid', 'company');
					$genrelist = get_options('W.genre', 'genre');
					$worthlist = get_options('O.worth', 'worth');
					$queryop = array($titleop, $companylist, $genrelist, $worthlist);
					
					$query = "SELECT W.name, W.wid, W.genre FROM Webtoon W, Webtoon_Worth O, Serialize S 
								WHERE S.wid = W.wid AND O.views = W.views AND O.score = W.score";
					$q = '';
					foreach($queryop as $selected) {
						if(!$selected == '') {
							$query = $query.' AND ('.$selected.')';
						}
					}
					$query = $query.';';
					$result  = $conn->query($query);
					
					if(mysqli_num_rows($result)==0) {
						echo "<p class=\"lead\">There is no Webtoon that match!</p>";
					}
					else {
						$delayNum = 100;
						$rows = $result->num_rows;
						for ($j = 0 ; $j < $rows ; ++$j) {
								$result->data_seek($j);
								$row = $result->fetch_row();
								$wid = $row[1];
								echo "<div class=\"col-md-6 col-lg-4 mb-5 mb-lg-5\"  data-aos=\"fade-up\" data-aos-delay=\"$delayNum\">
										<div>
										  <img src=\"images/$wid.png\" alt=\"Image\" class=\"img-fluid rounded mb-4\">
										  <div class=\"text\">
											<h2 class=\"mb-2 font-weight-light text-black h4\">$row[0]</h2>
											<span class=\"d-block mb-3 text-white-opacity-05\">$row[2]</span>
											<form action =\"webtoon.php\" method=\"post\">
											<input type='hidden' name='webtoon' value=".$wid."><input type=\"submit\" class = \"btn btn-outline-primary rounded-0 py-2 px-5\" value=\"View Info\"></form>
										  </div>
										</div>
									  </div>";
								$delayNum = $delayNum + 50;
							}
					}
				}
				
				function get_options($type, $oplist) {
					$options = '';
					if(!isset($_POST[$oplist])) return $options;
					foreach($_POST[$oplist] as $selected) {
						
						if($options == '') {
							$options = $type." = '".$selected."'";
						}
						else {
							$options = $options." OR ".$type." = '".$selected."'";
						}
					}
					return $options;
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