package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ProductoMenuTest {

    @Test
    void testGetNombreYPrecio() {
        ProductoMenu p = new ProductoMenu("papas medianas", 5500);
        assertEquals("papas medianas", p.getNombre());
        assertEquals(5500, p.getPrecio());
    }

    @Test
    void testGenerarTextoFactura() {
        ProductoMenu p = new ProductoMenu("corral", 14000);
        String factura = p.generarTextoFactura();
        assertTrue(factura.contains("corral\n"));
        assertTrue(factura.contains("            14000\n"));
    }
}

