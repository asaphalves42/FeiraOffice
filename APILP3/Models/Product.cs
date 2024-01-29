namespace APILP3.Models
{
    public class Product
    {      
        public string ID { get; set; }
        public string GroupId { get; set; }
        public string Code { get; set; }
        public string Description { get; set; }
        public double PVP { get; set; }
        public double Stock { get; set; }
        public string Unit {  get; set; }
        public bool Active { get; set; }
        public int Quantity { get; set; }

        public override string ToString()
        {
            return $"{ID},{GroupId},{Code},{Description},{PVP},{Stock},{Unit},{Quantity}";
        }

    }
}
