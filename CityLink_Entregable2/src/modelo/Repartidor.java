package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa Repartidor en el sistema CityLink.
 */
public class Repartidor implements Rastreable, Facturable {

    private int legajo;
    private String nombre;
    private Vehiculo vehiculo;
    private List<Pedido> pedidosAsignados;
    private List<Pedido> historialEntregas = new ArrayList<>();

    public Repartidor(int legajo, String nombre, Vehiculo vehiculo) {
        this.legajo = legajo;
        this.nombre = nombre;
        this.vehiculo = vehiculo;
        this.pedidosAsignados = new ArrayList<>();
    }

    public int getLegajo() {
        return legajo;
    }

    public String getNombre() {
        return nombre;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    /**
     * Asigna un pedido al repartidor.
     */
    public void asignarPedido(Pedido pedido)
            throws excepciones.AlmacenamientoLlenoException,
                   excepciones.CapacidadExcedidaException {

        vehiculo.puedeTransportar(pedido);

        pedido.setVehiculoAsignado(vehiculo);

        pedidosAsignados.add(pedido);
    }

    /**
     * Quita un pedido de los pedidos asignados al repartidor.
     */
    void quitarPedidoAsignado(Pedido pedido) {
        pedidosAsignados.remove(pedido);
    }

    /**
     * Registra la entrega de un pedido.
     */
   public void registrarEntrega(Pedido pedido) {

    if (!pedidosAsignados.contains(pedido)) {
        throw new IllegalArgumentException(
                "El pedido no está asignado a este repartidor."
        );
    }

    pedido.cambiarEstado("ENTREGADO");

    historialEntregas.add(pedido);
}
/**
 * Devuelve el historial de entregas del repartidor.
 */
public List<Pedido> getHistorialEntregas() {
    return new ArrayList<>(historialEntregas);
}

/**
 * Quita una entrega del historial.
 */
void quitarEntregaHistorial(Pedido pedido) {
    historialEntregas.remove(pedido);
}
    @Override
    public String obtenerEstadoActual() {
        return "Repartidor: " + nombre;
    }

    @Override
    public double calcularImporte() {

        double total = 0;

        for (Pedido pedido : pedidosAsignados) {
            if (pedido.getEstado().equals("ENTREGADO")) {
                total += pedido.calcularCosto(vehiculo);
            }
        }

        return total * 0.10;
    }
}