<?php

$mysql_error='Connection error!';
$mysql_host='localhost';
$mysql_user='root';
$mysql_pass='harsha';
$mysql_db='namedate';

if(!mysql_connect($mysql_host,$mysql_user,$mysql_pass)||!mysql_select_db($mysql_db))
{
  die($mysql_error);
}

?>