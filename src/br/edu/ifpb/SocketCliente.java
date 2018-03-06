package br.edu.ifpb;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class SocketCliente {

	private Socket socket;
	private String host;
	private int port;

	public SocketCliente() throws IOException {
		openConfig();
		this.socket = new Socket(host, port);
	}

	public String request(String msg) throws IOException {

		this.socket.getOutputStream().write(msg.getBytes());
		this.socket.getOutputStream().flush();
		this.socket.shutdownOutput();

		byte[] buffer = new byte[1024];

		this.socket.getInputStream().read(buffer);

		int aux = 0;

		for (int i = 0; i < buffer.length; i++) {
			if (buffer[i] != 0) {
				aux++;
			}
		}

		byte[] bufferAux = new byte[aux];

		System.arraycopy(buffer, 0, bufferAux, 0, bufferAux.length);

		this.socket.shutdownInput();
		// in.close();

		return new String(bufferAux);
	}

	public void close() throws IOException {
		this.socket.close();
	}

	private void openConfig() {
		host = "192.168.56.1";// ip da maquina
		port = 12345;
	}

	private static Double getNumero() {
		return new Scanner(System.in).nextDouble();
	}

	private String getValores() {
		Double num1 = 0D;
		Double num2 = 0D;

		System.out.println("Digite o primeiro número");
		num1 = getNumero();
		System.out.println("Digite o segundo número");
		num2 = getNumero();
		return Double.toString(num1) + ";" + Double.toString(num2) + ";";
	}

	private int menu() {

		int operacao = 0;
		Scanner s = new Scanner(System.in);
		System.out.println(" ------ MENU ------ ");
		System.out.println(" 1 - SOMA ");
		System.out.println(" 2 - SUBTRAÇÃO ");
		System.out.println(" 3 - DIVISÃO");
		System.out.println(" 4 - MULTIPLICAÇÃO");
		System.out.println(" 0 - SAIR");
		operacao = s.nextInt();

		while (operacao < 0 || operacao > 4) {
			System.out.println("Você digitou uma operaçãp inválida.");
			operacao = menu();
		}

		return operacao;
	}

	public static void main(String[] args) throws IOException {
		// inicia o servidor
		SocketCliente sc = new SocketCliente();
		String request = "";
		// Monta os dados para a requisição do calculo
		request = sc.getValores() + "" + String.valueOf(sc.menu());
		System.out.println("Resultado :" + sc.request(request));

	}

}
