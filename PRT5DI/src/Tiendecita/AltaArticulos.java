package Tiendecita;

import java.awt.Dialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.awt.event.ActionEvent;

public class AltaArticulos extends JFrame {

	/**
	 * 
	 * Clase para el alta de los articulos
	 * 
	 * 
	 * @author polib
	 * @since 10/06/2021
	 * @version 1.0
	 * 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textDescripcion;
	private JTextField textPrecio;
	private JTextField textCantidad;
	
	/**
	 * Llamada a clase BDCon para la base de datos
	 * 
	 * 
	 */
	BDCon bd = new BDCon();
	Connection conexion = null;
	Dialog dlgMensaje = new Dialog(this,"Mensaje", true);

	/**
	 * Creacion de la ventana.
	 */
	public AltaArticulos() {
		
		setTitle("Alta de Art\u00EDculo");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 388, 246);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblDescripcion = new JLabel("Descripci\u00F3n:");
		lblDescripcion.setBounds(12, 39, 87, 14);
		contentPane.add(lblDescripcion);
		
		JLabel lblPrecio = new JLabel("Precio:");
		lblPrecio.setBounds(12, 76, 80, 14);
		contentPane.add(lblPrecio);
		
		JLabel lblCantidad = new JLabel("Cantidad:");
		lblCantidad.setBounds(12, 117, 87, 14);
		contentPane.add(lblCantidad);
		
		textDescripcion = new JTextField();
		textDescripcion.setBounds(109, 36, 238, 20);
		contentPane.add(textDescripcion);
		textDescripcion.setColumns(10);
		
		textPrecio = new JTextField();
		textPrecio.setColumns(10);
		textPrecio.setBounds(109, 73, 238, 20);
		contentPane.add(textPrecio);
		
		textCantidad = new JTextField();
		textCantidad.setColumns(10);
		textCantidad.setBounds(109, 114, 238, 20);
		contentPane.add(textCantidad);
		
		/**
		 * 
		 * Conexion con la base de datos para dar de alta los articulos
		 * 
		 */
		JButton btnAceptar = new JButton("Aceptar");
		
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Conectar BD
				conexion = bd.conectar();
				String sentencia = "INSERT INTO articulos VALUES(null,'"+textPrecio.getText()+"','"+textDescripcion.getText()+"','"+textCantidad.getText()+"')";
				if((bd.AltaArticulos(conexion, sentencia))==0)
				{
					// Todo bien
					System.out.println("Alta de Artículo correcta");
				}
				else
				{
					// Error
					System.out.println("Error en Alta de Articulo");
				}
				bd.desconectar(conexion);
				// Desconectar
			}
		});
		btnAceptar.setBounds(28, 155, 89, 23);
		contentPane.add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
						setVisible(false);
						//Cierra la aplicacion
						//System.exit(0);
			}
		});
		btnCancelar.setBounds(258, 155, 89, 23);
		contentPane.add(btnCancelar);
		
		JLabel lblTitulo = new JLabel("Alta de art\u00EDculo");
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTitulo.setBounds(136, 11, 134, 14);
		contentPane.add(lblTitulo);
		setVisible(true);
	}
}
