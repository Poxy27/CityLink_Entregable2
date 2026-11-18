package modelo;

import excepciones.AlmacenamientoLlenoException;
import excepciones.CapacidadExcedidaException;
import excepciones.ClienteNoEncontradoException;
import excepciones.PedidoInvalidoException;

import algoritmos.OrdenamientoPedidos;
import algoritmos.BusquedaPedidos;
import algoritmos.IntercalacionPedidos;
import algoritmos.BusquedaClientes;

import java.util.Deque;
import java.util.Queue;
import java.util.ArrayDeque;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa CityLink en el sistema CityLink.
 */
public class CityLink {

    private TreeMap<Integer, Cliente> clientes = new TreeMap<>();

    private List<Pedido> pedidos = new ArrayList<>();
    private List<Repartidor> repartidores = new ArrayList<>();
    private Queue<Pedido> pedidosNormales = new ArrayDeque<>();
    private Queue<Pedido> pedidosUrgentes = new ArrayDeque<>();
    private List<Pedido> historialEntregas = new ArrayList<>();
    private Deque<Runnable> accionesDeshacer = new ArrayDeque<>();
    private List<Pedido> pedidosTurnoManana = new ArrayList<>();
    private List<Pedido> pedidosTurnoTarde = new ArrayList<>();

    /**
     * Registra un cliente en el sistema.
     */
    public void registrarCliente(Cliente cliente) {

        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo.");
        }

        if (clientes.containsKey(cliente.getCodigo())) {
            throw new IllegalArgumentException("El código de cliente ya existe.");
        }

        clientes.put(cliente.getCodigo(), cliente);
    }

    /**
     * Busca un cliente por su código.
     */
    public Cliente buscarCliente(int codigo)
            throws ClienteNoEncontradoException {

        Cliente cliente = clientes.get(codigo);

        if (cliente == null) {
            throw new ClienteNoEncontradoException(
                    "No se encontró un cliente con código " + codigo
            );
        }

        return cliente;
    }

    /**
     * Elimina un cliente por su código.
     */
    public void eliminarCliente(int codigo)
            throws ClienteNoEncontradoException {

        Cliente cliente = clientes.remove(codigo);

        if (cliente == null) {
            throw new ClienteNoEncontradoException(
                    "No se encontró un cliente con código " + codigo
            );
        }
    }

    /**
     * Busca un cliente por su nombre.
     */
public Cliente buscarCliente(String nombre)
        throws ClienteNoEncontradoException {

    Cliente cliente = BusquedaClientes.buscarLinealPorNombre(
            clientes.values(), nombre);

    if (cliente == null) {
        throw new ClienteNoEncontradoException(
                "No se encontró el cliente: " + nombre
        );
    }

    return cliente;
}

    /**
     * Registra un repartidor en el sistema.
     */
    public void registrarRepartidor(Repartidor repartidor) {

        if (repartidor == null) {
            throw new IllegalArgumentException("El repartidor no puede ser nulo.");
        }

        for (Repartidor r : repartidores) {
            if (r.getLegajo() == repartidor.getLegajo()) {
                throw new IllegalArgumentException("El legajo ya existe.");
            }
        }

        repartidores.add(repartidor);
    }
    
/**
 * Elimina un repartidor del sistema por su legajo.
 * @param legajo legajo del repartidor a eliminar
 * @throws IllegalArgumentException si no existe un repartidor con ese legajo
 */
public void eliminarRepartidor(int legajo) {

    Repartidor repartidor = buscarRepartidor(legajo);

    if (repartidor == null) {
        throw new IllegalArgumentException(
                "No existe un repartidor con legajo " + legajo
        );
    }

    repartidores.remove(repartidor);
}

    /**
     * Registra un pedido en el sistema.
     */

public void registrarPedido(Pedido pedido, String turno)
        throws ClienteNoEncontradoException,
               PedidoInvalidoException {

    if (pedido == null) {
        throw new IllegalArgumentException(
                "El pedido no puede ser nulo."
        );
    }

    if (!"mañana".equalsIgnoreCase(turno)
            && !"tarde".equalsIgnoreCase(turno)) {
        throw new IllegalArgumentException(
                "El turno debe ser mañana o tarde."
        );
    }

    buscarCliente(pedido.getCliente().getCodigo());

    for (Pedido p : pedidos) {
        if (p.getCodigo() == pedido.getCodigo()) {
            throw new PedidoInvalidoException(
                    "El código de pedido ya existe."
            );
        }
    }

    pedidos.add(pedido);

    // Incorporamos el pedido al turno correspondiente.
if ("mañana".equalsIgnoreCase(turno)) {
    agregarPedidoOrdenado(pedidosTurnoManana, pedido);
} else {
    agregarPedidoOrdenado(pedidosTurnoTarde, pedido);
}

    if (pedido instanceof PedidoUrgente) {
        pedidosUrgentes.offer(pedido);
    } else {
        pedidosNormales.offer(pedido);
    }

    // Deshacer el registro del pedido.
    accionesDeshacer.push(() -> {
        pedidos.remove(pedido);
        pedidosTurnoManana.remove(pedido);
        pedidosTurnoTarde.remove(pedido);
        pedidosNormales.remove(pedido);
        pedidosUrgentes.remove(pedido);
    });
}
/**
 * Inserta un pedido manteniendo la lista ordenada por código.
 */
