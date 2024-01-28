using APILP3.Models;
using Azure;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.Extensions.Logging;
using NuGet.Packaging.Signing;
using NuGet.Protocol;
using System.Net.Http.Headers;
using System.Text;
using System.Text.Json;


namespace APILP3.Areas.Identity.Pages.Account.Manage
{
    public class HomePageModel : PageModel
    {

        private readonly ILogger<HomePageModel> _logger;

        public List<Product> Products { get; set; }

        public HomePageModel(ILogger<HomePageModel> logger)
        {
            _logger = logger;
        }

        public async Task<IActionResult> OnGet()

        {
            var apiEndpoint = "https://services.inapa.com/feiraoffice/api/product/";
            var credentials = Convert.ToBase64String(Encoding.ASCII.GetBytes("FG2:W0gyYJ!)Y6"));

            using (var httpClient = new HttpClient())
            {
                try
                {
                    httpClient.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Basic", credentials);

                    HttpResponseMessage response = await httpClient.GetAsync(apiEndpoint);

                    if(response.IsSuccessStatusCode)
                    {
                        string responseData = await response.Content.ReadAsStringAsync();
                        _logger.LogInformation(responseData);

                        ProductRequest result2 = JsonSerializer.Deserialize<ProductRequest>(responseData);
                        Products = result2.Products;


                    }
                }
                catch (Exception ex)
                {
                    _logger.LogError($"Erro ao procurar produtos: {ex.Message}");
                    ModelState.AddModelError(string.Empty, "Erro ao procurar produtos.");
                    return Page();
                }
            }

            return Page();

        }
       
        [HttpPost]
        [Route("/Identity/Account/Manage/HomePage")]
        public async Task<IActionResult> OnPostAsync([FromBody] List<Product> selectedProductsData)
        {
            _logger.LogInformation(selectedProductsData.ToString());
            // Processar a string JSON em SelectedProducts
            // Aqui voc� pode converter a string JSON de volta para a lista de produtos ou fazer o que for necess�rio

            // Exemplo: var products = JsonSerializer.Deserialize<List<Product>>(SelectedProducts);

            // Se precisar, voc� pode acessar o token de verifica��o de solicita��o usando User.Claims
            // var verificationToken = User.Claims.FirstOrDefault(c => c.Type == "http://schemas.microsoft.com/identity/claims/nonce")?.Value;

            // Seu c�digo de processamento aqui

            TempData["SelectedProductsData"] = selectedProductsData;

            // Ap�s processar os dados, voc� pode redirecionar para outra p�gina ou realizar outras a��es
            return RedirectToPage("/Identity/Account/Manage/FinalizarCompra");
        }
    }

}


