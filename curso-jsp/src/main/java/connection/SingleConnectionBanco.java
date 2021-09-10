/*
 * O conteúdo deste código foi obtido através do curso Formação Java Web Full-Stack
 * Fonte: https://projetojavaweb.com/
 */

package connection;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 
 * @author Wilson Diniz Silva
 * 
 * Classe responsável pela conexão ao banco de dados.
 *
 */

public class SingleConnectionBanco {

	private static String banco = "jdbc:postgresql://localhost:5432/curso-jsp?autoReconnect=true";
	private static String user = "postgres";
	private static String senha = "123";
	private static Connection connection = null;

	static {
		
		conectar();
	}

	/* Quando tiver uma instancia vai conectar */
	public SingleConnectionBanco() {
		
		conectar();
	}

	private static void conectar() {

		try {
			if (connection == null) {

				/* Carrega o driver de conexão do banco */
				Class.forName("org.postgresql.Driver");

				/* Passa para o objeto connection o banco de dados, usuário e senha */
				connection = DriverManager.getConnection(banco, user, senha);

				/* Impede alterações no banco sem que para isso seja gerado um comando */
				connection.setAutoCommit(false);

			}
		} catch (Exception e) {

			/* Mostrar qualquer erro no momento de conectar */
			e.printStackTrace();

		} /* Fim da declaração try catch */

	}/* Fim do método conectar */

	public static Connection getConnection() {
		
		return connection;
		
	} /* Fim do método getConnection */

} /* Fim da Classe SingleConnectionBanco */
