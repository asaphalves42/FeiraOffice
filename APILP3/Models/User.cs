namespace APILP3.Models
{
    public class User
    {
        public string Id { get; set; }
        public string Name { get; set; }
        public string Email { get; set; }
        public string Address1 { get; set; }
        public string Address2 { get; set; }
        public string PostalCode {  get; set; }
        public string City { get; set; }
        public string Country { get; set; }
        public string TaxIdentificationNumber { get; set; }
        public bool Active { get; set; }
           
    }
}
