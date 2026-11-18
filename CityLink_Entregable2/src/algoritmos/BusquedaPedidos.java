
package algoritmos;

import java.util.ArrayList;
import java.util.List;
import modelo.Pedido;

/**
 * Contiene métodos para buscar pedidos.
 */
public class BusquedaPedidos {

    /**
     * Busca un pedido recorriendo la lista desde el principio.
     */
    public static Pedido buscarLineal(List<Pedido> pedidos, int codigo) {

        for (Pedido pedido : pedidos) {
            if (pedido.getCodigo() == codigo) {
                return pedido;
            }
        }

        return null;
    }

    /**
     * Busca un pedido mediante búsqueda binaria.
     */
    public static Pedido buscarBinaria(List<Pedido> pedidos, int codigo) {

        // Creamos una copia para no modificar la lista original.
        List<Pedido> pedidosOrdenados = new ArrayList<>(pedidos);

        // Ordenamos la copia por código mediante inserción.
        for (int i = 1; i < pedidosOrdenados.size(); i++) {

            Pedido actual = pedidosOrdenados.get(i);
            int j = i - 1;

            while (j >= 0 &&
                    pedidosOrdenados.get(j).getCodigo() > actual.getCodigo()) {

                pedidosOrdenados.set(j + 1, pedidosOrdenados.get(j));
                j--;
            }

            pedidosOrdenados.set(j + 1, actual);
        }

        // Realizamos la búsqueda binaria.
        int inicio = 0;
        int fin = pedidosOrdenados.size() - 1;

        while (inicio <= fin) {

            int medio = (inicio + fin) / 2;
            Pedido actual = pedidosOrdenados.get(medio);

            if (actual.getCodigo() == codigo) {
                return actual;
            }

            if (actual.getCodigo() < codigo) {
                inicio = medio + 1;
            } else {
                fin = medio - 1;
            }
        }

        return null;
    }
}
