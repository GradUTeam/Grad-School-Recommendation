<?php
 
require_once 'include/DB_Functions.php';
$db = new DB_Functions();
 
// json response array
$response = array("error" => FALSE);
 
 
if (isset($_POST['usrname'])) {
 
    // receiving the post params
    $usrname = $_POST['usrname'];
    
        // Get usr information
        $usr = $db->fetchUserProfile($usrname);
		
		/* $universitynationalrank = $db->fetchUniversityNationalrank($uniid);
		$universitycompsciencerank = $db->fetchUniversityCompScienceRank($uniid);
		$universitycivilrank = $db->fetchUniversityCivilRank($uniid);
		$universityelectricalrank = $db->fetchUniversityElectricalRank($uniid);
		$universitymechanicalrank = $db->fetchUniversityMechanicalRank($uniid);
		$universitymisrank = $db->fetchUniversityMISRank($uniid); */
        
		if ($usr) {
            // university data retrived successfully
            $response["error"] = FALSE;
            //$response["uniid"] = $university["univId"];
            $response["usr"]["fullname"] = $usr["fullname"];
            $response["usr"]["username"] = $usr["username"];
            $response["usr"]["undergradGpa"] = $usr["undergradGpa"];
			$response["usr"]["greQuant"] = $usr["greQuant"];
			$response["usr"]["greVerbal"] = $usr["greVerbal"];
			$response["usr"]["engScore"] = $usr["engScore"];
			$response["usr"]["workExp"] = $usr["workExp"];
			
			echo json_encode($response);
        } else {
            // user failed to fetch
            $response["error"] = TRUE;
            $response["error_msg"] = "Unknown error occurred in fetching university data!";
            echo json_encode($response);
        }
    
} else {
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameter (usrname) is missing!";
    echo json_encode($response);
}
?>`