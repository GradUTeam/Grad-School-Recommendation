<?php
 
require_once 'include/DB_Functions.php';
$db = new DB_Functions();
 
// json response array
$response = array("error" => FALSE);
 
if (isset($_POST['fullname']) && isset($_POST['username'])) {
 
    // receiving the post params
    $fullname = $_POST['fullname'];
	$username = $_POST['username'];
	$courseInterest = $_POST['courseInterest'];
	$undergradGpa = floatval($_POST['undergradGpa']);
	$greQuant = floatval($_POST['greQuant']);
	$greVerbal = floatval($_POST['greVerbal']);
	$greAWA = floatval($_POST['greAWA']);
	$engScore = floatval($_POST['engScore']);
	$workExp = floatval($_POST['workExp']);
	$greTotal = floatval($greQuant + $greVerbal);
	
 
    // check if user is already existed with the same email
    //if ($db->isUserExisting($username)) {
		
        // create a new user
        $user = $db->updateUser($fullname, $username, $courseInterest, $undergradGpa, $greQuant, $greVerbal, $greAWA, $greTotal, $engScore, $workExp);
       /*  if ($user) {
            // user stored successfully */
            $response["error"] = FALSE;
			$response["user"]["courseInterest"] = $user["courseInterest"];
            $response["user"]["undergradGpa"] = $user["undergradGpa"];
			$response["user"]["greQuant"] = $user["greQuant"];
			$response["user"]["greVerbal"] = $user["greVerbal"];
			$response["user"]["greAWA"] = $user["greAWA"];
			$response["user"]["greTotal"] = $user["greTotal"];
			$response["user"]["engScore"] = $user["engScore"];
			$response["user"]["workExp"] = $user["workExp"];
            echo json_encode($response);
       /*  } else {
            // user failed to store
            $response["error"] = TRUE;
            $response["error_msg"] = "Unknown error occurred in registration!";
            echo json_encode($response);
        } */
		
		
     
/*     } else {
		   
        $response["error"] = TRUE;
        $response["error_msg"] = "User already exists with " . $username;
        echo json_encode($response); */
    //}
} else {
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters (username, name, email or password) is missing!";
    echo json_encode($response);
}
?>`