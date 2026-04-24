using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace clientForm
{
    public partial class Form1 : Form
    {
        // 1. Conexión con tu servicio web
        soap.WebServiceSoapClient cliente = new soap.WebServiceSoapClient();

        public Form1()
        {
            InitializeComponent();

            label1.Text = "Obtener cotización";
            button1.Text = "Cotizar";
            label2.Text = "...";

            label3.Text = "Registrar cotización (fecha, monto)";
            button2.Text = "Registrar";
            label4.Text = "...";
        }

        // --- BOTÓN 1: COTIZAR (Obtener) ---
        private void button1_Click(object sender, EventArgs e)
        {
            try
            {
                // CORREGIDO: Leemos el dateTimePicker2 (El de ARRIBA)
                string fecha = dateTimePicker2.Value.ToString("yyyy-MM-dd");

                decimal monto = cliente.obtenerCotizacion(fecha);

                if (monto > 0)
                {
                    label2.Text = "¡Éxito! Cotización: " + monto.ToString();
                }
                else
                {
                    // Le agregué la fecha al mensaje de error para que veas qué está buscando exactamente
                    label2.Text = "Fallo: No hay datos para " + fecha;
                }
            }
            catch (Exception ex)
            {
                label2.Text = "Error de conexión: " + ex.Message;
            }
        }

        // --- BOTÓN 2: REGISTRAR ---
        // CORREGIDO: Usamos el nombre exacto que te creó el diseñador (button2_Click_1)
        private void button2_Click_1(object sender, EventArgs e)
        {
            try
            {
                // CORREGIDO: Leemos el dateTimePicker1 (El de ABAJO)
                string fecha = dateTimePicker1.Value.ToString("yyyy-MM-dd");
                decimal monto = Convert.ToDecimal(textBox1.Text);

                string respuesta = cliente.registrarCotizacion(fecha, monto);
                label4.Text = respuesta;
            }
            catch (Exception)
            {
                label4.Text = "Error: Escribe un número válido en el monto.";
            }
        }

        // --- MÉTODOS DE RELLENO ---
        // (No los borres, sirven para que Visual Studio no te tire errores en el diseño)
        private void button2_Click(object sender, EventArgs e) { }
        private void label3_Click(object sender, EventArgs e) { }
        private void textBox1_TextChanged(object sender, EventArgs e) { }
        private void label1_Click(object sender, EventArgs e) { }
        private void label2_Click(object sender, EventArgs e) { }

        private void splitContainer2_Panel1_Paint(object sender, PaintEventArgs e)
        {

        }
    }
}