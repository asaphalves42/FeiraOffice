using APILP3.Models.Requests;

namespace APILP3.Models
{
    public class OrderRequest
    {
        public string Status { get; set; }
        public List<OrderGetRequest> Orders { get; set; }
    }
}
