package datos.access.interfaces;

import datos.access.exceptions.DuplicateEntryException;
import datos.access.exceptions.EmptyResultSetException;
import datos.access.exceptions.GeneralException;
import datos.access.result.InsertResult;
import datos.access.result.UpdateResult;
import java.util.List;
import javax.json.JsonObject;

public interface IAccess <T> {
    List<T> selectAll();
    T selectById(T comercio) throws EmptyResultSetException;
    InsertResult insert(T object) throws DuplicateEntryException, GeneralException;
    UpdateResult delete(T object) throws EmptyResultSetException;
    UpdateResult update(T object) throws EmptyResultSetException;
    JsonObject toJSONObjectArray(List<T> objects);
}
