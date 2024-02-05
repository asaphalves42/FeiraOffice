
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
using Microsoft.AspNetCore.Mvc.RazorPages;
using APILP3.Models;

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
        public RegisterUser Input { get; set; }

        public async Task<IActionResult> OnPostAsync(/*string returnUrl = null*/)
        {
            //returnUrl ??= Url.Content("~/");

            if (ModelState.IsValid)
            {
                var apiEndpoint = "https://services.inapa.com/feiraoffice/api/client/";
                var credentials = Convert.ToBase64String(Encoding.ASCII.GetBytes("FG2:W0gyYJ!)Y6"));

                using (var httpClient = new HttpClient())
                {
                    try
                    {
                        var user = new User
                        {
                            Name = Input.Nome,
                            Address1 = Input.Morada1,
                            Address2 = Input.Morada2,
                            PostalCode = Input.CodigoPostal,
                            City = Input.Cidade,
                            Country = Input.Pais,
                            TaxIdentificationNumber = Input.Nif,
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

        private string ConstruirDadosDoCliente(User user)
        {
            return $"{{\"Id\": \"{Guid.NewGuid()}\","
                 + $"\"GroupId\": \"FG2\","
                 + $"\"Name\": \"{user.Name}\","
                 + $"\"Email\": \"{user.Email}\","
                 + $"\"Address1\": \"{user.Address1}\","
                 + $"\"Address2\": \"{user.Address2}\","
                 + $"\"PostalCode\": \"{user.PostalCode}\","
                 + $"\"City\": \"{user.City}\","
                 + $"\"Country\": \"{user.Country}\","
                 + $"\"TaxIdentificationNumber\": \"{user.TaxIdentificationNumber}\","
                 + $"\"Password\": \"{Input.Password}\","
                 + "\"Active\": false}}";
        }
    }
}
