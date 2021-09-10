/*
 * O conte�do deste c�digo foi obtido atrav�s do curso Forma��o Java Web Full-Stack
 * Fonte: https://projetojavaweb.com/
 */

package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.SingleConnectionBanco;
import model.ModelLogin;

/*
 * Estabelece comunica��o com o banco de dados curso-jsp para gerar consultas e inser��es, caso necess�rio.
 * 
 * */

public class DAOLoginRepository {

	private Connection connection;

	public DAOLoginRepository() {
		connection = SingleConnectionBanco.getConnection();
	}

	public boolean validarAutenticacao(ModelLogin modelLogin) throws Exception {

		String sql = "select * from model_login where login = ? and senha = ?";

		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setString(1, modelLogin.getLogin());
		statement.setString(2, modelLogin.getSenha());
		
		ResultSet resultSet = statement.executeQuery();
		
		if(resultSet.next()) {
			return true; // Autenticado
		}
		
		return false; // N�o autenticado
	}
}
