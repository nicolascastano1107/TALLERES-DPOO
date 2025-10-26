package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Ingrediente;
import uniandes.dpoo.hamburguesas.mundo.Pedido;
import uniandes.dpoo.hamburguesas.mundo.ProductoAjustado;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class PedidoTest {

    @Test
    void testAgregarProductoYTotales() {
        Pedido p = new Pedido("Fulano", "Calle 123");

        ProductoMenu corral = new ProductoMenu("corral", 14000);
        ProductoMenu papas = new ProductoMenu("papas medianas", 5500);
        ProductoAjustado papasA = new ProductoAjustado(papas);
        papasA.agregarIngrediente(new Ingrediente("salsa", 1000));

        p.agregarProducto(corral);
        p.agregarProducto(papasA);

     
        String factura = p.generarTextoFactura();
        assertTrue(factura.contains("Cliente: Fulano\n"));
        assertTrue(factura.contains("Precio Neto:  20500\n"));
        assertTrue(factura.contains("IVA:          3895\n"));
        assertTrue(factura.contains("Precio Total: 24395\n"));
    }

    @Test
    void testGuardarFactura() throws IOException {
        Pedido p = new Pedido("Ana", "Av 45");
        p.agregarProducto(new ProductoMenu("gaseosa", 5000));

        File tmp = File.createTempFile("factura_", ".txt");
        try {
            p.guardarFactura(tmp);
            byte[] bytes = Files.readAllBytes(Paths.get(tmp.getAbsolutePath()));
            String contenido = new String(bytes, StandardCharsets.UTF_8);
            assertTrue(contenido.startsWith("Cliente: "));
        } finally {
            tmp.delete();
        }
    }

    @Test
    void testIdsIncrementan() {
        Pedido p1 = new Pedido("C1", "D1");
        Pedido p2 = new Pedido("C2", "D2");
        assertEquals(p1.getIdPedido() + 1, p2.getIdPedido());
    }

    @Test
    void testGetNombreCliente() {
        Pedido p = new Pedido("Carlos", "Diag 5");
        assertEquals("Carlos", p.getNombreCliente());
    }
}

