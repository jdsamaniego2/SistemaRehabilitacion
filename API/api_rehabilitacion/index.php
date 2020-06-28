<?php
 
//Cargamos el framework
require_once 'vendor/autoload.php';
 
$app = new \Slim\Slim();
 
//Creamos la conexión a la base de datos con MySQLi
$db = new mysqli('localhost', 'root', '', 'bd_rehabilitacion');
 
/*
 * Ruta/Controlador Get, le decimos que use las variables $db, $app
 * GET para CONSEGUIR
 */
$app->get('/conexion/', function () use($db, $app) {
 
            /* comprobar la conexión */
			if ($db->connect_errno) {
			     $res = array('status' => 'true','code'	 => 400, 'message' => 'Hay Conexion');
			    exit();
			}
			else{
				 $res = array('status' => 'true','code'	 => 200, 'message' => 'Hay Conexion');
			}

			echo json_encode($res);
        });
 

/*API SESIONES*/

	//obtener todas las sesiones
$app->get('/sesiones/', function () use($db, $app) {
 
 			$sql = "SELECT * FROM SESION";
            $select = $db->query($sql);
       
            $sesiones=array();
            while($fila=$select->fetch_assoc()){
                $sesiones[]=$fila;
            }
 
            echo json_encode($sesiones);
        });
/*
	//obtener una sesion por id
$app->get('/sesiones/:id', function ($id) use($db, $app) {
 			$sql = "SELECT  * FROM sesion WHERE ID_SES=$id";
            $select = $db->query($sql);
            $sesiones=array();
            while($fila=$select->fetch_assoc()){
                $sesiones[]=$fila;
            }
            echo json_encode($sesiones);
        });

	//obtener una sesion por cedula y fecha 

        */

$app->get('/sesiones/:cedula', function ($cedula) use($db, $app) {


			$sql = "SELECT * FROM PACIENTE WHERE CEDULA_PAC=$cedula";
            $select = $db->query($sql);
  
            $pacientes=array();
            while($fila=$select->fetch_assoc()){
                $pacientes[]=$fila;
			}
			if(empty($pacientes)) {
				echo json_encode(array());; 
				return;
			}
    		
			$paciente = json_encode($pacientes[0]);

 			
 		 

 			$data = json_decode($paciente, true);

			$id_paciente = $data['ID_PAC'];


 
 			$sql = "SELECT * FROM SESION WHERE ID_PAC=$id_paciente";
            $select = $db->query($sql);
       
            $sesiones=array();
            while($fila=$select->fetch_assoc()){
                $sesiones[]=$fila;
            }
 
            echo json_encode($sesiones);
        });

$app->get('/sesiones/:cedula/:fecha', function ($cedula,$fecha) use($db, $app) {


			$sql = "SELECT * FROM PACIENTE WHERE CEDULA_PAC=$cedula";
            $select = $db->query($sql);
  
            $pacientes=array();
            while($fila=$select->fetch_assoc()){
                $pacientes[]=$fila;
			}
			$paciente = json_encode($pacientes[0]);

 			$data = json_decode($paciente, true);

			$id_paciente = $data['ID_PAC'];
			 
		 

 			$sql1 = "SELECT  * FROM sesion WHERE ID_PAC=$id_paciente AND FECHA_SES='$fecha'";
            $select1 = $db->query($sql1);
            $sesiones1=array(); 

            while($fila1=$select1->fetch_assoc()){
                $sesiones1[]=$fila1;
            }
            echo json_encode($sesiones1);
        });

	//insertar una sesion
