package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnectionBanco;
import model.ModelLogin;

public class DAOUsuarioRepository {

	private Connection connection;

	public DAOUsuarioRepository() {
		connection = SingleConnectionBanco.getConnection();
	}

	public ModelLogin gravarUsuario(ModelLogin objeto, Long userLogado) throws Exception {

		/* Grava um novo */
		if (objeto.isNovo()) {

			String sql = "INSERT INTO model_Login(login, senha, nome, email, usuario_id) VALUES (?, ?, ?, ?, ?);";
			PreparedStatement preparedSql = connection.prepareStatement(sql);

			preparedSql.setString(1, objeto.getLogin());
			preparedSql.setString(2, objeto.getSenha());
			preparedSql.setString(3, objeto.getNome());
			preparedSql.setString(4, objeto.getEmail());
			preparedSql.setLong(5, userLogado);

			preparedSql.execute();
			connection.commit();

		} else {
			String sql = "UPDATE model_login SET login=?, senha=?, nome=?, email=? WHERE id = " + objeto.getId() + ";";
			PreparedStatement prepareSql = connection.prepareStatement(sql);

			prepareSql.setString(1, objeto.getLogin());
			prepareSql.setString(2, objeto.getSenha());
			prepareSql.setString(3, objeto.getNome());
			prepareSql.setString(4, objeto.getEmail());

			prepareSql.executeUpdate();
			connection.commit();
		}

		return this.consultaUsuario(objeto.getLogin(), userLogado);

	}

	public List<ModelLogin> consultaUsuarioList(Long userLogado) throws Exception {
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();

		String sql = "SELECT * FROM model_login WHERE useradmin IS false and usuario_id = " + userLogado;
		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		/* Percorre as linhas de resultado do SQL */
		while (resultado.next()) {
			ModelLogin modelLogin = new ModelLogin();

			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));

			retorno.add(modelLogin);
		}

		return retorno;
	}

	public List<ModelLogin> consultaUsuarioList(String nome, Long userLogado) throws Exception {
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();

		String sql = "SELECT * FROM model_login WHERE upper(nome) LIKE upper(?) AND useradmin IS false and usuario_id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);

		statement.setString(1, "%" + nome + "%");
		statement.setLong(2, userLogado);

		ResultSet resultado = statement.executeQuery();

		/* Percorre as linhas de resultado do SQL */
		while (resultado.next()) {
			ModelLogin modelLogin = new ModelLogin();

			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));

			retorno.add(modelLogin);
		}

		return retorno;
	}
	
	
	public ModelLogin consultaUsuarioLogado(String login) throws Exception {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "SELECT * FROM model_login WHERE upper(login) = upper('" + login + "')";
		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		/* Se tem resultado */
		while (resultado.next()) {
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
		}

		return modelLogin;
	}
	
	
	
	public ModelLogin consultaUsuario(String login) throws Exception {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "SELECT * FROM model_login WHERE upper(login) = upper('" + login + "') AND useradmin IS false";
		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		/* Se tem resultado */
		while (resultado.next()) {
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
		}

		return modelLogin;
	}
	
	
	

	public ModelLogin consultaUsuario(String login, Long userLogado) throws Exception {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "SELECT * FROM model_login WHERE upper(login) = upper('" + login + "') AND useradmin IS false and usuario_id = " + userLogado;
		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		/* Se tem resultado */
		while (resultado.next()) {
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
		}

		return modelLogin;
	}

	public ModelLogin consultaUsuarioId(String id, Long userLogado) throws Exception {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "SELECT * FROM model_login WHERE id = ? and useradmin is false and usuario_id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, Long.parseLong(id));
		statement.setLong(2, userLogado);

		ResultSet resultado = statement.executeQuery();

		/* Se tem resultado */
		while (resultado.next()) {
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
		}

		return modelLogin;
	}

	public boolean validarLogin(String login) throws Exception {

		String sql = "SELECT count(1) > 0 AS existe FROM model_login WHERE upper(login) = upper('" + login + "');";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();

		resultado.next();
		return resultado.getBoolean("existe");
	}

	public void deletarUser(String idUser) throws Exception {
		String sql = "DELETE FROM model_login WHERE id = ?;";
		PreparedStatement prepareSql = connection.prepareStatement(sql);

		prepareSql.setLong(1, Long.parseLong(idUser));
		prepareSql.executeUpdate();
		connection.commit();

	}
}
