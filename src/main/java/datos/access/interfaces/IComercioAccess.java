package datos.access.interfaces;

import datos.access.exceptions.DuplicateEntryException;
import datos.access.exceptions.EmptyResultSetException;
import datos.access.result.InsertResult;
import datos.access.result.UpdateResult;
import datos.models.ComercioModel;
import java.util.List;

public interface IComercioAccess {
    List<ComercioModel> selectAll();
    ComercioModel selectById(ComercioModel comercio) throws EmptyResultSetException;
    ComercioModel selectByName(ComercioModel comercio);
    InsertResult insertComercio(ComercioModel comercio) throws DuplicateEntryException;
    UpdateResult deleteComercio(ComercioModel comercio);
    UpdateResult updateComercio(ComercioModel comercio);
}
