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
    public partial class frmSingleImport : Form
    {
        private String applicantName;
        private String result;
        private String mailFlag;
        private String entryId;
        private Microsoft.Office.Interop.Outlook.Application applicationObject;

        public void setMailFlag(String flag)
        {
            this.mailFlag = flag;
        }

        public void setEntryId(String entryId)
        {
            this.entryId = entryId;
        }

        public void setApplicationObject(Microsoft.Office.Interop.Outlook.Application applicationObject) {
            this.applicationObject = applicationObject;
        }

        public frmSingleImport()
        {
            InitializeComponent();
            webBrowser1.Visible = true;
            button1.Visible = false;
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
                button1.Visible = true;
                applicantName = webBrowser1.Document.All["applicantName"].GetAttribute("value");
                result = response;
            }
        }

        private void webBrowser1_Navigating(object sender, WebBrowserNavigatingEventArgs e)
        {
            // Set text while the page has not yet loaded.

        }

        public void Navigate(String url)
        {
            try
            {   
                webBrowser1.Navigate(new Uri(url));
            }
            catch (Exception e)
            {
                System.Windows.Forms.MessageBox.Show("Single Import internal error");
            }

        }

        private void button1_Click(object sender, EventArgs e)
        {
            Common cm = new Common();
            String flagMessage = null;
            if (mailFlag == "1")
            {
                if (String.Equals(result, "SUCCESS", StringComparison.OrdinalIgnoreCase))
                {
                    flagMessage = cm.getTimeFormat() + "Email Imported for " + applicantName;
                    cm.addFollowUpMessage(applicationObject, entryId, flagMessage);
                }
            }
           
            button1.Visible = false;
            webBrowser1.Navigate("about:blank");
            this.Close();

        }


    }
}
