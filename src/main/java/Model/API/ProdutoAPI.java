package Model.API;
/**
 * A classe ProdutoAPI representa um produto com informações como ID, código, descrição, preço de venda ao público (PVP), stock, unidade e estado de ativação.
 */
public class ProdutoAPI {
    /**
     * O ID do produto.
     */
    public String Id;
    /**
     * O ID do grupo do produto.
     */
    public String GroupId;
    /**
     * O código do produto.
     */
    public String Code;
    /**
     * A descrição do produto.
     */
    public String Description;
    /**
     * O preço de venda ao público (PVP) do produto.
     */
    public double PVP;
    /**
     * O stock do produto.
     */
    public double Stock;
    /**
     * A unidade do produto.
     */
    public String Unit;
    /**
     * Indica se o produto está ativo ou não.
     */
    public boolean Active;
    /**
     * Constrói um novo objeto ProdutoAPI com os detalhes especificados.
     *
     * @param id          O ID do produto.
     * @param groupId     O ID do grupo do produto.
     * @param code        O código do produto.
     * @param description A descrição do produto.
     * @param PVP         O preço de venda ao público (PVP) do produto.
     * @param stock       O stock do produto.
     * @param unit        A unidade do produto.
     * @param active      Indica se o produto está ativo ou não.
     */
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
    /**
     * Obtém o ID do produto.
     *
     * @return O ID do produto.
     */
    public String getId() {
        return Id;
    }
    /**
     * Define o ID do produto.
     *
     * @param id O ID do produto a definir.
     */
    public void setId(String id) {
        Id = id;
    }
    /**
     * Obtém o ID do grupo do produto.
     *
     * @return O ID do grupo do produto.
     */
    public String getGroupId() {
        return GroupId;
    }
    /**
     * Define o ID do grupo do produto.
     *
     * @param groupId O ID do grupo do produto a definir.
     */
    public void setGroupId(String groupId) {
        GroupId = groupId;
    }
    /**
     * Obtém o código do produto.
     *
     * @return O código do produto.
     */
    public String getCode() {
        return Code;
    }
    /**
     * Define o código do produto.
     *
     * @param code O código do produto a definir.
     */
    public void setCode(String code) {
        Code = code;
    }
    /**
     * Obtém a descrição do produto.
     *
     * @return A descrição do produto.
     */
    public String getDescription() {
        return Description;
    }
    /**
     * Define a descrição do produto.
     *
     * @param description A descrição do produto a definir.
     */
    public void setDescription(String description) {
        Description = description;
    }
    /**
     * Obtém o preço de venda ao público (PVP) do produto.
     *
     * @return O preço de venda ao público (PVP) do produto.
     */
    public double getPVP() {
        return PVP;
    }
    /**
     * Define o preço de venda ao público (PVP) do produto.
     *
     * @param PVP O preço de venda ao público (PVP) do produto a definir.
     */
    public void setPVP(double PVP) {
        this.PVP = PVP;
    }
    /**
     * Obtém o stock do produto.
     *
     * @return O stock do produto.
     */
    public double getStock() {
        return Stock;
    }
    /**
     * Define o stock do produto.
     *
     * @param stock O stock do produto a definir.
     */
    public void setStock(double stock) {
        Stock = stock;
    }
    /**
     * Obtém a unidade do produto.
     *
     * @return A unidade do produto.
     */
    public String getUnit() {
        return Unit;
    }

    /**
     * Define a unidade do produto.
     *
     * @param unit A unidade do produto a definir.
     */
    public void setUnit(String unit) {
        Unit = unit;
    }
    /**
     * Verifica se o produto está ativo.
     *
     * @return true se o produto estiver ativo, false caso contrário.
     */
    public boolean isActive() {
        return Active;
    }
    /**
     * Define se o produto está ativo ou não.
     *
     * @param active true se o produto estiver ativo, false caso contrário.
     */
    public void setActive(boolean active) {
        Active = active;
    }
}
