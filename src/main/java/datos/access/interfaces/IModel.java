package datos.access.interfaces;

import java.util.List;
import javax.json.*;

public interface IModel<T> {
    JsonObject toJSONObject(T object);
    JsonObject toJSONArray(List<T> objects);
}
