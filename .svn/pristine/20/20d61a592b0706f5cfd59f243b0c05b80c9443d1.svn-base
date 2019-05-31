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
    public partial class frmAttachToCandidate : Form
    {
        
        private Connect con;
        private String emailIds;
        private String mailFlag;
        private Microsoft.Office.Interop.Outlook.Application applicationObject;

        public void setMailFlag(String mailFlag) {
            this.mailFlag = mailFlag;
        }
        
        public void setConnect(Connect con) {
            this.con = con;
        }

        public void setApplicationObject(Microsoft.Office.Interop.Outlook.Application applicationObject)
        {
            this.applicationObject = applicationObject;
        }


        public frmAttachToCandidate(String serverURL)
        {
            InitializeComponent();
            this.Activated += new EventHandler(this.form_Activated);
            label1.Visible = true;
            webBrowser1.Visible = false;
            button2.Visible = false;
            button1.Visible = true;
            webBrowser1.Navigate( serverURL + "/desktop.do?mode=searchToAttach");
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
                button1.Visible = false;
                button2.Visible = true;
                emailIds = response;
            }
        }

        private void form_Activated(object sender , EventArgs e) {
            con.candidateAttachProcessEmail(this);
        }

        private void button2_Click(object sender, EventArgs e)
        {
            if (mailFlag == "1") {
                Common com = new Common();
                String flagMessage = null;
                String[] emailWithApplicantName = null;
                if (emailIds != null && emailIds.Length > 0) {
                    String[] results = emailIds.Split('$');
                    for (int i = 0; i < results.Length; i++) {
                        if (results[i] != null && results[i].Trim().Length > 0 ) {
                            emailWithApplicantName = (results[i]).Split('|');
                            flagMessage = com.getTimeFormat() + "Email Attached to " + emailWithApplicantName[1];
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

        private void button1_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        public void Navigate(String url) {
            try
            {
                webBrowser1.Navigate(new Uri(url));
            }
            catch (Exception e)
            {
                this.Close();
                //System.Windows.Forms.MessageBox.Show("Attach to Candidate internal error");
            }
        }
        
        public void resetScreenAfterUpload() {
            button2.Visible = false;
            label1.Visible = false;
            webBrowser1.Visible = true;
        }

    }
}
