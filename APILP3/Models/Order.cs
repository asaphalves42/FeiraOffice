namespace APILP3.Models
{
    public class Order
    {
        public string Id { get; set; }
        public DateTime Date { get; set; }
        public string ClientId { get; set; }
        public Address DeliveryAddress{ get; set; }
        public Address BillingAddress { get; set; }
        public double NetAmount { get; set; }
        public double TaxAmount { get; set; }
        public double TotalAmount { get; set; }
        public string Currency { get; set; }
        public List<OrderLine> Lines { get; set; }
    }
}
