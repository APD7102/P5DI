package Tiendecita;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.awt.event.ActionEvent;
import javax.swing.JMenu;

import Tiendecita.AltaTickets;
import Tiendecita.BajaArticulos;
import Tiendecita.ModificacionArticulos;
import Tiendecita.AltaArticulos;
import Tiendecita.ConsultaArticulos;
import Tiendecita.ConsultaTickets;
/**
 * 
 * Clase para la creacion de la ventana principal
 * 
 * 
 * @author polib
 * @since 10/06/2021
 * @version 1.0
 * 
 * 
 */

public class VentanaPrincipal extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal frame = new VentanaPrincipal();
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
	JMenuItem menuAltaArticulos = new JMenuItem("Alta");
	JMenuItem menuBajaArticulos = new JMenuItem("Baja");
	JMenuItem menuModificacionArticulos = new JMenuItem("Modificacion");
	JMenuItem menuConsultaArticulos = new JMenuItem("Consulta");
	JMenuItem menuAltaTickets = new JMenuItem("Alta");
	JMenuItem menuConsultaTickets = new JMenuItem("Consulta");
	public VentanaPrincipal() {
		setTitle("Tiendecita");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnArticulos = new JMenu("Articulos");
		menuBar.add(mnArticulos);


		mnArticulos.add(menuAltaArticulos);
		mnArticulos.add(menuBajaArticulos);
		mnArticulos.add(menuModificacionArticulos);
		mnArticulos.add(menuConsultaArticulos);
		
		JMenu mnTickets = new JMenu("Tickets");
		menuBar.add(mnTickets);


		mnTickets.add(menuAltaTickets);
		mnTickets.add(menuConsultaTickets);
		
		menuAltaArticulos.addActionListener(this);
		menuBajaArticulos.addActionListener(this);
		menuModificacionArticulos.addActionListener(this);
		menuConsultaArticulos.addActionListener(this);
		
		menuAltaTickets.addActionListener(this);
		menuConsultaTickets.addActionListener(this);
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

	}
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==menuAltaArticulos) {
			new AltaArticulos();
		}
		if (e.getSource()==menuBajaArticulos) {
			new BajaArticulos();
		}
		if (e.getSource()==menuModificacionArticulos) {
			new ModificacionArticulos();
		}  
		if (e.getSource()==menuConsultaArticulos) {
			new ConsultaArticulos();
		}
		if (e.getSource()==menuAltaTickets) {
			try {
				new AltaTickets();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (e.getSource()==menuConsultaTickets) {
			new ConsultaTickets();
		}
	}
}
