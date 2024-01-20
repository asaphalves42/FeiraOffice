using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Security.Claims;
using System.Text;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Authentication.Cookies;
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

        public async Task OnGetAsync()
        {
        
            _logger.LogInformation("OnGetAsync1");
            if (User.Identity.IsAuthenticated)
            {
                _logger.LogInformation("OnGetAsync2");
                Response.Redirect("/");
            }
            if (!string.IsNullOrEmpty(ErrorMessage))
            {
                _logger.LogInformation("OnGetAsync3");
                ModelState.AddModelError(string.Empty, ErrorMessage);
            }
            _logger.LogInformation("OnGetAsync4");
            
        }

        public async Task<IActionResult> OnPostAsync()
        {
         
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
                        _logger.LogInformation(loginData);

                        httpClient.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Basic", credentials);

                        var jsonContent = new StringContent(loginData, Encoding.UTF8, "application/json");
                        var response = await httpClient.PostAsync(apiEndpoint, jsonContent);

                        if (response.IsSuccessStatusCode)
                        {
                            _logger.LogInformation("Utilizador autenticado com sucesso.");

                            var claims = new List<Claim>
                            {
                                new Claim(ClaimTypes.Name,Input.Email ), 
                            };

                            var claimsIdentity = new ClaimsIdentity(claims, CookieAuthenticationDefaults.AuthenticationScheme);

                            var authProperties = new AuthenticationProperties
                            {
                                
                            };

                            HttpContext.SignInAsync(CookieAuthenticationDefaults.AuthenticationScheme, new ClaimsPrincipal(claimsIdentity), authProperties);

                            return Redirect("/Identity/Account/Manage/HomePage");

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
