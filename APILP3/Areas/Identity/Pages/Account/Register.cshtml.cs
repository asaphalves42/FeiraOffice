
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.WebUtilities;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Threading.Tasks;
using Microsoft.Extensions.Logging;
using APILP3.Areas.Identity.Data;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace APILP3.Areas.Identity.Pages.Account
{
    public class RegisterModel : PageModel
    {
        private readonly ILogger<RegisterModel> _logger;

        public RegisterModel(ILogger<RegisterModel> logger)
        {
            _logger = logger;
        }

        [BindProperty]
        public InputModel Input { get; set; }

        public class InputModel
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
            [Display(Name = "Email") ]
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

        public async Task<IActionResult> OnPostAsync(string returnUrl = null)
        {
            returnUrl ??= Url.Content("~/");

            if (ModelState.IsValid)
            {
                var apiEndpoint = "https://services.inapa.com/feiraoffice/api/client/";
                var credentials = Convert.ToBase64String(Encoding.ASCII.GetBytes("FG2:W0gyYJ!)Y6"));

                using (var httpClient = new HttpClient())
                {
                    try
                    {
                        var user = new APILP3User
                        {
                            Nome = Input.Nome,
                            Morada1 = Input.Morada1,
                            Morada2 = Input.Morada2,
                            CodigoPostal = Input.CodigoPostal,
                            Cidade = Input.Cidade,
                            Pais = Input.Pais,
                            Nif = Input.Nif,
                            Email = Input.Email,
                           
                        };

                        var clientData = ConstruirDadosDoCliente(user);

                        httpClient.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Basic", credentials);

                        var jsonContent = new StringContent(clientData, Encoding.UTF8, "application/json");
                        var response = await httpClient.PostAsync(apiEndpoint, jsonContent);

                        if (response.IsSuccessStatusCode)
                        {
                            _logger.LogInformation("Dados enviados com sucesso para a API.");
                           
                        }
                        else
                        {
                            _logger.LogError($"Falha ao enviar dados para a API. Status Code: {response.StatusCode}");
                            
                        }
                    }
                    catch (Exception ex)
                    {
                        _logger.LogError($"Erro ao enviar dados para a API: {ex.Message}");
                        
                    }
                }
            }

            return RedirectToPage("/Identity/Account/Login");
        }

        private string ConstruirDadosDoCliente(APILP3User user)
        {
            return $"{{\"Id\": \"{Guid.NewGuid()}\","
                 + $"\"GroupId\": \"FG2\","
                 + $"\"Name\": \"{user.Nome}\","
                 + $"\"Email\": \"{user.Email}\","
                 + $"\"Address1\": \"{user.Morada1}\","
                 + $"\"Address2\": \"{user.Morada2}\","
                 + $"\"PostalCode\": \"{user.CodigoPostal}\","
                 + $"\"City\": \"{user.Cidade}\","
                 + $"\"Country\": \"{user.Pais}\","
                 + $"\"TaxIdentificationNumber\": \"{user.Nif}\","
                 + $"\"Password\": \"{Input.Password}\","
                 + "\"Active\": false}}";
        }
    }
}
