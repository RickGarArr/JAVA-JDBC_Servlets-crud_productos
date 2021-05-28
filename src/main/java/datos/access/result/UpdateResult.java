package datos.access.result;

import datos.interfaces.IToJSON;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class UpdateResult implements IToJSON {

    private int affectedRows;
    private int updatedID;

    public UpdateResult() {
    }

    public UpdateResult(int affectedRows, int updatedID) {
        this.affectedRows = affectedRows;
        this.updatedID = updatedID;
    }

    public int getAffectedRows() {
        return affectedRows;
    }

    public void setAffectedRows(int affectedRows) {
        this.affectedRows = affectedRows;
    }

    public int getUpdatedID() {
        return updatedID;
    }

    public void setUpdatedID(int updatedID) {
        this.updatedID = updatedID;
    }

    @Override
    public String toString() {
        return "UpdateResult{" + "affectedRows=" + affectedRows + ", updatedID=" + updatedID + '}';
    }
    
    @Override
    public JsonObject toJSON() {
        JsonObjectBuilder ob = Json.createObjectBuilder();
        ob.add("affected_rows", this.affectedRows);
        ob.add("updated_id", this.updatedID);
        return ob.build();
    }

}
