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
                  <li class="active"><a href="cartoonists.php">Cartoonists</a></li>
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
        <div class="row align-items-center justify-content-center text-center">
          <div class="col-md-10">
            <h1 class="mb-2"><?php $msg = 'Cartoonists :)'; echo $msg;?></h1>
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
        <!--Form was here-->
        
                <div class="select-wrap">
          <!--<span class="icon icon-arrow_drop_down"></span>-->
            <form action="#" method="post">
          <select id="key" Name="key">
            <option value="all">All</option>
            <option value="b">Both</option>
            <option value="w">Story</option>
            <option value="d">Drawing</option>
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
require_once 'login.php';

// Create connection
$conn = mysqli_connect($hn, $un, $pw, $db);

// Check connection
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}

$upperTab = "<div class=\"site-section site-section-sm bg-light\">
      <div class=\"container\">
        <div class=\"row mb-5\">";
$belowTab = "<div class=\"site-section site-section-sm bg-light\">
      <div class=\"container\">";
echo $upperTab;    
   
$sql = "SELECT t.tid, t.name, p.wage, c.name, c.position, c.cid FROM Team t, Member_of m, Team_Pay p, Cartoonist c WHERE t.tid = m.tid and p.wnumber = t.wnumber and m.cid = c.cid ORDER BY c.name ASC;";
//$result = $conn->query($sql);
//will be used in loop inside team
function all() {
    $GLOBALS['sql'] = "SELECT t.tid, t.name, p.wage, c.name, c.position, c.cid FROM Team t, Member_of m, Team_Pay p, Cartoonist c WHERE t.tid = m.tid and p.wnumber = t.wnumber and m.cid = c.cid ORDER BY c.name ASC;";
    /*$GLOBALS['result'] = $conn->query($GLOBALS['sql']);*/
};    

function both() {
    $GLOBALS['sql'] = "SELECT t.tid, t.name, p.wage, c.name, c.position, c.cid FROM Team t, Member_of m, Team_Pay p, Cartoonist c WHERE t.tid = m.tid and p.wnumber = t.wnumber and m.cid = c.cid and c.position = \"B\" ORDER BY c.name ASC;";
    /*$GLOBALS['result'] = $conn->query($GLOBALS['sql']);*/
};
    
function story_Write() {
    $GLOBALS['sql'] = "SELECT t.tid, t.name, p.wage, c.name, c.position, c.cid FROM Team t, Member_of m, Team_Pay p, Cartoonist c WHERE t.tid = m.tid and p.wnumber = t.wnumber and m.cid = c.cid and c.position = \"W\" ORDER BY c.name ASC;";
    //echo $GLOBALS['sql'];
    /*$GLOBALS['result'] = $conn->query($GLOBALS['sql']);*/
};
    
function drawing() {
    $GLOBALS['sql'] = "SELECT t.tid, t.name, p.wage, c.name, c.position, c.cid FROM Team t, Member_of m, Team_Pay p, Cartoonist c WHERE t.tid = m.tid and p.wnumber = t.wnumber and m.cid = c.cid and c.position = \"D\" ORDER BY c.name ASC;";
    /*$GLOBALS['result'] = $conn->query($GLOBALS['sql']);*/
};

if(isset($_POST["key"])) {
    $value = trim($_POST["key"]);
    if($value === NULL or $value === "all") {
        all();
    }
    if($value === "b") {
        both();
    }
    if($value === "w") {
        story_Write();
    }
    if($value === "d") {
        drawing();
    }
}
//$GLOBALS['result'] = $conn->query($GLOBALS['sql']);
$result = $conn->query($sql);
function genTable() {
    if (mysqli_num_rows($GLOBALS['result']) > 0) { 
        $rows = $GLOBALS['result']->num_rows;
        for ($i = 0 ; $i < $rows ; ++$i) {
            $GLOBALS['result']->data_seek($i);
            $row = $GLOBALS['result']->fetch_row();
            $delayNum = 200;
            $position = "";
            if($row[4] === 'B') {
                $pos = 'Story/Drawing';
            }
            else if($row[4] === 'W') {
                $pos = 'Story';
            }
            else if($row[4] === 'D') {
                $pos = 'Drawing';
            }
            $table = "<div class=\"col-md-6 col-lg-4 mb-5 mb-lg-5\"  data-aos=\"fade-up\" data-aos-delay=\"$delayNum\">
				<div>
				<div class=\"text\">
				<h2 class=\"mb-2 font-weight-light text-black h4\">".$row[3]."</h2>
				<span class=\"d-block mb-3 text-white-opacity-0\">Wage: ".$row[2]." Million Won</span>
                <span class=\"d-block mb-3 text-white-opacity-0\">Team: ".$row[1]."</span>
                <span class=\"d-block mb-3 text-white-opacity-0\">Position: ".$pos."</span>
				<form action =\"cartoonist.php\" method=\"post\">
				<input type='hidden' name='cartoonist' value=".$row[5]."><input type=\"submit\" class = \"btn btn-outline-primary rounded-0 py-2 px-4\" value=\"View Info\"></form>
				</div>
				</div>
				</div>";
            $delayNum = $delayNum + 50;
            //"<p class=\"lead\">User Name:			 //$username</p>
        echo $table;
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