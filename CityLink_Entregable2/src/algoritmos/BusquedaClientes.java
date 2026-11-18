package algoritmos;

import java.util.Collection;
import modelo.Cliente;

/**
 * Contiene algoritmos de búsqueda sobre clientes.
 */
public class BusquedaClientes {

    /**
     * Busca un cliente por nombre recorriendo la colección de a uno
     * (búsqueda lineal), sin distinguir mayúsculas de minúsculas.
     * @param clientes clientes donde buscar
     * @param nombre nombre a buscar
     * @return el cliente encontrado, o null si no existe
     */
    public static Cliente buscarLinealPorNombre(
            Collection<Cliente> clientes, String nombre) {

        if (nombre == null) {
            return null;
        }

        for (Cliente cliente : clientes) {
            if (cliente.getNombre().equalsIgnoreCase(nombre.trim())) {
                return cliente;
            }
        }

        return null;
    }
}