package queue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.jms.*;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.advisory.DestinationSource;
import org.apache.activemq.command.ActiveMQQueue;

import interfacegrafica.Monitor;

public class Consumidor {
	/*
	 * URL do servidor JMS. DEFAULT_BROKER_URL indica que o servidor está em localhost
	 * */
	private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
	
	private static String queueName = "FILA_EXEMPLO";
	
	private static ArrayList<Destination> destinationFila = new ArrayList<>();
	private static ArrayList<MessageConsumer> consumerList = new ArrayList<>();
	
	static ConnectionFactory connectionFactory;
	static Connection connection;
	static Session session;		
	
	public Consumidor () throws JMSException {
		/*
		 * Estabelecendo conexão com o Servidor JMS
		 * */
		connectionFactory = new ActiveMQConnectionFactory(url);
		Connection connection = connectionFactory.createConnection();
		connection.start();
		System.out.println("Conexão estabelecida");		
		
		/*
		 * Criando Session
		 * */
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		System.out.println("Sessão criada");
				
	}
	
	public void monitorar (String filaDestino, JLabel labelSensor, JLabel labelLimites, JLabel labelForaFosLimites) throws JMSException {
		/*
		 * Criando Queue
		 * */
		Destination destination = session.createQueue(filaDestino);
		destinationFila.add(destination);
		System.out.println("Fila criada");
		
		/*
		 * Criando Consumidor
		 * */
		MessageConsumer consumer = session.createConsumer(destination);
		consumerList.add(consumer);
		System.out.println("Consumidor criado");				
		
		labelSensor.setText(filaDestino);
		recebeMensagens(filaDestino, labelLimites, labelForaFosLimites);
		System.out.println("Thread criada!!!");
	}

	public void recebeMensagens (String filaDestino, JLabel labelLimites, JLabel labelForaFosLimites) throws JMSException {
		System.out.println("Recebendo mensagens...");
		new Thread() {
			Message message;
			int index = indexDoConsumer(filaDestino);			
			JLabel lblLimites = labelLimites;
			JLabel lblForaDosLimites = labelForaFosLimites;
			int quantidadeVezesLimiteAtingido = 0;
			
			@Override
			public void run () {
				while (true) {
					try {
						message = consumerList.get(index).receive();
						
						if (message instanceof TextMessage) {
							TextMessage textMessage = (TextMessage) message;
							String text = textMessage.getText();							
							System.out.println("Recebido: " + text);
							
							if (text.equals("limiteAtingido")) {
								quantidadeVezesLimiteAtingido++;
								labelForaFosLimites.setText(Integer.toString(quantidadeVezesLimiteAtingido));
								labelLimites.setIcon(new ImageIcon(Monitor.class.getResource("/imagens/pecavermelha.png")));
							}
							
						} else {
							System.out.println("Recebido: " + message);
							
							if (message.toString().equals("limiteAtingido")) {
								quantidadeVezesLimiteAtingido++;
								labelForaFosLimites.setText(Integer.toString(quantidadeVezesLimiteAtingido));
								labelLimites.setIcon(new ImageIcon(Monitor.class.getResource("/imagens/pecavermelha.png")));
							}
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
	
	/*
	 * Retorna o indice da destino com nome 'fila' do array de produtores
	 * */
	public int indexDoConsumer (String fila) throws JMSException {
		int i;		
		for (i = 0; i < consumerList.size (); i++) {			
			if (destinationFila.get (i).toString ().equals ("queue://" + fila) ) {				
				return i;
			}
		}
		return -1;
	}
	
	public void atualizaListaDeFilasExistentes (JTextArea textAreaLista) throws JMSException {
		ActiveMQConnectionFactory cntFactory = new ActiveMQConnectionFactory(url);
		ActiveMQConnection cnt = (ActiveMQConnection) cntFactory.createConnection();		
		cnt.start();
		
		DestinationSource ds = cnt.getDestinationSource();
		Set<ActiveMQQueue> queues = ds.getQueues();
		
		textAreaLista.setText("");
		for(ActiveMQQueue queue : queues){
		    try {
		    	textAreaLista.setText(textAreaLista.getText() + queue.getQueueName() + "\r\n");
		        System.out.println(queue.getQueueName());
		    } catch (JMSException e) {
		        e.printStackTrace();
		    }
		}
	}
	
	public void encerra () throws JMSException {
		// consumer.close();
		session.close();
		connection.close();
	}
}
