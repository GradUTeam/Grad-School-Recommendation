<?php
 
require_once 'include/DB_Functions.php';
$db = new DB_Functions();
 
// json response array
$response = array("error" => FALSE);
 
if (isset($_POST['uniid'])) {
 
    // receiving the post params
    $uniid = $_POST['uniid'];
    
        // Get university information
        $university = $db->fetchUniversityInfo($uniid);
		
		$universitynationalrank = $db->fetchUniversityNationalrank($uniid);
		$universitycompsciencerank = $db->fetchUniversityCompScienceRank($uniid);
		$universitycivilrank = $db->fetchUniversityCivilRank($uniid);
		$universityelectricalrank = $db->fetchUniversityElectricalRank($uniid);
		$universitymechanicalrank = $db->fetchUniversityMechanicalRank($uniid);
		$universitymisrank = $db->fetchUniversityMISRank($uniid);
        
		if ($university && $universitycompsciencerank && $universitynationalrank) {
            // university data retrived successfully
            $response["error"] = FALSE;
            //$response["uniid"] = $university["univId"];
            $response["university"]["univName"] = $university["univName"];
            $response["university"]["univDescription"] = $university["univDescription"];
            $response["university"]["univLocation"] = $university["uniState"];
			$response["university"]["univCost"] = $university["univCost"];
			$response["university"]["univResearchOpp"] = $university["univResearchOpp"];
			
			$response["university"]["univNationalRank"] = $universitynationalrank["nationalRank"];
			$response["university"]["univCompScienceRank"] = $universitycompsciencerank["compSciRank"];
            $response["university"]["univCivilEngRank"] = $universitycivilrank["CivilEngRank"];
			$response["university"]["univElecEngRank"] = $universityelectricalrank["ElecEngRank"];
			$response["university"]["univMechEngRank"] = $universitymechanicalrank["MechEngRank"];
			$response["university"]["univMisRank"] = $universitymisrank["misRank"];
			echo json_encode($response);
        } else {
            // university failed to store
            $response["error"] = TRUE;
            $response["error_msg"] = "Unknown error occurred in fetching university data!";
            echo json_encode($response);
        }
    
} else {
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameter (uniid) is missing!";
    echo json_encode($response);
}
?>