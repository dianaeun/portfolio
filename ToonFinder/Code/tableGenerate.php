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
    <title>Webtoons</title>
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
                  <li class="active"><a href="tableGenerate.php">Webtoons</a></li>
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
        <div class="row align-items-center justify-content-center text-center">
          <div class="col-md-10">
            <h1 class="mb-2"><?php $msg = 'Chill your day with favorite webtoons :)'; echo $msg;?></h1>
          </div>
        </div>
      </div>
    
    </div> <!--background-->
    <div class="site-section site-section-sm pb-0">
        <div class="container">
            <!-----sort----->
            <div class="row">
                <div class="col-md-12">
            <div class="ml-auto d-flex align-items-center">
        <!--<div>
        <a href="tableGenerate.php?view=true" class="view-list px-3 border-right active">Views</a>
        
        <a href="tableGenerate.php?score=true" class="view-list px-3 border-right">Scores</a>
            
        <a href="tableGenerate.php?alpa=true" class="view-list px-3">Alphabets</a>
        </div>-->
        <!--Form was here-->
        
                <div class="select-wrap">
          <!--<span class="icon icon-arrow_drop_down"></span>-->
            <form action="#" method="post">
          <select id="key" Name="key">
            <option value="view">Views</option>
            <option value="score">Score</option>
            <option value="alpa">Alphabet</option>
          </select>
		  <input type="submit" name="click" class = "btn btn-outline-primary rounded-0 py-1 px-3" value="Click">
                </form>
        </div>
            
              </div>
                </div>
            </div> <!--rowtag-->
        </div> <!--container-->
        </div>
<?php //index.php
//$servername = "localhost";
//$username = "toonfinder";
//$password = "cse305";
//$dbname = "project_database";


$upperTab = "<div class=\"site-section site-section-sm bg-light\">
      <div class=\"container\">
        <div class=\"row mb-5\">";
$belowTab = "<div class=\"site-section site-section-sm bg-light\">
      <div class=\"container\">";
echo $upperTab;    
   
$sql = "SELECT * FROM Webtoon ORDER BY views DESC;";
//$result = $conn->query($sql);
function scoreOrder() {
    $GLOBALS['sql'] = "SELECT * FROM Webtoon ORDER BY score DESC;";
    /*$GLOBALS['result'] = $conn->query($GLOBALS['sql']);*/
};
    
function viewOrder() {
    $GLOBALS['sql'] = "SELECT * FROM Webtoon ORDER BY views DESC;";
    //echo $GLOBALS['sql'];
    /*$GLOBALS['result'] = $conn->query($GLOBALS['sql']);*/
};
    
function alpaOrder() {
    $GLOBALS['sql'] = "SELECT * FROM Webtoon ORDER BY name ASC;";
    /*$GLOBALS['result'] = $conn->query($GLOBALS['sql']);*/
};

if(isset($_POST["key"])) {
    $value = trim($_POST["key"]);
    if($value === NULL or $value === "view") {
        viewOrder();
    }
    if($value === "score") {
        scoreOrder();
    }
    if($value === "alpa") {
        alpaOrder();
    }   
}
$GLOBALS['result'] = $conn->query($GLOBALS['sql']);
$result = $conn->query($sql);
function genTable() {
    if ($GLOBALS['result']->num_rows > 0) { 
        while ($row = $GLOBALS['result']->fetch_assoc()) {
        $ten = 10;
        $company = 'none';
        $background = 'ha';
        if (substr($row['wid'], 0, 1) == '1') {
            $company = 'naver';
            $background = 'success';
        }
        else if (substr($row['wid'], 0, 1) == '2') {
            $company = 'daum';
            $background = 'danger';
        }
        else {
            $company = 'kakao';
            $background = 'warning';
        }
		//<a href=\"AddToLikes.php\" class=\"property-favorite\"><span class=\"icon-heart-o\"></span></a>
        $table = "<div class=\"col-md-6 col-lg-4 mb-4\">
            <div class=\"property-entry h-100\">
              <div class=\"property-thumbnail\">
                <div class=\"offer-type-wrap\">
                  <span class=\"offer-type bg-".$background."\">".$company."</span>
                    
                </div>
                <img src=\"images/".$row['wid'].".png\" alt=\"Image\" class=\"img-fluid\">
              </div>
              <div class=\"p-4 property-body\">
				<h2 class=\"property-title\">".$row["name"]."</h2>
				
				
				<div>
				<form method='post' style='display: inline-block;'> 
				<input type='hidden' name='wid' value=".$row['wid'].">
				<input type=\"submit\" name='addLikes' class = \"btn btn-outline-danger rounded-0 py-2 px-4\" value=\"Like\">
				</form>
				<form action =\"webtoon.php\" method=\"post\" style='display: inline-block;'>
				<input type='hidden' name='webtoon' value=".$row['wid']."><input type=\"submit\" class = \"btn btn-outline-primary rounded-0 py-2 px-4\" value=\"View Info\">
				</form>
				</div>
				
				<span class=\"property-location d-block mb-3\"><span class=\"property-icon icon-room\"></span> Genre: ".$row["genre"]."</span>
                <strong class=\"property-price text-primary mb-3 d-block text-success\" > Views: ".$row["views"]/$ten." Millions</strong>
                <ul class=\"property-specs-wrap mb-3 mb-lg-0\">
                  <li>
                    <span class=\"property-specs\" >Score</span>
                    <span class=\"property-specs-number\">".$row["score"]." <sup>+</sup></span>

                  </li>
                  <li>
                    <span class=\"property-specs\" >Buy</span>
                    <span class=\"property-specs-number\">".$row["price"]."</span>

                  </li>
                </ul>

              </div>
            </div>
          </div>";
        echo $table;
        }
    } else {
    echo "0 results";
    }
}

if(isset($_POST['addLikes']))
{
	if(isset($_SESSION['username'])) {
		$uname = $_SESSION['username'];
		$wid = $_POST['wid'];
		$query  = "SELECT* FROM Reader WHERE uname = '$uname';";
		$result = $conn->query($query);
		$result->data_seek(0);
		$row = $result->fetch_array(MYSQLI_NUM);
		$rid = $row[0];
		$query  = "INSERT INTO Likes (rid, wid) VALUES ($rid, $wid);";
		$result = $conn->query($query);
		if($result) echo "<script>alert(\"Your Favorite!\");</script>";
		header("Refresh:0");
	}
	else {
		echo "<script>if (confirm(\"Please Login\")) {location.replace(\"userlogin.php\");}</script>";
	}
}

genTable();
$conn->close();
?>

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