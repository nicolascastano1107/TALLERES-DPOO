package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Combo;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ComboTest {

    private ArrayList<ProductoMenu> buildItems() {
        ArrayList<ProductoMenu> items = new ArrayList<>();
        items.add(new ProductoMenu("corral", 14000));
        items.add(new ProductoMenu("papas medianas", 5500));
        items.add(new ProductoMenu("gaseosa", 5000));
        return items;
    }

    @Test
    void testNombreYPrecioConDescuento10() {
        Combo c = new Combo("corral", 0.10, buildItems());
        assertEquals("corral", c.getNombre());
        assertEquals(22050, c.getPrecio());
    }

    @Test
    void testPrecioConDescuentoDecimal() {
        Combo c = new Combo("especial", 0.095, buildItems());
        assertEquals(22172, c.getPrecio());
    }

    @Test
    void testGenerarTextoFactura() {
        Combo c = new Combo("corral", 0.10, buildItems());
        String factura = c.generarTextoFactura();
        assertTrue(factura.contains("Combo corral\n"));
        assertTrue(factura.contains(" Descuento: 0.1\n"));
        assertTrue(factura.contains("            22050\n"));
    }
}