private void agregarPedidoOrdenado(
        List<Pedido> lista, Pedido pedido) {

    int posicion = 0;

    while (posicion < lista.size()
            && lista.get(posicion).getCodigo() < pedido.getCodigo()) {
        posicion++;
    }

    lista.add(posicion, pedido);
}

/**
 * Busca un pedido por su código.
 */
public Pedido buscarPedido(int codigo) {

    for (Pedido pedido : pedidos) {
        if (pedido.getCodigo() == codigo) {
            return pedido;
        }
    }

    return null;
}

/**
 * Busca un pedido por código mediante búsqueda lineal.
 */
public Pedido buscarPedidoLineal(int codigo) {
    return BusquedaPedidos.buscarLineal(pedidos, codigo);
}

/**
 * Busca un pedido por código mediante búsqueda binaria.
 */
public Pedido buscarPedidoBinaria(int codigo) {
    return BusquedaPedidos.buscarBinaria(pedidos, codigo);
}
    /**
    * Ordena los pedidos mediante inserción.
    */
    public List<Pedido> ordenarPedidosPorInsercion(String criterio) {
     return OrdenamientoPedidos.ordenarPorInsercion(pedidos, criterio);
}

    /**
    * Ordena los pedidos mediante burbuja.
    */
    public List<Pedido> ordenarPedidosBurbuja(String criterio) {
        return OrdenamientoPedidos.ordenarBurbuja(pedidos, criterio);
}
/**
 * Intercala los pedidos de los turnos mañana y tarde.
 */
public List<Pedido> intercalarPedidosPorTurno() {
    return IntercalacionPedidos.intercalar(
            pedidosTurnoManana,
            pedidosTurnoTarde
    );
}

    /**
     * Busca un repartidor por su legajo.
     */
    public Repartidor buscarRepartidor(int legajo) {

        for (Repartidor repartidor : repartidores) {
            if (repartidor.getLegajo() == legajo) {
                return repartidor;
            }
        }

        return null;
    }

    /**
     * Asigna un pedido al primer repartidor disponible.
     */
    public void asignarPedido(Pedido pedido)
            throws CapacidadExcedidaException {

        CapacidadExcedidaException ultimoError = null;

        for (Repartidor repartidor : repartidores) {
            try {
                repartidor.asignarPedido(pedido);

                if (pedido.getEstado().equals("PENDIENTE")) {
                    pedido.cambiarEstado("EN_CAMINO");
                }

                return;

            } catch (CapacidadExcedidaException | AlmacenamientoLlenoException e) {
                if (e instanceof CapacidadExcedidaException) {
                    ultimoError = (CapacidadExcedidaException) e;
                }
            }
        }

        if (ultimoError != null) {
            throw ultimoError;
        }

        throw new CapacidadExcedidaException(
                "No hay repartidores disponibles para transportar el pedido."
        );
    }


/**
 * Asigna el siguiente pedido pendiente, dando prioridad a los urgentes.
 */
public void asignarSiguientePedido(int legajo)
        throws CapacidadExcedidaException,
               AlmacenamientoLlenoException {

    Pedido siguiente = proximoPedidoPendiente();

    if (siguiente == null) {
        throw new IllegalArgumentException(
                "No hay pedidos pendientes."
        );
    }

    Repartidor repartidor = buscarRepartidor(legajo);

    if (repartidor == null) {
        throw new IllegalArgumentException(
                "No existe ese repartidor."
        );
    }

    String estadoAnterior = siguiente.getEstado();

    repartidor.asignarPedido(siguiente);

    if (estadoAnterior.equals("PENDIENTE")) {
        siguiente.cambiarEstado("EN_CAMINO");
    }

    if (siguiente instanceof PedidoUrgente) {
        pedidosUrgentes.remove(siguiente);
    } else {
        pedidosNormales.remove(siguiente);
    }

    final Pedido pedidoAsignado = siguiente;
    final Repartidor repartidorAsignado = repartidor;

    accionesDeshacer.push(() -> {
        repartidorAsignado.quitarPedidoAsignado(pedidoAsignado);
        pedidoAsignado.setVehiculoAsignado(null);
        pedidoAsignado.restaurarEstado(estadoAnterior);

        reinsertarPedidoEnCola(pedidoAsignado);
    });
}
/**
 * Devuelve el próximo pedido a atender, sin sacarlo de la cola.
 * Prioriza los urgentes y descarta los que fueron cancelados.
 * @return el próximo pedido pendiente, o null si no hay ninguno
 */
