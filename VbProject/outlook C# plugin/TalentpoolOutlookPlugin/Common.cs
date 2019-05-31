using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace TalentpoolOutlookPlugin
{
    class Common
    {
        public String getResponse(String url)
        {
            System.Net.HttpWebRequest request = (System.Net.HttpWebRequest)System.Net.WebRequest.Create(url);
            request.ContentType = "multipart/form-data";
            request.Method = "POST";
            request.KeepAlive = true;
            request.Credentials = System.Net.CredentialCache.DefaultCredentials;
            System.Net.HttpWebResponse response = (System.Net.HttpWebResponse)request.GetResponse();
            System.IO.Stream stream2 = response.GetResponseStream();
            System.IO.StreamReader reader2 = new System.IO.StreamReader(stream2);
            String result = reader2.ReadToEnd();
            response.Close();
            reader2.Close();
            return result;

        }

        public String getTimeFormat()
        {
            return "TalentPool [ " + String.Format("{0:MMM dd hh:mm tt}", System.DateTime.Now) + "]: ";
        }

        public void addFollowUpMessage(Microsoft.Office.Interop.Outlook.Application applicationObject,String entryId, String flagMessage)
        {
            Microsoft.Office.Interop.Outlook.NameSpace objNameSpace = applicationObject.GetNamespace("MAPI");
            Microsoft.Office.Interop.Outlook.MailItem ooMail = objNameSpace.GetItemFromID(entryId.Trim());
            if (flagMessage.Length > 100) {
                flagMessage = flagMessage.Substring(0,95) + "...";
            }
            ooMail.FlagRequest = flagMessage;
            ooMail.Save();

            objNameSpace = null;
            ooMail = null;
        }

    }
}
