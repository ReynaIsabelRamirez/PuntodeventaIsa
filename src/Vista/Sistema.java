
package Vista;

import Modelo.CajaDAO;
import Modelo.Cajas;
import Modelo.Cambio;
import Modelo.Categoria;
import Modelo.CategoriaDAO;
import Modelo.Conexion;
import Modelo.Config;
import Modelo.Detalle;
import Modelo.Eventos;
import Modelo.Ingreso;
import Modelo.MoBi;
import Modelo.Productos;
import Modelo.ProductosDAO;
import Modelo.Proveedor;
import Modelo.ProveedorDAO;
import Modelo.Venta;
import Modelo.VentaDAO;
import Reportes.Excel;
import Reportes.Grafico;
import Reportes.Graficod;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class Sistema extends javax.swing.JFrame {

    Date fechaVenta = new Date();
    String fechaActual = new SimpleDateFormat("dd/MM/yyyy").format(fechaVenta);
    MoBi mo = new MoBi();
    Cajas caja = new Cajas();
    CajaDAO caj = new CajaDAO();
    Proveedor pr = new Proveedor();
    ProveedorDAO PrDao = new ProveedorDAO();
    Categoria cat = new Categoria();
    CategoriaDAO catDao = new CategoriaDAO();
    Productos pro = new Productos();
    ProductosDAO proDao = new ProductosDAO();
    Venta v = new Venta();
    VentaDAO Vdao = new VentaDAO();
    Detalle Dv = new Detalle();
    Cambio cam = new Cambio();
    Config conf = new Config();
    Eventos event = new Eventos();
    DefaultTableModel modelo = new DefaultTableModel();
    DefaultTableModel tmp = new DefaultTableModel();
    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;

    int item;
    double Totalpagar = 0.00;
    double TotalVentas = 0.00;

    public Sistema() {
        initComponents();
        this.setLocationRelativeTo(null);
        txtIdPro.setVisible(false);
        txtIdVenta.setVisible(false);
        txtIdpro.setVisible(false);
        txtIdProveedor.setVisible(false);
        txtIdCat.setVisible(false);
        txtIdConfig.setVisible(false);
        txtCambio.setVisible(false);
        AutoCompleteDecorator.decorate(cbxProveedorPro);
        proDao.ConsularProveedor(cbxProveedorPro);
        AutoCompleteDecorator.decorate(cbxCategoriaCat);
        proDao.ConsularCategoria(cbxCategoriaCat);
        txtIdConfig.setVisible(false);
        ListarConfig();
        // MenuProduc.setEnabled(false);

    }

    public Sistema(Ingreso priv) {
        initComponents();
        this.setLocationRelativeTo(null);
        txtIdPro.setVisible(false);
        txtIdVenta.setVisible(false);
        txtIdpro.setVisible(false);
        txtIdProveedor.setVisible(false);
        txtIdCat.setVisible(false);
        txtIdConfig.setVisible(false);
        txtCambio.setVisible(false);
        AutoCompleteDecorator.decorate(cbxProveedorPro);
        proDao.ConsularProveedor(cbxProveedorPro);
        AutoCompleteDecorator.decorate(cbxCategoriaCat);
        proDao.ConsularCategoria(cbxCategoriaCat);
        txtIdConfig.setVisible(false);
        ListarConfig();

        if (priv.getRol().equals("Empleado")) {
            MenuProve.setEnabled(false);
            MenuVentas.setEnabled(false);
            MenuMarca.setEnabled(false);
            MenuConfig.setEnabled(false);
            btnEditarPro.setEnabled(false);
            LabelVendedor.setText(priv.getNombre());
        } else {
            LabelVendedor.setText(priv.getNombre());
            //   btnCaja.setEnabled(false);
        }

    }

    public void ListarProveedor() {
        List<Proveedor> ListarPr = PrDao.ListarProveedor();
        modelo = (DefaultTableModel) TableProveedor.getModel();
        Object[] ob = new Object[5];
        for (int i = 0; i < ListarPr.size(); i++) {
            ob[0] = ListarPr.get(i).getId();
            ob[1] = ListarPr.get(i).getRuc();
            ob[2] = ListarPr.get(i).getNombre();
            ob[3] = ListarPr.get(i).getTelefono();
            ob[4] = ListarPr.get(i).getDireccion();

            modelo.addRow(ob);
        }
        TableProveedor.setModel(modelo);

    }

    public void ListarCategoria() {
        List<Categoria> Listarcat = catDao.ListarCategoria();
        modelo = (DefaultTableModel) TableCat.getModel();
        Object[] ob = new Object[3];
        for (int i = 0; i < Listarcat.size(); i++) {
            ob[0] = Listarcat.get(i).getId();
            ob[1] = Listarcat.get(i).getClave();
            ob[2] = Listarcat.get(i).getNombre();

            modelo.addRow(ob);
        }
        TableCat.setModel(modelo);

    }

    public void ListarProductos() {
        List<Productos> ListarPro = proDao.ListarProductos();
        modelo = (DefaultTableModel) TableProductos.getModel();
        Object[] ob = new Object[7];
        for (int i = 0; i < ListarPro.size(); i++) {
            ob[0] = ListarPro.get(i).getId();
            ob[1] = ListarPro.get(i).getCodigo();
            ob[2] = ListarPro.get(i).getNombre();
            ob[3] = ListarPro.get(i).getProveedor();
            ob[4] = ListarPro.get(i).getCategoria();
            ob[5] = ListarPro.get(i).getStock();
            ob[6] = ListarPro.get(i).getPrecioventa();
            modelo.addRow(ob);
        }
        TableProductos.setModel(modelo);

    }

    public void ListarConfig() {
        conf = proDao.BuscarDatos();
        txtIdConfig.setText("" + conf.getId());
        txtRucConfig.setText("" + conf.getRuc());
        txtNombreConfig.setText("" + conf.getNombre());
        txtTelefonoConfig.setText("" + conf.getTelefono());
        txtDireccionConfig.setText("" + conf.getDireccion());
        txtRazonConfig.setText("" + conf.getMensaje());
    }

    public void ListarVentas() {
        List<Venta> ListarVenta = Vdao.ListarVentas();
        modelo = (DefaultTableModel) TableVentas.getModel();
        Object[] ob = new Object[4];
        for (int i = 0; i < ListarVenta.size(); i++) {
            ob[0] = ListarVenta.get(i).getId();
            ob[1] = ListarVenta.get(i).getVendedor();
            ob[2] = ListarVenta.get(i).getTotal();
            ob[3] = ListarVenta.get(i).getFecha();
            modelo.addRow(ob);
        }
        TableVentas.setModel(modelo);

    }

    public void ListarCaja() {
        List<Cajas> ListarCaja = caj.ListarCaja();
        modelo = (DefaultTableModel) TableCaja.getModel();
        Object[] ob = new Object[3];
        for (int i = 0; i < ListarCaja.size(); i++) {
            ob[0] = ListarCaja.get(i).getId();
            ob[1] = ListarCaja.get(i).getTotalfin();
            ob[2] = ListarCaja.get(i).getFecha();

            modelo.addRow(ob);
        }
        TableCaja.setModel(modelo);

    }

    public void LimpiarTable() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }

    public void buscarFecha(String buscar) {
        logico logica = new logico();

        DefaultTableModel modelo = logica.buscarFecha(buscar);

        TableVentas.setModel(modelo);

    }

    public void buscarFechaCaja(String buscar) {
        logico logica = new logico();

        DefaultTableModel modelo = logica.buscarFechaCaja(buscar);

        TableCaja.setModel(modelo);

    }

    public void buscarProducto(String buscar) {
        logico logica = new logico();

        DefaultTableModel modelo = logica.buscarProducto(buscar);

        TableProductos.setModel(modelo);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane5 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        txtAbrir = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        LabelVendedor = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtCodigoPro = new javax.swing.JTextField();
        txtDesPro = new javax.swing.JTextField();
        txtCantPro = new javax.swing.JTextField();
        cbxProveedorPro = new javax.swing.JComboBox<>();
        txtIdpro = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        txtPrecioVentaPro = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        cbxCategoriaCat = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        TableProductos = new javax.swing.JTable();
        btnGuardarPro = new javax.swing.JButton();
        btnEditarPro = new javax.swing.JButton();
        btnNuevoPro = new javax.swing.JButton();
        btnEliminarPro = new javax.swing.JButton();
        btnExcelPro = new javax.swing.JButton();
        txtBuscarProducto = new javax.swing.JTextField();
        jLabel53 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TableProveedor = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        txtRucProveedor = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtNombreProveedor = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtTelefonoProveedor = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtDireccionProveedor = new javax.swing.JTextField();
        txtIdProveedor = new javax.swing.JTextField();
        btnEditarProveedor = new javax.swing.JButton();
        btnNuevoProveedor = new javax.swing.JButton();
        btnEliminarProveedor = new javax.swing.JButton();
        btnGuardarProveedor = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtCodigoVenta = new javax.swing.JTextField();
        txtDescripcionVenta = new javax.swing.JTextField();
        txtCantidadVenta = new javax.swing.JTextField();
        btnEliminarVenta = new javax.swing.JButton();
        txtPrecioVenta = new javax.swing.JTextField();
        txtStockDisponible = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableVenta = new javax.swing.JTable();
        btnGenerarVenta = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        LabelTotal = new javax.swing.JLabel();
        txtIdPro = new javax.swing.JTextField();
        txtCantidad = new javax.swing.JTextField();
        txtCambio = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnGraficar = new javax.swing.JButton();
        Midate = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        TableVentas = new javax.swing.JTable();
        btnPdfVentas = new javax.swing.JButton();
        txtIdVenta = new javax.swing.JTextField();
        txtBuscar = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        txtMonedaDiez = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        txtMonedaCinco = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        txtMonedaDos = new javax.swing.JTextField();
        txtMonedaUno = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        txtMonedaCentavos = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        txtTotaldelDia = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        TableCaja = new javax.swing.JTable();
        datecaja = new com.toedter.calendar.JDateChooser();
        btnGraficaCaja = new javax.swing.JButton();
        jLabel47 = new javax.swing.JLabel();
        btnEliminarCaja = new javax.swing.JButton();
        jLabel49 = new javax.swing.JLabel();
        txtBuscarCaja = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel31 = new javax.swing.JLabel();
        CajaInicios = new javax.swing.JTextField();
        txtIdCaja = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        txtBilleteMil = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        txtBilleteDoci = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        txtBilleteCien = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        txtBilleteQuinientos = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        txtBilleteCincuenta = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        txtBilleteVeinte = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        btnGuardarCaja = new javax.swing.JButton();
        txtTotalCaja = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        txtNombreCat = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtClaveCat = new javax.swing.JTextField();
        txtIdCat = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        TableCat = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        btnGuardarCat = new javax.swing.JButton();
        btnNuevoCat = new javax.swing.JButton();
        btnEditarCat = new javax.swing.JButton();
        btnEliminarCat = new javax.swing.JButton();
        jLabel51 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txtRucConfig = new javax.swing.JTextField();
        txtNombreConfig = new javax.swing.JTextField();
        txtTelefonoConfig = new javax.swing.JTextField();
        txtRazonConfig = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        txtDireccionConfig = new javax.swing.JTextField();
        btnActualizarConfig = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        txtIdConfig = new javax.swing.JTextField();
        jPanel17 = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        MenuProduc = new javax.swing.JMenu();
        MenuProve = new javax.swing.JMenu();
        MenuNueva = new javax.swing.JMenu();
        MenuVentas = new javax.swing.JMenu();
        MenuCaja = new javax.swing.JMenu();
        MenuMarca = new javax.swing.JMenu();
        MenuConfig = new javax.swing.JMenu();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Punto de Venta.");
        setBackground(new java.awt.Color(153, 153, 255));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        txtAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAbrirActionPerformed(evt);
            }
        });
        txtAbrir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtAbrirKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAbrirKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAbrirKeyTyped(evt);
            }
        });

        jLabel52.setFont(new java.awt.Font("Microsoft YaHei UI Light", 1, 14)); // NOI18N
        jLabel52.setText("SE INICIA CON $");

        LabelVendedor.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        LabelVendedor.setForeground(new java.awt.Color(255, 255, 255));
        LabelVendedor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel59.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ima/logoem.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(LabelVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(186, 186, 186)
                .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 266, Short.MAX_VALUE)
                .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(txtAbrir, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(69, 69, 69))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addComponent(LabelVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtAbrir, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel52))))
                        .addGap(0, 11, Short.MAX_VALUE))
                    .addComponent(jLabel59, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1260, 100));

        jTabbedPane1.setBackground(new java.awt.Color(153, 153, 255));
        jTabbedPane1.setForeground(new java.awt.Color(51, 0, 51));
        jTabbedPane1.setToolTipText("");

        jPanel5.setBackground(new java.awt.Color(204, 204, 204));

        jPanel9.setBackground(new java.awt.Color(204, 204, 204));
        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("Código");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("Descripción");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setText("Cantidad");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setText("Proveedor");

        txtCodigoPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoProActionPerformed(evt);
            }
        });
        txtCodigoPro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoProKeyTyped(evt);
            }
        });

        txtDesPro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDesProKeyTyped(evt);
            }
        });

        txtCantPro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantProKeyTyped(evt);
            }
        });

        cbxProveedorPro.setEditable(true);
        cbxProveedorPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxProveedorProActionPerformed(evt);
            }
        });
        cbxProveedorPro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                cbxProveedorProKeyTyped(evt);
            }
        });

        txtIdpro.setEditable(false);
        txtIdpro.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtIdpro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdproActionPerformed(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel27.setText("Precio Venta");

        txtPrecioVentaPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioVentaProActionPerformed(evt);
            }
        });
        txtPrecioVentaPro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioVentaProKeyTyped(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel28.setText("Marca");

        cbxCategoriaCat.setEditable(true);
        cbxCategoriaCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxCategoriaCatActionPerformed(evt);
            }
        });
        cbxCategoriaCat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                cbxCategoriaCatKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbxCategoriaCat, 0, 186, Short.MAX_VALUE)
                            .addComponent(cbxProveedorPro, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13)
                            .addComponent(jLabel27)
                            .addComponent(jLabel11))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(txtCodigoPro, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                                .addComponent(txtIdpro, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtDesPro)
                                    .addGroup(jPanel9Layout.createSequentialGroup()
                                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtCantPro, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtPrecioVentaPro, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())))))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtCodigoPro, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIdpro, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtDesPro, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtCantPro, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(txtPrecioVentaPro, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(cbxProveedorPro, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(64, 64, 64)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel28)
                    .addComponent(cbxCategoriaCat, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(55, Short.MAX_VALUE))
        );

        TableProductos.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        TableProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CÓDIGO", "DESCRIPCIÓN", "PROVEEDOR", "MARCA", "STOCK", "PRECIO VENTA"
            }
        ));
        TableProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableProductosMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(TableProductos);
        if (TableProductos.getColumnModel().getColumnCount() > 0) {
            TableProductos.getColumnModel().getColumn(0).setPreferredWidth(20);
            TableProductos.getColumnModel().getColumn(1).setPreferredWidth(50);
            TableProductos.getColumnModel().getColumn(2).setPreferredWidth(100);
            TableProductos.getColumnModel().getColumn(3).setResizable(false);
            TableProductos.getColumnModel().getColumn(3).setPreferredWidth(60);
            TableProductos.getColumnModel().getColumn(5).setPreferredWidth(40);
            TableProductos.getColumnModel().getColumn(6).setPreferredWidth(50);
        }

        btnGuardarPro.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnGuardarPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ima/guardar2.png"))); // NOI18N
        btnGuardarPro.setText("GUARDAR");
        btnGuardarPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarProActionPerformed(evt);
            }
        });

        btnEditarPro.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnEditarPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ima/actualizado.png"))); // NOI18N
        btnEditarPro.setText("MODIFICAR");
        btnEditarPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarProActionPerformed(evt);
            }
        });

        btnNuevoPro.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnNuevoPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ima/nuevo2.png"))); // NOI18N
        btnNuevoPro.setText("NUEVO");
        btnNuevoPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoProActionPerformed(evt);
            }
        });

        btnEliminarPro.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnEliminarPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ima/eliminar.png"))); // NOI18N
        btnEliminarPro.setText("ELIMINAR");
        btnEliminarPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProActionPerformed(evt);
            }
        });

        btnExcelPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ima/excel.png"))); // NOI18N
        btnExcelPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcelProActionPerformed(evt);
            }
        });

        txtBuscarProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarProductoKeyReleased(evt);
            }
        });

        jLabel53.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ima/buscar.png"))); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnEliminarPro)
                                    .addComponent(btnGuardarPro))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnNuevoPro)
                                    .addComponent(btnEditarPro))
                                .addGap(189, 189, 189)
                                .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtBuscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 756, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(56, 56, 56))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(btnExcelPro, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(89, 89, 89))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnGuardarPro)
                                    .addComponent(btnNuevoPro))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnEliminarPro)
                                    .addComponent(btnEditarPro))
                                .addGap(35, 35, 35))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(67, 67, 67)
                                .addComponent(txtBuscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel53, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExcelPro, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(142, 142, 142))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jTabbedPane1.addTab("Productos", jPanel5);

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));

        TableProveedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "RUC", "NOMBRE", "TELEFONO", "DIRECCIÓN"
            }
        ));
        TableProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableProveedorMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(TableProveedor);
        if (TableProveedor.getColumnModel().getColumnCount() > 0) {
            TableProveedor.getColumnModel().getColumn(0).setPreferredWidth(10);
            TableProveedor.getColumnModel().getColumn(1).setPreferredWidth(20);
            TableProveedor.getColumnModel().getColumn(2).setPreferredWidth(100);
            TableProveedor.getColumnModel().getColumn(3).setPreferredWidth(50);
            TableProveedor.getColumnModel().getColumn(4).setPreferredWidth(80);
        }

        jPanel8.setBackground(new java.awt.Color(204, 204, 204));
        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setText("RUC:");

        txtRucProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRucProveedorActionPerformed(evt);
            }
        });
        txtRucProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRucProveedorKeyTyped(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setText("Nombre");

        txtNombreProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreProveedorKeyTyped(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setText("Telefono");

        txtTelefonoProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoProveedorKeyTyped(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setText("Dirección");

        txtDireccionProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDireccionProveedorKeyTyped(evt);
            }
        });

        txtIdProveedor.setEditable(false);
        txtIdProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18))
                .addGap(24, 24, 24)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDireccionProveedor)
                    .addComponent(txtTelefonoProveedor, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNombreProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(txtRucProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtIdProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 126, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtRucProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(txtIdProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombreProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTelefonoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDireccionProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        btnEditarProveedor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnEditarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ima/actualizado.png"))); // NOI18N
        btnEditarProveedor.setText("MODIFICAR");
        btnEditarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarProveedorActionPerformed(evt);
            }
        });

        btnNuevoProveedor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnNuevoProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ima/nuevo2.png"))); // NOI18N
        btnNuevoProveedor.setText("NUEVO");
        btnNuevoProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoProveedorActionPerformed(evt);
            }
        });

        btnEliminarProveedor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnEliminarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ima/eliminar.png"))); // NOI18N
        btnEliminarProveedor.setText("ELIMINAR");
        btnEliminarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProveedorActionPerformed(evt);
            }
        });

        btnGuardarProveedor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnGuardarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ima/guardar2.png"))); // NOI18N
        btnGuardarProveedor.setText("GUARDAR");
        btnGuardarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarProveedorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(btnEditarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnEliminarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(btnNuevoProveedor)
                                .addGap(52, 52, 52)
                                .addComponent(btnGuardarProveedor)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 792, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(376, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnNuevoProveedor)
                            .addComponent(btnGuardarProveedor))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnEditarProveedor)
                            .addComponent(btnEliminarProveedor))))
                .addGap(122, 288, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Proveedor", jPanel4);

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Código");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Descripción");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Cantidad");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Precio");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Stock disponible");

        txtCodigoVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoVentaActionPerformed(evt);
            }
        });
        txtCodigoVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoVentaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoVentaKeyTyped(evt);
            }
        });

        txtDescripcionVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripcionVentaActionPerformed(evt);
            }
        });
        txtDescripcionVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcionVentaKeyTyped(evt);
            }
        });

        txtCantidadVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadVentaActionPerformed(evt);
            }
        });
        txtCantidadVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCantidadVentaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadVentaKeyTyped(evt);
            }
        });

        btnEliminarVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ima/eliminar.png"))); // NOI18N
        btnEliminarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarVentaActionPerformed(evt);
            }
        });

        txtPrecioVenta.setEditable(false);
        txtPrecioVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioVentaActionPerformed(evt);
            }
        });
        txtPrecioVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioVentaKeyTyped(evt);
            }
        });

        txtStockDisponible.setEditable(false);
        txtStockDisponible.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStockDisponibleActionPerformed(evt);
            }
        });
        txtStockDisponible.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtStockDisponibleKeyTyped(evt);
            }
        });

        TableVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CÓDIGO", "DESCRIPCIÓN", "CANTIDAD", "PRECIO", "TOTAL"
            }
        ));
        jScrollPane1.setViewportView(TableVenta);
        if (TableVenta.getColumnModel().getColumnCount() > 0) {
            TableVenta.getColumnModel().getColumn(0).setPreferredWidth(30);
            TableVenta.getColumnModel().getColumn(1).setPreferredWidth(100);
            TableVenta.getColumnModel().getColumn(2).setPreferredWidth(30);
            TableVenta.getColumnModel().getColumn(3).setPreferredWidth(30);
            TableVenta.getColumnModel().getColumn(4).setPreferredWidth(40);
        }

        btnGenerarVenta.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnGenerarVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ima/vendedor.png"))); // NOI18N
        btnGenerarVenta.setText("GENERAR VENTA");
        btnGenerarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarVentaActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("TOTAL A PAGAR ");

        LabelTotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        LabelTotal.setText("------");

        txtIdPro.setEditable(false);
        txtIdPro.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        txtCantidad.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadActionPerformed(evt);
            }
        });
        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadKeyTyped(evt);
            }
        });

        txtCambio.setEditable(false);
        txtCambio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCambioActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ima/pago.png"))); // NOI18N
        jLabel1.setText("PAGO CON ");

        btnGraficar.setBackground(new java.awt.Color(204, 255, 255));
        btnGraficar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ima/barra-grafica.png"))); // NOI18N
        btnGraficar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGraficarActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Seleccionar");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCodigoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(jLabel2)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(72, 72, 72)
                                .addComponent(txtDescripcionVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 403, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3)
                                .addGap(111, 111, 111)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCantidadVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(80, 80, 80)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(txtPrecioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(97, 97, 97)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(txtStockDisponible, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(83, 83, 83)
                                .addComponent(txtIdPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(104, 104, 104)))
                        .addComponent(btnGraficar)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(58, 58, 58))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(Midate, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1097, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtCambio, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(45, 45, 45)
                                .addComponent(btnGenerarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(68, 68, 68)
                                .addComponent(jLabel9)
                                .addGap(36, 36, 36)
                                .addComponent(LabelTotal)
                                .addGap(46, 46, 46)
                                .addComponent(btnEliminarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(41, 41, 41)))
                        .addGap(58, 58, 58))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addGap(17, 17, 17)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtCodigoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDescripcionVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCantidadVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtStockDisponible, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPrecioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(txtIdPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(28, 28, 28))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnGraficar, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Midate, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(29, 29, 29)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(txtCambio, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnEliminarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                            .addGap(10, 10, 10)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(LabelTotal)
                                .addComponent(jLabel9)
                                .addComponent(btnGenerarVenta))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(213, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Nueva Venta", jPanel3);

        jPanel6.setBackground(new java.awt.Color(204, 204, 204));

        TableVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "VENDEDOR", "TOTAL", "FECHA"
            }
        ));
        TableVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableVentasMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(TableVentas);
        if (TableVentas.getColumnModel().getColumnCount() > 0) {
            TableVentas.getColumnModel().getColumn(0).setPreferredWidth(20);
            TableVentas.getColumnModel().getColumn(1).setPreferredWidth(60);
            TableVentas.getColumnModel().getColumn(2).setPreferredWidth(60);
        }

        btnPdfVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/pdf.png"))); // NOI18N
        btnPdfVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPdfVentasActionPerformed(evt);
            }
        });

        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ima/buscar.png"))); // NOI18N

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("DIA/MES/AÑO");

        jLabel30.setText("BUSCAR POR FECHA DE VENTA");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(jLabel30))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1017, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnPdfVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(116, 116, 116)
                                .addComponent(txtIdVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(424, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14)))
                    .addComponent(txtIdVenta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPdfVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(215, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Ventas", jPanel6);

        jPanel11.setBackground(new java.awt.Color(204, 204, 204));

        jPanel12.setBackground(new java.awt.Color(204, 204, 255));
        jPanel12.setForeground(new java.awt.Color(204, 204, 204));

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ima/monedas.png"))); // NOI18N
        jLabel34.setText("Monedas");

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel41.setText("$10");

        txtMonedaDiez.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMonedaDiezKeyTyped(evt);
            }
        });

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel42.setText("$5");

        txtMonedaCinco.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMonedaCincoKeyTyped(evt);
            }
        });

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel43.setText("$2");

        txtMonedaDos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMonedaDosKeyTyped(evt);
            }
        });

        txtMonedaUno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMonedaUnoActionPerformed(evt);
            }
        });
        txtMonedaUno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMonedaUnoKeyTyped(evt);
            }
        });

        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel44.setText("$1");

        txtMonedaCentavos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMonedaCentavosActionPerformed(evt);
            }
        });
        txtMonedaCentavos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMonedaCentavosKeyTyped(evt);
            }
        });

        jLabel45.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel45.setText("$0.50");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel43))
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtMonedaDiez, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMonedaDos, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel44)))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                        .addComponent(jLabel45)
                        .addGap(18, 18, 18)
                        .addComponent(txtMonedaCentavos, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)))
                .addGap(19, 19, 19)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMonedaUno, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMonedaCinco, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(57, 57, 57))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel41)
                            .addComponent(txtMonedaDiez, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel42)
                            .addComponent(txtMonedaCinco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(jLabel43))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel44)
                                    .addComponent(txtMonedaUno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtMonedaDos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(21, 21, 21)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMonedaCentavos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(43, Short.MAX_VALUE))
        );

        jPanel13.setBackground(new java.awt.Color(204, 204, 204));

        jLabel48.setText("VENTAS TOTAL DEL DIA     $");

        txtTotaldelDia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotaldelDiaActionPerformed(evt);
            }
        });
        txtTotaldelDia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTotaldelDiaKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTotaldelDia, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(114, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(txtTotaldelDia, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        TableCaja.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "GANACIA ", "FECHA"
            }
        ));
        TableCaja.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableCajaMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(TableCaja);

        btnGraficaCaja.setBackground(new java.awt.Color(255, 255, 255));
        btnGraficaCaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ima/barra-grafica.png"))); // NOI18N
        btnGraficaCaja.setBorderPainted(false);
        btnGraficaCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGraficaCajaActionPerformed(evt);
            }
        });

        jLabel47.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(255, 255, 255));
        jLabel47.setText("Seleccionar");

        btnEliminarCaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ima/eliminar.png"))); // NOI18N
        btnEliminarCaja.setText("ELIMINAR");
        btnEliminarCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarCajaActionPerformed(evt);
            }
        });

        jLabel49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ima/buscar.png"))); // NOI18N

        txtBuscarCaja.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarCajaKeyReleased(evt);
            }
        });

        jLabel50.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(255, 255, 255));
        jLabel50.setText("BUSCAR GANANCIAS POR FECHA ");

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel31.setText("Inicio     $");

        CajaInicios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CajaIniciosActionPerformed(evt);
            }
        });
        CajaInicios.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                CajaIniciosKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                CajaIniciosKeyTyped(evt);
            }
        });

        txtIdCaja.setEditable(false);

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ima/icons8-billetes-32.png"))); // NOI18N
        jLabel33.setText("Billetes");

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel35.setText("$1000");

        txtBilleteMil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBilleteMilActionPerformed(evt);
            }
        });
        txtBilleteMil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBilleteMilKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBilleteMilKeyTyped(evt);
            }
        });

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel37.setText("$200");

        txtBilleteDoci.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBilleteDociKeyTyped(evt);
            }
        });

        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel38.setText("$100");

        txtBilleteCien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBilleteCienKeyTyped(evt);
            }
        });

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel36.setText("$500");

        txtBilleteQuinientos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBilleteQuinientosActionPerformed(evt);
            }
        });
        txtBilleteQuinientos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBilleteQuinientosKeyTyped(evt);
            }
        });

        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel39.setText("$50");

        txtBilleteCincuenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBilleteCincuentaKeyTyped(evt);
            }
        });

        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel40.setText("$20");

        txtBilleteVeinte.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBilleteVeinteKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtBilleteMil, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBilleteQuinientos, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtBilleteDoci, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtBilleteCincuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(35, 35, 35)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtBilleteCien, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtBilleteVeinte, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel33)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(txtBilleteMil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37)
                    .addComponent(txtBilleteDoci, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel38)
                    .addComponent(txtBilleteCien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(txtBilleteQuinientos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39)
                    .addComponent(txtBilleteCincuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40)
                    .addComponent(txtBilleteVeinte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel32.setText("Cierre de caja");

        btnGuardarCaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ima/guardar2.png"))); // NOI18N
        btnGuardarCaja.setText("GUARDAR");
        btnGuardarCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarCajaActionPerformed(evt);
            }
        });

        txtTotalCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalCajaActionPerformed(evt);
            }
        });
        txtTotalCaja.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTotalCajaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTotalCajaKeyTyped(evt);
            }
        });

        jLabel46.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel46.setText("TOTAL    $");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel31)
                                .addGap(28, 28, 28)
                                .addComponent(CajaInicios, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(107, 107, 107)
                                .addComponent(txtIdCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(52, 52, 52)
                                .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(90, 90, 90))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 509, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGap(187, 187, 187)
                                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel49)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtBuscarCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel50))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnGraficaCaja)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(datecaja, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23))))
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 259, Short.MAX_VALUE)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 502, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(btnGuardarCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel11Layout.createSequentialGroup()
                        .addGap(463, 463, 463)
                        .addComponent(txtTotalCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(btnEliminarCaja)))
                .addContainerGap(195, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(txtIdCaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2))
                            .addComponent(CajaInicios))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnGraficaCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel11Layout.createSequentialGroup()
                                    .addComponent(jLabel50)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtBuscarCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                                    .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(datecaja, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel49, javax.swing.GroupLayout.Alignment.TRAILING)))))
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel46)
                                .addGap(37, 37, 37)
                                .addComponent(txtTotalCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(81, 81, 81))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(btnEliminarCaja)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGuardarCaja)
                        .addGap(103, 103, 103))))
        );

        jTabbedPane1.addTab("Caja", jPanel11);

        jPanel10.setBackground(new java.awt.Color(204, 204, 204));

        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder("Marca"));

        jLabel26.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel26.setText("NOMBRE");

        txtNombreCat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreCatKeyTyped(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("CLAVE");

        txtClaveCat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtClaveCatKeyTyped(evt);
            }
        });

        txtIdCat.setEditable(false);
        txtIdCat.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGap(0, 10, Short.MAX_VALUE)
                        .addComponent(txtNombreCat, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(txtClaveCat, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtIdCat, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25))))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtClaveCat, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIdCat, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(txtNombreCat, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        TableCat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Clave", "Nombre"
            }
        ));
        TableCat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableCatMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(TableCat);

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ima/xiaomi.png"))); // NOI18N

        btnGuardarCat.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnGuardarCat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ima/guardar2.png"))); // NOI18N
        btnGuardarCat.setText("GUARDAR");
        btnGuardarCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarCatActionPerformed(evt);
            }
        });

        btnNuevoCat.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnNuevoCat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ima/nuevo2.png"))); // NOI18N
        btnNuevoCat.setText("NUEVO");
        btnNuevoCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoCatActionPerformed(evt);
            }
        });

        btnEditarCat.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnEditarCat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ima/actualizado.png"))); // NOI18N
        btnEditarCat.setText("MODIFICAR");
        btnEditarCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarCatActionPerformed(evt);
            }
        });

        btnEliminarCat.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnEliminarCat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ima/eliminar.png"))); // NOI18N
        btnEliminarCat.setText("ELIMINAR");
        btnEliminarCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarCatActionPerformed(evt);
            }
        });

        jLabel51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ima/samsung_1.png"))); // NOI18N

        jLabel55.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ima/motorola.png"))); // NOI18N

        jLabel56.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ima/huawei.png"))); // NOI18N

        jLabel57.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ima/logotipo-de-apple.png"))); // NOI18N

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(btnGuardarCat, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(btnNuevoCat, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(154, 154, 154)
                        .addComponent(btnEditarCat)
                        .addGap(101, 101, 101)
                        .addComponent(btnEliminarCat, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 764, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(464, Short.MAX_VALUE))
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(92, 92, 92)
                .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(122, 122, 122)
                .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(115, 115, 115)
                .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel57))
                        .addGap(18, 18, 18))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel7))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(28, 28, 28)))
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(btnGuardarCat)
                        .addGap(2, 2, 2)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnEliminarCat, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEditarCat)))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnNuevoCat)))
                .addContainerGap(191, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Modelo", jPanel10);

        jPanel7.setBackground(new java.awt.Color(204, 204, 204));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setText("RUC");

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setText("NOMBRE DE LA EMPRESA");

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel22.setText("TELEFONO");

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel23.setText("DIRECCIÓN");

        txtRucConfig.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRucConfigKeyTyped(evt);
            }
        });

        txtNombreConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreConfigActionPerformed(evt);
            }
        });
        txtNombreConfig.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreConfigKeyTyped(evt);
            }
        });

        txtTelefonoConfig.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoConfigKeyTyped(evt);
            }
        });

        txtRazonConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRazonConfigActionPerformed(evt);
            }
        });
        txtRazonConfig.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRazonConfigKeyTyped(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel24.setText("MENSAJE");

        btnActualizarConfig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ima/actualizado.png"))); // NOI18N
        btnActualizarConfig.setText("ACTUALIZAR");
        btnActualizarConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarConfigActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel25.setText("DATOS DE LA EMPRESA");

        txtIdConfig.setEditable(false);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(txtDireccionConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(txtRucConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(txtNombreConfig)
                                .addGap(103, 103, 103)
                                .addComponent(txtTelefonoConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(77, 77, 77))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(txtRazonConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(225, 225, 225)
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 699, Short.MAX_VALUE)
                        .addComponent(jLabel22)
                        .addGap(170, 170, 170))))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(txtIdConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(429, 429, 429))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(jLabel23)
                .addGap(182, 182, 182)
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnActualizarConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel20)
                        .addGap(18, 18, 18)
                        .addComponent(txtRucConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jLabel25)
                        .addGap(41, 41, 41)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(jLabel22))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNombreConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTelefonoConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(79, 79, 79)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jLabel24)
                    .addComponent(btnActualizarConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDireccionConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRazonConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(71, 71, 71)
                .addComponent(txtIdConfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(144, 144, 144))
        );

        jTabbedPane1.addTab("Configuracion", jPanel7);

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1562, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab8", jPanel17);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 1250, 510));

        jMenuBar1.setBackground(new java.awt.Color(102, 102, 255));
        jMenuBar1.setForeground(new java.awt.Color(153, 153, 255));

        MenuProduc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ima/iphone.png"))); // NOI18N
        MenuProduc.setText("PRODUCTOS");
        MenuProduc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MenuProducMouseClicked(evt);
            }
        });
        jMenuBar1.add(MenuProduc);

        MenuProve.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ima/proveedor.png"))); // NOI18N
        MenuProve.setText("PROVEEDOR");
        MenuProve.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MenuProveMouseClicked(evt);
            }
        });
        jMenuBar1.add(MenuProve);

        MenuNueva.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ima/generar venta1.png"))); // NOI18N
        MenuNueva.setText("NUEVA VENTA");
        MenuNueva.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MenuNuevaMouseClicked(evt);
            }
        });
        jMenuBar1.add(MenuNueva);

        MenuVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ima/ventas.png"))); // NOI18N
        MenuVentas.setText("VENTAS");
        MenuVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MenuVentasMouseClicked(evt);
            }
        });
        jMenuBar1.add(MenuVentas);

        MenuCaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ima/caja-registradora.png"))); // NOI18N
        MenuCaja.setText("CAJA");
        MenuCaja.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MenuCajaMouseClicked(evt);
            }
        });
        jMenuBar1.add(MenuCaja);

        MenuMarca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ima/categorias.png"))); // NOI18N
        MenuMarca.setText("MARCA");
        MenuMarca.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MenuMarcaMouseClicked(evt);
            }
        });
        jMenuBar1.add(MenuMarca);

        MenuConfig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ima/conf.png"))); // NOI18N
        MenuConfig.setText("CONFIGURACIÓN");
        MenuConfig.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MenuConfigMouseClicked(evt);
            }
        });
        jMenuBar1.add(MenuConfig);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAbrirActionPerformed
        CajaInicios.setText(txtAbrir.getText());
        JOptionPane.showMessageDialog(null, "Se abrio caja con :$" + txtAbrir.getText());
