package com.adrian.EjerciciosProductManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
	static final String db = "jdbc:mysql://localhost:3306/product-manager?serverTimezone=UTC";
	static final String user = "root";
	static final String pass = "";
	static Scanner sc = new Scanner(System.in);
	static Connection conn;
	static Statement st = null;
	static ResultSet rs;
	static PreparedStatement pst = null;

	public static void setUp() {
		try {
			conn = DriverManager.getConnection(db, user, pass);
			st = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void tearDown() {

		try {
			st.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void categorias() {

		System.out.println("Selecciona la categoría que deseas eliminar");
		String x = "";
		int id;
		int y;

		boolean bucle = true;

		ArrayList<Integer> ids = new ArrayList<Integer>();

		String format = "%4d - %-20.20s\n";
		String sql = "select * from category";

		try (ResultSet rs = st.executeQuery(sql)) {

			while (rs.next()) {
				ids.add(rs.getInt("id"));
				System.out.printf(format, rs.getInt("id"), rs.getString("name"));
			}
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

		try {
			y = Integer.parseInt(x);

			while (bucle) {
				
				for (int element : ids) {
					
					if (y == element) {
						id = y;
					} else {
						System.out.println("Selecciona un número válido");
					}

				}
			}

		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			System.out.println("Error. Escribe un número válido");
		}

	}

	private static void insertCategoriaProducto(String categoria, String referencia, String nombre, double precio)
			throws SQLException {

		conn.setAutoCommit(false); // Desactivar el auto commit
		String sql = "insert into category values(NULL, ?)";
		try {
			pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, categoria);
			pst.executeUpdate();

			ResultSet keys = pst.getGeneratedKeys();
			keys.first();
			int idCategoria = keys.getInt(1);
			System.out.println("categoria insertada: " + idCategoria);
			// Aquí insertaríamos el producto
			PreparedStatement pst2 = conn.prepareStatement(
					"insert into product(id,reference,name,price,category) values(NULL, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			pst2.setString(1, referencia);
			pst2.setString(2, nombre);
			pst2.setDouble(3, precio);
			pst2.setInt(4, idCategoria);

			pst2.executeUpdate();

			conn.commit(); // Todo ok!
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
			conn.rollback(); // Error!
		}
	}

	private static void deleteProductosCategoria(String categoria) throws SQLException {

		conn.setAutoCommit(false); // Desactivar el auto commit

		String sql = "delete product from category where id = ?";
		try {
			pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, categoria);
			pst.executeUpdate();
			conn.commit(); // Todo ok!
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
			conn.rollback(); // Error!
		}
	}

	public static void insertCatProd() {

		String categoria;
		String referencia;
		String nombre;
		String x;
		double precio = 0;
		boolean incorrecto = true;

		System.out.println("Introducir categoría");
		System.out.println("--------------------");
		System.out.println("Introduce el nombre de la nueva categoría");
		categoria = sc.nextLine();
		System.out.println();
		System.out.println("Introducir producto:");
		System.out.println("--------------------");
		System.out.println("Introduce referencia del producto:");
		referencia = sc.nextLine();
		System.out.println("Introduce nombre del producto:");
		nombre = sc.nextLine();

		while (incorrecto) {
			System.out.println("Introduce precio del producto");
			x = sc.nextLine();
			try {
				precio = Double.parseDouble(x);
				incorrecto = false;
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				System.out.println("Error. Por favor introduce un valor válido");
			}
		}
		try {
			insertCategoriaProducto(categoria, referencia, nombre, precio);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {

		setUp();
		// insertCatProd();
		categorias();
		tearDown();

	}
}
