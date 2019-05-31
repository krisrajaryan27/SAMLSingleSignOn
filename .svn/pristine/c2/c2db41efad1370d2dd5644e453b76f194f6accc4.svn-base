namespace TalentpoolOutlookPlugin
{   
    using Microsoft.Office.Core;
	using System;
	using Extensibility;
	using System.Runtime.InteropServices;
    using Microsoft.Office.Interop.Outlook;

	#region Read me for Add-in installation and setup information.
	// When run, the Add-in wizard prepared the registry for the Add-in.
	// At a later time, if the Add-in becomes unavailable for reasons such as:
	//   1) You moved this project to a computer other than which is was originally created on.
	//   2) You chose 'Yes' when presented with a message asking if you wish to remove the Add-in.
	//   3) Registry corruption.
	// you will need to re-register the Add-in by building the TalentpoolOutlookPluginSetup project, 
	// right click the project in the Solution Explorer, then choose install.
	#endregion
	
	/// <summary>
	///   The object for implementing an Add-in.
	/// </summary>
	/// <seealso class='IDTExtensibility2' />
	[GuidAttribute("F9D1D566-ED23-4FCC-8912-F278D9F35533"), ProgId("TalentpoolOutlookPlugin.Connect")]
	public class Connect : Object, Extensibility.IDTExtensibility2
	{
		/// <summary>
		///		Implements the constructor for the Add-in object.
		///		Place your initialization code within this method.
		/// </summary>
		public Connect()
		{
		}

		/// <summary>
		///      Implements the OnConnection method of the IDTExtensibility2 interface.
		///      Receives notification that the Add-in is being loaded.
		/// </summary>
		/// <param term='application'>
		///      Root object of the host application.
		/// </param>
		/// <param term='connectMode'>
		///      Describes how the Add-in is being loaded.
		/// </param>
		/// <param term='addInInst'>
		///      Object representing this Add-in.
		/// </param>
		/// <seealso class='IDTExtensibility2' />
		public void OnConnection(object application, Extensibility.ext_ConnectMode connectMode, object addInInst, ref System.Array custom)
		{
            applicationObject = (Microsoft.Office.Interop.Outlook.Application)application;
            addInInstance = addInInst;
            if (connectMode != Extensibility.ext_ConnectMode.ext_cm_Startup)
            {
                OnStartupComplete(ref custom);
            }
		}

		/// <summary>
		///     Implements the OnDisconnection method of the IDTExtensibility2 interface.
		///     Receives notification that the Add-in is being unloaded.
		/// </summary>
		/// <param term='disconnectMode'>
		///      Describes how the Add-in is being unloaded.
		/// </param>
		/// <param term='custom'>
		///      Array of parameters that are host application specific.
		/// </param>
		/// <seealso class='IDTExtensibility2' />
		public void OnDisconnection(Extensibility.ext_DisconnectMode disconnectMode, ref System.Array custom)
		{
             if (disconnectMode != Extensibility.ext_DisconnectMode.ext_dm_HostShutdown)
            {
                OnBeginShutdown(ref custom);
            }
            applicationObject = null;
		}

		/// <summary>
		///      Implements the OnAddInsUpdate method of the IDTExtensibility2 interface.
		///      Receives notification that the collection of Add-ins has changed.
		/// </summary>
		/// <param term='custom'>
		///      Array of parameters that are host application specific.
		/// </param>
		/// <seealso class='IDTExtensibility2' />
		public void OnAddInsUpdate(ref System.Array custom)
		{
		}

		/// <summary>
		///      Implements the OnStartupComplete method of the IDTExtensibility2 interface.
		///      Receives notification that the host application has completed loading.
		/// </summary>
		/// <param term='custom'>
		///      Array of parameters that are host application specific.
		/// </param>
		/// <seealso class='IDTExtensibility2' />
		public void OnStartupComplete(ref System.Array custom)
		{
            System.Reflection.Assembly assemblyInfo = System.Reflection.Assembly.GetExecutingAssembly();
            //Location is where the assembly is run from 
            appLocation = assemblyInfo.Location;
            appLocation = appLocation.Substring(0, appLocation.LastIndexOf(@"\"));
          
            var fileStream = new System.IO.FileStream(appLocation + @"\talentpoolProperties.txt", System.IO.FileMode.Append);
            fileStream.Close();
            settingsCheck = false;

            CommandBars commandBars = applicationObject.ActiveExplorer().CommandBars;

            // Create a toolbar button on the standard toolbar that calls ToolbarButton_Click when clicked
            try
            {
                // See if it already exists
                this.toolbarButton4 = (CommandBarButton)commandBars["Standard"].Controls["Talentpool Addin"];
                this.toolbarButton = (CommandBarButton)commandBars["Standard"].Controls["Bulk Import"];
                this.toolbarButton1 = (CommandBarButton)commandBars["Standard"].Controls["Single Import"];
                this.toolbarButton3 = (CommandBarButton)commandBars["Standard"].Controls["Settings"];
                this.toolbarButton2 = (CommandBarButton)commandBars["Standard"].Controls["Attach to Candidate"];
              
            }
            catch (System.Exception)
            {
               
                // Create it
                this.toolbarButton4 = (CommandBarButton)commandBars["Standard"].Controls.Add(1, System.Reflection.Missing.Value,System.Reflection.Missing.Value,System.Reflection.Missing.Value, System.Reflection.Missing.Value);
                this.toolbarButton4.Caption = "Talentpool Addin";
                this.toolbarButton4.Style = MsoButtonStyle.msoButtonIcon;

                this.toolbarButton = (CommandBarButton)commandBars["Standard"].Controls.Add(1, System.Reflection.Missing.Value, System.Reflection.Missing.Value,System.Reflection.Missing.Value, System.Reflection.Missing.Value);
                this.toolbarButton.Caption = "Bulk Import";
                this.toolbarButton.Style = MsoButtonStyle.msoButtonIconAndCaption;

                this.toolbarButton3 = (CommandBarButton)commandBars["Standard"].Controls.Add(1, System.Reflection.Missing.Value, System.Reflection.Missing.Value, System.Reflection.Missing.Value, System.Reflection.Missing.Value);
                this.toolbarButton3.Caption = "Settings";
                this.toolbarButton3.Style = MsoButtonStyle.msoButtonIconAndCaption;

                this.toolbarButton1 = (CommandBarButton)commandBars["Standard"].Controls.Add(1, System.Reflection.Missing.Value, System.Reflection.Missing.Value, System.Reflection.Missing.Value, System.Reflection.Missing.Value);
                this.toolbarButton1.Caption = "Import";
                this.toolbarButton1.Style = MsoButtonStyle.msoButtonIconAndCaption;

                this.toolbarButton2 = (CommandBarButton)commandBars["Standard"].Controls.Add(1, System.Reflection.Missing.Value, System.Reflection.Missing.Value, System.Reflection.Missing.Value, System.Reflection.Missing.Value);
                this.toolbarButton2.Caption = "Attach to Candidate";
                this.toolbarButton2.Style = MsoButtonStyle.msoButtonIconAndCaption;

            }

            this.toolbarButton4.Tag = "Talentpool Addin";
            this.toolbarButton4.OnAction = "!<TalentpoolOutlookPlugin.Connect>";
            this.toolbarButton4.Visible = true;
            this.toolbarButton4.Picture = getImage("addin");

            this.toolbarButton.Tag = "Bulk Import";
            this.toolbarButton.OnAction = "!<TalentpoolOutlookPlugin.Connect>";
            this.toolbarButton.Visible = true;
            this.toolbarButton.Picture = getImage("bulkImport");
            this.toolbarButton.Click += new Microsoft.Office.Core._CommandBarButtonEvents_ClickEventHandler(this.OnBulkImportClick);

            this.toolbarButton1.Tag = "Single Import";
            this.toolbarButton1.OnAction = "!<TalentpoolOutlookPlugin.Connect>";
            this.toolbarButton1.Visible = true;
            this.toolbarButton1.Picture = getImage("singleImport");
            this.toolbarButton1.Click += new Microsoft.Office.Core._CommandBarButtonEvents_ClickEventHandler(this.OnSingleImportClick);

            this.toolbarButton3.Tag = "Settings";
            this.toolbarButton3.OnAction = "!<TalentpoolOutlookPlugin.Connect>";
            this.toolbarButton3.Visible = true;
            this.toolbarButton3.Picture = getImage("settings");
            this.toolbarButton3.Click += new Microsoft.Office.Core._CommandBarButtonEvents_ClickEventHandler(this.OnSettingsButtonClick);

            this.toolbarButton2.Tag = "Attach to Candidate";
            this.toolbarButton2.OnAction = "!<TalentpoolOutlookPlugin.Connect>";
            this.toolbarButton2.Visible = true;
            this.toolbarButton2.Picture = getImage("attachToCandidate");
            this.toolbarButton2.Click += new Microsoft.Office.Core._CommandBarButtonEvents_ClickEventHandler(this.OnAttachToCandidateClick);

            form1 = new frmSettings();
            form1.setConnect(this);
		}



		/// <summary>
		///      Implements the OnBeginShutdown method of the IDTExtensibility2 interface.
		///      Receives notification that the host application is being unloaded.
		/// </summary>
		/// <param term='custom'>
		///      Array of parameters that are host application specific.
		/// </param>
		/// <seealso class='IDTExtensibility2' />
        public void OnBeginShutdown(ref System.Array custom)
        {
            this.toolbarButton4.Delete(System.Reflection.Missing.Value);
            this.toolbarButton4 = null;

            this.toolbarButton.Delete(System.Reflection.Missing.Value);
            this.toolbarButton = null;

            this.toolbarButton1.Delete(System.Reflection.Missing.Value);
            this.toolbarButton1 = null;

            this.toolbarButton3.Delete(System.Reflection.Missing.Value);
            this.toolbarButton3 = null;
        }

        private void OnBulkImportClick(CommandBarButton cmdBarbutton, ref bool cancel)
        {   
           if (!checkSettingsUpdate())
            {
                System.Windows.Forms.MessageBox.Show("Please update settings first!");
                return;
            }
            if (userId == null)
            {
                loginForm.setServerUrl(serverURL);
                loginForm.setConnectForm(this);
                loginForm.ShowDialog();
            }
            else {
                Common cm = new Common();
                String sessionId = generateSessionId(userName);
                    //transferSelectedFilesToServer("BulkImport");
                if (sessionId != null && sessionId != "2")
                {
                    String url = serverURL + "/desktop.do?mode=selectBulkImportParameters&sessionType=0&userId=" + userId + "&sessionId=" + sessionId;
                    frmBulkImport bulkImportForm = new frmBulkImport();
                    bulkImportForm.setConnectObject(this);
                    bulkImportForm.setSessionId(sessionId);
                    bulkImportForm.Navigate(url);
                    bulkImportForm.Show();
                }
                else {
                    if (sessionId == "2")
                    {
                        System.Windows.Forms.MessageBox.Show("Please select one or more emails for bulk import");
                    }
                    else
                    {
                        System.Windows.Forms.MessageBox.Show("Unable to upload Email to Server");
                    }
                    return;
                }
                cm = null;
            }
        }

        public void bulkImportParse(String sessionId,frmBulkImport bulkImportForm) {
            sessionId = transferSelectedFilesToServer("BulkImport", sessionId);
            bulkImportForm.Close();
            if (sessionId != null)
            {
                BulkImportParseResult bulkImportParse = new BulkImportParseResult();
                String  url = serverURL + "/desktop.do?mode=autologin&screenType=bulkimport&sessionType=0&userId=" + userId + "&sessionId=" + sessionId;
                bulkImportParse.setMailFlag(mailFlag);
                bulkImportParse.setApplicationObject(applicationObject);
                bulkImportParse.Navigate(url);
                bulkImportParse.ShowDialog();
            }
        }

        private void OnSingleImportClick(CommandBarButton cmdBarbutton, ref bool cancel)
        {
            if (!checkSettingsUpdate())
            {
                System.Windows.Forms.MessageBox.Show("Please update settings first!");
                return;
            }

            if (userId == null)
            {
                loginForm.setServerUrl(serverURL);
                loginForm.setConnectForm(this);
                loginForm.ShowDialog();
            }
            else
            {
                Common cm = new Common();
                String sessionId = transferSelectedFilesToServer("SingleImport",null);
                if (sessionId != null && sessionId != "1")
                {
                    Microsoft.Office.Interop.Outlook.Selection sel = applicationObject.ActiveExplorer().Selection;
                    Microsoft.Office.Interop.Outlook.MailItem mail = null;
                    if (sel[1] is Microsoft.Office.Interop.Outlook.MailItem)
                    {
                        mail = sel[1] as Microsoft.Office.Interop.Outlook.MailItem;
                    }
                    String url = serverURL + "/importSingleEmailServlet.servlet?sessionId=" + sessionId + "&userId=" + userId;
                    String emailId = cm.getResponse(url);
                    if (emailId != null)
                    {
                        url = serverURL + "/desktop.do?mode=autologin&screenType=singleimport&userId=" + userId + "&emailId=" + emailId;
                        frmSingleImport bw = new frmSingleImport();
                        bw.setEntryId(mail.EntryID.ToString().Trim());
                        bw.setMailFlag(mailFlag);
                        bw.setApplicationObject(applicationObject);
                        bw.Navigate(url);
                        bw.ShowDialog();
                    }
                }
                else
                {
                    if (sessionId == "1")
                    {
                        System.Windows.Forms.MessageBox.Show("Please select one Email for single import");
                    }
                    else
                    {
                        System.Windows.Forms.MessageBox.Show("Unable to upload Email to Server");
                    }
                    return;
                }
                cm = null;
            }

        }

        private void OnAttachToCandidateClick(CommandBarButton cmdBarButton, ref bool cancel) {
            if (!checkSettingsUpdate())
            {
                System.Windows.Forms.MessageBox.Show("Please update settings first!");
                return;
            }

            if (userId == null)
            {
                loginForm.setServerUrl(serverURL);
                loginForm.setConnectForm(this);
                loginForm.ShowDialog();
            }
            else {
                frmAttachToCandidate wbForm = new frmAttachToCandidate(serverURL);
                wbForm.setConnect(this);
                wbForm.setMailFlag(mailFlag);
                wbForm.setApplicationObject(applicationObject);
                wbForm.Show();
            }
        }

        public void candidateAttachProcessEmail(frmAttachToCandidate form) {
            Common cm = new Common();
            String sessionId = transferSelectedFilesToServer("AttachToCandidate", null);
            if (sessionId != null && sessionId != "3")
            {
                Microsoft.Office.Interop.Outlook.Selection sel = applicationObject.ActiveExplorer().Selection;
                Microsoft.Office.Interop.Outlook.MailItem mail = null;
                if (sel[1] is Microsoft.Office.Interop.Outlook.MailItem)
                {
                    mail = sel[1] as Microsoft.Office.Interop.Outlook.MailItem;
                }
                String emailAddress = mail.SenderEmailAddress;
                String fromName = null;
                if (!String.Equals(mail.SenderName, mail.SenderEmailAddress, StringComparison.OrdinalIgnoreCase))
                {
                    fromName = mail.SenderName;
                }
                String url = serverURL + "/desktop.do?mode=searchToAttach&fromEmail=" + emailAddress + "&fromName=" + fromName + "&userId=" + userId + "&sessionId=" + sessionId;
                form.Navigate(url);
                form.resetScreenAfterUpload();
            }
            else
            {
                if (sessionId == "3")
                {
                    System.Windows.Forms.MessageBox.Show("Please select atleast one Email for attaching to candidate");
                }
                else
                {
                    System.Windows.Forms.MessageBox.Show("Unable to upload Email to Server");
                }
                return;
            }
            cm = null;
        }
             

        private void OnSettingsButtonClick(CommandBarButton cmdBarbutton, ref bool cancel)
        {
            System.Reflection.Assembly assemblyInfo = System.Reflection.Assembly.GetExecutingAssembly();
            //Location is where the assembly is run from 
            String appLocation = assemblyInfo.Location;
            appLocation = appLocation.Substring(0, appLocation.LastIndexOf(@"\"));
            form1.setPropertyFilePath(appLocation);
            String[] lines = System.IO.File.ReadAllLines(appLocation + @"\talentpoolProperties.txt");

            if (lines != null)
            {
                if (lines.Length == 2)
                {
                    form1.showTextFromPropertyFile(lines[0],lines[1]);
             
                }
            }
            form1.ShowDialog();
        }

        private String transferSelectedFilesToServer(String typeFunction, String sessionId)
        {
            if (sessionId == null)
            {
                sessionId = generateSessionId(userName);
            }
            Microsoft.Office.Interop.Outlook.Selection selObject = applicationObject.ActiveExplorer().Selection;
            int count = applicationObject.ActiveExplorer().Selection.Count;
            String servletURL = null;
            if (String.Equals(typeFunction, "SingleImport", StringComparison.OrdinalIgnoreCase))
            {
                if (count != 1)
                {
                    return "1";
                }
                servletURL = "/uploadEmailServlet.servlet";
            }
            else if (String.Equals(typeFunction, "BulkImport", StringComparison.OrdinalIgnoreCase))
            {
                if (count == 0)
                {
                    return "2";
                }
                servletURL = "/bulkImportServlet.servlet";
            }
            else if (String.Equals(typeFunction, "AttachToCandidate", StringComparison.OrdinalIgnoreCase))
            {
                if (count == 0)
                {
                    return "3";
                }
                servletURL = "/uploadEmailServlet.servlet";
            }

            for (int j = 1; j <= count; j++)
            {
                if (selObject[j] is Microsoft.Office.Interop.Outlook.MailItem)
                {
                    bool isLastFile = false;
                    if (j == count)
                    {
                        isLastFile = true;
                    }
                    transferSingleFileToServer(selObject[j] as Microsoft.Office.Interop.Outlook.MailItem, servletURL,
                        sessionId, typeFunction, isLastFile);
                }
            }
            return sessionId;
        }

        private void transferSingleFileToServer(Microsoft.Office.Interop.Outlook.MailItem mailItem, 
            String servletURL, String sessionId, String typeFunction, bool isLastFile)
        {
            String parentDirectory = appLocation + @"\" + sessionId;
            
            System.IO.Directory.CreateDirectory(parentDirectory);

            //creating folder
            String emailFolderName = mailItem.EntryID;
            String emailFolderPath = parentDirectory + @"\" + emailFolderName;
            System.IO.Directory.CreateDirectory(emailFolderPath);

            //saving email at temp folder
            Microsoft.Office.Interop.Outlook.Attachments attachments = mailItem.Attachments;

            int cnt = 0;
            foreach (Microsoft.Office.Interop.Outlook.Attachment attachment in attachments)
            {
                String fileName = checkFileNameSize(attachment.FileName);
                try
                {
                    attachment.SaveAsFile(emailFolderPath + @"\" + fileName);
                }
                catch (System.IO.DirectoryNotFoundException e)
                {
                    emailFolderName = System.Guid.NewGuid().ToString();
                    dictionary.Add(emailFolderName, mailItem.EntryID);
                    emailFolderPath = parentDirectory + @"\" + emailFolderName;
                    System.IO.Directory.CreateDirectory(emailFolderPath);
                    attachment.SaveAsFile(emailFolderPath + @"\" + fileName);
                }
                cnt = cnt + 1;
            }

            //save email body as text
            String fileNameBody = emailFolderPath + @"\ts-email-text-body.txt";
            System.IO.StreamWriter file = new System.IO.StreamWriter(fileNameBody);
            file.Write(mailItem.Body);
            file.Close();

            //save email body as html
            fileNameBody = emailFolderPath + @"\ts-email-html-body.html";
            System.IO.FileStream fs = new System.IO.FileStream(fileNameBody, System.IO.FileMode.Create);
            System.IO.StreamWriter file1 = new System.IO.StreamWriter(fs, System.Text.Encoding.UTF8);
            file1.WriteLine(mailItem.HTMLBody);
            file1.Close();
            fs.Close();


            //save email headers as text
            fileNameBody = emailFolderPath + @"\ts-email-header.txt";
            System.IO.StreamWriter file2 = new System.IO.StreamWriter(fileNameBody);
            file2.Write(getEmailHeader(mailItem));
            file2.Close();

            var textFiles = System.IO.Directory.EnumerateFiles(emailFolderPath, "*.*", System.IO.SearchOption.AllDirectories);
            int countFiles = System.IO.Directory.GetFiles(emailFolderPath, "*.*", System.IO.SearchOption.AllDirectories).Length;
            int i = 0;
            foreach (String currentFile in textFiles)
            {
                long length = 0;
                System.Net.HttpWebRequest request = (System.Net.HttpWebRequest)System.Net.WebRequest.Create(serverURL + servletURL);
                request.Headers.Add("sessionid", sessionId);
                request.Headers.Add("emailid", emailFolderName);

                request.ContentType = "multipart/form-data";
                request.Method = "POST";
                request.KeepAlive = true;
                request.Credentials = System.Net.CredentialCache.DefaultCredentials;

                System.IO.Stream memStream = new System.IO.MemoryStream();
                String fileName = currentFile.Substring(currentFile.LastIndexOf(@"\") + 1);
                System.IO.FileStream fileStream = new System.IO.FileStream(currentFile, System.IO.FileMode.Open, System.IO.FileAccess.Read);
                byte[] buffer = new byte[1024];
                int bytesRead = 0;
                while ((bytesRead = fileStream.Read(buffer, 0, buffer.Length)) != 0)
                {
                    memStream.Write(buffer, 0, bytesRead);
                    length += bytesRead;
                }
                fileStream.Close();
                request.Headers.Add("filename", fileName);
                if (String.Equals(typeFunction, "BulkImport", StringComparison.OrdinalIgnoreCase))
                {

                    if (i == (countFiles - 1))
                    {
                        request.Headers.Add("islastfile", "True");

                    }
                    else
                    {
                        request.Headers.Add("islastfile", "False");
                    }
                    if (isLastFile && i == (countFiles - 1))
                    {
                        request.Headers.Add("transfercomplete", "True");
                    }
                    else
                    {
                        request.Headers.Add("transfercomplete", "False");
                    }
                }
                request.ContentLength = memStream.Length;
                System.IO.Stream requestStream = request.GetRequestStream();
                memStream.Position = 0;
                byte[] tempBuffer = new byte[memStream.Length];
                memStream.Read(tempBuffer, 0, tempBuffer.Length);
                memStream.Close();
                requestStream.Write(tempBuffer, 0, tempBuffer.Length);
                requestStream.Close();


                System.Net.HttpWebResponse response = (System.Net.HttpWebResponse)request.GetResponse();
                response.Close();

                request = null;
                response = null;
                i++;
            }
        }
        

        private Boolean checkSettingsUpdate() {
            if (!settingsCheck)
            {
                String[] lines = System.IO.File.ReadAllLines(appLocation + @"\talentpoolProperties.txt");

                if (lines != null)
                {
                    if (lines.Length != 2)
                    {
                        return false;
                    }
                    else
                    {
                        serverURL = lines[0];
                        mailFlag = lines[1];
                        settingsCheck = true;
                    }
                }
            }
            return true;
           
        }

        private String checkFileNameSize(String fileName) {
            if (fileName.Length > 130)
            {
                String ext = fileName.Substring(fileName.Length - 4);
                String newName = fileName.Substring(0, 130);
                return newName + ext;
            }
            else {
                return fileName;
            }
        }

        private String getEmailHeader( Microsoft.Office.Interop.Outlook.MailItem mailItem) {
            String header = "emailfrom:";
            if (mailItem.SenderName.Equals(mailItem.SenderEmailAddress, System.StringComparison.OrdinalIgnoreCase))
            {
                header = header + mailItem.SenderEmailAddress;
            }
            else {
                header = header + mailItem.SenderName + "<" + mailItem.SenderEmailAddress+">";
            }
            header = header + "\r\n";
            header = header + "emailto:" + mailItem.To + "\r\n";
            header = header + "emailcc:" + mailItem.CC + "\r\n";
            header = header + "emailbcc:" + mailItem.BCC + "\r\n";
            header = header + "emailsubject:" + mailItem.Subject + "\r\n";
            header = header + "datesent:" + String.Format("{0:dd-MM-yyyy h:mm:ss tt}", mailItem.SentOn) + "\r\n";
            header = header + "datereceived:" + String.Format("{0:dd-MM-yyyy h:mm:ss tt}", mailItem.ReceivedTime) + "\r\n";
            header = header + "emailsize:" + mailItem.Size + "\r\n";

            return header;
        }

        public void setUserCredentials(String userName,String userId) {
            this.userId = userId;
            this.userName = userName;
        }

        private String generateSessionId(String userName) {
            System.DateTime dt = System.DateTime.Now;
            int day = dt.Day;
            int month = dt.Month;
            int year = dt.Year;
            int hour = dt.Hour;
            int minute = dt.Minute;
            int second = dt.Second;
            return userName + day + month + year + hour + minute + second;
        }

        private stdole.IPictureDisp getImage(String pictureType)
        {
            stdole.IPictureDisp tempImage = null;
            System.Windows.Forms.ImageList newImageList = new System.Windows.Forms.ImageList();
            try
            {
                System.Drawing.Bitmap newIcon = null;
                if (String.Equals(pictureType, "bulkImport", StringComparison.OrdinalIgnoreCase))
                {
                    newIcon = Properties.Resources.BulkImport;
                }
                else if (String.Equals(pictureType, "singleImport", StringComparison.OrdinalIgnoreCase))
                {
                    newIcon = Properties.Resources.Import;
                }
                else if (String.Equals(pictureType, "attachToCandidate", StringComparison.OrdinalIgnoreCase))
                {
                    newIcon = Properties.Resources.AttachToCandidate;
                }
                else if (String.Equals(pictureType, "addin", StringComparison.OrdinalIgnoreCase))
                {
                    newIcon = Properties.Resources.ts_icon;
                }
                else if (String.Equals(pictureType, "settings", StringComparison.OrdinalIgnoreCase))
                {
                    newIcon = Properties.Resources.Settings;
                }
                
                
                newImageList.Images.Add(newIcon);
                tempImage = ConvertImage.Convert(newIcon);
            }
            catch (System.Exception ex)
            {
                System.Windows.Forms.MessageBox.Show(ex.Message);
            }
            return tempImage;
        }

       sealed class ConvertImage : System.Windows.Forms.AxHost
        {
            private ConvertImage()
                : base(null)
            {
            }

            public static stdole.IPictureDisp Convert(System.Drawing.Image image)
            {
                return (stdole.IPictureDisp)System.Windows.Forms.AxHost.GetIPictureDispFromPicture(image);
            }
        }

        private Microsoft.Office.Interop.Outlook.Application applicationObject;
		private object addInInstance;
        private CommandBarButton toolbarButton, toolbarButton1, toolbarButton2, toolbarButton3,toolbarButton4;
        private frmSettings form1;
        private String serverURL = null;
        private String mailFlag = null;
        public Boolean settingsCheck = false;
        private String appLocation;
        private frmLogin loginForm = new frmLogin();
        private String userId = null;
        private String userName = null;
        public static System.Collections.Generic.Dictionary<String, String> dictionary = new System.Collections.Generic.Dictionary<string, string>();
	}
}