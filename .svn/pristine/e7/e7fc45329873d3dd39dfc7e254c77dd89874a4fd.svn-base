using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace TalentpoolOutlookPlugin
{
    public partial class frmLogin : Form
    {
        private String username;
        private String password;
        private String result;
        private String serverUrl;
        private Connect connectForm;
        private String userId = null;

        public frmLogin()
        {
            InitializeComponent();
            textBox1.MaxLength = 20;
            textBox2.Text = "";
            textBox2.PasswordChar = '*';
            textBox2.MaxLength = 25;
        }


        public void setServerUrl(String s)
        {
            this.serverUrl = s;
        }

        public void setConnectForm(Connect connectForm)
        {
            this.connectForm = connectForm;
        }

        private void textBox2_TextChanged(object sender, EventArgs e)
        {
            password = textBox2.Text;
        }

        private void textBox1_TextChanged(object sender, EventArgs e)
        {
            username = textBox1.Text;
        }

        private void button2_Click(object sender, EventArgs e)
        {
            textBox1.Clear();
            textBox2.Clear();
            this.Close();

        }

        private void button1_Click(object sender, EventArgs e)
        {
            try
            {
                String url = serverUrl;
                url += "/loginServlet.servlet?mode=processRequest&userName=" + username + "&password=" + password;
                Common com = new Common();
                result = com.getResponse(url);
                if (result != null && result.Length > 0)
                {
                    if (result.Contains("SUCCESS"))
                    {
                        String[] lines = result.Split(' ');
                        connectForm.setUserCredentials(lines[1], lines[2]);
                        userId = lines[2];
                    }
                    else if (result.Contains("FAIL"))
                    {
                        System.Windows.Forms.MessageBox.Show("Check Credentials");
                        textBox1.Clear();
                        textBox2.Clear();
                    }
                }


            }
            catch (Exception)
            {
                System.Windows.Forms.MessageBox.Show("Check Talentpool Settings");
                connectForm.settingsCheck = false;
                userId = null;
            }
            finally
            {
                if (userId != null)
                {
                    this.Close();
                }
            }

        }

    }
}
