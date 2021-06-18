package Tiendecita;
import java.awt.EventQueue;
import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.JLabel;
import java.awt.Choice;
import java.awt.Dialog;
import javax.swing.JButton;

/**
 * 
 * Clase para la consulta de los tickets
 * 
 * 
 * @author polib
 * @since 10/06/2021
 * @version 1.0
 * 
 * 
 */
public class ConsultaTickets extends JFrame {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFechaTicket;
	private JTextField textArticulosTicket;
	private JTextField textTotal;
	/**
	 * Llamada a clase BDCon para la base de datos
	 * Constructor sin parámetros
	 * 
	 */

	BDCon bd = new BDCon();
	Connection conexion = null;
	String[] cadena;
	int idTicketsEditar;
	int idArticuloEditar;
	Dialog dlgMensaje = new Dialog(this,"Mensaje", true);
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConsultaTickets frame = new
							ConsultaTickets();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the frame.
	 */
	public ConsultaTickets() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);


		JLabel lblTicket = new JLabel("Ticket");
		lblTicket.setBounds(12, 39, 87, 14);
		contentPane.add(lblTicket);

		JLabel lblFechaTicket = new JLabel("Fecha Ticket:");
		lblFechaTicket.setBounds(12, 90, 80, 14);
		contentPane.add(lblFechaTicket);

		JLabel lblArticulosTicket = new JLabel("Articulos Ticket:");
		lblArticulosTicket.setBounds(12, 130, 87, 14);
		contentPane.add(lblArticulosTicket);

		JLabel lblTotal = new JLabel("Total:");
		lblTotal.setBounds(12, 160, 87, 14);
		contentPane.add(lblTotal);

		textFechaTicket = new JTextField();
		textFechaTicket.setBounds(209, 90, 238, 20);
		contentPane.add(textFechaTicket);
		textFechaTicket.setColumns(10);

		textArticulosTicket = new JTextField();
		textArticulosTicket.setColumns(20);
		textArticulosTicket.setBounds(209, 130, 238, 20);
		contentPane.add(textArticulosTicket);

		textTotal = new JTextField();
		textTotal.setColumns(10);
		textTotal.setBounds(209, 160, 238, 20);
		contentPane.add(textTotal);

		Choice choiceSelecTicket = new Choice();
		choiceSelecTicket.setBounds(209, 36, 238, 20);
		contentPane.add(choiceSelecTicket);
		choiceSelecTicket.add("Seleccionar un Ticket...");
		// Conectar BD
		conexion = bd.conectar();
		cadena =(bd.ConsultarTicketsChoice(conexion)).split("#");
		for(int i = 0; i < cadena.length; i++)
		{
			choiceSelecTicket.add(cadena[i]);
		}
		/**
		 * Conexion a la base de datos
		 * @param conexion, llamada al metodo para conectarnos a la base de datos
		 * @param cadena, llamada al metodo para consultar los articulos con el choice
		 */
		add(choiceSelecTicket);
		JButton btnSeleccion = new JButton("Seleccionar");
		btnSeleccion.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				if(choiceSelecTicket.getSelectedItem().equals("Seleccionar un Ticket..."))
				{
					// Vacio
				}
				else
				{
					// Coger el elemento seleccionado
					String[] tabla =choiceSelecTicket.getSelectedItem().split("-");
					// El idCliente que quieroeditar está en tabla[0]
					idTicketsEditar =Integer.parseInt(tabla[0]);
					cadena = (bd.consultarTickets(conexion, idTicketsEditar)).split("#");
					textFechaTicket.setText(cadena[1]);
					textArticulosTicket.setText(cadena[3]);
					textTotal.setText(cadena[2]);
				}
			}
			/**
			 * Choice para seleccionar el ticket
			 * @param cadena, llamada al metodo para consultar los articulos con el choice 
			 * @param idTicketsEditar, id del ticket a editar
			 * 
			 */
		});
		btnSeleccion.setBounds(345, 61, 102, 23);
		contentPane.add(btnSeleccion);
		setVisible(true);
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				//Cierra la aplicacion
				//System.exit(0);
			}
		});
		btnSalir.setBounds(345, 190, 102, 23);
		contentPane.add(btnSalir);
		JLabel lblTitulo = new JLabel("Consulta de tickets");
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTitulo.setBounds(250, 11, 134, 14);
		contentPane.add(lblTitulo);
		setVisible(true);

		JButton btniReport = new JButton("iReport");
		btniReport.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try
					{
						// Compilar el informe generando el fichero jasper
						JasperCompileManager.compileReportToFile("TICKETS.jrxml");
						System.out.println("El fichero ha sido generado satisfactoriamente.");
						// Guardar parametros
						HashMap<String,Object> parametros = new HashMap<String,Object>();
						JasperReport report = (JasperReport)
								JRLoader.loadObjectFromFile("TICKETS.jasper");
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
		btniReport.setBounds(12, 190, 102, 23);
		contentPane.add(btniReport);
	}
}		
