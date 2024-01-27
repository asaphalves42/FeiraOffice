namespace APILP3.Models
{
    public class OrderLine
    {
        public int LineNumber { get; set; }
        public Product ProductCode { get; set; }
        public int Quantity { get; set; }
        public string Unit { get; set; }
        public decimal Price { get; set; }
    }
}
