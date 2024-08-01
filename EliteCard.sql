-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 31-07-2024 a las 18:22:26
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `EliteCard`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `beneficio`
--

CREATE TABLE `beneficio` (
  `codigo` varchar(5) NOT NULL,
  `descripcion` varchar(50) NOT NULL,
  `cantidad` int(8) NOT NULL,
  `nivelTarjeta` varchar(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `beneficio`
--

INSERT INTO `beneficio` (`codigo`, `descripcion`, `cantidad`, `nivelTarjeta`) VALUES
('DPNV1', 'Descuento permanente', 5, 'NV1'),
('DPNV2', 'Descuento permanente', 10, 'NV2'),
('DPNV3', 'Descuento permanente', 15, 'NV3'),
('GPNV1', 'Ganancia puntos', 10, 'NV1'),
('GPNV2', 'Ganancia puntos', 20, 'NV2'),
('GPNV3', 'Ganancia puntos', 30, 'NV3'),
('LPNV1', 'Limite de puntos', 300, 'NV1'),
('LPNV2', 'Limite puntos', 500, 'NV2'),
('LPNV3', 'Limite puntos', 1000, 'NV3'),
('VPNV1', 'Valor punto', 50, 'NV1'),
('VPNV2', 'Valor punto', 50, 'NV2'),
('VPNV3', 'Valor punto', 50, 'NV3');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cancelacion`
--

CREATE TABLE `cancelacion` (
  `numero` int(8) NOT NULL,
  `fechaCancelacion` date NOT NULL,
  `motivo` varchar(60) NOT NULL,
  `tarjetaMemb` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `cancelacion`
--

INSERT INTO `cancelacion` (`numero`, `fechaCancelacion`, `motivo`, `tarjetaMemb`) VALUES
(10, '2024-03-03', 'No desea continuar con el servicio', 10009),
(11, '2024-07-30', 'Prueba', 10011),
(12, '2024-07-30', '', 10013),
(16, '2024-07-30', 'prueba1', 10015),
(17, '2024-07-31', 'pruba', 10015);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empleado`
--

CREATE TABLE `empleado` (
  `numero` int(5) NOT NULL,
  `correo` varchar(35) DEFAULT NULL,
  `contrasenia` varchar(15) NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `primerApellido` varchar(30) NOT NULL,
  `segundoApellido` varchar(30) DEFAULT NULL,
  `puesto` varchar(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `empleado`
--

INSERT INTO `empleado` (`numero`, `correo`, `contrasenia`, `nombre`, `primerApellido`, `segundoApellido`, `puesto`) VALUES
(1, 'juanp@gmail.com', 'cisco', 'Juan', 'Pérez', 'González', 'PT1'),
(2, 'mary@outlook.com', 'contrasenia', 'María', 'López', 'Rodríguez', 'PT2'),
(3, 'pedropedrope@gmail.com', 'class', 'Pedro', 'García', 'Hernández', 'PT3'),
(4, 'almejandro@gmail.com', '123456', 'Alejandro', 'Gómez', 'Martínez', 'PT4'),
(5, 'luisito@hotmail.com', 'ciscopher', 'Luis', 'Sánchez', 'Gómez', 'PT1'),
(6, 'anasofi@example.com', 'roblox', 'Ana Sofía', 'Romero', 'Fernández', 'PT2');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `establecimiento`
--

CREATE TABLE `establecimiento` (
  `codigo` varchar(3) NOT NULL,
  `nombre` varchar(25) NOT NULL,
  `calle` varchar(25) NOT NULL,
  `numero` varchar(5) NOT NULL,
  `colonia` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `establecimiento`
--

INSERT INTO `establecimiento` (`codigo`, `nombre`, `calle`, `numero`, `colonia`) VALUES
('EST', 'Abarrotes Enjambre', 'Av. Principal', '123', 'Centro');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `miembro`
--

CREATE TABLE `miembro` (
  `numero` int(8) NOT NULL,
  `fechaRegistro` date NOT NULL,
  `numTel` varchar(15) NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `primerApellido` varchar(30) NOT NULL,
  `segundoApellido` varchar(30) DEFAULT NULL,
  `correo` varchar(45) DEFAULT NULL,
  `empleado` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `miembro`
--

INSERT INTO `miembro` (`numero`, `fechaRegistro`, `numTel`, `nombre`, `primerApellido`, `segundoApellido`, `correo`, `empleado`) VALUES
(1000, '2019-01-01', '6641123255', 'Ricardo', 'Pérez', 'Sevilla', NULL, 1),
(1001, '2019-01-05', '6647899212', 'José Luis', 'Slobotzky', 'Arteaga', 'marialop@gmail.com', 2),
(1002, '2020-01-10', '6612342341', 'Bernardo', 'Villalobos', 'Hernández', 'ingpedro@example.com', 3),
(1003, '2020-06-15', '6639082723', 'Patricia', 'Díaz', 'Cervantes', NULL, 4),
(1004, '2021-02-01', '6646723213', 'Javier', 'Gómez', 'Daniel', NULL, 5),
(1005, '2021-08-05', '6667778889', 'Daniela', 'Tiburco', 'García', NULL, 6),
(1006, '2022-02-10', '6645655645', 'Laura', 'Guzmán', 'Ramírez', NULL, 1),
(1007, '2022-09-15', '6645970121', 'Mateo', 'González', 'López', 'matt@gmail.com', 2),
(1008, '2023-03-01', '6645632344', 'Valentina', 'Martínez', 'García', 'valentinael@outlook.com', 3),
(1009, '2024-03-05', '661234567', 'Diego', 'Rodríguez', 'Díaz', NULL, 4),
(1010, '2024-07-30', '6644879993', 'Oscar Gael', 'Soto', 'Garcia', '', 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `niveltarjeta`
--

CREATE TABLE `niveltarjeta` (
  `codigo` varchar(3) NOT NULL,
  `nombre` varchar(20) NOT NULL,
  `descripcion` varchar(50) DEFAULT NULL,
  `color` varchar(10) NOT NULL,
  `costo` decimal(8,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `niveltarjeta`
--

INSERT INTO `niveltarjeta` (`codigo`, `nombre`, `descripcion`, `color`, `costo`) VALUES
('NV1', 'Bronce', 'Nivel básico', 'Gris', 500.00),
('NV2', 'Plata', 'Nivel intermedio', 'Plateado', 650.00),
('NV3', 'Oro', 'Nivel premium', 'Dorado', 1100.00);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `puesto`
--

CREATE TABLE `puesto` (
  `codigo` varchar(3) NOT NULL,
  `nombre` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `puesto`
--

INSERT INTO `puesto` (`codigo`, `nombre`) VALUES
('PT1', 'Gerente'),
('PT2', 'Vendedor'),
('PT3', 'Cajero'),
('PT4', 'Administrativo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `renovacion`
--

CREATE TABLE `renovacion` (
  `numero` int(8) NOT NULL,
  `fechaRenovacion` date NOT NULL,
  `monto` decimal(10,2) NOT NULL,
  `tarjetaMemb` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `renovacion`
--

INSERT INTO `renovacion` (`numero`, `fechaRenovacion`, `monto`, `tarjetaMemb`) VALUES
(100, '2021-10-10', 500.00, 10000),
(101, '2022-11-12', 500.00, 10000),
(102, '2023-12-01', 500.00, 10000),
(103, '2021-04-20', 500.00, 10001),
(104, '2023-08-05', 500.00, 10001),
(105, '2023-09-15', 500.00, 10003),
(106, '2023-03-15', 1500.00, 10005),
(107, '2023-01-21', 500.00, 10006),
(108, '2023-10-12', 1000.00, 10007),
(109, '2024-03-12', 1500.00, 10008);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tarjetamemb`
--

CREATE TABLE `tarjetamemb` (
  `numero` int(10) NOT NULL,
  `duracion` int(3) NOT NULL,
  `fechaCreacion` date NOT NULL,
  `fechaActiva` date NOT NULL,
  `fechaExpiracion` date NOT NULL,
  `estatus` tinyint(1) NOT NULL,
  `puntosActuales` int(8) NOT NULL,
  `puntosAcumulados` int(10) NOT NULL,
  `empleado` int(5) NOT NULL,
  `miembro` int(8) NOT NULL,
  `nivelTarjeta` varchar(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tarjetamemb`
--

INSERT INTO `tarjetamemb` (`numero`, `duracion`, `fechaCreacion`, `fechaActiva`, `fechaExpiracion`, `estatus`, `puntosActuales`, `puntosAcumulados`, `empleado`, `miembro`, `nivelTarjeta`) VALUES
(10000, 6, '2019-01-01', '2024-12-01', '2025-01-01', 1, 15, 684, 1, 1000, 'NV1'),
(10001, 6, '2019-01-05', '2024-08-05', '2025-01-05', 1, 45, 1277, 2, 1001, 'NV1'),
(10002, 6, '2020-01-10', '2024-03-10', '2026-01-10', 0, 18, 1244, 3, 1002, 'NV2'),
(10003, 6, '2020-06-15', '2024-09-15', '2026-06-15', 1, 100, 2345, 4, 1003, 'NV1'),
(10004, 6, '2021-02-01', '2025-07-30', '2027-02-01', 0, 65, 67, 5, 1004, 'NV2'),
(10005, 6, '2021-08-05', '2024-10-12', '2027-08-05', 1, 69, 689, 6, 1005, 'NV3'),
(10006, 6, '2022-02-10', '2024-08-04', '2028-02-10', 1, 11, 98, 1, 1006, 'NV1'),
(10007, 6, '2022-09-15', '2024-09-15', '2028-09-15', 1, 0, 87, 2, 1007, 'NV2'),
(10008, 6, '2023-03-01', '2025-03-12', '2029-03-01', 1, 12, 689, 3, 1008, 'NV3'),
(10009, 6, '2024-03-05', '2024-03-03', '2030-03-05', 0, 44, 567, 4, 1009, 'NV1'),
(10010, 6, '2024-07-28', '2025-07-28', '2030-07-28', 1, 109, 123, 3, 1002, 'NV3'),
(10011, 6, '2024-07-30', '2025-07-30', '2030-07-30', 0, 0, 0, 1, 1000, 'NV1'),
(10012, 6, '2024-07-30', '2025-07-30', '2030-07-30', 1, 0, 0, 1, 1000, 'NV1'),
(10013, 6, '2024-07-30', '2025-07-30', '2030-07-30', 0, 0, 0, 1, 1009, 'NV1'),
(10014, 6, '2024-07-30', '2025-07-30', '2030-07-30', 1, 0, 0, 4, 1010, 'NV3'),
(10015, 6, '2024-07-30', '2024-07-31', '2024-07-31', 0, 25, 0, 4, 1010, 'NV3');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `venta`
--

CREATE TABLE `venta` (
  `numero` int(10) NOT NULL,
  `fecha` date NOT NULL,
  `total` decimal(10,2) NOT NULL,
  `puntosUsados` int(8) NOT NULL,
  `puntosGanados` int(8) NOT NULL,
  `totalBeneficio` decimal(10,2) NOT NULL,
  `IVA` decimal(10,2) NOT NULL,
  `subtotalBeneficio` decimal(10,2) NOT NULL,
  `tarjetaMemb` int(10) NOT NULL,
  `nivelTarjeta` varchar(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `venta`
--

INSERT INTO `venta` (`numero`, `fecha`, `total`, `puntosUsados`, `puntosGanados`, `totalBeneficio`, `IVA`, `subtotalBeneficio`, `tarjetaMemb`, `nivelTarjeta`) VALUES
(1000000, '2019-01-02', 130.00, 40, 13, 100.00, 16.00, 74.00, 10000, 'NV1'),
(1000001, '2019-01-07', 200.00, 0, 20, 180.00, 32.00, 148.00, 10001, 'NV1'),
(1000002, '2020-01-12', 300.00, 0, 60, 270.00, 48.00, 222.00, 10002, 'NV2'),
(1000003, '2020-01-17', 400.00, 0, 40, 360.00, 64.00, 296.00, 10003, 'NV1'),
(1000004, '2021-01-22', 500.00, 12, 100, 450.00, 80.00, 370.00, 10004, 'NV2'),
(1000005, '2021-01-27', 600.00, 0, 180, 540.00, 96.00, 444.00, 10005, 'NV3'),
(1000006, '2021-02-01', 700.00, 0, 70, 630.00, 112.00, 518.00, 10006, 'NV1'),
(1000007, '2022-02-06', 800.00, 20, 160, 720.00, 128.00, 592.00, 10007, 'NV2'),
(1000008, '2022-02-11', 900.00, 0, 270, 810.00, 144.00, 666.00, 10008, 'NV3'),
(1000009, '2023-02-16', 1000.00, 0, 100, 900.00, 160.00, 740.00, 10009, 'NV1'),
(1000010, '2024-02-21', 1100.00, 0, 110, 990.00, 176.00, 814.00, 10001, 'NV1'),
(1000011, '2024-02-26', 1200.00, 60, 120, 1080.00, 192.00, 888.00, 10001, 'NV1'),
(1000012, '2024-03-03', 1300.00, 9, 130, 1170.00, 208.00, 962.00, 10002, 'NV2'),
(1000013, '2024-03-08', 1400.00, 30, 140, 1260.00, 224.00, 1036.00, 10003, 'NV1'),
(1000014, '2024-07-29', 100.00, 50, 7, 70.00, 11.20, 58.80, 10000, 'NV1'),
(1000015, '2024-07-30', 500.00, 43, 45, 453.50, 72.56, 380.94, 10001, 'NV1'),
(1000016, '2024-07-30', 400.00, 9, -50, 400.00, 64.00, 336.00, 10002, 'NV2'),
(1000017, '2024-07-30', 100.00, 0, 0, 100.00, 16.00, 84.00, 10002, 'NV2'),
(1000018, '2024-07-30', 100.00, 0, 18, 90.00, 14.40, 75.60, 10002, 'NV2'),
(1000019, '2024-07-30', 100.00, 30, 7, 79.70, 12.75, 66.95, 10000, 'NV1'),
(1000020, '2024-07-30', 100.00, 0, 0, 100.00, 16.00, 84.00, 10015, 'NV3'),
(1000021, '2024-07-30', 100.00, 0, 25, 85.00, 13.60, 71.40, 10015, 'NV3'),
(1000022, '2024-07-31', 100.00, 5, 9, 92.50, 14.80, 77.70, 10000, 'NV1');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `beneficio`
--
ALTER TABLE `beneficio`
  ADD PRIMARY KEY (`codigo`),
  ADD KEY `nivelTarjeta` (`nivelTarjeta`);

--
-- Indices de la tabla `cancelacion`
--
ALTER TABLE `cancelacion`
  ADD PRIMARY KEY (`numero`),
  ADD KEY `tarjetaMemb` (`tarjetaMemb`);

--
-- Indices de la tabla `empleado`
--
ALTER TABLE `empleado`
  ADD PRIMARY KEY (`numero`),
  ADD KEY `puesto` (`puesto`);

--
-- Indices de la tabla `establecimiento`
--
ALTER TABLE `establecimiento`
  ADD PRIMARY KEY (`codigo`);

--
-- Indices de la tabla `miembro`
--
ALTER TABLE `miembro`
  ADD PRIMARY KEY (`numero`),
  ADD KEY `empleado` (`empleado`);

--
-- Indices de la tabla `niveltarjeta`
--
ALTER TABLE `niveltarjeta`
  ADD PRIMARY KEY (`codigo`),
  ADD UNIQUE KEY `color` (`color`);

--
-- Indices de la tabla `puesto`
--
ALTER TABLE `puesto`
  ADD PRIMARY KEY (`codigo`);

--
-- Indices de la tabla `renovacion`
--
ALTER TABLE `renovacion`
  ADD PRIMARY KEY (`numero`),
  ADD KEY `tarjetaMemb` (`tarjetaMemb`);

--
-- Indices de la tabla `tarjetamemb`
--
ALTER TABLE `tarjetamemb`
  ADD PRIMARY KEY (`numero`),
  ADD KEY `empleado` (`empleado`),
  ADD KEY `miembro` (`miembro`),
  ADD KEY `nivelTarjeta` (`nivelTarjeta`);

--
-- Indices de la tabla `venta`
--
ALTER TABLE `venta`
  ADD PRIMARY KEY (`numero`),
  ADD KEY `tarjetaMemb` (`tarjetaMemb`),
  ADD KEY `nivelTarjeta` (`nivelTarjeta`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `cancelacion`
--
ALTER TABLE `cancelacion`
  MODIFY `numero` int(8) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT de la tabla `empleado`
--
ALTER TABLE `empleado`
  MODIFY `numero` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `miembro`
--
ALTER TABLE `miembro`
  MODIFY `numero` int(8) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1011;

--
-- AUTO_INCREMENT de la tabla `renovacion`
--
ALTER TABLE `renovacion`
  MODIFY `numero` int(8) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=110;

--
-- AUTO_INCREMENT de la tabla `tarjetamemb`
--
ALTER TABLE `tarjetamemb`
  MODIFY `numero` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10016;

--
-- AUTO_INCREMENT de la tabla `venta`
--
ALTER TABLE `venta`
  MODIFY `numero` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1000023;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `beneficio`
--
ALTER TABLE `beneficio`
  ADD CONSTRAINT `beneficio_ibfk_1` FOREIGN KEY (`nivelTarjeta`) REFERENCES `niveltarjeta` (`codigo`);

--
-- Filtros para la tabla `cancelacion`
--
ALTER TABLE `cancelacion`
  ADD CONSTRAINT `cancelacion_ibfk_1` FOREIGN KEY (`tarjetaMemb`) REFERENCES `tarjetamemb` (`numero`);

--
-- Filtros para la tabla `empleado`
--
ALTER TABLE `empleado`
  ADD CONSTRAINT `empleado_ibfk_1` FOREIGN KEY (`puesto`) REFERENCES `puesto` (`codigo`);

--
-- Filtros para la tabla `miembro`
--
ALTER TABLE `miembro`
  ADD CONSTRAINT `miembro_ibfk_1` FOREIGN KEY (`empleado`) REFERENCES `empleado` (`numero`);

--
-- Filtros para la tabla `renovacion`
--
ALTER TABLE `renovacion`
  ADD CONSTRAINT `renovacion_ibfk_1` FOREIGN KEY (`tarjetaMemb`) REFERENCES `tarjetamemb` (`numero`);

--
-- Filtros para la tabla `tarjetamemb`
--
ALTER TABLE `tarjetamemb`
  ADD CONSTRAINT `tarjetamemb_ibfk_1` FOREIGN KEY (`empleado`) REFERENCES `empleado` (`numero`),
  ADD CONSTRAINT `tarjetamemb_ibfk_2` FOREIGN KEY (`miembro`) REFERENCES `miembro` (`numero`),
  ADD CONSTRAINT `tarjetamemb_ibfk_3` FOREIGN KEY (`nivelTarjeta`) REFERENCES `niveltarjeta` (`codigo`);

--
-- Filtros para la tabla `venta`
--
ALTER TABLE `venta`
  ADD CONSTRAINT `venta_ibfk_1` FOREIGN KEY (`tarjetaMemb`) REFERENCES `tarjetamemb` (`numero`),
  ADD CONSTRAINT `venta_ibfk_2` FOREIGN KEY (`nivelTarjeta`) REFERENCES `niveltarjeta` (`codigo`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
