<?php
 
require_once 'include/DB_Functions.php';
$db = new DB_Functions();
 
// json response array
$response = array("error" => FALSE);
 
if (isset($_POST['univName'])) {
 
    // receiving the post params
    $uniname = $_POST['univName'];
    
        // Get university information
        $universityId = $db->fetchUniversityId($uniname);
		
		if ($universityId) {
            // university data retrived successfully
            $response["error"] = FALSE;
            $response["univId"] = $universityId;
			echo json_encode($response);
        } else {
            // university failed to store
            $response["error"] = TRUE;
            $response["error_msg"] = "Unknown error occurred in fetching university id!";
            echo json_encode($response);
        }
    
} else {
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameter (univName) is missing!";
    echo json_encode($response);
}
?>