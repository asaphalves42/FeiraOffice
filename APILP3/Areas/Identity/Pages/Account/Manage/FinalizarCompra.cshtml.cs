using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using APILP3.Models;
using System.Text.Json;
using Microsoft.CodeAnalysis.CSharp.Syntax;
using System.Text;
using Microsoft.AspNetCore.Mvc.ModelBinding.Validation;
using APILP3.Areas.Identity.Data;
using System.Security.Claims;

namespace APILP3.Areas.Identity.Pages.Account.Manage
{
    public class FinalizarCompraModel : PageModel
    {
        private readonly ILogger<FinalizarCompraModel> _logger;

        public FinalizarCompraModel(ILogger<FinalizarCompraModel> logger)
        {
            _logger = logger;
        }
        public List<Product> SelectedProducts { get; set; } = new List<Product>();

        public async Task<IActionResult> OnPostAsync()
        {
            var apiEndpoint = "https://services.inapa.com/feiraoffice/api/order/";
            var credentials = Convert.ToBase64String(Encoding.ASCII.GetBytes("FG2:W0gyYJ!)Y6"));

            using (var httpClient = new HttpClient())
            {
                try
                {
                    
                    ClaimsPrincipal userPrincipal = HttpContext.User;
                    Claim userClaim = userPrincipal.FindFirst("APILP3User");
                    _logger.LogInformation("Try get user..." + userClaim.Type);
                    _logger.LogInformation("Try get user2..." + userClaim.ValueType);
                    if (userClaim != null)
                    {
                        // Deserialize the user information from the claim
                        APILP3User user = JsonSerializer.Deserialize<APILP3User>(userClaim.Value);
                        _logger.LogInformation("Try get user3 ID..." + user.Id);
                        _logger.LogInformation("Try get user3 Active..." + user.Active);
                        DateTime currentDate = DateTime.Now;

                        if (!user.Active) //mudar isto
                        {
                            _logger.LogInformation("UserID ORDER() -> ." + user.Id);
                            _logger.LogInformation("SelectedProducts -> ." + SelectedProducts.Count());

                            
                            Order order = new Order(





                            );

                        }
                        else
                        {
                            return Page();
                        }

                    }
                    else
                    {
                        return Page();
                    }



                }catch (Exception ex)
                {

                }
            }

            return null;


        }

        public async Task<IActionResult> OnGet()
        {
                // Recuperar os produtos selecionados armazenados no localStorage
                var selectedProductsJson = HttpContext.Request.Cookies["selectedProducts"];
                 _logger.LogInformation(selectedProductsJson);

             
             return null;
        }
            
       }
  }
    

