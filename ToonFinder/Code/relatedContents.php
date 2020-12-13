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
    <title>Related Contents</title>
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
                  <li class="active"><a href="relatedContents.php">Related Contents</a></li>
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
            <h1 class="mb-2"><?php $msg = 'Meet Webtoon Related Contents! :)'; echo $msg;?></h1>
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
            <option value="all">All Work</option>
            <option value="under">Underlying Work</option>
            <option value="deriv">Derivative Work</option>
            <option value="serie">Series</option>
          </select>
		  <input type="submit" class = "btn btn-outline-primary rounded-0 py-1 px-3" name="click" value="Click">
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
   
$sql = "SELECT * FROM (SELECT w.wid, w.name, r.content_id, r.rtype FROM Webtoon w, Related_to r WHERE w.wid = r.wid) t1 INNER JOIN (SELECT * FROM Content) t2 ON (t1.content_id = t2.content_id) ORDER BY name ASC;";
//$result = $conn->query($sql);
function allOrder() {
    $GLOBAL['sql'] = "SELECT * FROM (SELECT w.wid, w.name, r.content_id, r.rtype FROM Webtoon w, Related_to r WHERE w.wid = r.wid) t1 INNER JOIN (SELECT * FROM Content) t2 ON (t1.content_id = t2.content_id) ORDER BY name ASC;";
};
    
function underOrder() {
    $GLOBALS['sql'] = "SELECT * FROM (SELECT w.wid, w.name, r.content_id, r.rtype FROM Webtoon w, Related_to r WHERE w.wid = r.wid and r.rtype = \"Underlying Work\") t1 INNER JOIN (SELECT * FROM Content) t2 ON (t1.content_id = t2.content_id) ORDER BY name ASC;";
    /*$GLOBALS['result'] = $conn->query($GLOBALS['sql']);*/
};
    
function derivOrder() {
    $GLOBALS['sql'] = "SELECT * FROM (SELECT w.wid, w.name, r.content_id, r.rtype FROM Webtoon w, Related_to r WHERE w.wid = r.wid and r.rtype = \"Derivative Work\") t1 INNER JOIN (SELECT * FROM Content) t2 ON (t1.content_id = t2.content_id) ORDER BY name ASC;";
    //echo $GLOBALS['sql'];
    /*$GLOBALS['result'] = $conn->query($GLOBALS['sql']);*/
};
    
function serieOrder() {
    $GLOBALS['sql'] = "SELECT * FROM (SELECT w.wid, w.name, r.content_id, r.rtype FROM Webtoon w, Related_to r WHERE w.wid = r.wid and r.rtype = \"Series\") t1 INNER JOIN (SELECT * FROM Content) t2 ON (t1.content_id = t2.content_id) ORDER BY name ASC;";
    /*$GLOBALS['result'] = $conn->query($GLOBALS['sql']);*/
};

if(isset($_POST["key"])) {
    $value = trim($_POST["key"]);
    if($value === NULL or $value === "all") {
        allOrder();
    }
    if($value === "under") {
        underOrder();
    }
    if($value === "deriv") {
        derivOrder();
    }
    if($value === "serie") {
        serieOrder();
    }   
}
//$GLOBALS['result'] = $conn->query($GLOBALS['sql']);
$result = $conn->query($sql);
function genTable() {
    $delayNum = 200;
    if ($GLOBALS['result']->num_rows > 0) { 
        while ($row = $GLOBALS['result']->fetch_assoc()) {
            $table = "<div class=\"col-md-6 col-lg-4 mb-5 mb-lg-5\"  data-aos=\"fade-up\" data-aos-delay=\"$delayNum\">
            <div>
            <img src=\"images/content_image/".$row['content_id'].".png\" alt=\"Image\" class=\"img-fluid rounded mb-4\">
            <div class=\"text\">
            <h2 class=\"mb-2 font-weight-light text-black h4\">".$row['title']."</h2>
            <span class=\"d-block mb-3 text-white-opacity-05\">".$row['rtype']."</span><span class=\"d-block mb-3 text-white-opacity-05\">Content: ".$row['ctype']."</span>
            <span class=\"d-block mb-3 text-white-opacity-05\">Webtoon: ".$row['name']."</span>
            <span class=\"d-block mb-3 text-white-opacity-05\">Age: ".$row['age_range']."</span>
            <span class=\"d-block mb-3 text-white-opacity-05\">Price: ".$row['price']." won</span>
            <form action =\"webtoon.php\" method=\"post\">
				<input type='hidden' name='webtoon' value=".$row['wid']."><input type=\"submit\" class = \"btn btn-outline-primary rounded-0 py-2 px-4\"  value=\"View Related Webtoon\"></form>
            </div></div></div>";
            echo $table;
            $delayNum = $delayNum + 50;
        }
    } else {
    echo "0 results";
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