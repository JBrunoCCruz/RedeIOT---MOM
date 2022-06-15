package interfacegrafica;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.UIManager;
import javax.swing.ImageIcon;

import queue.Consumidor;

public class Monitor {

	private JFrame frame;
	private JTextField textFieldNomeDaFila;
	
	static Consumidor consumidor;
	
	static int quantidadeDeMonitorados = 0;
	
	static ArrayList<String> listaFilasInscritas = new ArrayList<>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Monitor window = new Monitor();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Monitor() {
		initialize();
		try {
			consumidor = new Consumidor();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 450, 577);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblTitulo = new JLabel("Monitor de sensores");
		lblTitulo.setForeground(Color.BLUE);
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setBounds(120, 11, 200, 18);
		frame.getContentPane().add(lblTitulo);
		
		JScrollPane scrollPaneLista = new JScrollPane();
		scrollPaneLista.setBounds(23, 69, 388, 60);
		frame.getContentPane().add(scrollPaneLista);
		
		JTextArea textAreaLista = new JTextArea();
		textAreaLista.setEditable(false);
		textAreaLista.setFont(new Font("Monospaced", Font.PLAIN, 15));
		scrollPaneLista.setViewportView(textAreaLista);
		
		JButton btnAtualizarLista = new JButton("Atualizar");
		btnAtualizarLista.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				atualizaFilaDeSensores(textAreaLista);
				try {
					consumidor.atualizaListaDeFilasExistentes(textAreaLista);
				} catch (Exception e2) {
					e2.printStackTrace();
				}				
			}
		});
		btnAtualizarLista.setBounds(174, 138, 89, 23);
		frame.getContentPane().add(btnAtualizarLista);
		
		JLabel lblLista = new JLabel("Lista de filas");
		lblLista.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblLista.setHorizontalAlignment(SwingConstants.CENTER);
		lblLista.setBounds(120, 40, 200, 20);
		frame.getContentPane().add(lblLista);
		
		JLabel lblNomeDaFila = new JLabel("Nome da fila");
		lblNomeDaFila.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNomeDaFila.setHorizontalAlignment(SwingConstants.CENTER);
		lblNomeDaFila.setBounds(87, 179, 100, 14);
		frame.getContentPane().add(lblNomeDaFila);
		
		textFieldNomeDaFila = new JTextField();
		textFieldNomeDaFila.setFont(new Font("Tahoma", Font.PLAIN, 13));
		textFieldNomeDaFila.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldNomeDaFila.setBounds(42, 204, 200, 23);
		frame.getContentPane().add(textFieldNomeDaFila);
		textFieldNomeDaFila.setColumns(10);
		
		
		
		JSeparator separatorMenu = new JSeparator();
		separatorMenu.setBounds(19, 250, 400, 15);
		frame.getContentPane().add(separatorMenu);
		
		JLabel lblSensor1 = new JLabel("...");
		lblSensor1.setHorizontalAlignment(SwingConstants.CENTER);
		lblSensor1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSensor1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		lblSensor1.setBounds(25, 316, 120, 18);
		frame.getContentPane().add(lblSensor1);
		
		JLabel lblSensor2 = new JLabel("...");
		lblSensor2.setHorizontalAlignment(SwingConstants.CENTER);
		lblSensor2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSensor2.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		lblSensor2.setBounds(25, 370, 120, 18);
		frame.getContentPane().add(lblSensor2);
		
		JLabel lblSensor3 = new JLabel("...");
		lblSensor3.setHorizontalAlignment(SwingConstants.CENTER);
		lblSensor3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSensor3.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		lblSensor3.setBounds(23, 428, 120, 18);
		frame.getContentPane().add(lblSensor3);
		
		JLabel lblSensor = new JLabel("Sensor");
		lblSensor.setHorizontalAlignment(SwingConstants.CENTER);
		lblSensor.setBounds(60, 271, 46, 14);
		frame.getContentPane().add(lblSensor);
		
		JLabel lblLimitesAtingidos = new JLabel("Limites atingidos");
		lblLimitesAtingidos.setHorizontalAlignment(SwingConstants.CENTER);
		lblLimitesAtingidos.setBounds(160, 271, 110, 14);
		frame.getContentPane().add(lblLimitesAtingidos);
		
		JLabel lblVezesForaDos = new JLabel("Vezes fora dos limites");
		lblVezesForaDos.setHorizontalAlignment(SwingConstants.CENTER);
		lblVezesForaDos.setBounds(275, 271, 140, 15);
		frame.getContentPane().add(lblVezesForaDos);
		
		JLabel lblLimites1 = new JLabel();		
		lblLimites1.setIcon(new ImageIcon(Monitor.class.getResource("/imagens/pecaverde.png")));
		lblLimites1.setHorizontalAlignment(SwingConstants.CENTER);
		lblLimites1.setBounds(196, 310, 30, 30);
		frame.getContentPane().add(lblLimites1);
		
		JLabel lblLimites2 = new JLabel();
		lblLimites2.setIcon(new ImageIcon(Monitor.class.getResource("/imagens/pecaverde.png")));
		lblLimites2.setHorizontalAlignment(SwingConstants.CENTER);
		lblLimites2.setBounds(196, 365, 30, 30);
		frame.getContentPane().add(lblLimites2);
		
		JLabel lblLimites3 = new JLabel();
		lblLimites3.setIcon(new ImageIcon(Monitor.class.getResource("/imagens/pecaverde.png")));
		lblLimites3.setHorizontalAlignment(SwingConstants.CENTER);
		lblLimites3.setBounds(196, 422, 30, 30);
		frame.getContentPane().add(lblLimites3);
		
		JLabel lblForaDosLimites1 = new JLabel("...");
		lblForaDosLimites1.setHorizontalAlignment(SwingConstants.CENTER);
		lblForaDosLimites1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblForaDosLimites1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		lblForaDosLimites1.setBounds(326, 316, 50, 18);
		frame.getContentPane().add(lblForaDosLimites1);
		
		JLabel lblForaDosLimites2 = new JLabel("...");
		lblForaDosLimites2.setHorizontalAlignment(SwingConstants.CENTER);
		lblForaDosLimites2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblForaDosLimites2.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		lblForaDosLimites2.setBounds(326, 373, 50, 18);
		frame.getContentPane().add(lblForaDosLimites2);
		
		JLabel lblForaDosLimites3 = new JLabel("...");
		lblForaDosLimites3.setHorizontalAlignment(SwingConstants.CENTER);
		lblForaDosLimites3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblForaDosLimites3.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		lblForaDosLimites3.setBounds(326, 428, 50, 18);
		frame.getContentPane().add(lblForaDosLimites3);
		
		JLabel lblLegenda = new JLabel("Legenda");
		lblLegenda.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblLegenda.setHorizontalAlignment(SwingConstants.CENTER);
		lblLegenda.setBounds(183, 475, 60, 18);
		frame.getContentPane().add(lblLegenda);
		
		JLabel lblNew_2 = new JLabel();
		lblNew_2.setIcon(new ImageIcon(Monitor.class.getResource("/imagens/pecaverde.png")));
		lblNew_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNew_2.setBounds(10, 498, 30, 30);
		frame.getContentPane().add(lblNew_2);
		
		JLabel lblNew_2_1 = new JLabel();
		lblNew_2_1.setIcon(new ImageIcon(Monitor.class.getResource("/imagens/pecavermelha.png")));
		lblNew_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNew_2_1.setBounds(210, 498, 30, 30);
		frame.getContentPane().add(lblNew_2_1);
		
		JLabel lblLimitesNuncaAtingidos = new JLabel("Limites nunca atingidos");
		lblLimitesNuncaAtingidos.setHorizontalAlignment(SwingConstants.CENTER);
		lblLimitesNuncaAtingidos.setBounds(50, 505, 135, 14);
		frame.getContentPane().add(lblLimitesNuncaAtingidos);
		
		JLabel lblLimitesAtingidosAlguma = new JLabel("Limites atingidos alguma vez");
		lblLimitesAtingidosAlguma.setHorizontalAlignment(SwingConstants.CENTER);
		lblLimitesAtingidosAlguma.setBounds(250, 505, 170, 14);
		frame.getContentPane().add(lblLimitesAtingidosAlguma);
		
		JSeparator separatorSensores = new JSeparator();
		separatorSensores.setBounds(23, 465, 400, 10);
		frame.getContentPane().add(separatorSensores);
		
		JButton btnMonitorarFila = new JButton("Monitorar");
		btnMonitorarFila.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!textFieldNomeDaFila.getText().isBlank() && !listaFilasInscritas.contains(textFieldNomeDaFila.getText() ) ) {
					if (quantidadeDeMonitorados == 0) {
						try {
							consumidor.monitorar(textFieldNomeDaFila.getText(), lblSensor1, lblLimites1, lblForaDosLimites1);
							listaFilasInscritas.add(textFieldNomeDaFila.getText());
							quantidadeDeMonitorados++;
						} catch (Exception e2) {
							e2.printStackTrace();
						}						
					} else if (quantidadeDeMonitorados == 1) {
						try {
							consumidor.monitorar(textFieldNomeDaFila.getText(), lblSensor2, lblLimites2, lblForaDosLimites2);
							listaFilasInscritas.add(textFieldNomeDaFila.getText());
							quantidadeDeMonitorados++;
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					} else if (quantidadeDeMonitorados == 2) {
						try {
							consumidor.monitorar(textFieldNomeDaFila.getText(), lblSensor3, lblLimites3, lblForaDosLimites3);
							listaFilasInscritas.add(textFieldNomeDaFila.getText());
							quantidadeDeMonitorados++;
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
				}					
			}
		});
		btnMonitorarFila.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnMonitorarFila.setBounds(272, 203, 89, 23);
		frame.getContentPane().add(btnMonitorarFila);
	}
	
	public void atualizaFilaDeSensores (JTextArea textAreaLista) {
		System.out.println("Atualizando fila...");
		textAreaLista.setText(textAreaLista.getText() + "\r\nNovo sensor");
		return;
	}
	
	public void monitoraSensor (String nomeDaFila, JFrame sensor, JFrame limite, JFrame vezesFora) {
		System.out.println("Monitorando novo sensor..." + nomeDaFila);
				
		return;
	}
}
