package Tiendecita;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Choice;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.awt.event.ActionEvent;

public class BajaArticulos extends JFrame {
	int idArticulosBorrar = 0;
	BDCon bd = new BDCon();
	Connection conexion = null;
	String[] cadena;

	/**
	 * 
	 * Clase para la baja de los articulos
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
	/**
	 * Create the frame.
	 */
	public BajaArticulos() {
		setTitle("Baja de art\u00EDculo");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 396, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Choice choiceSelecArt = new Choice();
		choiceSelecArt.setBounds(122, 57, 235, 20);
		contentPane.add(choiceSelecArt);
		
		JLabel lblSelecArt = new JLabel("Seleccione el art\u00EDculo a dar de baja:");
		lblSelecArt.setBounds(10, 37, 209, 14);
		contentPane.add(lblSelecArt);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(choiceSelecArt.getSelectedItem().equals("Seleccionar un articulo..."))
				{
					// No hacemos nada
				}
				else
				{
					// Coger el elemento seleccionado
					String[] tabla = choiceSelecArt.getSelectedItem().split("-");
					// El idCliente que quiero borrar está en tabla[0]
					idArticulosBorrar = Integer.parseInt(tabla[0]);
					if((bd.BajaArticulos(conexion, idArticulosBorrar))==0)
					{
						// Todo bien
						System.out.println("Baja de articulo correcta");
						setVisible(false);
					}
					else
					{
						// Error
						System.out.println("Error al borrar el articulo");
					}
					// Desconectar BD
					bd.desconectar(conexion);
				}
			}
			/**
			 * Choice para elegir articulo
			 * @param conexion, llamada al metodo de conexion a la base de datos
			 * @param cadena, llamada al metodo creado en BDCon para el choice consulta de articulos
			 * @param idArticulosBorrar, id del articulo que se vsa a eliminar
			 */
		});
		btnAceptar.setBounds(10, 120, 89, 23);
		contentPane.add(btnAceptar);
		
		choiceSelecArt.add("Seleccionar un articulo...");
		// Conectar BD
		conexion = bd.conectar();
		cadena = (bd.consultarArticulosChoice(conexion)).split("#");
		for(int i = 0; i < cadena.length; i++)
		{
			choiceSelecArt.add(cadena[i]);
		}
		add(choiceSelecArt);
		/**
		 * Conexion a la base de datos
		 * @param conexion, llamada al metodo para conectarnos a la base de datos
		 * @param cadena, llamada al metodo para consultar los articulos con el choice
		 */
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				//Cierra la aplicacion
				//System.exit(0);
			}
		});
		btnCancelar.setBounds(268, 120, 89, 23);
		contentPane.add(btnCancelar);
		setVisible(true);
	}
}
