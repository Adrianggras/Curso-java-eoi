package com.adrian.EjerciciosEOI2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
	static final String db = "jdbc:mysql://localhost:3306/eoi2?serverTimezone=UTC";
	static final String user = "root";
	static final String pass = "";
	static Scanner sc = new Scanner(System.in);

	public static void selectCategorias() {

		String sql = "select * from categorias";
		String format = "%4d - %-20.20s -> Salario: %-10.2f€ - Trienio: %5.6s€\n";

		try (Connection conn = DriverManager.getConnection(db, user, pass);
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(sql)) {

			while (rs.next()) {
				System.out.printf(format, rs.getInt("categoria"), rs.getString("titulo"), rs.getDouble("salario"),
						rs.getInt("trienio"));
			}
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

	}

	public static void selectEmpleados40() {

		// String sql = "SELECT * FROM empleados WHERE empleados.edad>=40 AND
		// YEAR(contrato)<1998";
		String format = "ID: %4s - %-20.20s -> Departamento: %4.4s - Categoria: %4.4s - Contrato: %10.10s\n";

		try (Connection conn = DriverManager.getConnection(db, user, pass);
				PreparedStatement st = conn.prepareStatement(
						"SELECT * FROM empleados WHERE empleados.edad>= 40 AND contrato < '1998-01-01'")) {

			try (ResultSet rs = st.executeQuery()) { // Dentro del try para que cierre el ResultSet
				while (rs.next()) {
					System.out.printf(format, rs.getString("num"), rs.getString("nombre"), rs.getInt("departamento"),
							rs.getInt("categoria"), rs.getDate("contrato"));
				}
			}

		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
	}

	private static void select() {

		String format = "%4d - %-20.20s\n";
		try (Connection conn = DriverManager.getConnection(db, user, pass);
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery("select * from departamentos")) {

			while (rs.next()) {
				System.out.printf(format, rs.getInt("deptno"), rs.getString("nombre"));
			}
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

	}

	private static boolean deleteProducto(int id) {

		try (Connection conn = DriverManager.getConnection(db, user, pass);
				PreparedStatement st = conn.prepareStatement("DELETE FROM departamentos WHERE deptno = ?");) {
			st.setInt(1, id);

			int filas = st.executeUpdate();
			return filas > 0;
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
			return false;
		}
	}

	private static void insertDepart() {

		String x;
		int numero = 0;

		boolean bucle = true;

		while (bucle) {
			System.out.println("Introduce un número de departamento:");
			x = sc.nextLine();

			try {

				numero = Integer.parseInt(x);
				bucle = false;

			} catch (Exception e) {

				System.out.println("Ha habido un error, por favor introduce un valor numérico válido.");

			}

		}
		System.out.println("Introduce el nombre de departamento");
		String nombre = sc.nextLine();

		try (Connection conn = DriverManager.getConnection(db, user, pass);
				PreparedStatement st = conn.prepareStatement("insert into departamentos values(?, ?)",
						Statement.RETURN_GENERATED_KEYS);) {
			st.setInt(1, numero);
			st.setString(2, nombre);
			st.executeUpdate();

		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

		select();
		deleteProducto(numero);

	}

	public static void main(String[] args) {

//		 selectCategorias();
//		 selectEmpleados40();
//		 insertDepart();
	}
}
