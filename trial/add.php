<?php

require 'connect.inc.php';

$response = array();

if (isset($_POST['latitude']) || isset($_POST['longitude']) || isset($_POST['density'])) {

    $latitude = $_POST['latitude'];
    $longitude = $_POST['longitude'];
    $density = $_POST['density'];

    

$query = "INSERT INTO shower (latitude, longitude, density) VALUES('$latitude', '$longitude', '$density')";
$result = mysql_query($query);

// check if row inserted or not
    if ($result) {

        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "successfully inserted";

        // echoing JSON response
        echo json_encode($response);
    }            
    else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";
        
        // echoing JSON response
        echo json_encode($response);
    }
}
?>