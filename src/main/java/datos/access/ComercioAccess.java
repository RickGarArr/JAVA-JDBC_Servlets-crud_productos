package datos.access;

import datos.access.exceptions.*;
import datos.access.interfaces.*;
import datos.access.result.*;
import datos.conexion.Conexion;
import datos.models.ComercioModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.json.*;

public class ComercioAccess implements IComercioAccess, IToJSON<ComercioModel> {

    private Connection conexionTransaccional = null;
    private static String INSERT = "INSERT INTO comercios(id_comercio, nombre) VALUES (?,?)";
    private static String SELECT_ALL = "SELECT id_comercio, nombre, esta_activo FROM comercios ORDER BY id_comercio";
    private static String SELECT_BY_ID = "SELECT id_comercio, nombre, esta_activo FROM comercios WHERE id_comercio = ?";
    private static String DELETE_BY_ID = "DELETE FROM comercios WHERE id_comercio = ?";
    private static String UPDATE_BY_ID = "UPDATE comercios set nombre = ?, esta_activo = ? WHERE id_comercio = ?";

    public ComercioAccess() {
    }

    public ComercioAccess(Connection connection) {
        this.conexionTransaccional = connection;
    }

    @Override
    public List<ComercioModel> selectAll() {
        Connection conexion = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<ComercioModel> comercios = new ArrayList<>();
        try {
            conexion = this.conexionTransaccional == null ? Conexion.getConexion() : this.conexionTransaccional;
            statement = conexion.createStatement();
            resultSet = statement.executeQuery(SELECT_ALL);
            while (resultSet.next()) {
                comercios.add(new ComercioModel(
                        resultSet.getInt("id_comercio"),
                        resultSet.getString("nombre"),
                        resultSet.getBoolean("esta_activo")
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
        return comercios;
    }

    @Override
    public ComercioModel selectById(ComercioModel comercio) throws EmptyResultSetException {
        Connection conexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            conexion = this.conexionTransaccional == null ? Conexion.getConexion() : this.conexionTransaccional;
            preparedStatement = conexion.prepareStatement(SELECT_BY_ID);
            preparedStatement.setInt(1, comercio.getIdComercio());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                comercio.setNombre(resultSet.getString("nombre"));
                comercio.setEstaActivo(resultSet.getBoolean("esta_activo"));
            } else {
                throw new EmptyResultSetException("No existe comercio con ID '" + comercio.getIdComercio() + "'");
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
        return comercio;
    }

    @Override
    public InsertResult insertComercio(ComercioModel comercio) throws DuplicateEntryException {
        Connection conexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;
        InsertResult insertResult = new InsertResult();
        try {
            conexion = this.conexionTransaccional == null ? Conexion.getConexion() : this.conexionTransaccional;
            preparedStatement = conexion.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, 0);
            preparedStatement.setString(2, comercio.getNombre());
            int rows = preparedStatement.executeUpdate();
            insertResult.setAffectedRows(rows);
            generatedKeys = preparedStatement.getGeneratedKeys();
            int insertedId = generatedKeys.first() ? generatedKeys.getInt(1) : 0;
            insertResult.setInsertedId(insertedId);
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1062) {
                throw new DuplicateEntryException("El comercio con nombre '" + comercio.getNombre() + "' ya está regitrado");
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
    public UpdateResult deleteComercio(ComercioModel comercio) throws EmptyResultSetException {
        Connection conexion = null;
        PreparedStatement preparedStatement = null;
        UpdateResult updateResult = null;
        try {
            conexion = this.conexionTransaccional == null ? Conexion.getConexion() : this.conexionTransaccional;
            preparedStatement = conexion.prepareStatement(DELETE_BY_ID);
            preparedStatement.setInt(1, comercio.getIdComercio());
            int rows = preparedStatement.executeUpdate();
            if (rows == 0) throw new EmptyResultSetException("No existe comercio con ID '" + comercio.getIdComercio() + "'");
            updateResult = new UpdateResult(rows, comercio.getIdComercio());
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
    public UpdateResult updateComercio(ComercioModel comercio) throws EmptyResultSetException {
        Connection conexion = null;
        PreparedStatement preparedStatement = null;
        UpdateResult updateResult = null;
        try {
            conexion = this.conexionTransaccional == null ? Conexion.getConexion() : this.conexionTransaccional;
            preparedStatement = conexion.prepareStatement(UPDATE_BY_ID);
            preparedStatement.setString(1, comercio.getNombre());
            preparedStatement.setBoolean(2, comercio.isActivo());
            preparedStatement.setInt(3, comercio.getIdComercio());
            int rows = preparedStatement.executeUpdate();
            if (rows == 0) throw new EmptyResultSetException("No existe comercio con ID '" + comercio.getIdComercio() + "'");
            updateResult = new UpdateResult(rows, comercio.getIdComercio());
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
    public JsonObject toJSONObjectArray(List<ComercioModel> comercios) {
        JsonObjectBuilder ob = Json.createObjectBuilder();
        JsonArrayBuilder ab = Json.createArrayBuilder();
        comercios.forEach(comercio -> {
            ab.add(comercio.toJSON());
        });
        ob.add("size", comercios.size());
        ob.add("comercios", ab.build());
        return ob.build();
    }

}
