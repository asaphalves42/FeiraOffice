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
using System.Drawing;
using System.Globalization;
using System.Net.Http.Headers;
using Newtonsoft.Json;

namespace APILP3.Areas.Identity.Pages.Account.Manage
{
    public class FinalizarCompraModel : PageModel
    {
        private readonly ILogger<FinalizarCompraModel> _logger;

        public FinalizarCompraModel(ILogger<FinalizarCompraModel> logger)
        {
            _logger = logger;
        }

        [BindProperty]
        public List<OrderLine> ProductsDataAux { get; set; }

        public async Task<IActionResult> OnPostAsync()
        {
            var apiEndpoint = "https://services.inapa.com/feiraoffice/api/order/";
            var credentials = Convert.ToBase64String(Encoding.ASCII.GetBytes("FG2:W0gyYJ!)Y6"));

            using (var httpClient = new HttpClient())
            {
                try
                {

                    ClaimsPrincipal userPrincipal = HttpContext.User;


                        // Deserialize the user information from the claim
                        var userId = userPrincipal.Claims.ElementAt(0).Value;
                        var userAddress1 = userPrincipal.Claims.ElementAt(1).Value;
                        var userAddress2 = userPrincipal.Claims.ElementAt(2).Value;
                        var userCity = userPrincipal.Claims.ElementAt(3).Value;
                        var userCodigoPostal = userPrincipal.Claims.ElementAt(4).Value;
                        var userPais = userPrincipal.Claims.ElementAt(5).Value;
                        var userActive = Boolean.Parse(userPrincipal.Claims.ElementAt(6).Value);

                        DateTime currentDate = DateTime.Now;

                        if (userActive) 
                        {
                            _logger.LogInformation("UserID ORDER() -> ." + userId);
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

                            Address billing = new Address
                            {
                                Address1 = shippingAddress1,
                                Address2 = shippingAddress1,
                                PostalCode = shippingAddress1,
                                City = shippingAddress1,
                                Country = shippingAddress1
                            };

                            Address delivery = new Address
                            {
                                Address1 = userAddress1,
                                Address2 = userAddress2,
                                PostalCode = userCodigoPostal,
                                City = userCity,
                                Country = userPais
                            };



                            string productsDataAuxJson = HttpContext.Request.Form["productsDataAux"];
                            //ProductsDataAux = JsonSerializer.Deserialize<List<OrderLine>>(productsDataAuxJson);
                            var Produtos = JsonConvert.DeserializeObject<List<Product>>(productsDataAuxJson);

                            List<OrderLine> orders = new List<OrderLine>();
                            
                            var lineNumber = 0;
                            foreach (var productsDataAux in Produtos)
                            {
                                lineNumber++;
                                OrderLine orderLine = new OrderLine
                                {
                                    LineNumber = lineNumber,
                                    Price = Math.Round(productsDataAux.PVP,2),
                                    ProductCode = productsDataAux.ID,
                                    Quantity = productsDataAux.Quantity,
                                    Unit = productsDataAux.Unit,
                                };
                                orders.Add(orderLine);

                                _logger.LogInformation(productsDataAux.ToString());
                                
                            }

                            // Cálculo do NetAmount
                            double netAmount = Produtos.Sum(line => line.PVP * line.Quantity);
                            netAmount = Math.Round(netAmount, 2);

                            //calculdo do total
                            double taxAmount = netAmount * 0.23;
                            taxAmount = Math.Round(taxAmount, 2);

                           
                            double total = netAmount + taxAmount;
                            total = Math.Round(total, 2);

                        

                            Order order = new Order
                            {
                                Id = "",
                                Date = currentDate,
                                ClientId = userId,
                                BillingAddress = billing,
                                DeliveryAddress = delivery,
                                NetAmount = netAmount,
                                TaxAmount = taxAmount,
                                TotalAmount = total,
                                Currency = "EUR",

                                //Lista de produtos
                                Lines = orders,

                            };

                            var jsonData = JsonConvert.SerializeObject(order);

                            
                            httpClient.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Basic", credentials);

                            var jsonContent = new StringContent(jsonData, Encoding.UTF8, "application/json");
                            var response = await httpClient.PostAsync(apiEndpoint, jsonContent);

                            if (response.IsSuccessStatusCode)
                            {
                                _logger.LogInformation("Order criada com sucesso!");
                            }
                            else
                            {
                                _logger.LogInformation(response.StatusCode.ToString());
                                _logger.LogInformation("Ocorreu um erro ao criar a order!");
                            }

                        }
                        else
                        {

                        _logger.LogInformation("User não foi aprovado!");

                        //return RedirectToAction("HomePage","Utilizador não ativo!");

                        }
                    
                }
                catch (Exception ex)
                {
                   
                }
            }

            return null;


        }




    }
}
    

