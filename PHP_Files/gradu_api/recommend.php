<?php
require_once 'include/DB_Functions.php';
$db = new DB_Functions();
 
// json response array
$response = array("error" => FALSE);
 
if (isset($_POST['uid']) && isset($_POST['rank_low']) && isset($_POST['rank_high']) && isset($_POST['tuition_low']) && isset($_POST['tuition_high']) && isset($_POST['state'])) {
 
    // receiving the post params
    $uid = $_POST['uid'];
    $rank_low = $_POST['rank_low'];
	$rank_high = $_POST['rank_high'];
	$tuition_low = $_POST['tuition_low'];
	$tuition_high = $_POST['tuition_high'];
	$state = $_POST['state'];
 
    // get the user by email and password
    $university = $db->getUniversities($uid, $rank_low, $rank_high, $tuition_low, $tuition_high, $state);
	//$university=$db->getUni();
	$user = $db->getUser($uid);
    if ($university != NULL) {
		
        // use is found
		$amb=array();
		$mod=array();
		$safe=array();
		$user_score=($user["undergradGpa"]/4.0)*20 + ($user["greQuant"]/170)*20 + ($user["greVerbal"]/170)*20 + ($user["greAWA"]/6)*10 + ($user["engScore"]/120)*10 + ($user["workExp"]/10)*20;
		while ($row = $university->fetch_assoc()) {
			$uni_score = ($row["minGPA"]/4.0)*20 + ($row["minGreQuant"]/170)*20 + ($row["minGreVerbal"]/170)*20 + ($row["minAWA"]/6)*10 + ($row["minToefl"]/120)*10 + ($row["minWorkExp"]/10)*20;
			$name=$db->getUniName($row["univId"]);
			if($user_score < $uni_score - 5){
				array_push($amb, $name);
			}
			if($user_score < $uni_score + 10 and $user_score >= $uni_score -5){
				array_push($mod, $db->getUniName($row["univId"]));
				
			}
			if($user_score >= $uni_score +10){
				array_push($safe, $db->getUniName($row["univId"]));
				
			}
		}
        $response["error"] = FALSE;
        $response["Amb"] = ($amb);
        $response["Mod"] = ($mod);
        $response["Safe"] = ($safe);
		//print_r($amb);
		//print(json_encode($amb));
        echo json_encode($response);
    } else {
        // user is not found with the credentials
        $response["error"] = TRUE;
        $response["error_msg"] = "Universities Not Found";
        echo json_encode($response);
    }
} else {
    // required post params is missing
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters are missing!";
    echo json_encode($response);
}
?>