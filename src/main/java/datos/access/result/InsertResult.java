package datos.access.result;

public class InsertResult {
    private int affectedRows;
    private int insertedId;
    
    public InsertResult() {}
    
    public InsertResult(int affectedRows, int insertedId) {
        this.affectedRows = affectedRows;
        this.insertedId = insertedId;
    }

    public int getAffectedRows() {
        return affectedRows;
    }

    public void setAffectedRows(int affectedRows) {
        this.affectedRows = affectedRows;
    }

    public int getInsertedId() {
        return insertedId;
    }

    public void setInsertedId(int insertedId) {
        this.insertedId = insertedId;
    }

    @Override
    public String toString() {
        return "InsertResult{" + "affectedRows=" + affectedRows + ", insertedId=" + insertedId + '}';
    }
}
