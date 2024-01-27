using APILP3.Areas.Identity.Data;

namespace APILP3.Models
{
    public class UserRequest
    {
        public string Status { get; set; }

        public List<APILP3User> Client { get; set; }
    }
}
