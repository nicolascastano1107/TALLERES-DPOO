package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Ingrediente;
import uniandes.dpoo.hamburguesas.mundo.ProductoAjustado;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ProductoAjustadoTest {

    @Test
    void testNombreYPrecioSinModificaciones() {
        ProductoMenu base = new ProductoMenu("corral", 14000);
        ProductoAjustado pa = new ProductoAjustado(base);
        assertEquals("corral", pa.getNombre());
        assertEquals(14000, pa.getPrecio());
    }

    @Test
    void testPrecioConAgregadosYEliminados() {
        ProductoMenu base = new ProductoMenu("corral", 14000);
        ProductoAjustado pa = new ProductoAjustado(base);
        pa.agregarIngrediente(new Ingrediente("queso", 2500));
        pa.agregarIngrediente(new Ingrediente("tomate", 1000));
        pa.eliminarIngrediente(new Ingrediente("cebolla", 1000));
        assertEquals(17500, pa.getPrecio());
    }

    @Test
    void testGenerarTextoFactura() {
        ProductoMenu base = new ProductoMenu("corral", 14000);
        ProductoAjustado pa = new ProductoAjustado(base);
        Ingrediente queso = new Ingrediente("queso", 2500);
        Ingrediente cebolla = new Ingrediente("cebolla", 1000);
        pa.agregarIngrediente(queso);
        pa.eliminarIngrediente(cebolla);

        String factura = pa.generarTextoFactura();
        assertTrue(factura.startsWith("corral\n"));
        assertTrue(factura.contains("    +queso                2500\n"));
        assertTrue(factura.contains("    -cebolla\n"));
        assertTrue(factura.endsWith("            16500\n"));
    }
}

