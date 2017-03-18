<?php
	
	require_once('db_config.php');
	require_once('password_hash.php');
		
    $con = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE);

	if (mysqli_connect_errno()) {
		echo("Can't connect");
		exit;
	}
    
    $firstName = $_POST["inputFirstName"];
    $lastName = $_POST["inputLastName"];
    $accountName = $_POST["inputAccount"];
    $password = $_POST["inputPassword"];
	
	function registerUser(){
		global $con, $firstName, $lastName, $accountName, $password;
		$password_hash = password_hash($password, PASSWORD_DEFAULT);
		$statement = mysqli_prepare($con, "INSERT INTO userinfo (firstName, lastName, accountName, password) VALUES (?, ?, ?, ?)");
		mysqli_stmt_bind_param($statement, "ssss", $firstName, $lastName, $accountName, $password_hash);
		mysqli_stmt_execute($statement);
	}
	
	function checkDuplicate(){
		global $con, $accountName;
		$statement = mysqli_prepare($con, "SELECT * FROM userinfo WHERE accountName = ?");
		mysqli_stmt_bind_param($statement, "s", $accountName);
		mysqli_stmt_execute($statement);
		mysqli_stmt_store_result($statement);
		$count = mysqli_stmt_num_rows($statement);
		mysqli_stmt_close($statement);
		if($count >= 1){
			return true;
		}else{
			return false;	
		}
	}
	
    $response = array();
    $response["success"] = false;
	$response["duplicate"] = false;
	
	if(!checkDuplicate()){
		registerUser();
		$response["success"] = true;
    }
	elseif(checkDuplicate()){
		$response["success"] = false;  
		$response["duplicate"] = true;
	}
	
    echo json_encode($response);
?>