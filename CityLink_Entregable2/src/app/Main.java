
package app;

import java.util.List;
import java.util.Scanner;

import algoritmos.OrdenamientoPedidos;

import modelo.*;
import excepciones.*;

/**
 * Representa Main en el sistema CityLink.
 */
public class Main {

    private static final Scanner TECLADO = new Scanner(System.in);
    private static final CityLink SISTEMA = new CityLink();

    /**
    * Inicia la aplicación y muestra el menú principal.
    */
    public static void main(String[] args) {
        precargarDatos();

        int opcion = 0;

        while (opcion != 10) {
            mostrarMenu();

            try {
                opcion = leerEntero("Elegí una opción: ");

                
switch (opcion) {
    case 1:
        gestionarClientes();
        break;

        case 2:
        gestionarRepartidores();
        break;

    case 3:
        altaPedido();
        break;

    case 4:
        asignarPedido();
        break;

    case 5:
        registrarEntregaOCambiarEstado();
        break;

    case 6:
        deshacerUltimaAccion();
        break;

    case 7:
        ordenarYListarPedidos();
        break;

    case 8:
        buscarPedidoPorCodigo();
        break;

    case 9:
        intercalarYListarPedidos();
        break;

    case 10:
        System.out.println("Hasta luego.");
        break;

    default:
        System.out.println("Opción inválida.");
}


            } catch (NumberFormatException e) {
                System.out.println("Ingresá un número válido.");

            } catch (ClienteNoEncontradoException
                     | PedidoInvalidoException
                     | CapacidadExcedidaException
                     | AlmacenamientoLlenoException e) {

                System.out.println("Error: " + e.getMessage());

            } catch (IllegalArgumentException
                     | EstadoInvalidoException e) {

                System.out.println("Error: " + e.getMessage());

            } finally {
                // Se ejecuta al finalizar cada operación del menú.
            }
        }

        TECLADO.close();
    }

    
private static void mostrarMenu() {
    System.out.println("\n===== CITYLINK =====");
    System.out.println("1. Gestión de clientes");
    System.out.println("2. Gestión de repartidores");
    System.out.println("3. Registrar un pedido");
    System.out.println("4. Atender el siguiente pedido");
    System.out.println("5. Registrar una entrega");
    System.out.println("6. Deshacer última operación");
    System.out.println("7. Ordenar y listar pedidos");
    System.out.println("8. Buscar pedido por código");
    System.out.println("9. Intercalar pedidos de dos turnos");
    System.out.println("10. Salir");
}


    private static void deshacerUltimaAccion() {

    if (SISTEMA.deshacerUltimaAccion()) {
        System.out.println("Última acción deshecha correctamente.");
    } else {
        System.out.println("No hay acciones para deshacer.");
    }
}

    private static void gestionarClientes()
        throws ClienteNoEncontradoException,
               AlmacenamientoLlenoException {

    int opcion = 0;

while (opcion != 6) {
    System.out.println("\n===== GESTIÓN DE CLIENTES =====");
    System.out.println("1. Registrar cliente");
    System.out.println("2. Eliminar cliente");
    System.out.println("3. Buscar cliente por código");
    System.out.println("4. Buscar cliente por nombre");
    System.out.println("5. Listar clientes");
    System.out.println("6. Volver al menú principal");

        opcion = leerEntero("Elegí una opción: ");

        switch (opcion) {
            case 1:
                altaCliente();
                break;

            case 2:
                bajaCliente();
                break;

            case 3:
                Cliente cliente = SISTEMA.buscarCliente(
                        leerEntero("Código del cliente: ")
                );
                System.out.println(cliente);
                break;

            case 4:
                Cliente porNombre = SISTEMA.buscarCliente(
                        leerTexto("Nombre del cliente: ")
                );
                System.out.println(porNombre);
                break;

            case 5:
                listarClientes();
                break;

            case 6:
                System.out.println("Volviendo al menú principal...");
                break;

            default:
                System.out.println("Opción inválida.");
        }
    }
}