$app->post('/sesiones/', function () use($db, $app) {
            //Request recoge variables de las peticiones http
            $json = $app->request->getBody();
			$data = json_decode($json, true);
			if(!isset($data['ID_PAC'])){
				$data['ID_PAC']=null;
			}
			if(!isset($data['TIEMPO_SES'])){
				$data['TIEMPO_SES']=null;
			}
			if(!isset($data['REPETICIONES_SES'])){
				$data['REPETICIONES_SES']=null;
			}
			if(!isset($data['TIPO_SES'])){
				$data['TIPO_SES']=null;
			}
			if(!isset($data['FECHA_SES'])){
				$data['FECHA_SES']=null;
			}
			if(!isset($data['SUPERVISOR_SES'])){
				$data['SUPERVISOR_SES']=null;
			}

 
           $query = "INSERT INTO SESION VALUES(NULL,".
			 "'{$data['ID_PAC']}',".
			 "'{$data['TIEMPO_SES']}',".
			 "'{$data['REPETICIONES_SES']}',".
 			 "'{$data['TIPO_SES']}',".
 			 "'{$data['FECHA_SES']}',".
			 "'{$data['SUPERVISOR_SES']}'".
			 ");";
			$insert = $db->query($query);

            if ($insert) {
                $result = array('status' => 'true','code'	 => 200, 'message' => 'Sesion registrada correctamente');
            } else {
                $result = array('status' => 'false','code'	 => 404, 'message' => 'No se pudo registrar la sesion');
            }
 
            echo json_encode($result);
        });
	 
	//editar una sesion
$app->put('/sesiones/:id', function ($id) use($db, $app) {
 			$json = $app->request->getBody();
			$data = json_decode($json, true);
			if(!isset($data['ID_PAC'])){
				$data['ID_PAC']=null;
			}
			if(!isset($data['TIEMPO_SES'])){
				$data['TIEMPO_SES']=null;
			}
			if(!isset($data['REPETICIONES_SES'])){
				$data['REPETICIONES_SES']=null;
			}
			if(!isset($data['TIPO_SES'])){
				$data['TIPO_SES']=null;
			}
			if(!isset($data['FECHA_SES'])){
				$data['FECHA_SES']=null;
			}
			if(!isset($data['SUPERVISOR_SES'])){
				$data['SUPERVISOR_SES']=null;
			}

 
 
            $sql = "UPDATE SESION SET
                                    ID_PAC = '{$data['ID_PAC']}',
                                    TIEMPO_SES = '{$data['TIEMPO_SES']}',
                                    REPETICIONES_SES = '{$data['REPETICIONES_SES']}',
                                    TIPO_SES = '{$data['TIPO_SES']}',
                                    FECHA_SES = '{$data['FECHA_SES']}',
                                    SUPERVISOR_SES = '{$data['SUPERVISOR_SES']}'
                                 WHERE ID_SES=$id";
 
            $update = $db->query($sql);
 
            if ($update) {
                $result = array('status' => 'true','code'	 => 200, 'message' => 'Sesion modificada correctamente');
            } else {
                $result = array('status' => 'false','code'	 => 404, 'message' => 'No se pudo modificar la sesion');
            }
            echo json_encode($result);
        });
 


     //eliminar sesion
$app->delete('/sesiones/:id', function ($id) use($db, $app) {
 
            $request = $app->request;
 
            $sql = "DELETE FROM SESION WHERE ID_SES=$id";
 
            $delete = $db->query($sql);
 
            if ($delete)
            {
                $result = array('status' => 'true','code'	 => 200, 'message' => 'Sesion eliminada correctamente');
            } else 
            {
                $result = array('status' => 'false','code'	 => 404, 'message' => 'No se pudo eliminar la sesion');
            }
            echo json_encode($result);
        });
/*FIN API SESIONES*/
 

/*API PACIENTES*/

	//obtener todas los pacientes
$app->get('/pacientes/', function () use($db, $app) {
 
			$sql = "SELECT * FROM PACIENTE";
            $select = $db->query($sql);
            $pacientes=array();
            while($fila=$select->fetch_assoc()){
                $pacientes[]=$fila;
            }
 
            echo json_encode($pacientes);
        });

	//obtener un paciente
$app->get('/pacientes/:id', function ($id) use($db, $app) {
 

 			$sql = "SELECT * FROM PACIENTE WHERE CEDULA_PAC=$id";
            $select = $db->query($sql);
  
            $pacientes=array();
            while($fila=$select->fetch_assoc()){
                $pacientes[]=$fila;
            }
 
            echo json_encode($pacientes);
        });

	//insertar un paciente
