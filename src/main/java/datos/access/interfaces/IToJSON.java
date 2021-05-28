package datos.access.interfaces;

import java.util.List;
import javax.json.*;

public interface IToJSON<T>{  
    JsonObject toJSONObjectArray(List<T> objects);
}
