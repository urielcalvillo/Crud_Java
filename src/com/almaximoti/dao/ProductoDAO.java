package com.almaximoti.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.almaximoti.conexion.Conexion;
import com.almaximoti.model.Producto;

public class ProductoDAO {
	private Connection  connection;
	private PreparedStatement statement;
	private boolean estadoOperacion;
	
	//Guardar
	public boolean guardar(Producto producto) throws SQLException {
		String sql= null;
		estadoOperacion = false;
		connection=obtenerConexion();
		
		try {
			connection.setAutoCommit(false);
			sql= "INSERT INTO productos (id, nombre, cantidad, precio, fecha_crear, fecha_actualizar) VALUES (?,?,?,?,?,?)";
			statement=connection.prepareStatement(sql);
			statement.setString(1, null);
			statement.setString(2, producto.getNombre());
			statement.setDouble(3, producto.getCantidad());
			statement.setDouble(4, producto.getPrecio());
			statement.setDate(5, producto.getFecha_crear());
			statement.setDate(6, producto.getFecha_actualizar());
			
			estadoOperacion= statement.executeUpdate()>0;
			
			connection.commit();
			statement.close();
			connection.close();

			
		} catch (SQLException e) {
			connection.rollback();
			e.printStackTrace();
		}
		return estadoOperacion;

	}
	//Editar
	public boolean Editar(Producto producto) throws SQLException {
		String sql= null;
		estadoOperacion = false;
		connection=obtenerConexion();
		try {
			connection.setAutoCommit(false);
			sql="Update productos set nombre=?, cantidad=?, precio=?, fecha_actualizar=? where id=?";
			statement=connection.prepareStatement(sql);
			statement.setString(1, producto.getNombre());
			statement.setDouble(2, producto.getCantidad());
			statement.setDouble(3, producto.getPrecio());
			statement.setDate(4, producto.getFecha_actualizar());
			statement.setInt(5, producto.getId());
			
			estadoOperacion= statement.executeUpdate()>0;
			connection.commit();
			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			connection.rollback();
			e.printStackTrace();
		}
		return estadoOperacion;
		
	}
	//Eliminar
	public boolean Eliminar(int idProducto) throws SQLException {
		String sql= null;
		estadoOperacion = false;
		connection=obtenerConexion();
		try {
			connection.setAutoCommit(false);
			sql="Delete from  productos where id=?";
			statement=connection.prepareStatement(sql);
			
			statement.setInt(1, idProducto);
			estadoOperacion= statement.executeUpdate()>0;
			connection.commit();
			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			connection.rollback();
			e.printStackTrace();
		}
		return estadoOperacion;
		
	}
	//Obtener lista de productos
	public List<Producto> ObtenerProductos() throws SQLException {
		ResultSet resultSet=null;
		List<Producto> listaProductos = new ArrayList<>();
		String sql= null;
		estadoOperacion = false;
		connection=obtenerConexion();
		
		try {
			sql="Select * From productos";
			statement=connection.prepareStatement(sql);
			resultSet= statement.executeQuery(sql);
			while (resultSet.next()) {
				Producto p = new Producto();
				p.setId(resultSet.getInt(1));
				p.setNombre(resultSet.getString(2));
				p.setCantidad(resultSet.getDouble(3));
				p.setPrecio(resultSet.getDouble(4));
				p.setFecha_crear(resultSet.getDate(5));
				p.setFecha_actualizar(resultSet.getDate(6));
				listaProductos.add(p);

			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaProductos;		
	}
	//Obtener un producto
	public Producto ObtenerProducto(int idProducto) throws SQLException {
		ResultSet resultSet=null;
		Producto p = new Producto();

		String sql= null;
		estadoOperacion = false;
		connection=obtenerConexion();
		
		try {
			sql="Select * From productos where id =?";
			statement= connection.prepareStatement(sql);
			statement.setInt(1, idProducto);
			resultSet= statement.executeQuery();
			if (resultSet.next()) {
				p.setId(resultSet.getInt(1));
				p.setNombre(resultSet.getString(2));
				p.setCantidad(resultSet.getDouble(3));
				p.setPrecio(resultSet.getDouble(4));
				p.setFecha_crear(resultSet.getDate(5));
				p.setFecha_actualizar(resultSet.getDate(6));

			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return p;	}
	
	//obtener conexion pool
		private Connection obtenerConexion() throws SQLException {
			return Conexion.getConnection();
		}	
}
