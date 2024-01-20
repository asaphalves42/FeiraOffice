using APILP3.Areas.Identity.Data;
using APILP3.Models;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using System.Diagnostics;

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
    }
}