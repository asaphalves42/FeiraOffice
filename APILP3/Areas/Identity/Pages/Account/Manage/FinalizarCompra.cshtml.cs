using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using APILP3.Models;
using System.Text.Json;
using Microsoft.CodeAnalysis.CSharp.Syntax;
using System.Text;
using Microsoft.AspNetCore.Mvc.ModelBinding.Validation;
using System.Security.Claims;
using Microsoft.AspNetCore.Http;
using System.Net.Mail;
using System.Collections.Specialized;
using System.Drawing;
using System.Globalization;
using System.Net.Http.Headers;
using Newtonsoft.Json;
using Microsoft.EntityFrameworkCore.Metadata.Internal;
using Microsoft.Extensions.Logging;

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
        public List<User> dadosUtil { get; set; } = new List<User>();
        public string Message { get; set; }

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
                           
                            // Obter dados do formulário de morada de envio
                            string shippingAddress1 = HttpContext.Request.Form["shippingAddress1"];
                            string shippingAddress2 = HttpContext.Request.Form["shippingAddress2"];
                            string shippingPostalCode = HttpContext.Request.Form["shippingPostalCode"];
                            string shippingCity = HttpContext.Request.Form["shippingCity"];
                            string shippingCountry = HttpContext.Request.Form["shippingCountry"];

                            Address billing = new Address
                            {
                                Address1 = userAddress1,
                                Address2 = userAddress2,
                                PostalCode = userCodigoPostal,
                                City = userCity,
                                Country = userPais
                            };

                            Address delivery = new Address
                            {
                                Address1 = shippingAddress1,
                                Address2 = shippingAddress2,
                                PostalCode = shippingPostalCode,
                                City = shippingCity,
                                Country = shippingCountry
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
                           
                            Message = "Compra efetuada com sucesso, aguarda aprovação!";
                           
                        }
                            else
                            {
                                _logger.LogInformation(response.StatusCode.ToString());
                                Message = "Ocorreu um erro ao criar a order!";
                                _logger.LogInformation("Ocorreu um erro ao criar a order!");
                            }

                        }
                        else
                        {

                        _logger.LogInformation("User não foi aprovado!");
                        
                        Message = "User não foi aprovado!";

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
            using (var httpClient = new HttpClient())
            {
                try
                {
                     ClaimsPrincipal userPrincipal = HttpContext.User;
                            var userId = userPrincipal.Claims.ElementAt(0).Value;
                            var userAddress1 = userPrincipal.Claims.ElementAt(1).Value;
                            var userAddress2 = userPrincipal.Claims.ElementAt(2).Value;
                            var userCity = userPrincipal.Claims.ElementAt(3).Value;
                            var userCodigoPostal = userPrincipal.Claims.ElementAt(4).Value;
                            var userPais = userPrincipal.Claims.ElementAt(5).Value;
                            var active = Boolean.Parse(userPrincipal.Claims.ElementAt(6).Value);
                            var userNome = userPrincipal.Claims.ElementAt(7).Value;
                            var userNif = userPrincipal.Claims.ElementAt(8).Value;
                            var userEmail = userPrincipal.Claims.ElementAt(9).Value;

                    User user = new User
                    {
                        Id = userId,
                        Address1 = userAddress1,
                        Address2 = userAddress2,
                        PostalCode = userCodigoPostal,
                        City = userCity,
                        Country = userPais,
                        Name = userNome,
                        Active = active,
                        Email = userEmail,
                       
                    };

                    dadosUtil.Add(user);

                    _logger.LogInformation(dadosUtil.ToString());

                    } catch (Exception ex)
                {
                    _logger.LogInformation("Erro ao obter dados do utilizador");
                    ex.ToString();
                }
            }
            return null;
        }
    }
}
    

