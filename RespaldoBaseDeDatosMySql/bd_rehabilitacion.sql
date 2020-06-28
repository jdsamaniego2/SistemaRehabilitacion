-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3306
-- Tiempo de generación: 28-06-2020 a las 20:02:37
-- Versión del servidor: 10.4.10-MariaDB
-- Versión de PHP: 7.3.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `bd_rehabilitacion`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `paciente`
--

DROP TABLE IF EXISTS `paciente`;
CREATE TABLE IF NOT EXISTS `paciente` (
  `ID_PAC` int(11) NOT NULL AUTO_INCREMENT,
  `NOMBRE_PAC` varchar(60) DEFAULT NULL,
  `APELLIDO_PAC` varchar(60) DEFAULT NULL,
  `CEDULA_PAC` varchar(60) DEFAULT NULL,
  `NACIMIENTO_PAC` varchar(60) DEFAULT NULL,
  `ULTIMAMODIFICACION_PAC` varchar(250) DEFAULT NULL,
  `ENFERMEDADES_PAC` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`ID_PAC`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `paciente`
--

INSERT INTO `paciente` (`ID_PAC`, `NOMBRE_PAC`, `APELLIDO_PAC`, `CEDULA_PAC`, `NACIMIENTO_PAC`, `ULTIMAMODIFICACION_PAC`, `ENFERMEDADES_PAC`) VALUES
(49, 'Jairo Daniel', 'Samaniego Villacres', '0604178541', '07/04/1998', '2020-06-28  07:15:52', 'Artitis'),
(50, 'Luis Carlos', 'Samaniego Parra', '0602960031', '02/07/1980', '2019-12-21  03:38:30', 'Artrosis'),
(51, 'Marco Antonio', 'Haro Espinoza', '0600710362', '05/04/1960', '2019-12-21  03:38:54', 'Artritis'),
(52, 'Juan Carlos', 'Velasco Murillo', '0604168845', '08/04/1990', '2019-12-21  03:27:08', 'Tendinitis'),
(53, 'Eduardo Alejandro', 'Santillan Vaca', '0504178541', '05/06/2000', '2019-12-21  04:33:08', 'Artrosis');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sesion`
--

DROP TABLE IF EXISTS `sesion`;
CREATE TABLE IF NOT EXISTS `sesion` (
  `ID_SES` int(11) NOT NULL AUTO_INCREMENT,
  `ID_PAC` int(11) DEFAULT NULL,
  `TIEMPO_SES` varchar(60) DEFAULT NULL,
  `REPETICIONES_SES` varchar(60) DEFAULT NULL,
  `TIPO_SES` varchar(60) DEFAULT NULL,
  `FECHA_SES` varchar(60) DEFAULT NULL,
  `SUPERVISOR_SES` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`ID_SES`),
  KEY `FK_SESION_REFERENCE_PACIENTE` (`ID_PAC`)
) ENGINE=InnoDB AUTO_INCREMENT=208 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `sesion`
--

INSERT INTO `sesion` (`ID_SES`, `ID_PAC`, `TIEMPO_SES`, `REPETICIONES_SES`, `TIPO_SES`, `FECHA_SES`, `SUPERVISOR_SES`) VALUES
(70, 49, '180', '5', 'Cierre', '2019-12-19  03:34:06', 'Jairo Daniel Samaniego Villacres'),
(71, 51, '180', '5', 'Cierre', '2019-12-19  04:32:17', 'Jairo Daniel Samaniego Villacres'),
(72, 51, '180', '5', 'Timon', '2019-12-19  04:32:17', 'Jairo Daniel Samaniego Villacres'),
(73, 50, '180', '5', 'Malla', '2019-12-19  03:34:14', 'Jairo Daniel Samaniego Villacres'),
(74, 51, '180', '5', 'Laberinto', '2019-12-19  04:32:12', 'Jairo Daniel Samaniego Villacres'),
(75, 51, '180', '5', 'Laberinto', '2019-12-19  06:04:33', 'Jairo Daniel Samaniego Villacres'),
(76, 51, '180', '5', 'Malla', '2019-12-19  04:32:18', 'Jairo Daniel Samaniego Villacres'),
(77, 51, '180', '5', 'Cierre', '2019-12-19  06:11:07', 'Jairo Daniel Samaniego Villacres'),
(190, 49, '180', '5', 'Cierre', '2019-12-19  06:11:07', 'Jairo Daniel Samaniego Villacres'),
(191, 50, '180', '5', 'Malla', '2019-12-19  07:00:35', 'Jairo Daniel Samaniego Villacres'),
(195, 53, '180', '5', 'Cierre', '2019-12-19  06:11:07', 'Jairo Daniel Samaniego Villacres'),
(206, 49, '170', '5', 'Manijas', '2019-12-14  03:34:06', 'Jairo Daniel Samaniego Villacres'),
(207, 49, '9', '0', 'CIERRE', '2020-06-22  09:01:20', 'Jairo Daniel Samaniego Villacres');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
