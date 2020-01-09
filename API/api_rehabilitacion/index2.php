<?php
 
//Cargamos el framework
require_once 'vendor/autoload.php';
 
$app = new \Slim\Slim();
 

 $db = new mysqli('localhost', 'root', '', 'bd_rehabilitacion');


$app->get('/pacientes/', function () use($db, $app) {
 
			$sql = "SELECT * FROM PACIENTE";
            $select = $db->query($sql);
            $pacientes=array();
            while($fila=$select->fetch_assoc()){
                $pacientes[]=$fila;
            }
 
            echo json_encode($pacientes);
        });

 $app->post('/pacientes/', function ($request, $response)  {
	
          //  $json = $app->request->post('json');
			$data = $request->getParsedBody();
			echo json_encode($data);
			

			

        });
 
function getConnection() {
    $dbhost="localhost";
    $dbuser="root";
    $dbpass="";
    $dbname="bd_rehabilitacion";
    $dbh = new PDO("mysql:host=$dbhost;dbname=$dbname", $dbuser, $dbpass);
    $dbh->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    return $dbh;
}
$app->run();
?>
