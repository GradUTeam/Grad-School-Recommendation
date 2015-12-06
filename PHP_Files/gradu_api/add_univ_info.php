<?php
 
require_once 'include/DB_Functions.php';
$db = new DB_Functions();

// json response array
$response = array("error" => FALSE);

if (isset($_POST['univName']) && isset($_POST['univDesc']) && isset($_POST['univLocn'])
	&& isset($_POST['tuitionFees']) && isset($_POST['researchOppt']) && isset($_POST['acceptanceRate'])
	&& isset($_POST['compSciRank']) && isset($_POST['electRank']) && isset($_POST['civilRank'])
	&& isset($_POST['misRank']) && isset($_POST['mechRank']) && isset($_POST['nationalRank'])
	&& isset($_POST['gpa_actual']) && isset($_POST['greQuant']) && isset($_POST['greVerbal'])
	&& isset($_POST['toeflScore']) && isset($_POST['ieltsScore']) && isset($_POST['workExp'])
	&& isset($_POST['paperPub']) && isset($_POST['greAwa'])) {
	
	// receiving the post params
    $univName = $_POST['univName'];
	$univDesc = $_POST['univDesc'];
	$univLocn = $_POST['univLocn'];
	$tuitionFees = $_POST['tuitionFees'];
	$researchOppt = $_POST['researchOppt'];
	$acceptanceRate = $_POST['acceptanceRate'];
	$compSciRank = $_POST['compSciRank'];
	$electRank = $_POST['electRank'];
	$civilRank = $_POST['civilRank'];
	$misRank = $_POST['misRank'];
	$mechRank = $_POST['mechRank'];
	$nationalRank = $_POST['nationalRank'];
	$gpa_actual = $_POST['gpa_actual'];
	$greQuant = $_POST['greQuant'];
	$greVerbal = $_POST['greVerbal'];
	$toeflScore = $_POST['toeflScore'];
	$ieltsScore = $_POST['ieltsScore'];
	$workExp = $_POST['workExp'];
	$paperPub = $_POST['paperPub'];
	$greAwa = $_POST['greAwa'];
	
	if ($db->isUnivExisted($univName)) {
        // user already existed
        $response["error"] = TRUE;
        $response["error_msg"] = "University already existed with " . $univName;
        echo json_encode($response);
    } else {
			$univ = $db->storeUnivInfo($univName, $univDesc, $univLocn, $tuitionFees, $researchOppt, $acceptanceRate,
								$compSciRank, $electRank, $civilRank, $misRank, $mechRank, $nationalRank,
								$gpa_actual, $greQuant, $greVerbal, $toeflScore, $ieltsScore, $workExp, $paperPub, $greAwa);
			if ($univ) {
				// univ stored successfully
				$response["error"] = FALSE;
				echo json_encode($response);
			} else {
				// univ failed to store
				$response["error"] = TRUE;
				$response["error_msg"] = "Unknown error occurred in submitting university details!";
				echo json_encode($response);
			}
	}
} else {
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters is missing!";
    echo json_encode($response);
}
?>