package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.excepciones.HamburguesaException;
import uniandes.dpoo.hamburguesas.excepciones.NoHayPedidoEnCursoException;
import uniandes.dpoo.hamburguesas.excepciones.ProductoFaltanteException;
import uniandes.dpoo.hamburguesas.excepciones.ProductoRepetidoException;
import uniandes.dpoo.hamburguesas.excepciones.YaHayUnPedidoEnCursoException;
import uniandes.dpoo.hamburguesas.mundo.Pedido;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;
import uniandes.dpoo.hamburguesas.mundo.Restaurante;

public class RestauranteTest {

    private Restaurante restaurante;

    @BeforeEach
    void setUp() {
        restaurante = new Restaurante();
    }

    @Test
    void testCargarInformacionRestauranteOK() throws Exception {
        File ingredientes = new File("data/ingredientes.txt");
        File menu = new File("data/menu.txt");
        File combos = new File("data/combos.txt");
        restaurante.cargarInformacionRestaurante(ingredientes, menu, combos);
        assertEquals(15, restaurante.getIngredientes().size());
        assertEquals(22, restaurante.getMenuBase().size());
        assertEquals(4, restaurante.getMenuCombos().size());
    }

    @Test
    void testIniciarYCerrarPedido() throws Exception {
        File carpeta = new File("./facturas/");
        carpeta.mkdirs();
        for (File f : carpeta.listFiles()) f.delete();

        restaurante.iniciarPedido("Juan", "Calle 1");
        assertNotNull(restaurante.getPedidoEnCurso());

        // Cargar menú e ingredientes para poder agregar un producto válido
        restaurante.cargarInformacionRestaurante(new File("data/ingredientes.txt"), new File("data/menu.txt"), new File("data/combos.txt"));
        ProductoMenu primero = restaurante.getMenuBase().get(0);
        Pedido p = restaurante.getPedidoEnCurso();
        int idPrimero = p.getIdPedido();
        p.agregarProducto(primero);

        restaurante.cerrarYGuardarPedido();
        assertNull(restaurante.getPedidoEnCurso());
        assertEquals(1, restaurante.getPedidos().size());
        assertTrue(new File("./facturas/factura_" + idPrimero + ".txt").exists());

        restaurante.iniciarPedido("Juan2", "Calle 2");
        Pedido p2 = restaurante.getPedidoEnCurso();
        int idSegundo = p2.getIdPedido();
        p2.agregarProducto(new ProductoMenu("agua cristal sin gas", 5000));
        restaurante.cerrarYGuardarPedido();
        assertEquals(2, restaurante.getPedidos().size());
        assertTrue(new File("./facturas/factura_" + idSegundo + ".txt").exists());
    }

    @Test
    void testCerrarPedidoSinPedido() {
        NoHayPedidoEnCursoException e =
            assertThrows(NoHayPedidoEnCursoException.class, () -> restaurante.cerrarYGuardarPedido());
        assertEquals("Actualmente no hay un pedido en curso", e.getMessage());
    }

    @Test
    void testIniciarPedidoConPedidoEnCurso() throws Exception {
        restaurante.iniciarPedido("Ana", "Av 3");
        YaHayUnPedidoEnCursoException e =
            assertThrows(YaHayUnPedidoEnCursoException.class, () -> restaurante.iniciarPedido("Luis", "Calle 9"));
        assertTrue(e.getMessage().startsWith("Ya existe un pedido en curso"));
    }

    @Test
    void testIngredientesRepetidos() throws Exception {
        Path tmpIng = Files.createTempFile("ingredientes_", ".txt");
        Files.write(tmpIng, ("tomate;1000\n" + "tomate;1000\n").getBytes(StandardCharsets.UTF_8));
        HamburguesaException e = assertThrows(HamburguesaException.class,
            () -> restaurante.cargarInformacionRestaurante(tmpIng.toFile(), new File("data/menu.txt"), new File("data/combos.txt")));
        String msg = e.getMessage();
        assertTrue(msg.toLowerCase().contains("ingrediente"));
        assertTrue(msg.contains("tomate"));
        assertTrue(msg.contains("repetido"));
    }

    @Test
    void testProductoRepetidoEnMenu() throws Exception {
        Path tmpMenu = Files.createTempFile("menu_", ".txt");
        Files.write(tmpMenu, "corral;14000\ncorral;14000\n".getBytes(StandardCharsets.UTF_8));
        ProductoRepetidoException e = assertThrows(ProductoRepetidoException.class,
            () -> restaurante.cargarInformacionRestaurante(new File("data/ingredientes.txt"), tmpMenu.toFile(), new File("data/combos.txt")));
        String msg = e.getMessage();
        assertTrue(msg.toLowerCase().contains("producto"));
        assertTrue(msg.contains("corral"));
        assertTrue(msg.contains("repetido"));
    }

    @Test
    void testProductoFaltanteEnCombo() throws Exception {
        Path tmpCombos = Files.createTempFile("combos_", ".txt");
        Files.write(tmpCombos, "combo X;10%;producto_que_no_existe\n".getBytes(StandardCharsets.UTF_8));
        ProductoFaltanteException e = assertThrows(ProductoFaltanteException.class,
            () -> restaurante.cargarInformacionRestaurante(new File("data/ingredientes.txt"), new File("data/menu.txt"), tmpCombos.toFile()));
        String msg = e.getMessage();
        assertTrue(msg.toLowerCase().contains("producto"));
        assertTrue(msg.contains("producto_que_no_existe"));
        assertTrue(msg.toLowerCase().contains("informaci"));
    }

