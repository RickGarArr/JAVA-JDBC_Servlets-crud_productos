package datos.access;

import datos.access.exceptions.DuplicateEntryException;
import datos.access.exceptions.EmptyResultSetException;
import datos.access.exceptions.GeneralException;
import datos.access.interfaces.IAccess;
import datos.access.result.InsertResult;
import datos.access.result.UpdateResult;
import datos.conexion.Conexion;
import datos.models.ComercioModel;
import datos.models.SucursalModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class SucursalAccess implements IAccess<SucursalModel> {

    private Connection conexionTransaccional = null;

    private static String INSERT = "INSERT INTO sucursales(id_sucursal, id_comercio, nombre) VALUES (?,?,?)";
    private static String SELECT_ALL = "SELECT id_sucursal, id_comercio, nombre, esta_activa FROM sucursales ORDER BY id_sucursal";
    private static String SELECT_BY_ID = "SELECT id_sucursal, id_comercio, nombre, esta_activa FROM sucursales WHERE id_sucursal = ?";
    private static String DELETE_BY_ID = "DELETE FROM sucursales WHERE id_sucursal = ?";
    private static String UPDATE_BY_ID = "UPDATE sucursales set nombre = ?, id_comercio = ?, esta_activa = ? WHERE id_sucursal = ?";

    public SucursalAccess() {
    }

    public SucursalAccess(Connection connection) {
        this.conexionTransaccional = connection;
    }

    @Override
    public List<SucursalModel> selectAll() {
        Connection conexion = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<SucursalModel> sucursales = new ArrayList<>();
        try {
            conexion = this.conexionTransaccional == null ? Conexion.getConexion() : this.conexionTransaccional;
            statement = conexion.createStatement();
            resultSet = statement.executeQuery(SELECT_ALL);
            while (resultSet.next()) {
                sucursales.add(new SucursalModel(
                        resultSet.getInt("id_sucursal"),
                        resultSet.getInt("id_comercio"),
                        resultSet.getString("nombre"),
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
        return sucursales;
    }

    @Override
    public SucursalModel selectById(SucursalModel sucursal) throws EmptyResultSetException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.conexionTransaccional == null ? Conexion.getConexion() : this.conexionTransaccional;
            preparedStatement = connection.prepareStatement(SELECT_BY_ID);
            preparedStatement.setInt(1, sucursal.getIdSucursal());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                sucursal.setNombre(resultSet.getString("nombre"));
                sucursal.setIdComercio(resultSet.getInt("id_comercio"));
                sucursal.setActiva(resultSet.getBoolean("esta_activa"));
            } else {
                throw new EmptyResultSetException(sucursal.getIdSucursal());
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(resultSet);
            Conexion.close(preparedStatement);
            if (this.conexionTransaccional == null) {
                Conexion.close(connection);
            }
        }
        return sucursal;
    }

    @Override
    public InsertResult insert(SucursalModel sucursal) throws DuplicateEntryException, GeneralException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;
        InsertResult insertResult = null;
        try {
            connection = this.conexionTransaccional == null ? Conexion.getConexion() : this.conexionTransaccional;
            preparedStatement = connection.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, 0);
            preparedStatement.setInt(2, sucursal.getIdComercio());
            preparedStatement.setString(3, sucursal.getNombre());
            int rows = preparedStatement.executeUpdate();
            generatedKeys = preparedStatement.getGeneratedKeys();
            int insertedId = generatedKeys.first() ? generatedKeys.getInt(1) : 0;
            insertResult = new InsertResult(rows, insertedId);
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1062) {
                throw new DuplicateEntryException("la sucursal con nombre '" + sucursal.getNombre() + "' ya está regitrada");
            }
            if (ex.getErrorCode() == 1452) {
                throw new GeneralException("No existe id_comercio '" + sucursal.getIdComercio() + "'");
            }
            ex.printStackTrace(System.out);
        } finally {
            if (generatedKeys != null) {
                Conexion.close(generatedKeys);
            }
            Conexion.close(preparedStatement);
            if (this.conexionTransaccional == null) {
                Conexion.close(connection);
            }
        }
        return insertResult;
    }

    @Override
    public UpdateResult delete(SucursalModel sucursal) throws EmptyResultSetException {
        Connection conexion = null;
        PreparedStatement preparedStatement = null;
        UpdateResult updateResult = null;
        try {
            conexion = this.conexionTransaccional == null ? Conexion.getConexion() : this.conexionTransaccional;
            preparedStatement = conexion.prepareStatement(DELETE_BY_ID);
            preparedStatement.setInt(1, sucursal.getIdSucursal());
            int rows = preparedStatement.executeUpdate();
            if (rows == 0) throw new EmptyResultSetException(sucursal.getIdSucursal());
            updateResult = new UpdateResult(rows, sucursal.getIdSucursal());
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(preparedStatement);
            if (this.conexionTransaccional == null) {
                Conexion.close(conexion);
            }
        }
        return updateResult;
    }

    @Override
    public UpdateResult update(SucursalModel sucursal) throws EmptyResultSetException {
        Connection conexion = null;
        PreparedStatement preparedStatement = null;
        UpdateResult updateResult = null;
        try {
            conexion = this.conexionTransaccional == null ? Conexion.getConexion() : this.conexionTransaccional;
            //private static String UPDATE_BY_ID = "UPDATE sucursales set nombre = ?, id_comercio = ?, esta_activa = ? WHERE id_sucursal = ?";
            preparedStatement = conexion.prepareStatement(UPDATE_BY_ID);
            preparedStatement.setString(1, sucursal.getNombre());
            preparedStatement.setInt(2, sucursal.getIdComercio());
            preparedStatement.setBoolean(3, sucursal.isActiva());
            preparedStatement.setInt(4, sucursal.getIdSucursal());
            int rows = preparedStatement.executeUpdate();
            if (rows == 0) throw new EmptyResultSetException(sucursal.getIdSucursal());
            updateResult = new UpdateResult(rows, sucursal.getIdSucursal());
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(preparedStatement);
            if (this.conexionTransaccional == null) {
                Conexion.close(conexion);
            }
        }
        return updateResult;
    }
    
    @Override
    public JsonObject toJSONObjectArray(List<SucursalModel> sucursales) {
        JsonObjectBuilder ob = Json.createObjectBuilder();
        JsonArrayBuilder ab = Json.createArrayBuilder();
        sucursales.forEach(sucursal -> {
            ab.add(sucursal.toJSON());
        });
        ob.add("size", sucursales.size());
        ob.add("sucursales", ab.build());
        return ob.build();
    }

}