$app->post('/pacientes/', function () use($app, $db) {
	
            $json = $app->request->getBody();
			$data = json_decode($json, true);

			
			if(!isset($data['NOMBRE_PAC'])){
				$data['NOMBRE_PAC']=null;
			}
			if(!isset($data['APELLIDO_PAC'])){
				$data['APELLIDO_PAC']=null;
			}
			if(!isset($data['CEDULA_PAC'])){
				$data['CEDULA_PAC']=null;
			}
			if(!isset($data['NACIMIENTO_PAC'])){
				$data['NACIMIENTO_PAC']=null;
			}
	 
			if(!isset($data['ENFERMEDADES_PAC'])){
				$data['ENFERMEDADES_PAC']=null;
			}
			if(!isset($data['ULTIMAMODIFICACION_PAC'])){
				$data['ULTIMAMODIFICACION_PAC']=null;
			}

			$query = "INSERT INTO PACIENTE VALUES(NULL,".
			 "'{$data['NOMBRE_PAC']}',".
			 "'{$data['APELLIDO_PAC']}',".
			 "'{$data['CEDULA_PAC']}',".
 			 "'{$data['NACIMIENTO_PAC']}',".
 			 "'{$data['ULTIMAMODIFICACION_PAC']}',".
			 "'{$data['ENFERMEDADES_PAC']}'".
			 ");";

			$insert = $db->query($query);

			$result = array(
				'status' => 'error',
				'code'	 => 404,
				'message' => 'Producto NO se ha creado'
			);

			if($insert==1){
				$result = array(
					'status' => 'success',
					'code'	 => 200,
					'message' => 'Producto creado correctamente'
				);
			}

			echo json_encode($result);

        });
	 
	//editar un paciente
$app->put('/pacientes/:id', function ($id) use($db, $app) {
 
             $json = $app->request->getBody();
			$data = json_decode($json, true);

			if(!isset($data['NOMBRE_PAC'])){
				$data['NOMBRE_PAC']=null;
			}
			if(!isset($data['APELLIDO_PAC'])){
				$data['APELLIDO_PAC']=null;
			}
			if(!isset($data['CEDULA_PAC'])){
				$data['CEDULA_PAC']=null;
			}
			if(!isset($data['NACIMIENTO_PAC'])){
				$data['NACIMIENTO_PAC']=null;
			}
			if(!isset($data['ULTIMAMODIFICACION_PAC'])){
				$data['ULTIMAMODIFICACION_PAC']=null;
			}
			if(!isset($data['ENFERMEDADES_PAC'])){
				$data['ENFERMEDADES_PAC']=null;
			}
 			$query = "UPDATE PACIENTE SET 
 									NOMBRE_PAC = '{$data['NOMBRE_PAC']}',
                                    APELLIDO_PAC = '{$data['APELLIDO_PAC']}',
                                    CEDULA_PAC = '{$data['CEDULA_PAC']}',
                                    NACIMIENTO_PAC = '{$data['NACIMIENTO_PAC']}',
                                    ULTIMAMODIFICACION_PAC = '{$data['ULTIMAMODIFICACION_PAC']}',
                                    ENFERMEDADES_PAC = '{$data['ENFERMEDADES_PAC']}'
                                 WHERE ID_PAC=$id";

	 
 
            $update = $db->query($query);


 
            if ($update) {
                $result = array("status" => "true",'code'	 => 200, "message" => "Paciente modificado correctamente");
            } else {
                $result = array("status" => "false",'code'	 => 404, "message" => "No se pudo modificar el paciente");
            }
            echo json_encode($result);
        });
 

/*
     //eliminar paciente
$app->delete('/pacientes/:id', function ($id) use($db, $app) {
 
            $request = $app->request;
 
            $sql = "DELETE FROM PACIENTE WHERE ID_PAC=$id";
 
            $delete = $db->query($sql);
 
            if ($delete) {
                $result = array("status" => "true", "message" => "Paciente eliminado correctamente");
            } else {
                $result = array("status" => "false", "message" => "No se pudo eliminar el paciente");
            }
            echo json_encode($result);
        });*/
/*FIN API PACIENTES*/
 
$app->run();
?>
