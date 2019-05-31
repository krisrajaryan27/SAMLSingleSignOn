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
    public partial class BulkImportParseResult : Form
    {
        private String emailIds;
        private String mailFlag;
        private Microsoft.Office.Interop.Outlook.Application applicationObject;

        public void setMailFlag(String flag) {
            this.mailFlag = flag;
        }

        public void setApplicationObject(Microsoft.Office.Interop.Outlook.Application applicationObject)
        {
            this.applicationObject = applicationObject;
        }

        public BulkImportParseResult()
        {
            InitializeComponent();
            button2.Visible = false;
            button1.Visible = true;
        }

        private void button1_Click(object sender, EventArgs e)
        {
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
                button2.Visible = true;
                button1.Visible = false;
                if (!String.Equals(response,"SUCCESS",StringComparison.OrdinalIgnoreCase)) {
                    emailIds = response;
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
                System.Windows.Forms.MessageBox.Show("Bulk Import parsing internal error");
            }
        }

        private void button2_Click(object sender, EventArgs e)
        {  
           
            if (mailFlag == "1") {
                Common com = new Common();
                String flagMessage = null;
                String[] emailWithApplicantName = null;
                if (emailIds != null) {
                    String[] results = emailIds.Split('$');
                    for (int i = 0; i < results.Length; i++) {
                        if (results[i] != null) {
                            emailWithApplicantName = (results[i]).Split('|');
                            flagMessage = com.getTimeFormat() + "Email Imported for " + emailWithApplicantName[1];
                            String entryId;
                            if (Connect.dictionary.ContainsKey(emailWithApplicantName[0]))
                            {
                                entryId = Connect.dictionary[emailWithApplicantName[0]];
                                Connect.dictionary.Remove(emailWithApplicantName[0]);
                            }
                            else
                            {
                                entryId = emailWithApplicantName[0];
                            }
                            com.addFollowUpMessage(applicationObject, entryId, flagMessage);
                        }
                    }
                }
            }

            this.Close();
        }
    }
}
