package queue;

import java.util.ArrayList;

import javax.jms.*;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Produtor {
	/*
	 * URL do servidor JMS. DEFAULT_BROKER_URL indica que o servidor está em localhost
	 * */
	private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
	
	private static String queueName = "FILA_EXEMPLO";
	
	private static ArrayList<Destination> destinationFila = new ArrayList<>();
	private static ArrayList<MessageProducer> producerList = new ArrayList<>();
	
	static ConnectionFactory connectionFactory;
	static Connection connection;
	static Session session;
	
	/*
	 * Construtor. Estabelece conexão e cria sessão
	 * */
	public Produtor () throws JMSException {
		/*
		 * Estabelecendo conexão com o Servidor JMS
		 * */
		connectionFactory = new ActiveMQConnectionFactory(url);
		connection = connectionFactory.createConnection();
		connection.start();
		System.out.println("Conexão estabelecida");
		
		/*
		 * Criando Session
		 * */
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		System.out.println("Sessão criada");
	}
	
	/*
	 * Criar um produto para a fila com nome 'filaDestino'
	 * */
	public void novaFila (String filaDestino) throws JMSException {
		/*
		 * Criando Queue
		 * */
		Destination destination = session.createQueue(filaDestino);
		destinationFila.add(destination);
		System.out.println("Fila criada");		
		
		/*
		 * Criando Produtor
		 * */
		MessageProducer producer = session.createProducer(destination);
		producerList.add(producer);
		System.out.println("Produtor criado");
		
		System.out.println(producer.getDestination().toString());		
		
	}
		
	/*
	 * Envia uma mensagem 'msg' para a fila 'filaNome' 
	 * */
	public void novaMensagem (String filaNome, String msg) throws JMSException {
		/*
		 * Criando nova mensagem
		 * */
		TextMessage message = session.createTextMessage(msg);
		System.out.println ("Nova mensagem criada");
		
		/*
		 * Enviando Mensagem
		 * */
		System.out.println ("Enviando mensagem");
		int indexFila = indexDoProducer (filaNome);
		if ( ! (indexFila == -1) ) {
			producerList.get (indexFila).send (message);		
			System.out.println ("Mensagem: '" + message.getText () + "', Enviada para a Fila");
		} else
			System.out.println ("Mensagem não enviada. Fila inexistente");		
	}
	
	/*
	 * Retorna o indice da destino com nome 'fila' do array de produtores
	 * */
	public int indexDoProducer (String fila) throws JMSException {
		int i;		
		for (i = 0; i < producerList.size (); i++) {			
			if (producerList.get (i).getDestination ().toString ().equals ("queue://" + fila) ) {				
				return i;
			}
		}
		return -1;
	}
	
	public void destroiProdutor (String nomeDoProdutor) throws JMSException {
		
	}
	
	/*
	 * Encerrar conexao e sessao
	 * */
	public void encerrar () throws JMSException {
		//producer.close();
		session.close();
		connection.close();
	}

}

