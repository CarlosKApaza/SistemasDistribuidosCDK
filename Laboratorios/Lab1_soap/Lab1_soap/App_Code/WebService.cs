using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;


using MySql.Data.MySqlClient;

/// <summary>
/// Descripción breve de WebService
/// </summary>
[WebService(Namespace = "http://tempuri.org/")]
[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
public class WebService : System.Web.Services.WebService
{
    // Cambié el nombre a "cadenaConexion" para que no choque con tus variables de abajo
    string cadenaConexion = "Server=localhost;Database=lab1_cotizacion;User Id=root;Password=;";

    public WebService()
    {
    }

    [WebMethod]
    public decimal obtenerCotizacion(string fecha)
    {
        decimal monto = 0;
        // Usamos MySqlConnection en lugar de SqlConnection
        using (MySqlConnection con = new MySqlConnection(cadenaConexion))
        {
            string sql = "SELECT Cotizacion FROM Cotizaciones WHERE Fecha = @f";
            // Usamos MySqlCommand en lugar de SqlCommand
            MySqlCommand cmd = new MySqlCommand(sql, con);
            cmd.Parameters.AddWithValue("@f", fecha);

            con.Open();
            object res = cmd.ExecuteScalar();
            if (res != null) monto = Convert.ToDecimal(res);
        }
        return monto;
    }

    [WebMethod]
    public string registrarCotizacion(string fecha, decimal monto)
    {
        try
        {
            // Usamos MySqlConnection
            using (MySqlConnection con = new MySqlConnection(cadenaConexion))
            {
                string sql = "INSERT INTO Cotizaciones (Fecha, Cotizacion, Cotizacion_oficial) VALUES (@f, @m, 6.97)";
                // Usamos MySqlCommand
                MySqlCommand cmd = new MySqlCommand(sql, con);
                cmd.Parameters.AddWithValue("@f", fecha); 
                cmd.Parameters.AddWithValue("@m", monto);

                con.Open();
                cmd.ExecuteNonQuery();
            }
            return "Registrado bro";
        }
        catch (Exception ex)
        {
            return "Error: " + ex.Message;
        }
    }
}