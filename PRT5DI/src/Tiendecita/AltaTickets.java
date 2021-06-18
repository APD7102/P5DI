package Tiendecita;

import java.awt.Choice;
import java.awt.Dialog;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.text.ParseException;
import java.awt.event.ActionEvent;

public class AltaTickets extends JFrame {
	/**
	 * 
	 * Clase para el alta de los tickets
	 * 
	 * 
	 * @author jalca
	 * @since 10/06/2021
	 * @version 1.0
	 * 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFecha;
	private JTextField textArticulo;
	private JTextField textTotal;

	BDCon bd = new BDCon();
	Connection conexion = null;
	String[] cadena;
	int idArticuloEditar;
	Dialog dlgMensaje = new Dialog(this,"Mensaje", true);
	/**
	 * Create the frame.
	 */
	public AltaTickets() throws ParseException{
		setTitle("Alta de Ticket");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(200, 200, 500, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);


		JLabel lblFecha = new JLabel("Fecha (YYYY-MM-DD):");
		lblFecha.setBounds(12, 39, 87, 14);
		contentPane.add(lblFecha);

		JLabel lblArticulo = new JLabel("Articulo:");
		lblArticulo.setBounds(12, 76, 80, 14);
		contentPane.add(lblArticulo);

		JLabel lblCantidad = new JLabel("Total:");
		lblCantidad.setBounds(12, 140, 87, 14);
		contentPane.add(lblCantidad);

		textFecha = new JTextField();
		textFecha.setBounds(209, 36, 238, 20);
		contentPane.add(textFecha);
		textFecha.setColumns(10);
		
		Choice choiceSelecArt = new Choice();
		choiceSelecArt.setBounds(209, 71, 238, 20);
		contentPane.add(choiceSelecArt);


		choiceSelecArt.add("Seleccionar un Articulo...");
		// Conectar BD
		conexion = bd.conectar();
		cadena = (bd.consultarArticulosChoice(conexion)).split("#");
		for(int i = 0; i < cadena.length; i++)
		{
			choiceSelecArt.add(cadena[i]);
		}
		add(choiceSelecArt);
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
				}
			}
		});
		btnSeleccionar.setBounds(345, 107, 102, 23);
		contentPane.add(btnSeleccionar);
		setVisible(true);
	
		textTotal = new JTextField();
		textTotal.setColumns(10);
		textTotal.setBounds(209, 140, 238, 20);
		contentPane.add(textTotal);

		JButton btnAceptar = new JButton("Aceptar");

		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Conectar BD
				conexion = bd.conectar();
				String sentencia = "INSERT INTO tickets VALUES(null,'"+textFecha.getText()+"','"+textArticulo.getText()+"','"+textTotal.getText()+"')";
				if((bd.AltaTickets(conexion, sentencia))==0)
				{
					// Todo bien
					System.out.println("Alta de tickets correcta");
				}
				else
				{
					// Error
					System.out.println("Error en Alta de tickets");
				}
				bd.desconectar(conexion);
				// Desconectar
			}
		});
		btnAceptar.setBounds(28, 170, 89, 23);
		contentPane.add(btnAceptar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				//Cierra la aplicacion
				//System.exit(0);
			}
		});
		btnCancelar.setBounds(345, 170, 102, 23);
		contentPane.add(btnCancelar);

		JLabel lblTitulo = new JLabel("Alta de tickets");
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTitulo.setBounds(250, 11, 134, 14);
		contentPane.add(lblTitulo);
		setVisible(true);
	}
}