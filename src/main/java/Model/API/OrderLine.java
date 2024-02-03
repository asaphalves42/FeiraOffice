package Model.API;

public class OrderLine {

    public int LineNumber;
    public String ProductCode;
    public double Quantity;
    public String Unit;
    public double Price;


    public OrderLine(int lineNumber, String productCode, double quantity, String unit, double price) {
        LineNumber = lineNumber;
        ProductCode = productCode;
        Quantity = quantity;
        Unit = unit;
        Price = price;
    }


    public int getLineNumber() {
        return LineNumber;
    }

    public void setLineNumber(int lineNumber) {
        LineNumber = lineNumber;
    }

    public String getProductCode() {
        return ProductCode;
    }

    public void setProductCode(String productCode) {
        ProductCode = productCode;
    }

    public double getQuantity() {
        return Quantity;
    }

    public void setQuantity(double quantity) {
        Quantity = quantity;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }
}
