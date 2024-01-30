using System.ComponentModel.DataAnnotations;

namespace APILP3.Models
{
    public class RegisterUser
    {
           [Required(ErrorMessage = "Introduz o Nome !")]
            [DataType(DataType.Text)]
            [Display(Name = "Nome")]
            public string Nome { get; set; }

            [Required(ErrorMessage = "Introduz a Morada1 !")]
            [DataType(DataType.Text)]
            [Display(Name = "Morada 1")]
            public string Morada1 { get; set; }

            [DataType(DataType.Text)]
            [Display(Name = "Morada 2")]
            public string Morada2 { get; set; }

            [Required(ErrorMessage = "Introduz o Código Postal !")]
            [DataType(DataType.Text)]
            [Display(Name = "Codigo Postal")]
            public string CodigoPostal { get; set; }

            [Required(ErrorMessage = "Introduz a cidade !")]
            [DataType(DataType.Text)]
            [Display(Name = "Cidade")]
            public string Cidade { get; set; }

            [Required(ErrorMessage = "Introduz o País !")]
            [DataType(DataType.Text)]
            [Display(Name = "Pais")]
            public string Pais { get; set; }

            [Required(ErrorMessage = "Introduz o NIF !")]
            [DataType(DataType.Text)]
            [Display(Name = "Nif")]
            public string Nif { get; set; }

            [Required(ErrorMessage = "Introduz o email !")]
            [EmailAddress]
            [Display(Name = "Email")]
            public string Email { get; set; }

            [Required(ErrorMessage = "Introduz a  Password !")]
            [StringLength(100, ErrorMessage = "A {0} deve ter no minimo {2} e no maximo {1} caracteres.", MinimumLength = 6)]
            [DataType(DataType.Password)]
            [Display(Name = "Password")]

            public string Password { get; set; }
            [Required(ErrorMessage = "Introduz a confirmação da Password !")]
            [DataType(DataType.Password)]
            [Display(Name = "Confirma a password")]
            [Compare("Password", ErrorMessage = " As passwords nao sao iguais !")]
            public string ConfirmPassword { get; set; }
        }
    }

