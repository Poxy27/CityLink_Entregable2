
package algoritmos;

import java.util.ArrayList;
import java.util.List;
import modelo.Moto;
import modelo.Pedido;
import modelo.Vehiculo;

/**
 * Contiene algoritmos para ordenar pedidos.
 */
public class OrdenamientoPedidos {

    /**
     * Ordena pedidos mediante el algoritmo de inserción.
     */
    public static List<Pedido> ordenarPorInsercion(
            List<Pedido> pedidos, String criterio) {

        List<Pedido> ordenados = new ArrayList<>(pedidos);

        for (int i = 1; i < ordenados.size(); i++) {

            Pedido actual = ordenados.get(i);
            int j = i - 1;

            while (j >= 0 &&
                    comparar(ordenados.get(j), actual, criterio) > 0) {

                ordenados.set(j + 1, ordenados.get(j));
                j--;
            }

            ordenados.set(j + 1, actual);
        }

        return ordenados;
    }

    /**
     * Ordena pedidos mediante el algoritmo burbuja.
     */
    public static List<Pedido> ordenarBurbuja(
            List<Pedido> pedidos, String criterio) {

        List<Pedido> ordenados = new ArrayList<>(pedidos);

        for (int i = 0; i < ordenados.size() - 1; i++) {

            for (int j = 0; j < ordenados.size() - 1 - i; j++) {

                if (comparar(
                        ordenados.get(j),
                        ordenados.get(j + 1),
                        criterio) > 0) {

                    Pedido auxiliar = ordenados.get(j);

                    ordenados.set(j, ordenados.get(j + 1));
                    ordenados.set(j + 1, auxiliar);
                }
            }
        }

        return ordenados;
    }

    /**
     * Compara dos pedidos según el criterio seleccionado.
     */
    private static int comparar(
            Pedido p1, Pedido p2, String criterio) {

        switch (criterio.toLowerCase()) {

            case "codigo":
                return Integer.compare(
                        p1.getCodigo(), p2.getCodigo());

            case "distancia":
                return Double.compare(
                        p1.getDistanciaKm(), p2.getDistanciaKm());

            case "fecha":
                return p1.getFechaHora().compareTo(
                        p2.getFechaHora());

            case "costo":
                return Double.compare(

            costoParaOrdenar(p1), costoParaOrdenar(p2));
            default:
                throw new IllegalArgumentException(
                        "Criterio de ordenamiento no válido.");
        }
    }
    /** Vehículo usado para estimar el costo de pedidos aún sin asignar. */
private static final Vehiculo VEHICULO_REFERENCIA = new Moto("REF");

/**
 * Devuelve el costo con el que se ordena un pedido. Usa el vehículo
 * asignado y, si todavía no tiene, un vehículo de referencia.
 * @param pedido pedido a evaluar
 * @return costo real o estimado del pedido
 */
public static double costoParaOrdenar(Pedido pedido) {
    Vehiculo vehiculo = pedido.getVehiculoAsignado();

    if (vehiculo == null) {
        vehiculo = VEHICULO_REFERENCIA;
    }

    return pedido.calcularCosto(vehiculo);
}
}
