using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore.Metadata.Internal;

namespace APILP3.Areas.Identity.Data;

// Add profile data for application users by adding properties to the APILP3User class
public class APILP3User : IdentityUser
{
  [PersonalData]  
  [Column(TypeName = "nvarchar(100)")]
    public string Nome { get; set; }

    [PersonalData]
    [Column(TypeName = "nvarchar(100)")]
    public string Morada1 { get; set; }

    [PersonalData]
    [Column(TypeName = "nvarchar(100)")]
    public string Morada2 { get; set; }

    [PersonalData]
    [Column(TypeName = "nvarchar(100)")]
    public string CodigoPostal { get; set; }

    [PersonalData]
    [Column(TypeName = "nvarchar(100)")]
    public string Cidade { get; set; }

    [PersonalData]
    [Column(TypeName = "nvarchar(100)")]
    public string Pais { get; set; }

    [PersonalData]
    [Column(TypeName = "nvarchar(100)")]
    public string Nif { get; set; }

    [PersonalData]
    [Column(TypeName = "bit")]
    public bool Active { get; set; }

    public override string ToString()
    {
        return $"{Morada1},{Morada2},{Cidade},{Pais},{CodigoPostal}";
    }

}

