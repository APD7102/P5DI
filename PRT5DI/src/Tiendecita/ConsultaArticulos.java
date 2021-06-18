package Tiendecita;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;


import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.HashMap;
import java.awt.event.ActionEvent;
import java.awt.Choice;


/**
 * 
 * Clase para la consulta de los articulos
 * 
 * 
 * @author polib
 * @since 10/06/2021
 * @version 1.0
 * 
 * 
 */
public class ConsultaArticulos extends JFrame {
	BDCon bd = new BDCon();
	Connection conexion = null;
	String[] cadena;
	int idArticuloEditar;
	/**
	 * Llamada a clase BDCon para la base de datos
	 * Constructor sin parámetros
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textDescripcion;
	private JTextField textPrecio;
	private JTextField textCantidad;

	/**
	 * Create the frame.
	 */
	public ConsultaArticulos() {
		setTitle("Consultar Articulos");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 388, 368);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);


		JLabel lblPrecio = new JLabel("Precio:");
		lblPrecio.setBounds(10, 195, 82, 14);
		contentPane.add(lblPrecio);

		JLabel lblDescripcion = new JLabel("Descripci\u00F3n:");
		lblDescripcion.setBounds(10, 158, 89, 14);
		contentPane.add(lblDescripcion);

		JLabel lblCantidad = new JLabel("Cantidad:");
		lblCantidad.setBounds(10, 236, 89, 14);
		contentPane.add(lblCantidad);

		textPrecio = new JTextField();
		textPrecio.setColumns(10);
		textPrecio.setBounds(109, 192, 238, 20);
		contentPane.add(textPrecio);


		textDescripcion = new JTextField();
		textDescripcion.setBounds(109, 155, 238, 20);
		contentPane.add(textDescripcion);
		textDescripcion.setColumns(10);


		textCantidad = new JTextField();
		textCantidad.setColumns(10);
		textCantidad.setBounds(109, 233, 238, 20);
		contentPane.add(textCantidad);


		JButton btnSalir = new JButton("Salir");

		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				//Cierra la aplicacion
				//System.exit(0);
			}
		});
		btnSalir.setBounds(258, 274, 89, 23);
		contentPane.add(btnSalir);
		
		JButton btniReport = new JButton("iReport");
		btniReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					// Compilar el informe generando el fichero jasper
					JasperCompileManager.compileReportToFile("ARTICULOS.jrxml");
					System.out.println("El fichero ha sido generado satisfactoriamente.");
					// Guardar parametros
					HashMap<String,Object> parametros = new HashMap<String,Object>();
					JasperReport report = (JasperReport)
					JRLoader.loadObjectFromFile("report3.jasper");
					// Completar el informe
					JasperPrint print = JasperFillManager.fillReport(report, parametros, conexion);
					// Mostrar el informe en JasperViewer
					JasperViewer.viewReport(print, false);
					}
					catch (Exception er)
					{
					System.out.println("Error: " + er.toString());
					}
			}
			/**
			 * Creacion boton iReport
			 * @param print, imprimir en pantalla el fichero iReport
			 * @param parametros, guardar los parametros introducidos
			 * @param report, llamada al metodo para completar el informe
			 */
		});
		btniReport.setBounds(12, 274, 89, 23);
		contentPane.add(btniReport);


		JLabel lblTitulo = new JLabel("Consultar articulo");
		lblTitulo.setFont(new Font("Arial", Font.PLAIN, 18));
		lblTitulo.setBounds(136, 11, 134, 14);
		contentPane.add(lblTitulo);

		Choice choiceSelecArt = new Choice();
		choiceSelecArt.setBounds(109, 71, 238, 20);
		contentPane.add(choiceSelecArt);


		choiceSelecArt.add("Seleccionar un Articulo...");
		// Conectar BD
		conexion = bd.conectar();
		cadena = (bd.consultarArticulosChoice(conexion)).split("#");
		for(int i = 0; i < cadena.length; i++)
		{
			choiceSelecArt.add(cadena[i]);
		}
		/**
		 * Conexion a la base de datos
		 * @param conexion, llamada al metodo para conectarnos a la base de datos
		 * @param cadena, llamada al metodo para consultar los articulos con el choice
		 */
		add(choiceSelecArt);

		JLabel lblSeleccionaUnArtculo = new JLabel("Seleccione un articulo:");
		lblSeleccionaUnArtculo.setBounds(10, 46, 156, 20);
		contentPane.add(lblSeleccionaUnArtculo);

		JButton btnSeleccionar = new JButton("Seleccionar");
		btnSeleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(choiceSelecArt.getSelectedItem().equals("Seleccionar un Articulo..."))
				{
					// Vacio
				}
				else
				{
					// Coger el elemento seleccionado
					String[] tabla = choiceSelecArt.getSelectedItem().split("-");
					// El idCliente que quiero editar está en tabla[0]
					idArticuloEditar = Integer.parseInt(tabla[0]);
					cadena = (bd.consultarArticulo(conexion, idArticuloEditar)).split("#");
					// cadena[0] = idArticulo
					// cadena[1] = nombreArticulo
					// cadena[2] = cantidadArticulo 
					// cadena[3] = precioArticulo
					textDescripcion.setText(cadena[1]);
					textPrecio.setText(cadena[3]);
					textCantidad.setText(cadena[2]);
				}
			}
			/**
			 * Choice para consultar el articulo
			 *@param conexion, llamada al metodo para conectarnos a la base de datos
			 *@param cadena, llamada al metodo para consultar los articulos con el choice
			 *
			 */
		});
		btnSeleccionar.setBounds(245, 107, 102, 23);
		contentPane.add(btnSeleccionar);
		setVisible(true);
	}
}