private Pedido proximoPedidoPendiente() {

    for (Pedido pedido : pedidosUrgentes) {
        if (!pedido.getEstado().equals("CANCELADO")) {
            return pedido;
        }
    }

    for (Pedido pedido : pedidosNormales) {
        if (!pedido.getEstado().equals("CANCELADO")) {
            return pedido;
        }
    }

    return null;
}
    /**
     * Asigna un pedido al repartidor que posee el vehículo indicado.
     */
    public void asignarPedido(Pedido pedido, Vehiculo vehiculo)
            throws CapacidadExcedidaException {

        if (vehiculo == null) {
            throw new IllegalArgumentException("El vehículo no puede ser nulo.");
        }

        for (Repartidor repartidor : repartidores) {
            if (repartidor.getVehiculo().getPatente()
                    .equalsIgnoreCase(vehiculo.getPatente())) {

                try {
                    repartidor.asignarPedido(pedido);
                } catch (AlmacenamientoLlenoException e) {
                throw new CapacidadExcedidaException(e.getMessage());
                }

                if (pedido.getEstado().equals("PENDIENTE")) {
                    pedido.cambiarEstado("EN_CAMINO");
                }

                return;
            }
        }

        throw new CapacidadExcedidaException(
                "No existe un repartidor registrado con ese vehículo."
        );
    }

    /**
     * Registra la entrega de un pedido.
     */
    public void registrarEntrega(int codigoPedido, int legajo) {

        Pedido pedido = buscarPedido(codigoPedido);
        Repartidor repartidor = buscarRepartidor(legajo);

        if (pedido == null) {
            throw new IllegalArgumentException("No existe ese pedido.");
        }

        if (repartidor == null) {
            throw new IllegalArgumentException("No existe ese repartidor.");
        }

        String estadoAnterior = pedido.getEstado();

        repartidor.registrarEntrega(pedido);
        historialEntregas.add(pedido);

        accionesDeshacer.push(() -> {
            pedido.restaurarEstado(estadoAnterior);
            historialEntregas.remove(pedido);
            repartidor.quitarEntregaHistorial(pedido);
        });
    }

    /**
     * Cambia el estado de un pedido.
     */
public void cambiarEstadoPedido(int codigo, String estado) {

    Pedido pedido = buscarPedido(codigo);

    if (pedido == null) {
        throw new IllegalArgumentException("No existe ese pedido.");
    }

    String estadoAnterior = pedido.getEstado();

    pedido.cambiarEstado(estado);

    accionesDeshacer.push(() ->
            pedido.restaurarEstado(estadoAnterior)
    );
}

    /**
     * Devuelve los clientes del sistema.
     */
    public Cliente[] getClientes() {
        return clientes.values().toArray(new Cliente[0]);
    }

    /**
     * Devuelve los pedidos del sistema.
     */
    public Pedido[] getPedidos() {
    return pedidos.toArray(new Pedido[0]);
    }
    /**
 * Devuelve el historial de entregas.
 */
public Pedido[] getHistorialEntregas() {
    return historialEntregas.toArray(new Pedido[0]);
}

    /**
     * Devuelve los repartidores del sistema.
     */
    public Repartidor[] getRepartidores() {
        return repartidores.toArray(new Repartidor[0]);
    }

    /**
     * Calcula el importe total de los pedidos.
     */
    public double calcularImporteTotalPedidos() {

    double total = 0;

    for (Pedido pedido : pedidos) {
        total += pedido.calcularImporte();
    }

    return total;
    }
        // Otros métodos que ya tenés...

    public boolean deshacerUltimaAccion() {

        if (accionesDeshacer.isEmpty()) {
            return false;
        }

        accionesDeshacer.pop().run();

        return true;
    }

    private void reinsertarPedidoEnCola(Pedido pedido) {

        if (pedido instanceof PedidoUrgente) {
            pedidosUrgentes = insertarAlInicio(pedidosUrgentes, pedido);
        } else {
            pedidosNormales = insertarAlInicio(pedidosNormales, pedido);
        }
    }

    private Queue<Pedido> insertarAlInicio(
            Queue<Pedido> cola, Pedido pedido) {

        Queue<Pedido> nuevaCola = new ArrayDeque<>();

        nuevaCola.offer(pedido);
        nuevaCola.addAll(cola);

        return nuevaCola;
    }

}