    @Test
    void testComboRepetido() throws Exception {
        Path tmpCombos = Files.createTempFile("combos_rep_", ".txt");
        Files.write(tmpCombos, "combo rep;10%;corral\ncombo rep;7%;corral queso\n".getBytes(StandardCharsets.UTF_8));
        ProductoRepetidoException e = assertThrows(ProductoRepetidoException.class,
            () -> restaurante.cargarInformacionRestaurante(new File("data/ingredientes.txt"), new File("data/menu.txt"), tmpCombos.toFile()));
        String msg = e.getMessage();
        assertTrue(msg.toLowerCase().contains("producto"));
        assertTrue(msg.contains("combo rep"));
        assertTrue(msg.contains("repetido"));
    }

    @Test
    void testArchivosConLineasVacias() throws Exception {
        Path ing = Files.createTempFile("ing_blancos_", ".txt");
        Path men = Files.createTempFile("men_blancos_", ".txt");
        Path com = Files.createTempFile("com_blancos_", ".txt");
        Files.write(ing, "\n\n".getBytes(StandardCharsets.UTF_8));
        Files.write(men, "\n\n".getBytes(StandardCharsets.UTF_8));
        Files.write(com, "\n\n".getBytes(StandardCharsets.UTF_8));
        restaurante.cargarInformacionRestaurante(ing.toFile(), men.toFile(), com.toFile());
        assertEquals(0, restaurante.getIngredientes().size());
        assertEquals(0, restaurante.getMenuBase().size());
        assertEquals(0, restaurante.getMenuCombos().size());
    }

    @Test
    void testComboSinItems() throws Exception {
        Path ing = Files.createTempFile("ing_vacio_", ".txt");
        Path men = Files.createTempFile("men_vacio_", ".txt");
        Path com = Files.createTempFile("com_sin_items_", ".txt");
        Files.write(ing, new byte[0]);
        Files.write(men, new byte[0]);
        Files.write(com, "combo raro;5%\n".getBytes(StandardCharsets.UTF_8));
        restaurante.cargarInformacionRestaurante(ing.toFile(), men.toFile(), com.toFile());
        assertEquals(1, restaurante.getMenuCombos().size());
        assertEquals("combo raro", restaurante.getMenuCombos().get(0).getNombre());
    }

    @Test
    void testCreaCarpetaFacturasSiNoExiste() throws Exception {
        File carpeta = new File("./facturas/");
        carpeta.mkdirs();
        for (File f : carpeta.listFiles()) f.delete();
        carpeta.delete();
        assertFalse(carpeta.exists());

        restaurante.iniciarPedido("Carlos", "Calle 10");
        Pedido p = restaurante.getPedidoEnCurso();
        int id = p.getIdPedido();
        p.agregarProducto(new ProductoMenu("dummy", 100));
        restaurante.cerrarYGuardarPedido();

        assertTrue(carpeta.exists());
        assertTrue(new File("./facturas/factura_" + id + ".txt").exists());
    }

    @Test
    void testNumeroInvalidoEnIngredientes() throws Exception {
        Path tmpIng = Files.createTempFile("ing_inval_", ".txt");
        Path tmpMenu = Files.createTempFile("men_ok_", ".txt");
        Path tmpComb = Files.createTempFile("com_ok_", ".txt");
        Files.write(tmpIng, "tomate;abc\n".getBytes(StandardCharsets.UTF_8));
        Files.write(tmpMenu, new byte[0]);
        Files.write(tmpComb, new byte[0]);
        assertThrows(NumberFormatException.class,
            () -> restaurante.cargarInformacionRestaurante(tmpIng.toFile(), tmpMenu.toFile(), tmpComb.toFile()));
    }

    @Test
    void testNumeroInvalidoEnMenu() throws Exception {
        Path tmpIng = Files.createTempFile("ing_ok_", ".txt");
        Path tmpMenu = Files.createTempFile("men_inval_", ".txt");
        Path tmpComb = Files.createTempFile("com_ok_", ".txt");
        Files.write(tmpIng, new byte[0]);
        Files.write(tmpMenu, "corral;abc\n".getBytes(StandardCharsets.UTF_8));
        Files.write(tmpComb, new byte[0]);
        assertThrows(NumberFormatException.class,
            () -> restaurante.cargarInformacionRestaurante(tmpIng.toFile(), tmpMenu.toFile(), tmpComb.toFile()));
    }

    @Test
    void testDescuentoInvalidoEnCombo() throws Exception {
        Path tmpIng = Files.createTempFile("ing_ok_", ".txt");
        Path tmpMenu = Files.createTempFile("men_ok_", ".txt");
        Path tmpComb = Files.createTempFile("com_inval_", ".txt");
        Files.write(tmpIng, new byte[0]);
        Files.write(tmpMenu, new byte[0]);
        Files.write(tmpComb, "combo Y;xx%;corral\n".getBytes(StandardCharsets.UTF_8));
        assertThrows(NumberFormatException.class,
            () -> restaurante.cargarInformacionRestaurante(tmpIng.toFile(), tmpMenu.toFile(), tmpComb.toFile()));
    }
}


