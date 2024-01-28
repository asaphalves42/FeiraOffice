using APILP3.Areas.Identity.Data;
using APILP3.Models;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using System.Diagnostics;
using System.Text;
using System.Text.Json;

namespace APILP3.Controllers
{
    [Authorize]
    public class HomeController : Controller
    {
        private readonly ILogger<HomeController> _logger;
        private readonly UserManager<APILP3User> _userManager;

        public HomeController(ILogger<HomeController> logger,UserManager<APILP3User> userManager)
        {
            _logger = logger;
            this._userManager = userManager;
        }

        public IActionResult Index()
        {
            ViewData["UserID"] =_userManager.GetUserId(this.User);
            return View();
        }

        public IActionResult Privacy()
        {
            return View();
        }

        [ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
        public IActionResult Error()
        {
            return View(new ErrorViewModel { RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier });
        }

        [HttpPost]
        [Route("/Identity/Account/Manage/HomePage")]
        public async Task<IActionResult> ReceberDados([FromBody]ProductRequest produtos)
        {
            try
            {
               
                if (produtos == null || produtos.Products == null || produtos.Products.Count == 0)
                {
                    return BadRequest("Nenhum produto enviado na solicitação");
                }


                // Armazenar os produtos selecionados no TempData
                TempData["ProdutosSelecionados"] = Convert.ToBase64String(Encoding.UTF8.GetBytes(JsonSerializer.Serialize(produtos.Products)));



                return Ok(new { Message = "Dados recebidos com sucesso!", Products = produtos });

               

            }
            catch (Exception ex)
            {
                // Log do erro para diagnóstico
                _logger.LogError(ex, "Erro ao processar dados recebidos");

                // Responda ao cliente com código 500 (Internal Server Error)
                return StatusCode(500, "Erro interno do servidor");
            }
        }

    

    }
}