    private static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        return Integer.parseInt(TECLADO.nextLine().trim());
    }

    private static double leerDouble(String mensaje) {
        System.out.print(mensaje);
        return Double.parseDouble(
                TECLADO.nextLine().trim().replace(',', '.')
        );
    }

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return TECLADO.nextLine().trim();
    }

    private static void precargarDatos() {
        try {
            Cliente c1 = new Cliente(1, "Ana Pérez", "2664000001", "Centro");
            Cliente c2 = new Cliente(2, "Luis Gómez", "2664000002", "Estación");
            Cliente c3 = new Cliente(3, "Marta Díaz", "2664000003", "Norte");
            Cliente c4 = new Cliente(4, "Juan López", "2664000004", "Sur");
            Cliente c5 = new Cliente(5, "Sofía Ruiz", "2664000005", "Oeste");

            SISTEMA.registrarCliente(c1);
            SISTEMA.registrarCliente(c2);
            SISTEMA.registrarCliente(c3);
            SISTEMA.registrarCliente(c4);
            SISTEMA.registrarCliente(c5);

            SISTEMA.registrarRepartidor(
                    new Repartidor(101, "Pedro", new Bicicleta("A100AAA"))
            );
            SISTEMA.registrarRepartidor(
                    new Repartidor(102, "Lucía", new Moto("B200BBB"))
            );
            SISTEMA.registrarRepartidor(
                    new Repartidor(103, "Marcos", new Camioneta("C300CCC"))
            );

            SISTEMA.registrarPedido(
        new PedidoNormal(1001, c1, "Plaza central", 2, 3),
        "mañana"
);

SISTEMA.registrarPedido(
        new PedidoUrgente(1002, c2, "Hospital", 4, 5),
        "tarde"
);

SISTEMA.registrarPedido(
        new PedidoFragil(1003, c3, "Barrio Norte", 3, 7),
        "mañana"
);

SISTEMA.registrarPedido(
        new PedidoNormal(1004, c4, "Terminal", 5, 4),
        "tarde"
);

SISTEMA.registrarPedido(
        new PedidoUrgente(1005, c5, "Universidad", 1, 6),
        "mañana"
);

SISTEMA.registrarPedido(
        new PedidoFragil(1006, c1, "Barrio Sur", 8, 2),
        "tarde"
);

SISTEMA.registrarPedido(
        new PedidoNormal(1007, c2, "Municipalidad", 12, 8),
        "mañana"
);

SISTEMA.registrarPedido(
        new PedidoUrgente(1008, c3, "Parque", 6, 10),
        "tarde"
);

        } catch (Exception e) {
            System.out.println("Error al precargar datos: " + e.getMessage());
        }
    }

    private static void altaCliente()
            throws AlmacenamientoLlenoException {

        int codigo = leerEntero("Código: ");
        String nombre = leerTexto("Nombre: ");
        String telefono = leerTexto("Teléfono: ");
        String zona = leerTexto("Zona: ");

        SISTEMA.registrarCliente(
                new Cliente(codigo, nombre, telefono, zona)
        );

        System.out.println("Cliente registrado.");
    }

    private static void bajaCliente()
        throws ClienteNoEncontradoException {

    int codigo = leerEntero("Código del cliente a eliminar: ");

    SISTEMA.eliminarCliente(codigo);

    System.out.println("Cliente eliminado correctamente.");
}

