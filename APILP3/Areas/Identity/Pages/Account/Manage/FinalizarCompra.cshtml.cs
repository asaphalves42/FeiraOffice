using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using APILP3.Models;
using System.Text.Json;
using Microsoft.CodeAnalysis.CSharp.Syntax;
using System.Text;
using Microsoft.AspNetCore.Mvc.ModelBinding.Validation;
using APILP3.Areas.Identity.Data;
using System.Security.Claims;
using Microsoft.AspNetCore.Http;
using System.Net.Mail;
using System.Collections.Specialized;

namespace APILP3.Areas.Identity.Pages.Account.Manage
{
    public class FinalizarCompraModel : PageModel
    {
        private readonly ILogger<FinalizarCompraModel> _logger;

        public FinalizarCompraModel(ILogger<FinalizarCompraModel> logger)
        {
            _logger = logger;
        }

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
                            // Obter dados do formulário de morada de faturação
                            string billingAddress1 = HttpContext.Request.Form["billingAddress1"];
                            string billingAddress2 = HttpContext.Request.Form["billingAddress2"];
                            string billingPostalCode = HttpContext.Request.Form["billingPostalCode"];
                            string billingCity = HttpContext.Request.Form["billingCity"];
                            string billingCountry = HttpContext.Request.Form["billingCountry"];

                            // Obter dados do formulário de morada de envio
                            string shippingAddress1 = HttpContext.Request.Form["shippingAddress1"];
                            string shippingAddress2 = HttpContext.Request.Form["shippingAddress2"];
                            string shippingPostalCode = HttpContext.Request.Form["shippingPostalCode"];
                            string shippingCity = HttpContext.Request.Form["shippingCity"];
                            string shippingCountry = HttpContext.Request.Form["shippingCountry"];

                            Address billingAddress = new Address
                            {
                                Address1 = billingAddress1,
                                Address2 = billingAddress2,
                                PostalCode = billingPostalCode,
                                City = billingCity,
                                Country = billingCountry
                            };

                            Address shippingAddress = new Address
                            {
                                Address1 = shippingAddress1,
                                Address2 = shippingAddress2,
                                PostalCode = shippingPostalCode,
                                City = shippingCity,
                                Country = shippingCountry
                            };






                            Order order = new Order
                            {
                                Id = "IdTeste",
                                Date = currentDate,
                                ClientId = user.Id,
                                BillingAddress = billingAddress,
                                Shipping = shippingAddress,
                                //Net Amount
                                TaxAmount = 0.23,
                                //TotalAmount = NetAmount * TaxAmout
                                Currency = "EUR",

                                //Lista de produtos





                            };




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



                }
                catch (Exception ex)
                {

                }
            }

            return null;


        }

        public async Task<IActionResult> OnGet()
        {
            try
            {
                if (TempData.TryGetValue("ProdutosSelecionados", out var produtosSelecionadosBase64) &&
                    produtosSelecionadosBase64 is string produtosSelecionadosBase64String)
                {
                    // Convertendo de Base64 para o array de bytes
                    byte[] produtosSelecionadosBytes = Convert.FromBase64String(produtosSelecionadosBase64String);

                    // Convertendo bytes de volta para a lista de produtos
                    string produtosSelecionadosJson = Encoding.UTF8.GetString(produtosSelecionadosBytes);
                    List<Product> produtosSelecionados = JsonSerializer.Deserialize<List<Product>>(produtosSelecionadosJson);
                    

                    // Agora, você pode usar a lista de produtos conforme necessário
                    _logger.LogInformation("Produtos Selecionados Count: " + produtosSelecionados.Count);

                  
                    // Calcular a soma do valor total (preço * quantidade) dos produtos
                    double somaTotal = CalcularSomaTotal(produtosSelecionados);

                    _logger.LogInformation("Soma do PVP dos Produtos: " + somaTotal);


                }
                else
                {
                    _logger.LogInformation("Nenhum produto selecionado encontrado no TempData");
                }

                // Restante do seu código...

            }
            catch (Exception ex)
            {
                _logger.LogError(ex, "Erro durante o processamento: " + ex.Message);
                // Lide com a exceção conforme necessário...
            }

            return null;
        }

        private int GetProductQuantity(string productCode)
        {
            // Obtém o conteúdo dos produtos selecionados do TempData
            if (TempData.TryGetValue("ProdutosSelecionados", out var produtosSelecionadosBase64))
            {
                if (produtosSelecionadosBase64 is string produtosSelecionadosBase64String)
                {
                    // Convertendo de Base64 para o array de bytes
                    byte[] produtosSelecionadosBytes = Convert.FromBase64String(produtosSelecionadosBase64String);

                    // Convertendo bytes de volta para a lista de produtos
                    string produtosSelecionadosJson = Encoding.UTF8.GetString(produtosSelecionadosBytes);
                    List<Product> produtosSelecionados = JsonSerializer.Deserialize<List<Product>>(produtosSelecionadosJson);

                    // Log do conteúdo dos produtos selecionados
                    int quantidade = 0;
                    foreach (var produto in produtosSelecionados)
                    {
                        _logger.LogInformation($"Produto: Code = {produto.Code}, Description = {produto.Description}, Quantity = {produto.Quantity}, PVP = {produto.PVP}");
                        quantidade = produto.Quantity;
                    }

                    _logger.LogInformation(quantidade.ToString());
                    return quantidade;
                }
            }

            // Retorna 0 se não encontrar a quantidade
            return 0;
        }


        public double CalcularSomaTotal(List<Product> produtosSelecionados)
        {
            double somaTotal = produtosSelecionados.Sum(p => p.PVP * GetProductQuantity(p.ID));
            return somaTotal;
        }

    }
}
    

