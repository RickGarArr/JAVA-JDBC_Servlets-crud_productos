package datos.access.result;

public class UpdateResult {

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

}
