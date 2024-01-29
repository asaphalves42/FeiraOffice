using APILP3.Models;
using Azure;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.AspNetCore.Mvc.ViewFeatures;
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

        public async Task OnGet()

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
                }
            }

            _logger.LogInformation("OnGet Home Page >>>>");

        }
      
    }

}


