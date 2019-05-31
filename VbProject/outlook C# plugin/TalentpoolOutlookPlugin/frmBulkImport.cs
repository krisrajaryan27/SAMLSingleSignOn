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
    public partial class frmBulkImport : Form
    {
        private Connect con;
        private String sessionId;

        public void setConnectObject(Connect con) 
        {
            this.con = con;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }
        
        public frmBulkImport()
        {
            InitializeComponent();
            webBrowser1.Visible = true;
            label1.Visible = false;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            label1.Visible = false;
            webBrowser1.Visible = true;
            this.Close();
        }

        private void webBrowser1_DocumentCompleted(object sender, WebBrowserDocumentCompletedEventArgs e)
        {
            String response = null;
            if (webBrowser1.Document != null)
            {
                if (webBrowser1.Document.All["result"] != null)
                {
                    response = webBrowser1.Document.All["result"].GetAttribute("value");
                }
            }
            if (response != null)
            {
                if (String.Equals(response, "SUCCESS", StringComparison.OrdinalIgnoreCase))
                {
                    webBrowser1.Visible = false;
                    label1.Visible = true;
                    button1.Visible = false;
                    con.bulkImportParse(sessionId,this);
                }
            }
        }

        public void Navigate(String url) {
            try
            {
                webBrowser1.Navigate(new Uri(url));
            }
            catch (Exception e)
            {
                System.Windows.Forms.MessageBox.Show("Bulk Import internal error");
            }
        }


    }
}
