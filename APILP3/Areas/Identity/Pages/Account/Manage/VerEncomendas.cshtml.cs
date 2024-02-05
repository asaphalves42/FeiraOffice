using APILP3.Models.Requests;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;
using System.Net.Http.Headers;
using System.Security.Claims;
using System.Text;
using System.Text.Json;

namespace APILP3.Areas.Identity.Pages.Account.Manage
{
    public class VerEncomendasModel : PageModel
    {
        private readonly ILogger<VerEncomendasModel> _logger;

        public VerEncomendasModel(ILogger<VerEncomendasModel> logger)
        {
            _logger = logger;
        }

        public List<OrderGetRequest> UserOrders { get; set; } = new List<OrderGetRequest>();

        public async Task OnGet()
        {
            ClaimsPrincipal userPrincipal = HttpContext.User;
            var userId = userPrincipal.Claims.ElementAt(0).Value;

            var apiEndpoint = $"https://services.inapa.com/feiraoffice/api/order/client/{userId}";
            var credentials = Convert.ToBase64String(Encoding.ASCII.GetBytes("FG2:W0gyYJ!)Y6"));

            using (var httpClient = new HttpClient())
            {
                try
                {
                    httpClient.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Basic", credentials);
                    HttpResponseMessage response = await httpClient.GetAsync(apiEndpoint);
                   
                    if (response.IsSuccessStatusCode)
                    {
                        string responseData = await response.Content.ReadAsStringAsync();
                        _logger.LogInformation(responseData);

                        OrderRequest resultado = JsonConvert.DeserializeObject<OrderRequest>(responseData);
                        UserOrders = resultado.Orders;

                        //_logger.LogInformation(resultado.ToString());

                    }

                }
                catch (Exception ex)
                {
                    _logger.LogInformation(ex.Message);
                    _logger.LogInformation("Erro ao ler orders");
                }


            }

        }
    }
}