//        btnCaja.setEnabled(true);
        LimpiarTable();
        ListarCaja();
        txtBilleteMil.setText("0");
        txtBilleteQuinientos.setText("0");
        txtBilleteDoci.setText("0");
        txtBilleteCien.setText("0");
        txtBilleteCincuenta.setText("0");
        txtBilleteVeinte.setText("0");
        txtMonedaDiez.setText("0");
        txtMonedaCinco.setText("0");
        txtMonedaDos.setText("0");
        txtMonedaUno.setText("0");
        txtMonedaCentavos.setText("0");
        jTabbedPane1.setSelectedIndex(4);


    }//GEN-LAST:event_txtAbrirActionPerformed

    private void txtAbrirKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAbrirKeyReleased
        //CajaInicios.setText(txtAbrir.getText());
        //JOptionPane.showMessageDialog(null, "Se abrio caja con :$"+txtAbrir.getText());
    }//GEN-LAST:event_txtAbrirKeyReleased

    private void txtAbrirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAbrirKeyPressed
        //  CajaInicios.setText(txtAbrir.getText());

    }//GEN-LAST:event_txtAbrirKeyPressed

    private void txtAbrirKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAbrirKeyTyped
        event.numberDecimalKeyPress(evt, txtAbrir);
    }//GEN-LAST:event_txtAbrirKeyTyped

    private void btnActualizarConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarConfigActionPerformed

        if (!"".equals(txtRucConfig.getText()) || !"".equals(txtNombreConfig.getText()) || !"".equals(txtTelefonoConfig.getText()) || !"".equals(txtDireccionConfig.getText()) || !"".equals(txtRazonConfig.getText())) {
            conf.setRuc(Integer.parseInt(txtRucConfig.getText()));
            conf.setNombre(txtNombreConfig.getText());
            conf.setTelefono(Integer.parseInt(txtTelefonoConfig.getText()));
            conf.setDireccion(txtDireccionConfig.getText());
            conf.setMensaje(txtRazonConfig.getText());
            conf.setId(Integer.parseInt(txtIdConfig.getText()));
            proDao.ModificarDatos(conf);
            JOptionPane.showMessageDialog(null, "Datos de la empresa modificado");
            ListarConfig();
        } else {
            JOptionPane.showMessageDialog(null, "Los campos estan vacios");
        }
    }//GEN-LAST:event_btnActualizarConfigActionPerformed

    private void txtRazonConfigKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRazonConfigKeyTyped
        // event.textKeyPress(evt);
    }//GEN-LAST:event_txtRazonConfigKeyTyped

    private void txtRazonConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRazonConfigActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRazonConfigActionPerformed

    private void txtTelefonoConfigKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoConfigKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtTelefonoConfigKeyTyped

    private void txtNombreConfigKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreConfigKeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtNombreConfigKeyTyped

    private void txtNombreConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreConfigActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreConfigActionPerformed

    private void txtRucConfigKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRucConfigKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtRucConfigKeyTyped

    private void TableCatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableCatMouseClicked
        int fila = TableCat.rowAtPoint(evt.getPoint());
        txtIdCat.setText(TableCat.getValueAt(fila, 0).toString());
        txtClaveCat.setText(TableCat.getValueAt(fila, 1).toString());
        txtNombreCat.setText(TableCat.getValueAt(fila, 2).toString());
    }//GEN-LAST:event_TableCatMouseClicked

    private void btnEliminarCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarCatActionPerformed
        if (!"".equals(txtIdCat.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar?");
            if (pregunta == 0) {
                int id = Integer.parseInt(txtIdCat.getText());
                catDao.EliminarCategoria(id);
                LimpiarTable();
                ListarCategoria();
                LimpiarCategoria();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila");
        }
    }//GEN-LAST:event_btnEliminarCatActionPerformed

    private void txtClaveCatKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtClaveCatKeyTyped
        // event.numberKeyPress(evt);
    }//GEN-LAST:event_txtClaveCatKeyTyped

    private void btnEditarCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarCatActionPerformed
        if ("".equals(txtIdCat.getText())) {
            JOptionPane.showMessageDialog(null, "Seleecione una fila");
        } else {
            if (!"".equals(txtClaveCat.getText()) || !"".equals(txtNombreCat.getText())) {
                cat.setClave(txtClaveCat.getText());
                cat.setNombre(txtNombreCat.getText());
                cat.setId(Integer.parseInt(txtIdCat.getText()));
                catDao.ModificarCategoria(cat);
                JOptionPane.showMessageDialog(null, "Categoria Modificada");
                LimpiarTable();
                ListarCategoria();
                LimpiarCategoria();

            }

        }
    }//GEN-LAST:event_btnEditarCatActionPerformed

    private void btnGuardarCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarCatActionPerformed
        if (!"".equals(txtClaveCat.getText()) || !"".equals(txtNombreCat.getText())) {
            cat.setClave(txtClaveCat.getText());
            cat.setNombre(txtNombreCat.getText());
            catDao.RegistrarCategorias(cat);
            LimpiarTable();
            LimpiarCategoria();
            ListarCategoria();
            JOptionPane.showMessageDialog(null, "Categoria Registrada");

        } else {
            JOptionPane.showMessageDialog(null, "los campos estan vacios");
        }
    }//GEN-LAST:event_btnGuardarCatActionPerformed

    private void btnNuevoCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoCatActionPerformed
        LimpiarCategoria();
    }//GEN-LAST:event_btnNuevoCatActionPerformed

    private void txtNombreCatKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreCatKeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtNombreCatKeyTyped

    private void txtTotalCajaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotalCajaKeyTyped
        event.numberDecimalKeyPress(evt, txtTotalCaja);
    }//GEN-LAST:event_txtTotalCajaKeyTyped

    private void txtTotalCajaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotalCajaKeyReleased

    }//GEN-LAST:event_txtTotalCajaKeyReleased

    private void txtTotalCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalCajaActionPerformed

        mo.a = Float.parseFloat(txtBilleteMil.getText());
        mo.BilleteMil();
        mo.a = Float.parseFloat(txtBilleteQuinientos.getText());
        mo.BilleteQuinien();
        mo.a = Float.parseFloat(txtBilleteDoci.getText());
        mo.BilleteDocientos();
        mo.a = Float.parseFloat(txtBilleteCien.getText());
        mo.BilleteCien();
        mo.a = Float.parseFloat(txtBilleteCincuenta.getText());
        mo.BilleteCincuenta();
        mo.a = Float.parseFloat(txtBilleteVeinte.getText());
        mo.BilleteVeinte();
        mo.a = Float.parseFloat(txtMonedaDiez.getText());
        mo.MonedaDiez();
        mo.a = Float.parseFloat(txtMonedaCinco.getText());
        mo.MonedaCinco();
        mo.a = Float.parseFloat(txtMonedaDos.getText());
        mo.MonedaDos();
        mo.a = Float.parseFloat(txtMonedaUno.getText());
        mo.MonedaUno();
        mo.a = Float.parseFloat(txtMonedaCentavos.getText());
        mo.MonedaCentavos();
        mo.suma();
        txtTotalCaja.setText(String.valueOf(mo.resultadopr));
    }//GEN-LAST:event_txtTotalCajaActionPerformed

    private void btnGuardarCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarCajaActionPerformed

        if (!"".equals(CajaInicios.getText()) || !"".equals(txtTotaldelDia.getText())) {
            caja.setInicio(Float.parseFloat(CajaInicios.getText()));
            caja.setTotalfin(Float.parseFloat(txtTotaldelDia.getText()));
            caja.setFecha(fechaActual);
            caj.RegistrarCaja(caja);
            JOptionPane.showMessageDialog(null, "Se guardo el total del dia  $" + txtTotaldelDia.getText());
            ListarCaja();
            CajaInicios.setText("");
            txtTotaldelDia.setText("");
            txtTotalCaja.setText("");
            txtBilleteMil.setText("0");
            txtBilleteQuinientos.setText("0");
            txtBilleteDoci.setText("0");
            txtBilleteCien.setText("0");
            txtBilleteCincuenta.setText("0");
            txtBilleteVeinte.setText("0");
            txtMonedaDiez.setText("0");
            txtMonedaCinco.setText("0");
            txtMonedaDos.setText("0");
            txtMonedaUno.setText("0");
            txtMonedaCentavos.setText("0");

        } else {
            JOptionPane.showMessageDialog(null, "Ingrese las cantidades correspondientes");
        }
    }//GEN-LAST:event_btnGuardarCajaActionPerformed

    private void txtBilleteVeinteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBilleteVeinteKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtBilleteVeinteKeyTyped

    private void txtBilleteCincuentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBilleteCincuentaKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtBilleteCincuentaKeyTyped

    private void txtBilleteQuinientosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBilleteQuinientosKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtBilleteQuinientosKeyTyped

    private void txtBilleteQuinientosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBilleteQuinientosActionPerformed
        /* mo.a = Float.parseFloat(txtBilleteQuinientos.getText());
        mo.bquini =500;
        mo.BilleteQuinien();
        txtTotalCaja.setText(String.valueOf(mo.resultado2));
         */
    }//GEN-LAST:event_txtBilleteQuinientosActionPerformed

    private void txtBilleteCienKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBilleteCienKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtBilleteCienKeyTyped

    private void txtBilleteDociKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBilleteDociKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtBilleteDociKeyTyped

    private void txtBilleteMilKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBilleteMilKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtBilleteMilKeyTyped

    private void txtBilleteMilKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBilleteMilKeyReleased

        /* mo.a = Float.parseFloat(txtBilleteMil.getText());
        mo.bmil =1000;
        mo.BilleteMil();
        txtTotalCaja.setText(String.valueOf(mo.resultado));
         */
    }//GEN-LAST:event_txtBilleteMilKeyReleased

    private void txtBilleteMilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBilleteMilActionPerformed
        // txtBilleteMil.setText("0.00");
        /* mo.a = Float.parseFloat(txtBilleteMil.getText());
        mo.bmil =1000;
        mo.BilleteMil();
        txtBilleteMil.getText();
        txtTotalCaja.setText(String.valueOf(mo.resultado1));
         */
    }//GEN-LAST:event_txtBilleteMilActionPerformed

    private void CajaIniciosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CajaIniciosKeyTyped
        event.numberDecimalKeyPress(evt, CajaInicios);
    }//GEN-LAST:event_CajaIniciosKeyTyped

    private void CajaIniciosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CajaIniciosKeyReleased

    }//GEN-LAST:event_CajaIniciosKeyReleased

    private void CajaIniciosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CajaIniciosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CajaIniciosActionPerformed

    private void txtBuscarCajaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarCajaKeyReleased
        buscarFechaCaja(txtBuscarCaja.getText());
    }//GEN-LAST:event_txtBuscarCajaKeyReleased

    private void btnEliminarCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarCajaActionPerformed
        if (!"".equals(txtIdCaja.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar?");
            if (pregunta == 0) {
                int id = Integer.parseInt(txtIdCaja.getText());
                caj.EliminarGanancia(id);
                LimpiarTable();
                ListarCaja();
                CajaInicios.setText("");
                txtTotaldelDia.setText("");
                txtTotalCaja.setText("");
                txtBilleteMil.setText("0");
                txtBilleteQuinientos.setText("0");
                txtBilleteDoci.setText("0");
                txtBilleteCien.setText("0");
                txtBilleteCincuenta.setText("0");
                txtBilleteVeinte.setText("0");
                txtMonedaDiez.setText("0");
                txtMonedaCinco.setText("0");
                txtMonedaDos.setText("0");
                txtMonedaUno.setText("0");
                txtMonedaCentavos.setText("0");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila");
        }
    }//GEN-LAST:event_btnEliminarCajaActionPerformed

    private void btnGraficaCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGraficaCajaActionPerformed
        String fechaReportes = new SimpleDateFormat("dd/MM/yyyy").format(datecaja.getDate());
        Graficod.Graficard(fechaReportes);
    }//GEN-LAST:event_btnGraficaCajaActionPerformed

    private void TableCajaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableCajaMouseClicked
        int fila = TableCaja.rowAtPoint(evt.getPoint());
        txtIdCaja.setText(TableCaja.getValueAt(fila, 0).toString());
        //txtTotaldelDia.setText(TableCaja.getValueAt(fila, 1).toString());
        /*SimpleDateFormat dFormat = new SimpleDateFormat("dd-MM-yyyy");
        String date =dFormat.format(datecaja.getDate());
        this.modelo.addRow(new Object []{(date)});
         */
    }//GEN-LAST:event_TableCajaMouseClicked

    private void txtTotaldelDiaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotaldelDiaKeyTyped
        event.numberDecimalKeyPress(evt, txtTotaldelDia);
    }//GEN-LAST:event_txtTotaldelDiaKeyTyped

    private void txtTotaldelDiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotaldelDiaActionPerformed
        mo.a = Float.parseFloat(CajaInicios.getText());
        mo.resultadopr = Float.parseFloat(txtTotalCaja.getText());
        mo.total();
        txtTotaldelDia.setText(String.valueOf(mo.total));
    }//GEN-LAST:event_txtTotaldelDiaActionPerformed

    private void txtMonedaCentavosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMonedaCentavosKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtMonedaCentavosKeyTyped

    private void txtMonedaCentavosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMonedaCentavosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMonedaCentavosActionPerformed

    private void txtMonedaUnoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMonedaUnoKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtMonedaUnoKeyTyped

    private void txtMonedaUnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMonedaUnoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMonedaUnoActionPerformed

    private void txtMonedaDosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMonedaDosKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtMonedaDosKeyTyped

    private void txtMonedaCincoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMonedaCincoKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtMonedaCincoKeyTyped

    private void txtMonedaDiezKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMonedaDiezKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtMonedaDiezKeyTyped

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        buscarFecha(txtBuscar.getText());
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void btnPdfVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPdfVentasActionPerformed
        try {
            int id = Integer.parseInt(txtIdVenta.getText());
            File file = new File("src/pdfs/ventas" + id + ".pdf");
            Desktop.getDesktop().open(file);
        } catch (IOException ex) {
            Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnPdfVentasActionPerformed

    private void TableVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableVentasMouseClicked

        int fila = TableVentas.rowAtPoint(evt.getPoint());
        txtIdVenta.setText(TableVentas.getValueAt(fila, 0).toString());
        TotalVentas();
    }//GEN-LAST:event_TableVentasMouseClicked

    private void btnGraficarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGraficarActionPerformed

        String fechaReporte = new SimpleDateFormat("dd/MM/yyyy").format(Midate.getDate());
        Grafico.Graficar(fechaReporte);
    }//GEN-LAST:event_btnGraficarActionPerformed

    private void txtCambioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCambioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCambioActionPerformed

    private void txtCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyTyped
        event.numberDecimalKeyPress(evt, txtCantidad);
    }//GEN-LAST:event_txtCantidadKeyTyped

    private void txtCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadActionPerformed

    private void btnGenerarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarVentaActionPerformed
        if (TableVenta.getRowCount() > 0) {
            if (!"".equals(txtCantidad.getText())) {
                // Icon m = new ImageIcon(getClass().getResource("/imagenes/dinero.png"));
                Icon a = new ImageIcon(getClass().getResource("/ima/generar venta1.png"));
                RegistrarVenta();
                RegistrarDetalle();
                ActualizaStock();
                CambioRe();
                JOptionPane.showMessageDialog(null, "Cambio: $" + cam.resultado);
                JOptionPane.showMessageDialog(null, "Venta generada", "VENTA REALIZADA", JOptionPane.INFORMATION_MESSAGE, a);
                pdf();
                LimpiarTableVenta();
                LabelTotal.setText("");
                txtCantidad.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese cantidad recibida");
            }

        } else {
            JOptionPane.showMessageDialog(null, "No hay productos en la venta");
        }
    }//GEN-LAST:event_btnGenerarVentaActionPerformed

    private void txtStockDisponibleKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtStockDisponibleKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtStockDisponibleKeyTyped

    private void txtPrecioVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioVentaKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtPrecioVentaKeyTyped

    private void txtPrecioVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioVentaActionPerformed

    private void btnEliminarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarVentaActionPerformed
        modelo = (DefaultTableModel) TableVenta.getModel();
        modelo.removeRow(TableVenta.getSelectedRow());
        TotalPagar();
        txtCodigoVenta.requestFocus();
        txtCantidad.setText("");
    }//GEN-LAST:event_btnEliminarVentaActionPerformed

    private void txtCantidadVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadVentaKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtCantidadVentaKeyTyped

    private void txtCantidadVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadVentaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(txtCantidadVenta.getText())) {
                String cod = txtCodigoVenta.getText();
                String descripcion = txtDescripcionVenta.getText();
                int cant = Integer.parseInt(txtCantidadVenta.getText());
                double precio = Double.parseDouble(txtPrecioVenta.getText());
                double total = cant * precio;
                int stock = Integer.parseInt(txtStockDisponible.getText());
                if (stock >= cant) {
                    item = item + 1;
                    tmp = (DefaultTableModel) TableVenta.getModel();
                    for (int i = 0; i < TableVenta.getRowCount(); i++) {
                        if (TableVenta.getValueAt(i, 1).equals(txtDescripcionVenta.getText())) {
                            JOptionPane.showMessageDialog(null, "El producto ya esta registrado");
                            return;
                        }

                    }
                    ArrayList lista = new ArrayList();
                    lista.add(item);
                    lista.add(cod);
                    lista.add(descripcion);
                    lista.add(cant);
                    lista.add(precio);
                    lista.add(total);

                    Object[] O = new Object[5];
                    O[0] = lista.get(1);
                    O[1] = lista.get(2);
                    O[2] = lista.get(3);
                    O[3] = lista.get(4);
                    O[4] = lista.get(5);
                    tmp.addRow(O);
                    TableVenta.setModel(tmp);
                    TotalPagar();
                    LimparVenta();
                    txtCodigoVenta.requestFocus();

                } else {
                    JOptionPane.showMessageDialog(null, "Stock no disponible");
                }

            } else {
                JOptionPane.showMessageDialog(null, "Ingrese cantidad");
            }
        }
    }//GEN-LAST:event_txtCantidadVentaKeyPressed

    private void txtCantidadVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadVentaActionPerformed

    private void txtDescripcionVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionVentaKeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtDescripcionVentaKeyTyped

    private void txtDescripcionVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripcionVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionVentaActionPerformed

    private void txtCodigoVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoVentaKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtCodigoVentaKeyTyped

    private void txtCodigoVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoVentaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(txtCodigoVenta.getText())) {
                String cod = txtCodigoVenta.getText();
                pro = proDao.BuscarPro(cod);
                if (pro.getNombre() != null) {
                    txtDescripcionVenta.setText("" + pro.getNombre());
                    txtPrecioVenta.setText("" + pro.getPrecioventa());
                    txtStockDisponible.setText("" + pro.getStock());

                    txtCantidadVenta.requestFocus();

                } else {
                    LimparVenta();
                    txtCodigoVenta.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese el codigo del producto");
                txtCodigoVenta.requestFocus();
            }
        }
    }//GEN-LAST:event_txtCodigoVentaKeyPressed

    private void txtCodigoVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoVentaActionPerformed

    private void btnGuardarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarProveedorActionPerformed
        if (!"".equals(txtRucProveedor.getText()) || !"".equals(txtNombreProveedor.getText()) || !"".equals(txtTelefonoProveedor.getText()) || !"".equals(txtDireccionProveedor.getText())) {
            pr.setRuc(Integer.parseInt(txtRucProveedor.getText()));
            pr.setNombre(txtNombreProveedor.getText());
            pr.setTelefono(Integer.parseInt(txtTelefonoProveedor.getText()));
            pr.setDireccion(txtDireccionProveedor.getText());
            PrDao.RegistrarProveedor(pr);
            LimpiarTable();
            ListarProveedor();
            LimpiarProveedor();
            JOptionPane.showMessageDialog(null, "Proveedor Registrado");
        } else {
            JOptionPane.showMessageDialog(null, "los campos estan vacios");
        }
    }//GEN-LAST:event_btnGuardarProveedorActionPerformed

    private void btnEliminarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProveedorActionPerformed
        if (!"".equals(txtIdProveedor.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar?");
            if (pregunta == 0) {
                int id = Integer.parseInt(txtIdProveedor.getText());
                PrDao.EliminarProveedor(id);
                LimpiarTable();
                ListarProveedor();
                LimpiarProveedor();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila");
        }
    }//GEN-LAST:event_btnEliminarProveedorActionPerformed

    private void btnNuevoProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoProveedorActionPerformed
        LimpiarProveedor();
    }//GEN-LAST:event_btnNuevoProveedorActionPerformed

    private void btnEditarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarProveedorActionPerformed
        if ("".equals(txtIdProveedor.getText())) {
            JOptionPane.showMessageDialog(null, "Seleecione una fila");
        } else {
            if (!"".equals(txtRucProveedor.getText()) || !"".equals(txtNombreProveedor.getText()) || !"".equals(txtTelefonoProveedor.getText()) || !"".equals(txtDireccionProveedor.getText())) {
                pr.setRuc(Integer.parseInt(txtRucProveedor.getText()));
                pr.setNombre(txtNombreProveedor.getText());
                pr.setTelefono(Integer.parseInt(txtTelefonoProveedor.getText()));
                pr.setDireccion(txtDireccionProveedor.getText());
                pr.setId(Integer.parseInt(txtIdProveedor.getText()));
                PrDao.ModificarProveedor(pr);
                JOptionPane.showMessageDialog(null, "Proveedor Modificado");
                LimpiarTable();
                ListarProveedor();
                LimpiarProveedor();
            }
        }
    }//GEN-LAST:event_btnEditarProveedorActionPerformed

    private void txtDireccionProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionProveedorKeyTyped

    }//GEN-LAST:event_txtDireccionProveedorKeyTyped

    private void txtTelefonoProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoProveedorKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtTelefonoProveedorKeyTyped

    private void txtNombreProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreProveedorKeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtNombreProveedorKeyTyped

    private void txtRucProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRucProveedorKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtRucProveedorKeyTyped

    private void txtRucProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRucProveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRucProveedorActionPerformed

    private void TableProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableProveedorMouseClicked
        int fila = TableProveedor.rowAtPoint(evt.getPoint());
        txtIdProveedor.setText(TableProveedor.getValueAt(fila, 0).toString());
        txtRucProveedor.setText(TableProveedor.getValueAt(fila, 1).toString());
        txtNombreProveedor.setText(TableProveedor.getValueAt(fila, 2).toString());
        txtTelefonoProveedor.setText(TableProveedor.getValueAt(fila, 3).toString());
        txtDireccionProveedor.setText(TableProveedor.getValueAt(fila, 4).toString());

        // btnGuardarProveedor.setEnabled(false);
    }//GEN-LAST:event_TableProveedorMouseClicked

    private void txtBuscarProductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarProductoKeyReleased
        buscarProducto(txtBuscarProducto.getText());
    }//GEN-LAST:event_txtBuscarProductoKeyReleased

    private void btnExcelProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelProActionPerformed
        Excel.reporte();
    }//GEN-LAST:event_btnExcelProActionPerformed

    private void btnEliminarProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProActionPerformed
        if (!"".equals(txtIdPro.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar?");
            if (pregunta == 0) {
                int id = Integer.parseInt(txtIdPro.getText());
                proDao.EliminarProductos(id);
                LimpiarTable();
                LimpiarProductos();
                ListarProductos();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila");
        }
    }//GEN-LAST:event_btnEliminarProActionPerformed

    private void btnNuevoProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoProActionPerformed
        LimpiarProductos();
    }//GEN-LAST:event_btnNuevoProActionPerformed

    private void btnEditarProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarProActionPerformed
        if ("".equals(txtIdPro.getText())) {
            JOptionPane.showMessageDialog(null, "Seleecione una fila");
        } else {
            if (!"".equals(txtCodigoPro.getText()) || !"".equals(txtDesPro.getText()) || !"".equals(txtCantPro.getText()) || !"".equals(txtPrecioVentaPro.getText())) {
                pro.setCodigo(txtCodigoPro.getText());
                pro.setNombre(txtDesPro.getText());
                pro.setProveedor(cbxProveedorPro.getSelectedItem().toString());
                pro.setCategoria(cbxCategoriaCat.getSelectedItem().toString());
                pro.setStock(Integer.parseInt(txtCantPro.getText()));
                pro.setPrecioventa(Double.parseDouble(txtPrecioVentaPro.getText()));
                pro.setId(Integer.parseInt(txtIdPro.getText()));
                proDao.ModificarProductos(pro);
                JOptionPane.showMessageDialog(null, "Producto Modificado");
                LimpiarTable();
                ListarProductos();
                LimpiarProductos();
            }
        }
    }//GEN-LAST:event_btnEditarProActionPerformed

    private void btnGuardarProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarProActionPerformed
        if (!"".equals(txtCodigoPro.getText()) || !"".equals(txtDesPro.getText()) || !"".equals(cbxProveedorPro.getSelectedItem()) || !"".equals(cbxCategoriaCat.getSelectedItem()) || !"".equals(txtCantPro.getText()) || !"".equals(txtPrecioVentaPro.getText())) {
            pro.setCodigo(txtCodigoPro.getText());
            pro.setNombre(txtDesPro.getText());
            pro.setProveedor(cbxProveedorPro.getSelectedItem().toString());
            pro.setCategoria(cbxCategoriaCat.getSelectedItem().toString());
            pro.setStock(Integer.parseInt(txtCantPro.getText()));
            pro.setPrecioventa(Double.parseDouble(txtPrecioVentaPro.getText()));
            proDao.RegistrarProductos(pro);
            LimpiarTable();
            LimpiarProductos();
            ListarProductos();
            JOptionPane.showMessageDialog(null, "Productos Registrado");

        } else {
            JOptionPane.showMessageDialog(null, "Los campos estan vacios");
        }
    }//GEN-LAST:event_btnGuardarProActionPerformed

    private void TableProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableProductosMouseClicked
        int fila = TableProductos.rowAtPoint(evt.getPoint());
        txtIdPro.setText(TableProductos.getValueAt(fila, 0).toString());
        txtCodigoPro.setText(TableProductos.getValueAt(fila, 1).toString());
        txtDesPro.setText(TableProductos.getValueAt(fila, 2).toString());
        cbxProveedorPro.setSelectedItem(TableProductos.getValueAt(fila, 3).toString());
        cbxCategoriaCat.setSelectedItem(TableProductos.getValueAt(fila, 4).toString());
        txtCantPro.setText(TableProductos.getValueAt(fila, 5).toString());
        txtPrecioVentaPro.setText(TableProductos.getValueAt(fila, 6).toString());
    }//GEN-LAST:event_TableProductosMouseClicked

    private void cbxCategoriaCatKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbxCategoriaCatKeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_cbxCategoriaCatKeyTyped

    private void cbxCategoriaCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxCategoriaCatActionPerformed

    }//GEN-LAST:event_cbxCategoriaCatActionPerformed

    private void txtPrecioVentaProKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioVentaProKeyTyped
        event.numberDecimalKeyPress(evt, txtPrecioVenta);
    }//GEN-LAST:event_txtPrecioVentaProKeyTyped

    private void txtPrecioVentaProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioVentaProActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioVentaProActionPerformed

    private void cbxProveedorProKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbxProveedorProKeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_cbxProveedorProKeyTyped

    private void cbxProveedorProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxProveedorProActionPerformed

    }//GEN-LAST:event_cbxProveedorProActionPerformed

    private void txtCantProKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantProKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtCantProKeyTyped

    private void txtDesProKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDesProKeyTyped
        //        event.textKeyPress(evt);
    }//GEN-LAST:event_txtDesProKeyTyped

    private void txtCodigoProKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoProKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtCodigoProKeyTyped

    private void MenuProducMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MenuProducMouseClicked
        LimpiarTable();
        ListarProductos();
        LimpiarProductos();
        jTabbedPane1.setSelectedIndex(0);
    }//GEN-LAST:event_MenuProducMouseClicked

    private void MenuProveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MenuProveMouseClicked
        LimpiarTable();
        ListarProveedor();
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_MenuProveMouseClicked

    private void MenuNuevaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MenuNuevaMouseClicked
        jTabbedPane1.setSelectedIndex(2);
    }//GEN-LAST:event_MenuNuevaMouseClicked

    private void MenuCajaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MenuCajaMouseClicked
        LimpiarTable();
        ListarCaja();
        txtBilleteMil.setText("0");
        txtBilleteQuinientos.setText("0");
        txtBilleteDoci.setText("0");
        txtBilleteCien.setText("0");
        txtBilleteCincuenta.setText("0");
        txtBilleteVeinte.setText("0");
        txtMonedaDiez.setText("0");
        txtMonedaCinco.setText("0");
        txtMonedaDos.setText("0");
        txtMonedaUno.setText("0");
        txtMonedaCentavos.setText("0");
        jTabbedPane1.setSelectedIndex(4);
    }//GEN-LAST:event_MenuCajaMouseClicked

    private void MenuVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MenuVentasMouseClicked
        jTabbedPane1.setSelectedIndex(3);
        LimpiarTable();
        ListarVentas();
    }//GEN-LAST:event_MenuVentasMouseClicked

    private void MenuMarcaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MenuMarcaMouseClicked
        LimpiarTable();
        ListarCategoria();
        jTabbedPane1.setSelectedIndex(5);
    }//GEN-LAST:event_MenuMarcaMouseClicked

    private void MenuConfigMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MenuConfigMouseClicked
        jTabbedPane1.setSelectedIndex(6);
    }//GEN-LAST:event_MenuConfigMouseClicked

    private void txtCodigoProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoProActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoProActionPerformed

    private void txtStockDisponibleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStockDisponibleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStockDisponibleActionPerformed

    private void txtIdproActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdproActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdproActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Sistema().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTextField CajaInicios;
    public javax.swing.JLabel LabelTotal;
    public javax.swing.JLabel LabelVendedor;
    private javax.swing.JMenu MenuCaja;
    private javax.swing.JMenu MenuConfig;
    private javax.swing.JMenu MenuMarca;
    private javax.swing.JMenu MenuNueva;
    private javax.swing.JMenu MenuProduc;
    private javax.swing.JMenu MenuProve;
    private javax.swing.JMenu MenuVentas;
    private com.toedter.calendar.JDateChooser Midate;
    public javax.swing.JTable TableCaja;
    private javax.swing.JTable TableCat;
    private javax.swing.JTable TableProductos;
    public javax.swing.JTable TableProveedor;
    public javax.swing.JTable TableVenta;
    private javax.swing.JTable TableVentas;
    private javax.swing.JButton btnActualizarConfig;
    private javax.swing.JButton btnEditarCat;
    private javax.swing.JButton btnEditarPro;
    public javax.swing.JButton btnEditarProveedor;
    private javax.swing.JButton btnEliminarCaja;
    private javax.swing.JButton btnEliminarCat;
    private javax.swing.JButton btnEliminarPro;
    public javax.swing.JButton btnEliminarProveedor;
    private javax.swing.JButton btnEliminarVenta;
    private javax.swing.JButton btnExcelPro;
    private javax.swing.JButton btnGenerarVenta;
    private javax.swing.JButton btnGraficaCaja;
    private javax.swing.JButton btnGraficar;
    private javax.swing.JButton btnGuardarCaja;
    private javax.swing.JButton btnGuardarCat;
    private javax.swing.JButton btnGuardarPro;
    public javax.swing.JButton btnGuardarProveedor;
    private javax.swing.JButton btnNuevoCat;
    private javax.swing.JButton btnNuevoPro;
    public javax.swing.JButton btnNuevoProveedor;
    private javax.swing.JButton btnPdfVentas;
    private javax.swing.JComboBox<String> cbxCategoriaCat;
    private javax.swing.JComboBox<String> cbxProveedorPro;
    public com.toedter.calendar.JDateChooser datecaja;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtAbrir;
    public javax.swing.JTextField txtBilleteCien;
    public javax.swing.JTextField txtBilleteCincuenta;
    public javax.swing.JTextField txtBilleteDoci;
    public javax.swing.JTextField txtBilleteMil;
    public javax.swing.JTextField txtBilleteQuinientos;
    public javax.swing.JTextField txtBilleteVeinte;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtBuscarCaja;
    private javax.swing.JTextField txtBuscarProducto;
    public javax.swing.JTextField txtCambio;
    private javax.swing.JTextField txtCantPro;
    public javax.swing.JTextField txtCantidad;
    public javax.swing.JTextField txtCantidadVenta;
    private javax.swing.JTextField txtClaveCat;
    private javax.swing.JTextField txtCodigoPro;
    public javax.swing.JTextField txtCodigoVenta;
    private javax.swing.JTextField txtDesPro;
    public javax.swing.JTextField txtDescripcionVenta;
    private javax.swing.JTextField txtDireccionConfig;
    public javax.swing.JTextField txtDireccionProveedor;
    public javax.swing.JTextField txtIdCaja;
    private javax.swing.JTextField txtIdCat;
    private javax.swing.JTextField txtIdConfig;
    public javax.swing.JTextField txtIdPro;
    public javax.swing.JTextField txtIdProveedor;
    private javax.swing.JTextField txtIdVenta;
    private javax.swing.JTextField txtIdpro;
    public javax.swing.JTextField txtMonedaCentavos;
    public javax.swing.JTextField txtMonedaCinco;
    public javax.swing.JTextField txtMonedaDiez;
    public javax.swing.JTextField txtMonedaDos;
    public javax.swing.JTextField txtMonedaUno;
    private javax.swing.JTextField txtNombreCat;
    private javax.swing.JTextField txtNombreConfig;
    public javax.swing.JTextField txtNombreProveedor;
    public javax.swing.JTextField txtPrecioVenta;
    private javax.swing.JTextField txtPrecioVentaPro;
    private javax.swing.JTextField txtRazonConfig;
    private javax.swing.JTextField txtRucConfig;
    public javax.swing.JTextField txtRucProveedor;
    public javax.swing.JTextField txtStockDisponible;
    private javax.swing.JTextField txtTelefonoConfig;
    public javax.swing.JTextField txtTelefonoProveedor;
    public javax.swing.JTextField txtTotalCaja;
    public javax.swing.JTextField txtTotaldelDia;
    // End of variables declaration//GEN-END:variables

    private void LimpiarProveedor() {
        txtIdProveedor.setText("");
        txtRucProveedor.setText("");
        txtNombreProveedor.setText("");
        txtTelefonoProveedor.setText("");
        txtDireccionProveedor.setText("");

    }

    private void LimpiarCategoria() {
        txtIdCat.setText("");
        txtClaveCat.setText("");
        txtNombreCat.setText("");
    }

    private void LimpiarProductos() {
        txtIdPro.setText("");
        txtCodigoPro.setText("");
        cbxProveedorPro.setSelectedItem(null);
        cbxCategoriaCat.setSelectedItem(null);
        txtDesPro.setText("");
        txtCantPro.setText("");
        txtPrecioVentaPro.setText("");

    }

    private void LimparCaja() {
        CajaInicios.setText("");
        txtTotaldelDia.setText("");
        txtTotalCaja.setText("");
        txtBilleteMil.setText("");
        txtBilleteQuinientos.setText("");
        txtBilleteDoci.setText("");
        txtBilleteCien.setText("");
        txtBilleteCincuenta.setText("");
        txtBilleteVeinte.setText("");
        txtMonedaDiez.setText("");
        txtMonedaCinco.setText("");
        txtMonedaDos.setText("");
        txtMonedaUno.setText("");
        txtMonedaCentavos.setText("");
    }

    private void TotalPagar() {
        Totalpagar = 0.00;
        int numFila = TableVenta.getRowCount();
        for (int i = 0; i < numFila; i++) {
            double cal = Double.parseDouble(String.valueOf(TableVenta.getModel().getValueAt(i, 4)));
            Totalpagar = Totalpagar + cal;

        }
        LabelTotal.setText(String.format("%.2f", Totalpagar));
    }

    private void TotalVentas() {
        TotalVentas = 0.00;
        int numFila = TableVentas.getRowCount();
        for (int i = 0; i < numFila; i++) {
            double cal = Double.parseDouble(String.valueOf(TableVentas.getModel().getValueAt(i, 2)));
            TotalVentas = TotalVentas + cal;

        }
        //  JLabelTotalVentas.setText(String.format("%.2f", TotalVentas));
    }

    public void CambioRe() {

        cam.n1 = Double.parseDouble(txtCantidad.getText());
        cam.n2 = Double.parseDouble(LabelTotal.getText());
        cam.calresu();
        txtCambio.setText(String.valueOf(cam.resultado));
    }

    private void LimparVenta() {
        txtCodigoVenta.setText("");
        txtDescripcionVenta.setText("");
        txtCantidadVenta.setText("");
        txtStockDisponible.setText("");
        txtPrecioVenta.setText("");
        txtIdPro.setText("");
        txtIdVenta.setText("");
    }

    private void RegistrarVenta() {
        String vendedor = LabelVendedor.getText();
        double monto = Totalpagar;
        v.setVendedor(vendedor);
        v.setTotal(monto);
        v.setFecha(fechaActual); //---mo
        Vdao.RegistrarVenta(v);
    }

    private void RegistrarDetalle() {
        int id = Vdao.IdVenta();
        for (int i = 0; i < TableVenta.getRowCount(); i++) {
            String descripcion = TableVenta.getValueAt(i, 1).toString();
            int cant = Integer.parseInt(TableVenta.getValueAt(i, 2).toString());
            double precio = Double.parseDouble(TableVenta.getValueAt(i, 3).toString());
            Dv.setCod_nombre(descripcion);
            Dv.setCantidad(cant);
            Dv.setPrecio(precio);
            Dv.setId(id);
            Vdao.RegistrarDetalle(Dv);
        }
    }

    private void ActualizaStock() {
        for (int i = 0; i < TableVenta.getRowCount(); i++) {
            String cod = TableVenta.getValueAt(i, 0).toString();
            int cant = Integer.parseInt(TableVenta.getValueAt(i, 2).toString());
            pro = proDao.BuscarPro(cod);
            int StockActual = pro.getStock() - cant;
            Vdao.ActualizarStock(StockActual, cod);

        }
    }

    private void LimpiarTableVenta() {
        tmp = (DefaultTableModel) TableVenta.getModel();
        int fila = TableVenta.getRowCount();
        for (int i = 0; i < fila; i++) {
            tmp.removeRow(0);
        }

    }

    private void pdf() {
        try {
            int id = Vdao.IdVenta();
            FileOutputStream archivo;
            File file = new File("src/pdfs/ventas" + id + ".pdf");
            archivo = new FileOutputStream(file);
            Document doc = new Document();
            PdfWriter.getInstance(doc, archivo);
            doc.open();
            Image img = Image.getInstance("src/ima/ima.png");

            Paragraph fecha = new Paragraph();
            Font negrita = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.WHITE);
            fecha.add(Chunk.NEWLINE);
            Date date = new Date();
            fecha.add("Num:" + id + "\n" + "Fecha: " + new SimpleDateFormat("dd-MM-yyyy").format(date) + "\n\n");

            PdfPTable Encabezado = new PdfPTable(4);
            Encabezado.setWidthPercentage(100);
            Encabezado.getDefaultCell().setBorder(0);
            float[] ColumnaEncabezado = new float[]{80f, 90f, 130f, 100f};
            Encabezado.setWidths(ColumnaEncabezado);
            Encabezado.setHorizontalAlignment(Element.ALIGN_LEFT);

            Encabezado.addCell(img);

            String ruc = txtRucConfig.getText();
            String nom = txtNombreConfig.getText();
            String tel = txtTelefonoConfig.getText();
            String dir = txtDireccionConfig.getText();
            String ra = txtRazonConfig.getText();

            Encabezado.addCell("");
            Encabezado.addCell("Ruc: " + ruc + "\nNombre: " + nom + "\nTelefono: " + tel + "\nDireccion: " + dir + "\nRazon: " + ra);
            Encabezado.addCell(fecha);
            doc.add(Encabezado);

            //Productos
            PdfPTable tablapro = new PdfPTable(4);
            tablapro.setWidthPercentage(100);
            tablapro.getDefaultCell().setBorder(0);
            float[] Columnapro = new float[]{20f, 50f, 30f, 40f};
            tablapro.setWidths(Columnapro);
            tablapro.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell pro1 = new PdfPCell(new Phrase("Cant.", negrita));
            PdfPCell pro2 = new PdfPCell(new Phrase("Descripción", negrita));
            PdfPCell pro3 = new PdfPCell(new Phrase("Precio U.", negrita));
            PdfPCell pro4 = new PdfPCell(new Phrase("Precio T.", negrita));
            pro1.setBorder(0);
            pro2.setBorder(0);
            pro3.setBorder(0);
            pro4.setBorder(0);
            pro1.setBackgroundColor(BaseColor.GREEN);
            pro2.setBackgroundColor(BaseColor.GREEN);
            pro3.setBackgroundColor(BaseColor.GREEN);
            pro4.setBackgroundColor(BaseColor.GREEN);
            tablapro.addCell(pro1);
            tablapro.addCell(pro2);
            tablapro.addCell(pro3);
            tablapro.addCell(pro4);
            for (int i = 0; i < TableVenta.getRowCount(); i++) {
                String producto = TableVenta.getValueAt(i, 1).toString();
                String cantidad = TableVenta.getValueAt(i, 2).toString();
                String precio = TableVenta.getValueAt(i, 3).toString();
                String total = TableVenta.getValueAt(i, 4).toString();
                tablapro.addCell(cantidad);
                tablapro.addCell(producto);
                tablapro.addCell(precio);
                tablapro.addCell(total);
            }
            doc.add(tablapro);

            Paragraph info = new Paragraph();
            info.add(Chunk.NEWLINE);
            info.add("Total a Pagar: $" + Totalpagar);
            info.setAlignment(Element.ALIGN_RIGHT);
            doc.add(info);

            Paragraph resta = new Paragraph();
            resta.add(Chunk.NEWLINE);
            resta.add("Pago con: $" + cam.n1);
            resta.setAlignment(Element.ALIGN_RIGHT);
            doc.add(resta);

            Paragraph camb = new Paragraph();
            camb.add(Chunk.NEWLINE);
            camb.add("Cambio: $" + cam.resultado);
            camb.setAlignment(Element.ALIGN_RIGHT);
            doc.add(camb);

            doc.close();
            archivo.close();
            Desktop.getDesktop().open(file);
        } catch (DocumentException | IOException e) {
            System.out.println(e.toString());

        }
    }

}
