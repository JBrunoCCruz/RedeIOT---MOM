package interfacegrafica;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.TextField;

import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JSeparator;

import queue.Produtor;

public class Instanciador {

	private JFrame frame;
	private JTextField textMin;
	private JTextField textMax;
	
	static Produtor produtor;
	
	// 1 - Temperatura | 2 - Umidade | 3 - Velocidade
	static ArrayList<Boolean> sensoresExistentesArray = new ArrayList<>(Arrays.asList(false, false, false));
	
	// Valores máximos e mínimos dos sensores
	String tempMin = "0", tempMax = "1";
	String umidMin = "0", umidMax = "1";
	String veloMin = "0", veloMax = "1";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Instanciador window = new Instanciador();
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
	public Instanciador() {
		initialize();
		try {
			produtor = new Produtor();			
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
		frame.setBounds(100, 100, 450, 540);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblTitulo = new JLabel("Sensores");
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTitulo.setBounds(178, 11, 70, 22);
		frame.getContentPane().add(lblTitulo);
		
		JRadioButton rdbtnTemperatura = new JRadioButton("Temperatura");
		rdbtnTemperatura.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rdbtnTemperatura.setBounds(17, 81, 126, 23);
		frame.getContentPane().add(rdbtnTemperatura);
		
		JRadioButton rdbtnUmidade = new JRadioButton("Umidade");
		rdbtnUmidade.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rdbtnUmidade.setBounds(172, 81, 109, 23);
		frame.getContentPane().add(rdbtnUmidade);
		
		JRadioButton rdbtnVelocidade = new JRadioButton("Velocidade");
		rdbtnVelocidade.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rdbtnVelocidade.setBounds(305, 81, 109, 23);
		frame.getContentPane().add(rdbtnVelocidade);
		
		ButtonGroup buttongroup = new ButtonGroup();
		buttongroup.add(rdbtnTemperatura);
		buttongroup.add(rdbtnUmidade);
		buttongroup.add(rdbtnVelocidade);		
		
		JLabel lblValores = new JLabel("Valores:");
		lblValores.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblValores.setBounds(28, 160, 70, 14);
		frame.getContentPane().add(lblValores);
		
		textMin = new JTextField("0");
		textMin.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textMin.setHorizontalAlignment(SwingConstants.CENTER);
		textMin.setBounds(120, 159, 86, 20);
		frame.getContentPane().add(textMin);
		textMin.setColumns(10);
		
		textMax = new JTextField("10");
		textMax.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textMax.setHorizontalAlignment(SwingConstants.CENTER);
		textMax.setBounds(280, 159, 86, 20);
		frame.getContentPane().add(textMax);
		textMax.setColumns(10);
		
		JLabel lblMin = new JLabel("Min");
		lblMin.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblMin.setBounds(216, 162, 46, 14);		
		frame.getContentPane().add(lblMin);
		
		JLabel lblMax = new JLabel("Max");
		lblMax.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblMax.setBounds(378, 162, 46, 14);
		frame.getContentPane().add(lblMax);
		
		JButton btnInstanciar = new JButton("Instanciar");
		btnInstanciar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				novoSensor (rdbtnTemperatura.isSelected(), rdbtnUmidade.isSelected(), rdbtnVelocidade.isSelected(), textMin.getText(), textMax.getText());
			}
		});
		btnInstanciar.setBounds(178, 228, 100, 23);
		frame.getContentPane().add(btnInstanciar);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(17, 267, 396, 14);
		frame.getContentPane().add(separator);
	}
	
	/*
	 * Instancia novo sensor
	 * */
	public void novoSensor (Boolean t, Boolean u, Boolean v, String Min, String Max) {
		System.out.println ("T: " + t.toString() + " U: " + u.toString() + " V: " + v.toString() + " Min: " + Min + " Max: " + Max);
		
		if (Min.isBlank()) Min = "0";
		if (Max.isBlank()) Max = "10";
		if (Float.parseFloat(Min) >= Float.parseFloat(Max)) return;
		if (Float.parseFloat(Max) <= Float.parseFloat(Min)) return;
		
		if (t && !sensoresExistentesArray.get(0)) {
			System.out.println("T!");
			tempMin = Min;
			tempMax = Max;
			
			try {
				produtor.novaFila("temperatura");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			JLabel lblSensTemp = new JLabel("Temperatura (" + Min + " - " + Max + ")");
			lblSensTemp.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblSensTemp.setBounds(28, 300, 150, 23);
			frame.getContentPane().add(lblSensTemp);
			
			JLabel lblSensTempVal = new JLabel(Float.toString ( (Float.parseFloat (Max) + Float.parseFloat (Min)) / 2) );
			lblSensTempVal.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblSensTempVal.setBounds(205, 300, 50, 23);
			frame.getContentPane().add(lblSensTempVal);
			
			JTextField textFieldTemp = new JTextField();
			textFieldTemp.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldTemp.setFont(new Font("Tahoma", Font.PLAIN, 13));
			textFieldTemp.setColumns(10);
			textFieldTemp.setBounds(250, 300, 50, 25);
			frame.getContentPane().add(textFieldTemp);
			
			JButton btnAlterarTemp = new JButton("Alterar");
			btnAlterarTemp.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (!textFieldTemp.getText().isBlank() && Float.parseFloat(textFieldTemp.getText()) >= Float.parseFloat(tempMin) && Float.parseFloat(textFieldTemp.getText()) <= Float.parseFloat(tempMax)) {
						alterarValorDoSensor(textFieldTemp.getText(), lblSensTempVal);
						try {
							if (textFieldTemp.getText().equals(tempMin) || textFieldTemp.getText().equals(tempMax)) {
								produtor.novaMensagem("temperatura", "limiteAtingido");
							} else {
								produtor.novaMensagem("temperatura", textFieldTemp.getText());
							}
							// produtor.novaMensagem("temperatura", textFieldTemp.getText());
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}						
				}
			});
			btnAlterarTemp.setBounds(320, 300, 100, 23);
			frame.getContentPane().add(btnAlterarTemp);
			
			SwingUtilities.updateComponentTreeUI(frame);
			sensoresExistentesArray.set(0, true);
			
		} else if (u && !sensoresExistentesArray.get(1)) {
			System.out.println("U!");
			umidMin = Min;
			umidMax = Max;
			
			try {
				produtor.novaFila("umidade");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			JLabel lblSensUmid = new JLabel("Umidade (" + Min + " - " + Max + ")");
			lblSensUmid.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblSensUmid.setBounds(28, 370, 150, 23);
			frame.getContentPane().add(lblSensUmid);
			
			JLabel lblSensUmidVal = new JLabel(Float.toString ( (Float.parseFloat (Max) + Float.parseFloat (Min)) / 2) );
			lblSensUmidVal.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblSensUmidVal.setBounds(205, 370, 50, 23);
			frame.getContentPane().add(lblSensUmidVal);
			
			JTextField textFieldUmid = new JTextField();
			textFieldUmid.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldUmid.setFont(new Font("Tahoma", Font.PLAIN, 13));
			textFieldUmid.setColumns(10);
			textFieldUmid.setBounds(250, 370, 50, 25);
			frame.getContentPane().add(textFieldUmid);
			
			JButton btnAlterarUmid = new JButton("Alterar");
			btnAlterarUmid.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (!textFieldUmid.getText().isBlank() && Float.parseFloat(textFieldUmid.getText()) >= Float.parseFloat(umidMin) && Float.parseFloat(textFieldUmid.getText()) <= Float.parseFloat(umidMax)) {
						alterarValorDoSensor(textFieldUmid.getText(), lblSensUmidVal);
						try {
							if (textFieldUmid.getText().equals(umidMin) || textFieldUmid.getText().equals(umidMax)) {
								produtor.novaMensagem("umidade", "limiteAtingido");
							} else {
								produtor.novaMensagem("umidade", textFieldUmid.getText());
							}
							// produtor.novaMensagem("umidade", textFieldUmid.getText());
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}						
				}
			});
			btnAlterarUmid.setBounds(320, 370, 100, 23);
			frame.getContentPane().add(btnAlterarUmid);
			
			SwingUtilities.updateComponentTreeUI(frame);
			sensoresExistentesArray.set(1, true);
			
		} else if (v && !sensoresExistentesArray.get(2)) {
			System.out.println("V!");
			veloMin = Min;
			veloMax = Max;
			
			try {
				produtor.novaFila("velocidade");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			JLabel lblSensVelo = new JLabel("Velocidade (" + Min + " - " + Max + ")");
			lblSensVelo.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblSensVelo.setBounds(28, 440, 150, 23);
			frame.getContentPane().add(lblSensVelo);
			
			JLabel lblSensVeloVal = new JLabel(Float.toString ( (Float.parseFloat (Max) + Float.parseFloat (Min)) / 2) );
			lblSensVeloVal.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblSensVeloVal.setBounds(205, 440, 50, 23);
			frame.getContentPane().add(lblSensVeloVal);

			JTextField textFieldVelo = new JTextField();
			textFieldVelo.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldVelo.setFont(new Font("Tahoma", Font.PLAIN, 13));
			textFieldVelo.setColumns(10);
			textFieldVelo.setBounds(250, 440, 50, 25);
			frame.getContentPane().add(textFieldVelo);
			
			JButton btnAlterarVelo = new JButton("Alterar");
			btnAlterarVelo.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (!textFieldVelo.getText().isBlank() && Float.parseFloat(textFieldVelo.getText()) >= Float.parseFloat(veloMin) && Float.parseFloat(textFieldVelo.getText()) <= Float.parseFloat(veloMax)) {
						alterarValorDoSensor(textFieldVelo.getText(), lblSensVeloVal);
						try {
							if (textFieldVelo.getText().equals(veloMin) || textFieldVelo.getText().equals(veloMax)) {
								produtor.novaMensagem("velocidade", "limiteAtingido");
							} else {
								produtor.novaMensagem("velocidade", textFieldVelo.getText());
							}
							// produtor.novaMensagem("velocidade", textFieldVelo.getText());
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}						
				}
			});
			btnAlterarVelo.setBounds(320, 440, 100, 23);
			frame.getContentPane().add(btnAlterarVelo);
			
			SwingUtilities.updateComponentTreeUI(frame);
			sensoresExistentesArray.set(2, true);
		}
	}
	
	public void alterarValorDoSensor (String novoValor, JLabel lblSensVal) {
		lblSensVal.setText(novoValor);
	}
}
