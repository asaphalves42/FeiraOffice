namespace APILP3.Models
{
    public class OrderLine
    {
        public int LineNumber { get; set; }
        public string ProductCode { get; set; }
        public double Quantity { get; set; }
        public string Unit { get; set; }
        public double Price { get; set; }


        public override string ToString()
        {
            return $"{LineNumber},{ProductCode},{Quantity},{Unit},{Price}";
        }
    }
}
