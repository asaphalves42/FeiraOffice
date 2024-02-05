using System.ComponentModel.DataAnnotations;

namespace APILP3.Models
{
    public class Login
    {
        [Required(ErrorMessage = "Introduz o email !")]
        [EmailAddress]
        public string Email { get; set; }

        [Required(ErrorMessage = "Introduz a password !")]
        [DataType(DataType.Password)]
        public string Password { get; set; }

        [Display(Name = "Remember me?")]
        public bool RememberMe { get; set; }
    }
}
