<?php
 
class DB_Functions {
 
    private $conn;
 
    // constructor
    function __construct() {
        require_once 'DB_Connect.php';
        // connecting to database
        $db = new Db_Connect();
        $this->conn = $db->connect();
    }
 
    // destructor
    function __destruct() {
         
    }
 
	/**
     * Storing new user
     * returns user details
     */
    public function storeUser($username, $password, $fullname, $emailId, $courseInterest, $undergradGpa, $greQuant, $greVerbal, $greAWA, $greTotal, $engScore, $workExp)
	{
        $hash = $this->hashSSHA($password);
        $encrypted_password = $hash["encrypted"]; // encrypted password
        $salt = $hash["salt"]; // salt
		$paperPub = 4;
		$stmt = $this->conn->prepare("INSERT INTO student(username, fullname, emailId, encrypted_password, salt, courseInterest, undergradGpa, greQuant, greVerbal, greTotal, greAWA, engScore, workExp, papersPublished) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        $stmt->bind_param("ssssssdddddddi", $username, $fullname,  $emailId, $encrypted_password, $salt, $courseInterest, $undergradGpa, $greQuant, $greVerbal, $greTotal, $greAWA, $engScore, $workExp, $paperPub);
        $result = $stmt->execute();
        $stmt->close();
		
        // check for successful store
        if ($result) {
            $stmt = $this->conn->prepare("SELECT username FROM student WHERE username = ?");
            $stmt->bind_param("s", $username);
            $stmt->execute();
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();
 
            return $user;
        } else {
            return false;
        }
    }
 
    /**
     * Get user by username and password
     */
    public function getUserByUsernameAndPassword($username, $password) {
 
        $stmt = $this->conn->prepare("SELECT username, encrypted_password, salt FROM student WHERE username = ?");
 
        $stmt->bind_param("s", $username);
 
        if ($stmt->execute()) {
            $user = $stmt->get_result()->fetch_assoc();
			$salt = $user["salt"];
			$encrypted_password = $user["encrypted_password"];
			$hash = $this->checkhashSSHA($salt, $password);
			if ($encrypted_password == $hash) {
				// user authentication details are correct
				return $user;
			} else {
				return NULL;
			}
			$stmt->close();
        } else {
            return NULL;
        }
    }
	
	/**
     * Get user by username and password
     */
    public function getAdminByUsernameAndPassword($username, $password) {
 
        $stmt = $this->conn->prepare("SELECT * FROM admin WHERE admin_username = ?");
 
        $stmt->bind_param("s", $username);
 
        if ($stmt->execute()) {
            $user = $stmt->get_result()->fetch_assoc();
			$salt = $user["salt"];
			$encrypted_password = $user["encrypted_password"];
			$hash = $this->checkhashSSHA($salt, $password);
			if ($encrypted_password == $hash) {
				// user authentication details are correct
				return $user;
			} else {
				return NULL;
			}
			$stmt->close();
        } else {
            return NULL;
        }
    }
	
    /**
     * Check user is existed or not
     */
    public function isUserExisted($username) {
        $stmt = $this->conn->prepare("SELECT username from student WHERE username = ?");
 
        $stmt->bind_param("s", $username);
 
        $stmt->execute();
 
        $stmt->store_result();
 
        if ($stmt->num_rows > 0) {
            // user existed 
            $stmt->close();
            return true;
        } else {
            // user not existed
            $stmt->close();
            return false;
        }
    }
 
    /**
     * Encrypting password
     * @param password
     * returns salt and encrypted password
     */
    public function hashSSHA($password) {
 
        $salt = sha1(rand());
        $salt = substr($salt, 0, 10);
        $encrypted = base64_encode(sha1($password . $salt, true) . $salt);
        $hash = array("salt" => $salt, "encrypted" => $encrypted);
        return $hash;
    }
 
    /**
     * Decrypting password
     * @param salt, password
     * returns hash string
     */
    public function checkhashSSHA($salt, $password) {
 
        $hash = base64_encode(sha1($password . $salt, true) . $salt);
 
        return $hash;
    }
	
	public function getUniversities($uid, $rank_low, $rank_high, $tuition_low, $tuition_high, $state) {
 
        $stmt = $this->conn->prepare("SELECT * FROM student WHERE username = ?");
		$stmt->bind_param("s", $uid);
		$stmt->execute();
		$students = $stmt->get_result()->fetch_assoc();
        $course = $students['courseInterest']; 
		
		$stmt1 = $this->conn->prepare("Create Table uni1 as (SELECT * FROM university WHERE uniState = ? AND univCost <= ? AND univCost>= ?)");
		$stmt1->bind_param("sdd", $state, $tuition_high, $tuition_low);
		$stmt1->execute();
		//$uni1 = $stmt1->get_result();
		
		
		switch($course){
			case 'computer science': $stmt2=$this->conn->prepare("Create Table uni2 as (SELECT * FROM universitycompscirank WHERE compSciRank <= ? AND compSciRank >= ?)");break;
			//case 'computer engineering': $ranktable ='universitycompengrank'; $rankbranch='compEngRank'; break;
			//case 'chemical engineering': $ranktable ='universitychemengrank'; $rankbranch='ChemEngRank'; break;
			case 'civil engineering': $stmt2=$this->conn->prepare("Create Table uni2 as (SELECT * FROM universitycivilengrank WHERE CivilEngRank <= ? AND CivilEngRank >= ?)");break;
			
			case 'electrical engineering': $stmt2=$this->conn->prepare("Create Table uni2 as (SELECT * FROM universityelecengrank WHERE ElecEngRank <= ? AND ElecEngRank >= ?)");break;
	
			//case 'industrial engineering': $ranktable ='universityindengrank'; $rankbranch='IndEngRank'; break;
			case 'mechanical engineering': $stmt2=$this->conn->prepare("Create Table uni2 as (SELECT * FROM universitymechengrank WHERE MechEngRank <= ? AND MechEngRank >= ?)");break;

			case 'MIS': $stmt2=$this->conn->prepare("Create Table uni2 as (SELECT * FROM universitymisrank WHERE misRank <= ? AND misRank >= ?)");break;
	
			//case 'national': $ranktable ='universitynationalrank'; $rankbranch='nationalRank'; break;
		}
		
		$stmt2->bind_param("ii", $rank_high,$rank_low);
		$stmt2->execute();
		//$uni2 = $stmt2->get_result();
		
		$stmt3 = $this->conn->prepare("Create Table uni3 as (SELECT uni1.univId FROM uni1 WHERE uni1.univId IN (SELECT uni2.univId FROM uni2))");
		//$stmt3->bind_param("sssss", $uni1,$uni1,$uni1,$uni2,$uni2);
		$stmt3->execute();
		//$uni3 = $stmt3->get_result();
		
		$stmt4 = $this->conn->prepare("SELECT * FROM universitysearchcriteria WHERE universitysearchcriteria.univId IN (SELECT uni3.univId FROM uni3)");
		//$stmt4->bind_param("ss",$uni3,$uni3);
		$stmt4->execute();
		$uni4 = $stmt4->get_result();
		
		$stmt5 = $this->conn->prepare("Drop Table uni1");
		$stmt5->execute();
		$stmt6 = $this->conn->prepare("Drop Table uni2");
		$stmt6->execute();
		$stmt7 = $this->conn->prepare("Drop Table uni3");
		$stmt7->execute();
        return $uni4;
		$stmt->close();
		$stmt1->close();
		$stmt2->close();
		$stmt3->close();
		$stmt4->close();
		$stmt5->close();
		$stmt6->close();
		$stmt7->close();
    }
	public function getUser($uid){
		$stmt = $this->conn->prepare("SELECT * FROM student WHERE username = ?");
		$stmt->bind_param("s", $uid);
		$stmt->execute();
		$student = $stmt->get_result()->fetch_assoc();
		return $student;
		$stmt->close();
	}
	public function getUniName($univId){
		$stmt = $this->conn->prepare("SELECT univName FROM university WHERE univId = ?");
		$stmt->bind_param("i", $univId);
		$stmt->execute();
		$university = $stmt->get_result()->fetch_assoc();
		return $university["univName"];
		$stmt->close();
	}
	public function getUni(){
		$stmt = $this->conn->prepare("SELECT * FROM universitysearchcriteria");
		$stmt->execute();
		$university = $stmt->get_result();
		return $university;
		$stmt->close();
	}
	
	/**
     * Check univ is existed or not
     */
    public function isUnivExisted($univName) {
        $stmt = $this->conn->prepare("SELECT univName from university WHERE univName = ?");
 
        $stmt->bind_param("s", $univName);
 
        $stmt->execute();
 
        $stmt->store_result();
 
        if ($stmt->num_rows > 0) {
            // univ existed 
            $stmt->close();
            return true;
        } else {
            // univ not existed
            $stmt->close();
            return false;
        }
    }
	
	/**
     * Storing new user
     * returns user details
     */
    public function storeUnivInfo($univName, $univDesc, $univLocn, $tuitionFees, $researchOppt, $acceptanceRate,
								$compSciRank, $electRank, $civilRank, $misRank, $mechRank, $nationalRank,
								$gpa_actual, $greQuant, $greVerbal, $toeflScore, $ieltsScore, $workExp, $paperPub, $greAwa) {

		$univId = rand(1000,2000);
		
        $stmt = $this->conn->prepare("INSERT INTO university(univId, univName, univDescription, univCost, uniState, univResearchOpp, univAccRate) VALUES(?, ?, ?, ?, ?, ?, ?)");
        $stmt->bind_param("issdssd", $univId, $univName, $univDesc, $tuitionFees, $univLocn, $researchOppt, $acceptanceRate);
        $result = $stmt->execute();
        $stmt->close();
		
		$stmt = $this->conn->prepare("INSERT INTO universitysearchcriteria(univId, minGPA, minGreQuant, minGreVerbal, minAWA, minToefl, minIelts, minWorkExp) VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
        $stmt->bind_param("idddiddd", $univId, $gpa_actual, $greQuant, $greVerbal, $greAwa, $toeflScore, $ieltsScore, $workExp);
        $result = $stmt->execute();
        $stmt->close();
	
		$stmt = $this->conn->prepare("INSERT INTO universitycompscirank(univId, compSciRank) VALUES(?, ?)");
        $stmt->bind_param("ii", $univId, $compSciRank);
        $result = $stmt->execute();
        $stmt->close();
		
		$stmt = $this->conn->prepare("INSERT INTO universityelecengrank(univId, ElecEngRank) VALUES(?, ?)");
        $stmt->bind_param("ii", $univId, $electRank);
        $result = $stmt->execute();
        $stmt->close();
		
		$stmt = $this->conn->prepare("INSERT INTO universitycivilengrank(univId, CivilEngRank) VALUES(?, ?)");
        $stmt->bind_param("ii", $univId, $civilRank);
        $result = $stmt->execute();
        $stmt->close();
		
		$stmt = $this->conn->prepare("INSERT INTO universitymechengrank(univId, MechEngRank) VALUES(?, ?)");
        $stmt->bind_param("ii", $univId, $mechRank);
        $result = $stmt->execute();
        $stmt->close();
		
		$stmt = $this->conn->prepare("INSERT INTO universitynationalrank(univId, nationalRank) VALUES(?, ?)");
        $stmt->bind_param("ii", $univId, $nationalRank);
        $result = $stmt->execute();
        $stmt->close();
		
		$stmt = $this->conn->prepare("INSERT INTO universitymisrank(univId, misRank) VALUES(?, ?)");
        $stmt->bind_param("ii", $univId, $misRank);
        $result = $stmt->execute();
        $stmt->close();
		
        return true;
    }
	
	public function updateUser($username, $fullname, $courseInterest, $undergradGpa, $greQuant, $greVerbal, $greAWA, $greTotal, $engScore, $workExp)
	{
        
        
		$stmt = $this->conn->prepare("UPDATE student SET courseInterest = ?, undergradGpa = ?, greQuant = ?, greVerbal = ?, greTotal = ?, greAWA = ?, engScore = ?, workExp = ? WHERE username=?");
        $stmt->bind_param("sssssssss", $courseInterest, $undergradGpa, $greQuant, $greVerbal, $greTotal, $greAWA, $engScore, $workExp, $username);
        $stmt->execute();
        $stmt->close();
		
		
 
        // check for successful store
        if ($stmt->execute()) {
            $stmt = $this->conn->prepare("SELECT * FROM student WHERE username = ?");
            $stmt->bind_param("s", $username);
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();
 
            return $user;
        } else {
            return false;
        }
    }
	
	public function fetchUserProfile($usrname) {
        
        $stmt = $this->conn->prepare("SELECT * FROM student WHERE username = ?");
        $stmt->bind_param("s",$usrname);
        $result = $stmt->execute();
		$usr = $stmt->get_result()->fetch_assoc();
        $stmt->close();
		
		return $usr;
	}
	
	/**
     * fetching university list
     */
    public function fetchUniversityList() {
        
        $stmt = $this->conn->prepare("SELECT univId, univName FROM university");
        $result = $stmt->execute();
		$universityList = $stmt->get_result()->fetch_assoc();
        $stmt->close();
		
		return $universityList;
    }
	
	/**
     * fetching university id
     */
    public function fetchUniversityId($uniname) {
        
        $stmt = $this->conn->prepare("SELECT univId FROM university WHERE univName = ?");
		$stmt->bind_param("s",$uniname);
        $result = $stmt->execute();
		
		$universityId = $stmt->get_result()->fetch_assoc();
        $stmt->close();
		
		return $universityId;
    }
 
    /**
     * fetching university information
     */
    public function fetchUniversityInfo($uniid) {
        
        $stmt = $this->conn->prepare("SELECT * FROM university WHERE univId = ?");
        $stmt->bind_param("i",$uniid);
        $result = $stmt->execute();
		$university = $stmt->get_result()->fetch_assoc();
        $stmt->close();
		
		return $university;
    }
	
	/**
     * fetching university national rank 
     */
    public function fetchUniversityNationalrank($uniid) {
        
        $stmt = $this->conn->prepare("SELECT * FROM universitynationalrank WHERE univid = ?");
        $stmt->bind_param("s",$uniid);
        $result = $stmt->execute();
		$universitynationalrank = $stmt->get_result()->fetch_assoc();
        $stmt->close();
		
		return $universitynationalrank;
    }
	
	/**
     * fetching university computer science rank 
     */
    public function fetchUniversityCompScienceRank($uniid) {
        
        $stmt = $this->conn->prepare("SELECT * FROM universitycompscirank WHERE univid = ?");
        $stmt->bind_param("s",$uniid);
        $result = $stmt->execute();
		$universitycompsciencerank = $stmt->get_result()->fetch_assoc();
        $stmt->close();
		
		return $universitycompsciencerank;
    }
	
	/**
     * fetching university civil rank 
     */
    public function fetchUniversityCivilRank($uniid) {
        
        $stmt = $this->conn->prepare("SELECT * FROM universitycivilengrank WHERE univid = ?");
        $stmt->bind_param("s",$uniid);
        $result = $stmt->execute();
		$universitycivilrank = $stmt->get_result()->fetch_assoc();
        $stmt->close();
		
		return $universitycivilrank;
    }
 
    /**
     * fetching university electrical rank 
     */
    public function fetchUniversityElectricalRank($uniid) {
        
        $stmt = $this->conn->prepare("SELECT * FROM universityelecengrank WHERE univid = ?");
        $stmt->bind_param("s",$uniid);
        $result = $stmt->execute();
		$universityelectricalrank = $stmt->get_result()->fetch_assoc();
        $stmt->close();
		
		return $universityelectricalrank;
    }
    
	/**
     * fetching university mechanical rank 
     */
    public function fetchUniversityMechanicalRank($uniid) {
        
        $stmt = $this->conn->prepare("SELECT * FROM universitymechengrank WHERE univid = ?");
        $stmt->bind_param("s",$uniid);
        $result = $stmt->execute();
		$universitymechanicalrank = $stmt->get_result()->fetch_assoc();
        $stmt->close();
		
		return $universitymechanicalrank;
    }
    
	/**
     * fetching university MIS rank 
     */
    public function fetchUniversityMISRank($uniid) {
        
        $stmt = $this->conn->prepare("SELECT * FROM universitymisrank WHERE univid = ?");
        $stmt->bind_param("s",$uniid);
        $result = $stmt->execute();
		$universitymisrank = $stmt->get_result()->fetch_assoc();
        $stmt->close();
		
		return $universitymisrank;
    }
}
?>