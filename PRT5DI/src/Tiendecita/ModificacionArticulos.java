package Tiendecita;

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
import java.awt.Choice;
/**
 * 
 * Clase para la modificacion de los articulos
 * 
 * 
 * @author polib
 * @since 10/06/2021
 * @version 1.0
 * 
 * 
 */
public class ModificacionArticulos extends JFrame {
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
	public ModificacionArticulos() {
		setTitle("Modificar Art\u00EDculo");
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
		
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Hacer UPDATE
				String sentencia = "UPDATE articulos SET DescripcionArticulos = '"+textDescripcion.getText()+"', CantidadStock = '"+textCantidad.getText()+"', PrecioArticulos= '"+textPrecio.getText()+"' WHERE idArticulos = "+idArticuloEditar;
				// Feedback
				if((bd.ModificacionArticulos(conexion, sentencia))==0)
				{
					// Todo bien
					System.out.println("Modificación de Articulo correcta");
					setVisible(false);
				}
				else
				{
					// Error
					System.out.println("Error en Modificación de Articulo");
				}
				// Desconectar
				bd.desconectar(conexion);
			}
			/**
			 * Conexion con la base de datos para dar de alta los articulos
			 * @param sentencia, sentencia para la modificacion de los articulos en la base de datos
			 * @param conexion, llamada al metodo de conexión con la base de datos
			 */
	
		});
		btnAceptar.setBounds(28, 274, 89, 23);
		contentPane.add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				//Cierra la aplicacion
				//System.exit(0);
			}
		});
		btnCancelar.setBounds(258, 274, 89, 23);
		contentPane.add(btnCancelar);
		
		JLabel lblTitulo = new JLabel("Modificar art\u00EDculo");
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 18));
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
		
		JLabel lblSeleccionaUnArtculo = new JLabel("Selecciona un art\u00EDculo a editar:");
		lblSeleccionaUnArtculo.setBounds(10, 46, 156, 20);
		contentPane.add(lblSeleccionaUnArtculo);
		
		JButton btnSeleccionar = new JButton("Seleccionar");
		btnSeleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(choiceSelecArt.getSelectedItem().equals("Seleccionar un Articulo..."))
				{
					// No hacemos nada
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
