<?php
 
require_once 'include/DB_Functions.php';
$db = new DB_Functions();
 
// json response array
$response = array("error" => FALSE);
    
        // Get university list 
        $universitylist = $db->fetchUniversityList();
        
		if ($universitylist) {
            // university list retrieved successfully
            $response["error"] = FALSE;
			$response["count"] = count($universitylist);
			
			//$resultuniversityresult = array('a' => 1, 'b' => 2, 'c' => 3, 'd' => 4, 'e' => 5);
			
			/*for($i = 1; $i < count($universitylist); ++$i) {
				$resultuniversityresult[] = $universitylist[$i]["univId"];
				$resultuniversityresult[] = $universitylist[$i]["univName"];
			}*/
			
			$response["universityList"] = $universitylist;
			
			echo json_encode($response);
        } else {
            // university failed to store
            $response["error"] = TRUE;
            $response["error_msg"] = "Unknown error occurred in fetching university list!";
            echo json_encode($response);
        }
    

?>