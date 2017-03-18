<?php
	
	/*
		This file is to get the friend relationship in the database for the user.
		First takes in the name of the usuer's accountName.
		Then check inside the database for that name. Since this is a
		two way relationship, we have to check both of the column for the name.
	*/
	
	require_once('db_config.php');
		
    $con = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE);
	
	if (mysqli_connect_errno()) {
		echo("Can't connect");
		exit;
	}
	
	//Get the user's account to search
	$accountName = $_POST["inputAccount"];
	
	
	/*I think this is where I am wrong. The meaning of this statement is to looks for the 
	user's account inside the relationship table, where the account may be in the first column
	or the second column. (Idk if this is the right argument)
	*/
	$stm = "SELECT * FROM relationship WHERE first_user_id = ? AND second_user_id = ?";
	$query = mysqli_prepare($con, $stm);
	mysqli_stmt_bind_param($query, "ss", $accountName, $accountName);
	mysqli_stmt_execute($query);
	mysqli_stmt_bind_result($query, $firstAccount, $secondAccount);

	
    $response = array();
	
	/*
	After getting the account, store them into the respond array.
	If the user's account is in the first column then the friend's account is in the second one.
	If the user's account is in the second column then the friend's account is in the first one.
	*/
	while(mysqli_stmt_fetch($query)){
		if($accountName == $firstAccount){
			$result["friendList"] = $secondAccount;
		}else if($accountName == $secondAccount){
			$result["friendList"] = $firstAccount;
		}	
    }
	
    echo json_encode($response);
?>