private static void listarClientes() {

    Cliente[] clientes = SISTEMA.getClientes();

    if (clientes.length == 0) {
        System.out.println("No hay clientes registrados.");
        return;
    }

    for (Cliente cliente : clientes) {
        System.out.println(cliente);
    }
}

    private static void altaRepartidor()
            throws AlmacenamientoLlenoException {

        int legajo = leerEntero("Legajo: ");
        String nombre = leerTexto("Nombre: ");

        System.out.println("1. Bicicleta  2. Moto  3. Camioneta");
        int tipo = leerEntero("Tipo de vehículo: ");
        String patente = leerTexto("Patente: ");

        Vehiculo vehiculo;

        switch (tipo) {
            case 1:
                vehiculo = new Bicicleta(patente);
                break;
            case 2:
                vehiculo = new Moto(patente);
                break;
            case 3:
                vehiculo = new Camioneta(patente);
                break;
            default:
                throw new IllegalArgumentException(
                        "Tipo de vehículo inválido."
                );
        }

        SISTEMA.registrarRepartidor(
                new Repartidor(legajo, nombre, vehiculo)
        );

        System.out.println("Repartidor registrado.");
    }

    private static void gestionarRepartidores()
        throws AlmacenamientoLlenoException {

    int opcion = 0;

    while (opcion != 4) {
        System.out.println("\n===== GESTIÓN DE REPARTIDORES =====");
        System.out.println("1. Registrar repartidor");
        System.out.println("2. Eliminar repartidor");
        System.out.println("3. Listar repartidores");
        System.out.println("4. Volver al menú principal");

        opcion = leerEntero("Elegí una opción: ");

        switch (opcion) {
            case 1:
                altaRepartidor();
                break;

            case 2:
                bajaRepartidor();
                break;

            case 3:
                listarRepartidores();
                break;

            case 4:
                System.out.println("Volviendo al menú principal...");
                break;

            default:
                System.out.println("Opción inválida.");
        }
    }
}

private static void bajaRepartidor() {

    int legajo = leerEntero("Legajo del repartidor a eliminar: ");

    SISTEMA.eliminarRepartidor(legajo);

    System.out.println("Repartidor eliminado correctamente.");
}

private static void listarRepartidores() {

    Repartidor[] repartidores = SISTEMA.getRepartidores();

    if (repartidores.length == 0) {
        System.out.println("No hay repartidores registrados.");
        return;
    }

    for (Repartidor repartidor : repartidores) {
        System.out.println(
                "Legajo: " + repartidor.getLegajo()
                + " | Nombre: " + repartidor.getNombre()
                + " | Vehículo: "
                + repartidor.getVehiculo().getClass().getSimpleName()
                + " (" + repartidor.getVehiculo().getPatente() + ")"
        );
    }
}

    private static void altaPedido()
            throws ClienteNoEncontradoException,
                   PedidoInvalidoException,
                   AlmacenamientoLlenoException {

        int codigo = leerEntero("Código del pedido: ");
        int codigoCliente = leerEntero("Código del cliente: ");
        Cliente cliente = SISTEMA.buscarCliente(codigoCliente);

        String destino = leerTexto("Destino: ");
        double peso = leerDouble("Peso en kg: ");
        double distancia = leerDouble("Distancia en km: ");

        System.out.println("1. Normal  2. Urgente  3. Frágil");
        int tipo = leerEntero("Tipo de pedido: ");

        Pedido pedido;

        switch (tipo) {
            case 1:
                pedido = new PedidoNormal(
                        codigo, cliente, destino, peso, distancia
                );
                break;
            case 2:
                pedido = new PedidoUrgente(
                        codigo, cliente, destino, peso, distancia
                );
                break;
            case 3:
                pedido = new PedidoFragil(
                        codigo, cliente, destino, peso, distancia
                );
                break;
            default:
                throw new IllegalArgumentException(
                        "Tipo de pedido inválido."
                );
        }
System.out.println("1. Turno mañana");
System.out.println("2. Turno tarde");

int opcionTurno = leerEntero("Elegí el turno: ");

String turno;

if (opcionTurno == 1) {
    turno = "mañana";
} else if (opcionTurno == 2) {
    turno = "tarde";
} else {
    System.out.println("Turno inválido.");
    return;
}

SISTEMA.registrarPedido(pedido, turno);

System.out.println("Pedido registrado correctamente.");
    }

    

