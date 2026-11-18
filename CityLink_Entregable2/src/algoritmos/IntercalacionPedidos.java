
package algoritmos;

import java.util.ArrayList;
import java.util.List;
import modelo.Pedido;

/**
 * Combina dos listas de pedidos ordenadas por código.
 */
public class IntercalacionPedidos {

    /**
     * Intercala dos listas ordenadas en un único recorrido.
     */
    public static List<Pedido> intercalar(
            List<Pedido> manana, List<Pedido> tarde) {

        List<Pedido> resultado = new ArrayList<>();

        int i = 0;
        int j = 0;

        while (i < manana.size() && j < tarde.size()) {

            if (manana.get(i).getCodigo()
                    <= tarde.get(j).getCodigo()) {

                resultado.add(manana.get(i));
                i++;

            } else {

                resultado.add(tarde.get(j));
                j++;
            }
        }

        // Incorporamos los pedidos restantes del turno mañana.
        while (i < manana.size()) {
            resultado.add(manana.get(i));
            i++;
        }

        // Incorporamos los pedidos restantes del turno tarde.
        while (j < tarde.size()) {
            resultado.add(tarde.get(j));
            j++;
        }

        return resultado;
    }
}
