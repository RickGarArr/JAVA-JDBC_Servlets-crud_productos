package datos.access;

import datos.access.exceptions.*;
import datos.access.interfaces.IAccess;
import datos.access.result.*;
import datos.conexion.Conexion;
import datos.models.CategoriaModel;
import java.sql.*;
import java.util.*;
import javax.json.*;

public class CategoriaAccess implements IAccess<CategoriaModel>{
    
    private Connection conexionTransaccional ;
    private static final String INSERT = "INSERT INTO categorias(id_categoria, nombre, descripcion, esta_activa) VALUES (?,?,?,?)";
    private static final String SELECT_ALL = "SELECT id_categoria, nombre, descripcion, esta_activa FROM categorias ORDER BY id_categoria";
    private static final String SELECT_BY_ID = "SELECT id_categoria, nombre, descripcion, esta_activa FROM categorias WHERE id_categoria = ?";
    private static final String DELETE_BY_ID = "DELETE FROM categorias WHERE id_categoria = ?";
    private static final String UPDATE_BY_ID = "UPDATE categorias set nombre = ?, descripcion = ?, esta_activa = ? WHERE id_categoria = ?";

    @Override
    public List<CategoriaModel> selectAll() {
        Connection conexion = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<CategoriaModel> categorias = new ArrayList<>();
        try {
            conexion = this.conexionTransaccional == null ? Conexion.getConexion() : this.conexionTransaccional;
            statement = conexion.createStatement();
            resultSet = statement.executeQuery(SELECT_ALL);
            while (resultSet.next()) {
                categorias.add(new CategoriaModel(
                        resultSet.getInt("id_categoria"),
                        resultSet.getString("nombre"),
                        resultSet.getString("descripcion"),
                        resultSet.getBoolean("esta_activa")
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(resultSet);
            Conexion.close(statement);
            if (this.conexionTransaccional == null) {
                Conexion.close(conexion);
            }
        }
        return categorias;
    }

    @Override
    public CategoriaModel selectById(CategoriaModel categoria) throws EmptyResultSetException {
        Connection conexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            conexion = this.conexionTransaccional == null ? Conexion.getConexion() : this.conexionTransaccional;
            preparedStatement = conexion.prepareStatement(SELECT_BY_ID);
            preparedStatement.setInt(1, categoria.getidCategoria());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                categoria.setNombre(resultSet.getString("nombre"));
                categoria.setDescripcion(resultSet.getString("descripcion"));
                categoria.setestaActiva(resultSet.getBoolean("esta_activa"));
            } else {
                throw new EmptyResultSetException(categoria.getidCategoria());
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(resultSet);
            Conexion.close(preparedStatement);
            if (this.conexionTransaccional == null) {
                Conexion.close(conexion);
            }
        }
        return categoria;
    }

    @Override
    public InsertResult insert(CategoriaModel categoria) throws DuplicateEntryException, GeneralException {
        Connection conexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;
        InsertResult insertResult = new InsertResult();
        try {
            conexion = this.conexionTransaccional == null ? Conexion.getConexion() : this.conexionTransaccional;
            preparedStatement = conexion.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, categoria.getidCategoria());
            preparedStatement.setString(2, categoria.getNombre());
            preparedStatement.setString(3, categoria.getDescripcion());
            preparedStatement.setBoolean(4, categoria.isestaActiva());
            int rows = preparedStatement.executeUpdate();
            insertResult.setAffectedRows(rows);
            generatedKeys = preparedStatement.getGeneratedKeys();
            int insertedId = generatedKeys.first() ? generatedKeys.getInt(1) : 0;
            insertResult.setInsertedId(insertedId);
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1062) {
                throw new DuplicateEntryException("El comercio con nombre '" + categoria.getNombre() + "' ya está regitrado");
            }
            ex.printStackTrace(System.out);
        } finally {
            if (generatedKeys != null) {
                Conexion.close(generatedKeys);
            }
            Conexion.close(preparedStatement);
            if (this.conexionTransaccional == null) {
                Conexion.close(conexion);
            }
        }
        return insertResult;
    }

    @Override
    public UpdateResult delete(CategoriaModel categoria) throws EmptyResultSetException {
        Connection connection = null;
        PreparedStatement ps = null;
        UpdateResult ur = null;
        try {
            connection = this.conexionTransaccional == null ? Conexion.getConexion() : this.conexionTransaccional;
            ps = connection.prepareStatement(DELETE_BY_ID);
            ps.setInt(1, categoria.getidCategoria());
            int rows = ps.executeUpdate();
            if (rows == 0) throw new EmptyResultSetException(categoria.getidCategoria());
            ur = new UpdateResult(rows, categoria.getidCategoria());
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(ps);
            if (this.conexionTransaccional == null) {
                Conexion.close(connection);
            }
        }
        return ur;
    }

    @Override
    public UpdateResult update(CategoriaModel categoria) throws EmptyResultSetException {
        Connection connection = null;
        PreparedStatement ps = null;
        UpdateResult ur = null;
        try {
            connection = this.conexionTransaccional == null ? Conexion.getConexion() : this.conexionTransaccional;
            // "UPDATE categorias set nombre = ?, descripcion = ?, esta_activa = ? WHERE id_categoria = ?"
            ps = connection.prepareStatement(UPDATE_BY_ID);
            ps.setString(1, categoria.getNombre());
            ps.setString(2, categoria.getDescripcion());
            ps.setBoolean(3, categoria.isestaActiva());
            ps.setInt(4, categoria.getidCategoria());
            int rows = ps.executeUpdate();
            if (rows == 0) throw new EmptyResultSetException(categoria.getidCategoria());
            ur = new UpdateResult(rows, categoria.getidCategoria());
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }  finally {
            Conexion.close(ps);
            if (this.conexionTransaccional == null) {
                Conexion.close(connection);
            }
        }
        return ur;
    }

    @Override
    public JsonObject toJSONObjectArray(List<CategoriaModel> categorias) {
        JsonObjectBuilder ob = Json.createObjectBuilder();
        JsonArrayBuilder ab = Json.createArrayBuilder();
        categorias.forEach(categoria -> {
            ab.add(categoria.toJSON());
        });
        ob.add("size", categorias.size());
        ob.add("categorias", ab.build());
        return ob.build();
    }

}