private static void asignarPedido()
        throws AlmacenamientoLlenoException,
               CapacidadExcedidaException {

    Repartidor[] repartidores = SISTEMA.getRepartidores();

    System.out.println("Repartidores disponibles:");

    for (Repartidor repartidor : repartidores) {
        if (repartidor != null) {
            System.out.println(
                    repartidor.getLegajo() + ". "
                    + repartidor.getNombre() + " - "
                    + repartidor.getVehiculo()
                            .getClass().getSimpleName()
            );
        }
    }

    int legajo = leerEntero("Ingresá el legajo del repartidor: ");

    SISTEMA.asignarSiguientePedido(legajo);

    System.out.println("Pedido asignado correctamente.");
}

    private static void registrarEntregaOCambiarEstado() {
        int codigo = leerEntero("Código del pedido: ");
        Pedido pedido = SISTEMA.buscarPedido(codigo);

        if (pedido == null) {
            System.out.println("No existe ese pedido.");
            return;
        }

        System.out.println("1. Pasar a EN_CAMINO");
        System.out.println("2. Registrar entrega");
        System.out.println("3. Cancelar pedido");

        int opcion = leerEntero("Elegí una opción: ");

        if (opcion == 1) {
        SISTEMA.cambiarEstadoPedido(codigo, "EN_CAMINO");
        } else if (opcion == 2) {
            int legajo = leerEntero("Legajo del repartidor: ");
        SISTEMA.registrarEntrega(codigo, legajo);
        } else if (opcion == 3) {
        SISTEMA.cambiarEstadoPedido(codigo, "CANCELADO");
        } else {
            System.out.println("Opción inválida.");
            return;
        }

        System.out.println("Estado actual: " + pedido.getEstado());
    }

    private static void buscarPedidoPorCodigo() {
        System.out.println("1. Búsqueda lineal");
        System.out.println("2. Búsqueda binaria");

        int opcion = leerEntero("Elegí el método de búsqueda: ");
        int codigo = leerEntero("Código del pedido: ");
        Pedido pedido;

        if (opcion == 1) {
            pedido = SISTEMA.buscarPedidoLineal(codigo);
        } else if (opcion == 2) {
            pedido = SISTEMA.buscarPedidoBinaria(codigo);
        } else {
            System.out.println("Opción inválida.");
            return;
        }

        if (pedido == null) {
            System.out.println("No existe un pedido con ese código.");
        } else {
            System.out.println("Pedido encontrado:");
            System.out.println(pedido);
        }
    }

private static void ordenarYListarPedidos() {

    System.out.println("\n===== ORDENAR PEDIDOS =====");
    System.out.println("1. Por código");
    System.out.println("2. Por distancia");
    System.out.println("3. Por fecha");
    System.out.println("4. Por costo");

    int criterioOpcion = leerEntero("Elegí el criterio: ");

    String criterio;

    switch (criterioOpcion) {
        case 1:
            criterio = "codigo";
            break;
        case 2:
            criterio = "distancia";
            break;
        case 3:
            criterio = "fecha";
            break;
        case 4:
            criterio = "costo";
            break;
        default:
            System.out.println("Criterio inválido.");
            return;
    }

    System.out.println("\nElegí el algoritmo:");
    System.out.println("1. Inserción");
    System.out.println("2. Burbuja");

    int algoritmo = leerEntero("Elegí una opción: ");

    List<Pedido> pedidosOrdenados;

    if (algoritmo == 1) {
        pedidosOrdenados =
                SISTEMA.ordenarPedidosPorInsercion(criterio);

    } else if (algoritmo == 2) {
        pedidosOrdenados =
                SISTEMA.ordenarPedidosBurbuja(criterio);

    } else {
        System.out.println("Algoritmo inválido.");
        return;
    }

    System.out.println("\n===== PEDIDOS ORDENADOS =====");

    if (pedidosOrdenados.isEmpty()) {
        System.out.println("No hay pedidos registrados.");
        return;
    }

    for (Pedido pedido : pedidosOrdenados) {
        System.out.println(pedido);

        Vehiculo vehiculo = pedido.getVehiculoAsignado();

        if (vehiculo != null) {
            System.out.printf(
                    "Costo: $%.2f%n",
                    pedido.calcularCosto(vehiculo)
            );
} else {
    System.out.printf(
            "Costo estimado (moto de referencia): $%.2f%n",
            OrdenamientoPedidos.costoParaOrdenar(pedido)
    );
}
    }
}

private static void intercalarYListarPedidos() {

    List<Pedido> pedidosIntercalados =
            SISTEMA.intercalarPedidosPorTurno();

    System.out.println("\n===== PEDIDOS INTERCALADOS =====");

    if (pedidosIntercalados.isEmpty()) {
        System.out.println("No hay pedidos registrados.");
        return;
    }

    for (Pedido pedido : pedidosIntercalados) {
        System.out.println(pedido);
    }
}

}
