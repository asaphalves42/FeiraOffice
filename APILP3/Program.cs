using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;
using APILP3.Data;
using APILP3.Areas.Identity.Data;

var builder = WebApplication.CreateBuilder(args);
var connectionString = builder.Configuration.GetConnectionString("APILP3DBContextConnection") ?? throw new InvalidOperationException("Connection string 'APILP3DBContextConnection' not found.");

builder.Services.AddDbContext<APILP3DBContext>(options => options.UseSqlServer(connectionString));

builder.Services.AddDefaultIdentity<APILP3User>(options => options.SignIn.RequireConfirmedAccount = false).AddEntityFrameworkStores<APILP3DBContext>();


builder.Services.AddControllersWithViews();
builder.Services.AddRazorPages();


builder.Services.Configure<IdentityOptions>(options =>
{
    

    options.Password.RequireUppercase = false;
    options.Password.RequireLowercase = false;
    options.Password.RequireDigit = false;
    options.Password.RequireNonAlphanumeric= false;
    options.Password.RequiredUniqueChars = 0;
    



});
var app = builder.Build();

// Configure the HTTP request pipeline.
if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Home/Error");
}
app.UseStaticFiles();

app.UseRouting();

app.UseAuthorization();

app.MapControllerRoute(
    name: "default",
    pattern: "{controller=Home}/{action=Index}/{id?}");
app.MapRazorPages();
app.Run();
