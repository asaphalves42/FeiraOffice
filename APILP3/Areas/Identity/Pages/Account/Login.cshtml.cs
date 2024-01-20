using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Security.Claims;
using System.Text;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.Extensions.Logging;

namespace APILP3.Areas.Identity.Pages.Account
{
    public class LoginModel : PageModel
    {
        private readonly ILogger<LoginModel> _logger;

        public LoginModel(ILogger<LoginModel> logger)
        {
            _logger = logger;
        }

        [BindProperty]
        public InputModel Input { get; set; }

        public IList<AuthenticationScheme> ExternalLogins { get; set; }

        [TempData]
        public string ErrorMessage { get; set; }

        public class InputModel
        {
            [Required(ErrorMessage = "Introduz o email !")]
            [EmailAddress]
            public string Email { get; set; }

            [Required (ErrorMessage="Introduz a password !")]
            [DataType(DataType.Password)]
            public string Password { get; set; }

            [Display(Name = "Remember me?")]
            public bool RememberMe { get; set; }
        }

        public async Task OnGetAsync(string returnUrl = null)
        {
            if (User.Identity.IsAuthenticated)
            {
                Response.Redirect("/");
            }
            if (!string.IsNullOrEmpty(ErrorMessage))
            {
                ModelState.AddModelError(string.Empty, ErrorMessage);
            }

            returnUrl ??= Url.Content("~/");

            // Clear the existing external cookie to ensure a clean login process
            await HttpContext.SignOutAsync(IdentityConstants.ExternalScheme);

            ExternalLogins = new List<AuthenticationScheme>();
        }

        public async Task<IActionResult> OnPostAsync(string returnUrl = null)
        {
            returnUrl ??= Url.Content("~/");

            ExternalLogins = new List<AuthenticationScheme>();

            if (ModelState.IsValid)
            {
                var apiEndpoint = "https://services.inapa.com/feiraoffice/api/client/login";
                var credentials = Convert.ToBase64String(Encoding.ASCII.GetBytes("FG2:W0gyYJ!)Y6"));

                using (var httpClient = new HttpClient())
                {
                    try
                    {
                        var loginData = ConstruirDadosDoLogin();

                        httpClient.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Basic", credentials);

                        var jsonContent = new StringContent(loginData, Encoding.UTF8, "application/json");
                        var response = await httpClient.PostAsync(apiEndpoint, jsonContent);

                        if (response.IsSuccessStatusCode)
                        {
                            _logger.LogInformation("Utilizador autenticado com sucesso.");

                            
                            //return LocalRedirect(returnUrl);
                            return Redirect("http://www.google.com");

                        }
                        else
                        {
                            ModelState.AddModelError(string.Empty, "Tentativa de login inválida.");
                            return Page();
                        }
                    }
                    catch (Exception ex)
                    {
                        _logger.LogError($"Erro ao autenticar usuário: {ex.Message}");
                        ModelState.AddModelError(string.Empty, "Erro ao autenticar usuário.");
                        return Page();
                    }
                }
            }

            // Se chegou até aqui, algo deu errado, redisplay form
            return Page();
        }

        private string ConstruirDadosDoLogin()
        {
            return $"{{\"Email\": \"{Input.Email}\","
                 + $"\"Password\": \"{Input.Password}\"}}";
        }
    }
}
