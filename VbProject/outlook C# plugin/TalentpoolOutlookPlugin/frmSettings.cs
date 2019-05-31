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
    public partial class frmSettings : Form
    {
        private String url;
        private String flag = "0";
        private String propertyFilePath;
        private Connect con;

        public frmSettings()
        {
            InitializeComponent();
        }

        public void setConnect(Connect con)
        {
            this.con = con;
        }

        public void setPropertyFilePath(String s)
        {
            this.propertyFilePath = s + @"\talentpoolProperties.txt";
        }

        public void showTextFromPropertyFile(String path, String flag)
        {
            textBox1.Text = path;

            if (flag == "1")
            {
                checkBox1.Checked = true;
            }
            else
            {
                checkBox1.Checked = false;
            }
        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }

        private void button1_Click(object sender, EventArgs e)
        {
            System.IO.StreamWriter file = new System.IO.StreamWriter(propertyFilePath);
            file.WriteLine(url);
            file.WriteLine(flag);
            file.Close();
            con.settingsCheck = false;
            con.setUserCredentials(null, null);
            this.Close();
        }

        private void textBox1_TextChanged(object sender, EventArgs e)
        {
            url = textBox1.Text;
        }

        private void button2_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void checkBox1_CheckedChanged(object sender, EventArgs e)
        {
            flag = ((CheckBox)sender).Checked ? "1" : "0";
        }

    }
}
