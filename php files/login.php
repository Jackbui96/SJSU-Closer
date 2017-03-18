<?php

	require_once('db_config.php');
	require_once('password_hash.php');

	$con = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE);

	if (mysqli_connect_errno()) {
		echo("Can't connect");
		exit;
	}
	
	$userAccount = $_POST['inputAccount'];
	$userPassword = $_POST['inputPassword'];
	
	$statement =  mysqli_prepare($con, "SELECT * FROM userinfo WHERE accountName = ?");
	mysqli_stmt_bind_param($statement, "s", $userAccount);
	mysqli_stmt_execute($statement);
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $colUserid, $colFirebaseid, $colFirstName, $colLastName, $colAccountName, $colPassword, $colToken, $colStatus); 
	
	
	$result = array();
	$result["hit"] = false;
	
	while(mysqli_stmt_fetch($statement)){
        if (password_verify($userPassword, $colPassword)) {
            $result["hit"] = true;
			$result["id"] = $colUserid;
            $result["firstName"] = $colFirstName;
			$result["lastName"] = $colLastName;
            $result["accountName"] = $colAccountName;
			$addstmt =  mysqli_prepare($con, "UPDATE status SET status = '1' WHERE accountName = '$userAccount'");
        }
    }
	
	echo json_encode($result);
	
	//close connection 
	mysqli_close($con);
	
?>