<?php

require 'connect.inc.php';

$response = array();
$counter1 =0;
$counter2 =0;
$counter3 =0;
$counter4 =0;


if (isset($_POST["latitude"]) || isset($_POST["longitude"])) {
  
  $latitude = $_POST['latitude'];
  $longitude = $_POST['longitude'];


  //Top box!
    $top1 = $latitude + 0.02;
    $bottom1 =  $latitude;
    $right1 =  $longitude + 0.01;
    $left1 = $longitude - 0.01;

$query1 = "SELECT `density` FROM `shower` WHERE `latitude` > $top1 AND `latitude` < $bottom1 AND `longitude` > $left1 And `longitude` < $right1 ";

    if ($result1 = mysql_query($query1)) {

              $t_density = 0;

          while($row = mysql_fetch_assoc($result1)) {

              $density = $row['density'];
              $t_density = $t_density + $density;
              $counter++;
              }

                if ($counter1 != 0){
              $t_density = ceil($t_density / $counter1);
               }


              if ($t_density == 0){
              $t_density = 1;
               }

              $response["latitude1"] = $top1;
              $response["longitude1"] = $longitude;
              $response["density1"] = $t_density;


      }
    else {
          echo mysql_error();
              $response["latitude1"] = $top1;
              $response["longitude1"] = $longitude;
              $response["density1"] = 1;
    }


    //Bottom box!
    $top2 = $latitude;
    $bottom2 =  $latitude + 0.02;
    $right2 =  $longitude + 0.01;
    $left2 = $longitude - 0.01;

$query2 = "SELECT `density` FROM `shower` WHERE `latitude` > $top1 AND `latitude` < $bottom1 AND `longitude` > $left1 And `longitude` < $right1 ";

    if ($result2 = mysql_query($query2)) {

              $t_density = 0;

          while($row = mysql_fetch_assoc($result2)) {

              $density = $row['density'];
              $t_density = $t_density + $density;
              $counter++;
              }

              if ($counter2 != 0){
              $t_density = ceil($t_density / $counter2);
               }

              
              
              if ($t_density == 0){
              $t_density = 1;
               }


              $response["latitude2"] = $bottom2;
              $response["longitude2"] = $longitude;
              $response["density2"] = $t_density;


      }
    else {
          echo mysql_error();
              $response["latitude2"] = $bottom2;
              $response["longitude2"] = $longitude;
              $response["density2"] = 1;

    }



    //Right box!
    $top3 = $latitude - 0.01;
    $bottom3 =  $latitude+0.01;
    $right3 =  $longitude + 0.02;
    $left3 = $longitude;

$query3 = "SELECT `density` FROM `shower` WHERE `latitude` > $top1 AND `latitude` < $bottom1 AND `longitude` > $left1 And `longitude` < $right1 ";

    if ($result3 = mysql_query($query3)) {

              $t_density = 0;

          while($row = mysql_fetch_assoc($result3)) {

              $density = $row['density'];
              $t_density = $t_density + $density;
              $counter++;
              }
              
              if ($counter3 != 0){
              $t_density = ceil($t_density / $counter3);
               }


               if ($t_density == 0){
              $t_density = 1;
               }

              $response["latitude3"] = $latitude;
              $response["longitude3"] = $right3;
              $response["density3"] = $t_density;


      }
    else {
          echo mysql_error();
          $response["latitude3"] = $latitude;
              $response["longitude3"] = $right3;
              $response["density3"] = 1;
    }



    //Left box!
    $top4 = $latitude - 0.01;
    $bottom4 =  $latitude + 0.01;
    $right4 =  $longitude;
    $left4 = $longitude + 0.02;

$query4 = "SELECT `density` FROM `shower` WHERE `latitude` > $top1 AND `latitude` < $bottom1 AND `longitude` > $left1 And `longitude` < $right1 ";

    if ($result4 = mysql_query($query4)) {

              $t_density = 0;

          while($row = mysql_fetch_assoc($result4)) {

              $density = $row['density'];
              $t_density = $t_density + $density;
              $counter++;
              }
               if ($counter4 != 0){
              $t_density = ceil($t_density / $counter4);
               }


               if ($t_density == 0){
              $t_density = 1;
               }

              $response["latitude4"] = $latitude;
              $response["longitude4"] = $left4;
              $response["density4"] = $t_density;


      }
    else {
          echo mysql_error();
              $response["latitude4"] = $latitude;
              $response["longitude4"] = $left4;
              $response["density4"] = 1;
    }




}


else {
$response["result"] = 'error!, Lat long zero sent';

}

echo json_encode($response);
?>
