package Model.API;

public class ProdutoAPI {
    public String Id;
    public String GroupId;
    public String Code;
    public String Description;
    public double PVP;
    public double Stock;
    public String Unit;
    public boolean Active;

    public ProdutoAPI(String id, String groupId, String code, String description, double PVP, double stock, String unit, boolean active) {
        Id = id;
        GroupId = groupId;
        Code = code;
        Description = description;
        this.PVP = PVP;
        Stock = stock;
        Unit = unit;
        Active = active;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getGroupId() {
        return GroupId;
    }

    public void setGroupId(String groupId) {
        GroupId = groupId;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public double getPVP() {
        return PVP;
    }

    public void setPVP(double PVP) {
        this.PVP = PVP;
    }

    public double getStock() {
        return Stock;
    }

    public void setStock(double stock) {
        Stock = stock;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean active) {
        Active = active;
    }
}
