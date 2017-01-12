
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema.de.facturacion;

import dialogs.consultaPer;
import java.awt.event.KeyEvent;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author ADMIN
 */
public class IngresoUsuario extends javax.swing.JFrame {

    /**
     * Creates new form IngresoUsuario
     */
    DefaultListModel modelo;
    //java.sql.Connection conexiones = null;

    public IngresoUsuario() throws SQLException {
        initComponents();
        bloquearTodoBotones();
        bloquearTodasCajas();
        jchkExtranjero.setVisible(false);
        jbtPassword.setEnabled(false);
        txtClave.setEnabled(false);
    }

    private void guardar() {
        if(camposLlenos()){
            conexion_mysql cn=new conexion_mysql();
            Connection cc=cn.conectar();
            String sql="";
            sql="Insert into usuarios(CI_USU,EXT_USU,NOM1_USU,NOM2_USU,APE1_USU,APE2_USU,FEC_NAC_USU,TLF_USU,CELU_USU,DIR_USU,E_MAIL_USU,"
                    + "GEN_USU,EST_CIV_USU,PROCE_USU,JERAR_USU,ESTADO_USU,clave_usu)"
                    + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            String CI_USU,EXT_USU,NOM1_USU,NOM2_USU,APE1_USU,APE2_USU,FEC_NAC_USU,
                    TLF_USU,CELU_USU,DIR_USU,E_MAIL_USU,GEN_USU,EST_CIV_USU,
                    PROCE_USU,JERAR_USU,ESTADO_USU,clave_usu;
            CI_USU=txtCedula.getText().trim();
            EXT_USU="0";
            NOM1_USU=txtNombre.getText().trim();
            NOM2_USU=txtNombre1.getText().trim();
            APE1_USU=txtApellido.getText().trim();
            APE2_USU=txtApellido1.getText().trim();
            Date ini = jdtNac.getDate();
            String dia,mes,anio;
            dia=String.valueOf(ini.getDate());
            mes=String.valueOf(ini.getMonth()+1);
            anio=String.valueOf(ini.getYear()+1900);
            FEC_NAC_USU=anio+"-"+mes+"-"+dia;
            TLF_USU=txtTelefono.getText().trim();
            CELU_USU=txtCelular.getText().trim();
            DIR_USU=txtDireccion.getText().trim();
            E_MAIL_USU=txtCorreo.getText().trim();
            GEN_USU=jcmbGenero.getSelectedItem().toString();
            EST_CIV_USU=jcmbEstadoCivil.getSelectedItem().toString();
            PROCE_USU=jcmbProceso.getSelectedItem().toString();
            JERAR_USU=jcmbjerarquia.getSelectedItem().toString();
            if(jcActivo.isSelected()){
                ESTADO_USU="0";
            }else{
                ESTADO_USU="1";
            }
            clave_usu=encriptaEnMD5(txtCedula.getText().trim());
            System.out.println(sql);
            try {
                PreparedStatement ps=cc.prepareStatement(sql);
                ps.setString(1, CI_USU);
                ps.setString(2, EXT_USU);
                ps.setString(3, NOM1_USU);
                ps.setString(4, NOM2_USU);
                ps.setString(5, APE1_USU);
                ps.setString(6, APE2_USU);
                ps.setString(7, FEC_NAC_USU);
                ps.setString(8, TLF_USU);
                ps.setString(9, CELU_USU);
                ps.setString(10, DIR_USU);
                ps.setString(11, E_MAIL_USU);
                ps.setString(12, GEN_USU);
                ps.setString(13, EST_CIV_USU);
                ps.setString(14, PROCE_USU);
                ps.setString(15, JERAR_USU);
                ps.setString(16, ESTADO_USU);
                ps.setString(17, clave_usu);
                if(ps.executeUpdate()>0){
                    JOptionPane.showMessageDialog(this, "Se ha guardado correctamente, La contraseña es la cédula");
                    bloquearTodasCajas();
                    nuevo();
                    bloquearTodoBotones();
                    jbtBuscar.setEnabled(true);
                    txtCedula.setEnabled(true);
                    jbtNuevo.setEnabled(true);
                }
            } catch (SQLException ex) {
                Logger.getLogger(IngresoUsuario.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }

    public void ActivarTodoBotones() {
        jbtActualizar.setEnabled(true);
        jbtCancelar.setEnabled(true);
        jbtGuardar.setEnabled(true);
    }

    public void ActivarTodasCajas() {
        txtApellido.setEnabled(true);
        txtApellido1.setEnabled(true);
        txtCelular.setEnabled(true);
        txtClave.setEnabled(true);
        txtCorreo.setEnabled(true);
        txtDireccion.setEnabled(true);
        txtNombre.setEnabled(true);
        txtNombre1.setEnabled(true);
        txtTelefono.setEnabled(true);
        jcActivo.setEnabled(true);
        jchkExtranjero.setEnabled(true);
        jcmbEstadoCivil.setEnabled(true);
        jcmbGenero.setEnabled(true);
        jcmbProceso.setEnabled(true);
        jcmbjerarquia.setEnabled(true);
    }

    public void bloquearTodoBotones() {
        jbtActualizar.setEnabled(false);
        jbtCancelar.setEnabled(false);
        jbtGuardar.setEnabled(false);
    }

    public void bloquearTodasCajas() {
        txtApellido.setEnabled(false);
        txtApellido1.setEnabled(false);
        txtCelular.setEnabled(false);
        txtClave.setEnabled(false);
        txtCorreo.setEnabled(false);
        txtDireccion.setEnabled(false);
        txtNombre.setEnabled(false);
        txtNombre1.setEnabled(false);
        txtTelefono.setEnabled(false);
        jcActivo.setEnabled(false);
        jchkExtranjero.setEnabled(false);
        jcmbEstadoCivil.setEnabled(false);
        jcmbGenero.setEnabled(false);
        jcmbProceso.setEnabled(false);
        jcmbjerarquia.setEnabled(false);
    }

    private void nuevo() {
        txtClave.setText("");
        txtApellido.setText("");
        txtNombre.setText("");
        txtNombre1.setText("");
        txtApellido1.setText("");
        txtCedula.setText("");
        txtCelular.setText("");
        txtTelefono.setText("");
        txtDireccion.setText("");
        txtCorreo.setText("");
        jcmbEstadoCivil.setSelectedIndex(0);
        jcmbGenero.setSelectedIndex(0);
//        jcmbProceso.setSelectedIndex(0);
        jcmbjerarquia.setSelectedIndex(0);
    }

    public void buscar() {
        consultaPer con = new consultaPer(this, rootPaneCheckingEnabled, txtCedula.getText());
        con.show();
        txtCedula.setText(con.cedulas);
        jchkExtranjero.setEnabled(false);
    }

    public void buscarCedulaCompleta(String cedula) {
        jchkExtranjero.setEnabled(false);
        try {
            conexion_mysql cc = new conexion_mysql();
            Connection cn = cc.conectar();
            String sql = "";
            sql = "select EXT_USU,NOM1_USU,NOM2_USU,APE1_USU,APE2_USU,TLF_USU,CELU_USU,DIR_USU,"
                    + "E_MAIL_USU,FEC_NAC_USU,GEN_USU,EST_CIV_USU,PROCE_USU,JERAR_USU,ESTADO_USU from usuarios "
                    + "where ci_usu=" + cedula;
            Statement ps = cn.createStatement();
            ResultSet rs = ps.executeQuery(sql);
            if (rs.next()) {

                if (Integer.valueOf(rs.getString("EXT_USU")) == 0) {
                    jchkExtranjero.setSelected(true);
                    jchkExtranjero.setEnabled(false);
                } else {
                    jchkExtranjero.setSelected(false);
                    jchkExtranjero.setEnabled(false);
                }
                txtNombre.setText(rs.getString("NOM1_USU"));
                txtNombre1.setText(rs.getString("NOM2_USU"));
                txtApellido.setText(rs.getString("APE1_USU"));
                txtApellido1.setText(rs.getString("APE2_USU"));
                txtTelefono.setText(rs.getString("TLF_USU"));
                txtCelular.setText(rs.getString("CELU_USU"));
                txtDireccion.setText(rs.getString("DIR_USU"));
                txtCorreo.setText(rs.getString("E_MAIL_USU"));
                jcmbGenero.setSelectedItem(rs.getString("GEN_USU"));
                jcmbEstadoCivil.setSelectedItem(rs.getString("EST_CIV_USU"));
                jcmbProceso.setSelectedItem(rs.getString("PROCE_USU"));
                Date fechaS = null;
                SimpleDateFormat formatoDeFecha = new SimpleDateFormat("yyyy-MM-dd");
                fechaS = formatoDeFecha.parse(rs.getString("FEC_NAC_USU"));
                jdtNac.setDate(fechaS);
                jcmbjerarquia.setSelectedItem(rs.getString("JERAR_USU"));
                if (Integer.valueOf(rs.getString("ESTADO_USU")) == 0) {
                    jcActivo.setSelected(true);
                    //jchkExtranjero.setEnabled(false);
                } else {
                    jcActivo.setSelected(false);
                    //jchkExtranjero.setEnabled(false);
                }

            } else {
                JOptionPane.showMessageDialog(null, "No hay datos encontrados!");
            }

        } catch (Exception ex) {
            //Logger.getLogger(IngresoUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public boolean camposLlenos() {
        int y = 0;

        if (txtCedula.getText().length() == 10) {
            if (verficacion_cedula_ec.verificaCedula(txtCedula.getText().trim())) {

            } else {
                JOptionPane.showMessageDialog(null, "Número de cedula no valido");
                txtCedula.setText("");
                txtCedula.requestFocus();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ingrese la cedula completa");
            y++;
            txtCedula.requestFocus();
        }

        if (txtNombre.getText().length() < 2) {
            JOptionPane.showMessageDialog(null, "Debe ingresar nombre mayor a 2 caracteres");
            txtNombre.requestFocus();
            y++;
        }
        if (txtNombre1.getText().length() < 2) {
            JOptionPane.showMessageDialog(null, "Debe ingresar nombre mayor a 2 caracteres");
            txtNombre1.requestFocus();
            y++;
        }
        if (txtApellido.getText().length() < 2) {
            JOptionPane.showMessageDialog(null, "Debe ingresar apellido mayor a 2 caracteres");
            txtApellido.requestFocus();
            y++;
        }
        
        if (txtApellido1.getText().length() < 2) {
            JOptionPane.showMessageDialog(null, "Debe ingresar apellido mayor a 2 caracteres");
            txtApellido1.requestFocus();
            y++;
        }
        if (txtCelular.getText().length() < 10 || txtCelular.getText().length() > 10 ) {
            
            JOptionPane.showMessageDialog(null, "Debe ingresar ceular de 10 digitos");
            txtCelular.requestFocus();
            y++;
        }
        if (txtCorreo.getText().length() < 2) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un correo valido");
            txtCorreo.requestFocus();
            y++;
        }
        if (txtDireccion.getText().length() < 2) {
            JOptionPane.showMessageDialog(null, "Debe ingresar una dirección valida");
            txtDireccion.requestFocus();
            y++;
        }
        //restar para ver mayoria edad
        Date ini = jdtNac.getDate();
        final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000;
        Calendar actual = new GregorianCalendar();
        Date fin=actual.getTime();  
        float diferencia = (fin.getTime() - ini.getTime()) / MILLSECS_PER_DAY;
        if (diferencia <= 6570) {
            //y++;
            JOptionPane.showMessageDialog(this, "Fecha Nacimiento ingresada no es valida");
            y++;
            jdtNac.requestFocus();
        } 
        if(jcmbEstadoCivil.getSelectedItem().toString().equals("Seleccione uno")){
            JOptionPane.showMessageDialog(this, "Seleccion un estado civil");
            y++;
            jcmbEstadoCivil.requestFocus();
        }
        if(jcmbGenero.getSelectedItem().toString().equals("Seleccione uno")){
            JOptionPane.showMessageDialog(this, "Seleccion un género");
            y++;
            jcmbGenero.requestFocus();
        }
        if(jcmbProceso.getSelectedItem().toString().equals("Seleccione uno")){
            JOptionPane.showMessageDialog(this, "Seleccion un proceso");
            y++;
            jcmbProceso.requestFocus();
        }
        if(jcmbjerarquia.getSelectedItem().toString().equals("Seleccione uno")){
            JOptionPane.showMessageDialog(this, "Seleccion una Jerarquia");
            y++;
            jcmbjerarquia.requestFocus();
        }
        
        
        if (y > 0) {
            return false;
        } else {
            return true;
        }
    }
    public void cambiarClave(){
        if(txtClave.getText().length()<8){
            JOptionPane.showMessageDialog(this, "Debe ingresar una contraseña mayor a 8 digitos");
            txtClave.requestFocus();
            txtClave.setText("");
        }else{
            String pass=encriptaEnMD5(txtClave.getText().trim());
            //System.out.println(pass);
            conexion_mysql cn=new conexion_mysql();
            Connection cc=cn.conectar();
            String sql="";
            sql="update usuarios set clave_usu='"+pass+"' where CI_USU='"+txtCedula.getText()+"'";
            PreparedStatement ps;
            try {
                ps = cc.prepareStatement(sql);
                if(ps.executeUpdate()>0){
                JOptionPane.showMessageDialog(this, "Contraseña Actualizada!");
                bloquearTodasCajas();
                bloquearTodoBotones();
                    nuevo();
                    txtClave.setText("");
                     txtCedula.setEnabled(true);
        jbtBuscar.setEnabled(true);
        txtCedula.requestFocus();
        jbtPassword.setEnabled(false);
        jbtNuevo.setEnabled(true);
                }
            } catch (SQLException ex) {
                Logger.getLogger(IngresoUsuario.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
        private static final char[] CONSTS_HEX = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String encriptaEnMD5(String stringAEncriptar) {
        try {
            MessageDigest msgd = MessageDigest.getInstance("MD5");
            byte[] bytes = msgd.digest(stringAEncriptar.getBytes());
            StringBuilder strbCadenaMD5 = new StringBuilder(2 * bytes.length);
            for (int i = 0; i < bytes.length; i++) {
                int bajo = (int) (bytes[i] & 0x0f);
                int alto = (int) ((bytes[i] & 0xf0) >> 4);
                strbCadenaMD5.append(CONSTS_HEX[alto]);
                strbCadenaMD5.append(CONSTS_HEX[bajo]);
            }
            return strbCadenaMD5.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public void actualizar() {
        if(camposLlenos()){
        conexion_mysql cn = new conexion_mysql();
        Connection cc = cn.conectar();
        String sql = "";
        String activo = "";
        if (jcActivo.isSelected()) {
            activo = "0";
        } else {
            activo = "1";
        }
        Date fec = jdtNac.getDate();
        String dia = String.valueOf(fec.getDate());
        String mes = String.valueOf(fec.getMonth() + 1);
        String anio = String.valueOf(fec.getYear() + 1900);
        String fecha = anio + "-" + mes + "-" + dia;
        //System.out.println(fecha);
        sql = "update usuarios set NOM1_USU='" + txtNombre.getText().trim() + "' ,NOM2_USU='" + txtNombre1.getText().trim() + "' "
                + ",APE1_USU='" + txtApellido.getText().trim() + "' ,APE2_USU='" + txtApellido1.getText().trim() + "'"
                + ",TLF_USU='" + txtTelefono.getText().trim() + "' ,CELU_USU='" + txtCelular.getText().trim() + "' ,DIR_USU='" + txtDireccion.getText().trim() + "',"
                + "E_MAIL_USU='" + txtCorreo.getText().trim() + "' ,GEN_USU='" + jcmbGenero.getSelectedItem()
                + "' ,EST_CIV_USU='" + jcmbEstadoCivil.getSelectedItem() + "' ,PROCE_USU='" + jcmbProceso.getSelectedItem()
                + "' ,JERAR_USU='" + jcmbjerarquia.getSelectedItem() + "' ,ESTADO_USU='" + activo + "',FEC_NAC_USU='" + fecha + "' where CI_USU='" + txtCedula.getText() + "'";
        //System.out.println(sql);
        try {
            PreparedStatement ps = cc.prepareStatement(sql);
            int n = ps.executeUpdate();
            if (n > 0) {
                JOptionPane.showMessageDialog(null, "Actualización Correcta");
                nuevo();
                bloquearTodasCajas();
                bloquearTodoBotones();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erroe en la actualización de datos");
        }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton3 = new javax.swing.JButton();
        jbtGuardar = new javax.swing.JButton();
        jbtSalir = new javax.swing.JButton();
        jbtNuevo = new javax.swing.JButton();
        jbtActualizar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtApellido1 = new javax.swing.JTextField();
        txtApellido = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtNombre1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtCelular = new javax.swing.JTextField();
        txtDireccion = new javax.swing.JTextField();
        txtCorreo = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jcmbEstadoCivil = new javax.swing.JComboBox<String>();
        jLabel18 = new javax.swing.JLabel();
        jcmbGenero = new javax.swing.JComboBox<String>();
        jbtBuscar = new javax.swing.JButton();
        jchkExtranjero = new javax.swing.JCheckBox();
        txtCedula = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jdtNac = new com.toedter.calendar.JDateChooser();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jcmbProceso = new javax.swing.JComboBox();
        jcmbjerarquia = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jcActivo = new javax.swing.JCheckBox();
        txtClave = new javax.swing.JPasswordField();
        jbtPassword = new javax.swing.JButton();
        jbtCancelar = new javax.swing.JButton();

        jButton3.setText("jButton3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jbtGuardar.setText("Guardar");
        jbtGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtGuardarActionPerformed(evt);
            }
        });

        jbtSalir.setText("SALIR");
        jbtSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtSalirActionPerformed(evt);
            }
        });

        jbtNuevo.setText("Nuevo");
        jbtNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtNuevoActionPerformed(evt);
            }
        });

        jbtActualizar.setText("Actualizar");
        jbtActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtActualizarActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Personales"));

        jLabel2.setText("Nombre 1:");

        jLabel6.setText("Cédula:");

        jLabel9.setText("Apellido 2:");

        jLabel3.setText("Apellido 1:");

        jLabel10.setText("Nombre 2:");

        jLabel1.setText("Dirección:");

        jLabel4.setText("Correo:");

        jLabel11.setText("Teléfono:");

        jLabel12.setText("Celular:");

        txtDireccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDireccionActionPerformed(evt);
            }
        });

        jLabel17.setText("Estado Civíl:");

        jcmbEstadoCivil.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione uno", "SOLTERO/A", "CASADO/A", "DIVORCIADO/A", "VIUDO/A", "UNION LIBRE" }));
        jcmbEstadoCivil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcmbEstadoCivilActionPerformed(evt);
            }
        });

        jLabel18.setText("Género");

        jcmbGenero.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione uno", "MASCULINO", "FEMENINO", "NO DEFINIDO" }));
        jcmbGenero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcmbGeneroActionPerformed(evt);
            }
        });

        jbtBuscar.setText("Buscar");
        jbtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtBuscarActionPerformed(evt);
            }
        });
        jbtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jbtBuscarKeyTyped(evt);
            }
        });

        jchkExtranjero.setText("Extranjero");
        jchkExtranjero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkExtranjeroActionPerformed(evt);
            }
        });

        txtCedula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCedulaActionPerformed(evt);
            }
        });
        txtCedula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCedulaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCedulaKeyTyped(evt);
            }
        });

        jLabel13.setText("Fecha Nac.:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(jLabel17))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jcmbEstadoCivil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel18)
                                .addGap(18, 18, 18)
                                .addComponent(jcmbGenero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 6, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                    .addComponent(txtTelefono)
                                    .addComponent(txtApellido, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                    .addComponent(txtCedula))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtNombre1, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel9)
                                            .addComponent(jLabel12))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtCelular, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                            .addComponent(txtApellido1, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jbtBuscar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jchkExtranjero)
                                        .addGap(0, 10, Short.MAX_VALUE))))
                            .addComponent(txtDireccion, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                            .addComponent(txtCorreo, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jdtNac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel10)
                            .addComponent(txtNombre1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel9)
                            .addComponent(txtApellido1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel11))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel12)
                                    .addComponent(txtCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jbtBuscar)
                            .addComponent(jchkExtranjero)
                            .addComponent(jLabel6)
                            .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jdtNac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jcmbEstadoCivil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(jcmbGenero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Profesionales"));

        jLabel8.setText("Jerarquia:");

        jLabel5.setText("Proceso:");

        jcmbProceso.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione uno", "VENTAS", "COMPRAS", "BODEGA", "ADMINISTRATIVO" }));

        jcmbjerarquia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione uno", "ADMINISTRADOR", "ASISTENTE", "GERENTE" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jcmbjerarquia, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jcmbProceso, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jcmbProceso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jcmbjerarquia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de Cuenta"));

        jLabel7.setText("Clave:");

        jcActivo.setText("Activo");

        jbtPassword.setText("Cambiar Clave");
        jbtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtPasswordActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 16, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtClave, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jcActivo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbtPassword)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtClave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcActivo)
                    .addComponent(jbtPassword))
                .addContainerGap())
        );

        jbtCancelar.setText("Cancelar");
        jbtCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 24, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)))))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(162, 162, 162)
                .addComponent(jbtNuevo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtGuardar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtActualizar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtCancelar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtSalir)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtGuardar)
                    .addComponent(jbtActualizar)
                    .addComponent(jbtSalir)
                    .addComponent(jbtCancelar)
                    .addComponent(jbtNuevo))
                .addGap(22, 22, 22))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtGuardarActionPerformed
        // TODO add your handling code here:
        
       
        guardar();
    }//GEN-LAST:event_jbtGuardarActionPerformed

    private void jbtSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtSalirActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jbtSalirActionPerformed

    private void jbtNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtNuevoActionPerformed
        // TODO add your handling code here:
        nuevo();
        ActivarTodasCajas();
        ActivarTodoBotones();
        jbtBuscar.setEnabled(false);
        jbtActualizar.setEnabled(false);
        txtCedula.setEnabled(true);
        txtCedula.requestFocus();
        jbtNuevo.setEnabled(false);
        jbtPassword.setEnabled(false);
        txtClave.setEnabled(false);
    }//GEN-LAST:event_jbtNuevoActionPerformed

    private void jbtActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtActualizarActionPerformed
        // TODO add your handling code here:

        actualizar();
        jbtNuevo.setEnabled(true);
        txtCedula.setEnabled(true);

    }//GEN-LAST:event_jbtActualizarActionPerformed

    private void txtDireccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDireccionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDireccionActionPerformed

    private void jcmbEstadoCivilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcmbEstadoCivilActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcmbEstadoCivilActionPerformed

    private void jchkExtranjeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkExtranjeroActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jchkExtranjeroActionPerformed

    private void jcmbGeneroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcmbGeneroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcmbGeneroActionPerformed

    private void jbtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtBuscarActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jbtBuscarActionPerformed

    private void txtCedulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCedulaActionPerformed
        // TODO add your handling code here:

        jbtBuscar.requestFocus();
    }//GEN-LAST:event_txtCedulaActionPerformed

    private void jbtBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jbtBuscarKeyTyped
        // TODO add your handling code here:

        if (txtCedula.getText().length() == 10) {
            txtCedula.setEnabled(false);
            buscarCedulaCompleta(txtCedula.getText());
            jbtNuevo.setEnabled(false);
            ActivarTodasCajas();
            ActivarTodoBotones();
            jbtGuardar.setEnabled(false);
            jchkExtranjero.setEnabled(false);
            txtClave.setEnabled(true);
            jbtPassword.setEnabled(true);
            
        }
        if (txtCedula.getText().length() < 10) {
            buscar();
            buscarCedulaCompleta(txtCedula.getText());
            ActivarTodasCajas();
            ActivarTodoBotones();
            jbtGuardar.setEnabled(false);
            jchkExtranjero.setEnabled(false);
            txtClave.setEnabled(true);
            jbtPassword.setEnabled(true);
        }
    }//GEN-LAST:event_jbtBuscarKeyTyped

    private void txtCedulaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCedulaKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtCedulaKeyPressed

    private void txtCedulaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCedulaKeyTyped
        // TODO add your handling code here:
        if (txtCedula.getText().length() == 10) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCedulaKeyTyped

    private void jbtCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtCancelarActionPerformed
        // TODO add your handling code here:
        nuevo();
        bloquearTodasCajas();
        bloquearTodoBotones();
        txtCedula.setEnabled(true);
        txtCedula.requestFocus();
        jbtBuscar.setEnabled(true);
        jbtNuevo.setEnabled(true);
    }//GEN-LAST:event_jbtCancelarActionPerformed

    private void jbtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtPasswordActionPerformed
        // TODO add your handling code here:
        cambiarClave();
       
    }//GEN-LAST:event_jbtPasswordActionPerformed

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
            java.util.logging.Logger.getLogger(IngresoUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IngresoUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IngresoUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IngresoUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new IngresoUsuario().setVisible(true);
                } catch (SQLException ex) {
                    // Logger.getLogger(IngresoUsuario.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton jbtActualizar;
    private javax.swing.JButton jbtBuscar;
    private javax.swing.JButton jbtCancelar;
    private javax.swing.JButton jbtGuardar;
    private javax.swing.JButton jbtNuevo;
    private javax.swing.JButton jbtPassword;
    private javax.swing.JButton jbtSalir;
    private javax.swing.JCheckBox jcActivo;
    private javax.swing.JCheckBox jchkExtranjero;
    private javax.swing.JComboBox<String> jcmbEstadoCivil;
    private javax.swing.JComboBox<String> jcmbGenero;
    private javax.swing.JComboBox jcmbProceso;
    private javax.swing.JComboBox jcmbjerarquia;
    private com.toedter.calendar.JDateChooser jdtNac;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtApellido1;
    private javax.swing.JTextField txtCedula;
    private javax.swing.JTextField txtCelular;
    private javax.swing.JPasswordField txtClave;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNombre1;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}