using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using APILP3.Models;
using Newtonsoft.Json;
using Microsoft.CodeAnalysis.CSharp.Syntax;

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

        public async Task<IActionResult> OnGet()
        {
                // Recuperar os produtos selecionados armazenados no localStorage
                var selectedProductsJson = HttpContext.Request.Cookies["selectedProducts"];
                 _logger.LogInformation(selectedProductsJson);

             
             return null;
        }
            
       }
  }
    

