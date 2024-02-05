namespace APILP3.Models.Requests
{
    public class OrderRequest
    {
        public string Status { get; set; }
        public List<OrderGetRequest> Orders { get; set; }
    }
}
