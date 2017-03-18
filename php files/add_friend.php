<?php
	
	/*
		This file is to add friend into the relationship table
	*/
	
	
	
	require_once('db_config.php');
		
    $con = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE);

	if (mysqli_connect_errno()) {
		echo("Can't connect");
		exit;
	}
    //Getting both the sender's account and the reciver's account
    $friendAccount = $_POST["inputFriendAccount"];
	$userAccount = $_POST["inputUserAccount"];
	
	$response = array();
    $response["success"] = false; //This element holds if the insert is succesful or not
	$response["duplicate"] = false; //This element holds if the relationship have already existed
	
	//Search for the reciver's account inside the userinfomation database
	$friendStatement = mysqli_prepare($con, "SELECT * FROM userinfo WHERE accountName = ?");
	mysqli_stmt_bind_param($friendStatement, "s", $friendAccount);
	mysqli_stmt_execute($friendStatement);
	mysqli_stmt_store_result($friendStatement);
	
	//If the reciver is found
	while(mysqli_stmt_fetch($friendStatement)){
		
		//Check for row where the sender is in the first column of the relationship table 
		//and the reciver is in the second column
		$checkDuplicateUser = mysqli_prepare($con, "SELECT * FROM relationship WHERE first_user_id = ? && second_user_id = ?");
		mysqli_stmt_bind_param($checkDuplicateUser, "ss", $friendAccount, $userAccount);
		mysqli_stmt_execute($checkDuplicateUser);
		mysqli_stmt_store_result($checkDuplicateUser);
		
		//Check for row where the sender is in the second column of the relationship table 
		//and the reciver is in the first column
		$checkDuplicateFriend = mysqli_prepare($con, "SELECT * FROM relationship WHERE first_user_id = ? && second_user_id = ?");
		mysqli_stmt_bind_param($checkDuplicateFriend, "ss", $userAccount, $friendAccount);
		mysqli_stmt_execute($checkDuplicateFriend);
		mysqli_stmt_store_result($checkDuplicateFriend);
		
		//Return true that there is a duplication
		if(mysqli_stmt_fetch($checkDuplicateUser) || mysqli_stmt_fetch($checkDuplicateFriend)){
			$response["duplicate"] = true;
		}
		//If not insert
		else{
			$insertStatement = mysqli_prepare($con, "INSERT INTO relationship (first_user_id, second_user_id) VALUES (?, ?)");
			mysqli_stmt_bind_param($insertStatement, "ss", $userAccount, $friendAccount);
			mysqli_stmt_execute($insertStatement);
			mysqli_stmt_close($insertStatement);  
		
			$response["success"] = true;
		}
	
	}
	
	function checkDuplicateFirst(){
		global $con, $friendAccount, $userAccount;
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
	
    echo json_encode($response);
?>