namespace APILP3.Models.Requests
{
    public class OrderGetRequest
    {
        public string OrderNumber { get; set; }
        public DateOnly Date { get; set; }
        public List<User> Client { get; set; }
        public List<DeliveryAddress> DeliveryAddress { get; set; }
        public List<BillingAddress> BillingAddress { get; set; }
        public double NetAmount { get; set; }
        public double TaxAmount { get; set; }
        public double TotalAmount { get; set; }
        public string Currency { get; set; }
        public int Status { get; set; }
        public string StatusDescription { get; set; }
        public List<OrderLine> OrderLines { get; set; }
    }
}
