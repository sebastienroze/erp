package erp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Parametres {
	public static String get(String parametre) {
		return get(parametre, "");
	}

	public static String get(String parametre, String valeurParDefaut) {
		try {
			PreparedStatement statement = ErpFiche.conn
					.prepareStatement("SELECT VALEUR FROM PARAMETRES WHERE PARAMETRE=?");
			statement.setString(1, parametre);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}
			String sqlInsert = "INSERT INTO PARAMETRES (VALEUR,PARAMETRE) VALUES(?,?)";
			statement = ErpFiche.conn.prepareStatement(sqlInsert);
			statement.setString(1, valeurParDefaut);
			statement.setString(2, parametre);
			statement.executeUpdate();
			return valeurParDefaut;
		} catch (SQLException e) {
			e.printStackTrace();

			return valeurParDefaut;
		}
	}

	public static void set(String parametre, String valeur) {
		try {
			String sqlUpdate = "UPDATE PARAMETRES SET VALEUR=? WHERE PARAMETRE = ?";
			PreparedStatement statement = ErpFiche.conn.prepareStatement(sqlUpdate);
			statement.setString(1, valeur);
			statement.setString(2, parametre);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
