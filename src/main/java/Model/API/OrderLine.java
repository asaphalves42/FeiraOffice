package Model.API;
/**
 * A classe OrderLine representa uma linha de um pedido, contendo informações como número da linha, código do produto, quantidade, unidade e preço.
 */
public class OrderLine {
    /**
     * O número da linha.
     */
    public int LineNumber;
    /**
     * O código do produto.
     */
    public String ProductCode;
    /**
     * A quantidade da linha do pedido.
     */
    public double Quantity;
    /**
     * A unidade da linha do pedido.
     */
    public String Unit;
    /**
     * O preço da linha do pedido.
     */
    public double Price;

    /**
     * Constrói uma nova instância de OrderLine com os detalhes especificados.
     *
     * @param lineNumber  O número da linha.
     * @param productCode O código do produto.
     * @param quantity    A quantidade da linha do pedido.
     * @param unit        A unidade da linha do pedido.
     * @param price       O preço da linha do pedido.
     */
    public OrderLine(int lineNumber, String productCode, double quantity, String unit, double price) {
        LineNumber = lineNumber;
        ProductCode = productCode;
        Quantity = quantity;
        Unit = unit;
        Price = price;
    }

    /**
     * Obtém o número da linha.
     *
     * @return O número da linha.
     */
    public int getLineNumber() {
        return LineNumber;
    }
    /**
     * Define o número da linha.
     *
     * @param lineNumber O número da linha a definir.
     */
    public void setLineNumber(int lineNumber) {
        LineNumber = lineNumber;
    }
    /**
     * Obtém o código do produto.
     *
     * @return O código do produto.
     */
    public String getProductCode() {
        return ProductCode;
    }
    /**
     * Define o código do produto.
     *
     * @param productCode O código do produto a definir.
     */
    public void setProductCode(String productCode) {
        ProductCode = productCode;
    }
    /**
     * Obtém a quantidade da linha do pedido.
     *
     * @return A quantidade da linha do pedido.
     */
    public double getQuantity() {
        return Quantity;
    }
    /**
     * Define a quantidade da linha do pedido.
     *
     * @param quantity A quantidade da linha do pedido a definir.
     */
    public void setQuantity(double quantity) {
        Quantity = quantity;
    }
    /**
     * Obtém a unidade da linha do pedido.
     *
     * @return A unidade da linha do pedido.
     */
    public String getUnit() {
        return Unit;
    }
    /**
     * Define a unidade da linha do pedido.
     *
     * @param unit A unidade da linha do pedido a definir.
     */
    public void setUnit(String unit) {
        Unit = unit;
    }
    /**
     * Obtém o preço da linha do pedido.
     *
     * @return O preço da linha do pedido.
     */
    public double getPrice() {
        return Price;
    }
    /**
     * Define o preço da linha do pedido.
     *
     * @param price O preço da linha do pedido a definir.
     */
    public void setPrice(double price) {
        Price = price;
    }
}
