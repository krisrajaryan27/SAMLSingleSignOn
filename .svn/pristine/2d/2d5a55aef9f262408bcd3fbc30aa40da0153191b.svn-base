Attribute VB_Name = "modBulkImport"
Option Explicit

Private sessionId As String
Private entryId As String

Public Sub initialize()
    frmBulkImport.Show
    sessionId = ""
    entryId = ""
    modCommon.cancel = False
End Sub

Public Sub selectImportParameters()
    Dim url As String
    Dim emailId As String
    sessionId = modCommon.generateSessionId
    
    url = modCommon.serverURL & "/desktop.do?mode=selectBulkImportParameters&sessionType=0&userId=" & modCommon.userId & "&sessionId=" & sessionId
    frmBulkImport.wbBulkImport.Navigate url, "", "", "", ""
    
End Sub

Public Function showBulkImportProgress(ByVal sessionId As String)
    frmBulkImport.Hide
    Unload frmBulkImport
    If modCommon.cancel = False Then
        frmBulkImportParseResult.Show
        Dim url As String
        If sessionId <> "" Then
            url = modCommon.serverURL & "/desktop.do?mode=autologin&screenType=bulkimport&sessionType=0&userId=" & modCommon.userId & "&sessionId=" & sessionId
            frmBulkImportParseResult.wbParsedResult.Navigate url
        End If
        showBulkImportProgress = True
    End If
End Function

Public Sub processEmail()
    Dim url As String
    Dim item As Outlook.MailItem
    Dim emailFrom As String
    Dim fromName As String
    Dim emailId As String
    
    DoEvents
    sessionId = transferSelectedEmailFilesToServer(sessionId)
    If sessionId <> "" Then
        frmBulkImport.resetScreenAfterUpload
        showBulkImportProgress (sessionId)
    Else
        MsgBox "Unable to upload selected emails to server"
        frmBulkImport.Hide
        Unload frmBulkImport
    End If
End Sub

Public Function transferSelectedEmailFilesToServer(ByVal sessionId As String) As String

    On Error GoTo ErrorHandler
    
    Dim fol As Outlook.MAPIFolder
    Dim item As Outlook.MailItem
    Dim Atmt As Attachment
    Dim LngCounter As Long
    Dim StrMsg As String
    Dim fileName As String
    Dim FileNameBody As String
    Dim emailId As String
    Dim sessionFldrPath As String
    Dim emailFldrPath As String
    Dim emailCount As Integer
    Dim transferComplete As Boolean
    
    Set fol = moApp.ActiveExplorer.CurrentFolder
    If fol.GetExplorer.Selection.count = 0 Then
        MsgBox "Please Select an Email"
    Else
        'creating folder
        sessionFldrPath = tmpFolderPath & "\" & sessionId
        MkDir sessionFldrPath
        
        'Saving email at temp folder
        emailCount = fol.GetExplorer.Selection.count
        transferComplete = False
        For LngCounter = 1 To emailCount
            Set item = fol.GetExplorer.Selection.item(LngCounter)
            emailId = item.entryId
            emailFldrPath = sessionFldrPath & "\" & emailId
            MkDir emailFldrPath
            
            'Save Attachments
            For Each Atmt In item.Attachments
                fileName = modCommon.checkFileNameLength(Atmt.fileName)
                fileName = emailFldrPath & "\" & fileName
                Atmt.SaveAsFile fileName
            Next Atmt
            
            'Save Email-Body in txt format
            FileNameBody = emailFldrPath & "\" & "ts-email-text-body.txt"
            modCommon.SaveTextToFile FileNameBody, item.Body
            
            'Save Email-Body in html format
            FileNameBody = emailFldrPath & "\" & "ts-email-html-body.html"
            modCommon.SaveTextToFile FileNameBody, item.HTMLBody
            
            'Save the emailheaders in file
            FileNameBody = emailFldrPath & "\" & "ts-email-header.txt"
            modCommon.SaveTextToFile FileNameBody, getEmailHeaders(item)
            
            'sent transfer Complete
            If LngCounter = emailCount Then
            transferComplete = True
            End If
            
            If uploadEmailFilesToServer(emailFldrPath, emailId, sessionId, transferComplete) = False Then
                sessionId = ""
                Exit For
            End If
            DoEvents
            frmBulkImport.Label1.Caption = LngCounter & " files transfered out of " & emailCount
                       
           'In case of CANCEL
            If modCommon.cancel Then
                Exit For
            End If
        Next LngCounter
        
        Set item = Nothing
        Set fol = Nothing
        'show upload process
        
      End If
      transferSelectedEmailFilesToServer = sessionId
      
Exit Function
    
ErrorHandler:
    MsgBox Err.Description, vbCritical, "ERROR"
    Err.Clear
    transferSelectedEmailFilesToServer = ""
      
End Function

Private Function uploadEmailFilesToServer(ByVal emailFolderPath As String, ByVal emailId As String, ByVal sessionId, ByVal transferComplete As Boolean) As Boolean
    Dim fso As FileSystemObject
    Dim root As Scripting.Folder
    Dim fle As Scripting.File
    Dim result As Boolean
    Dim isLastFile As Boolean
    Dim count As Integer
    Dim lTransferComplete As Boolean
    On Error GoTo ErrorHandler
    
    Set fso = CreateObject("Scripting.FileSystemObject")
    Set root = fso.GetFolder(emailFolderPath)
    
    result = False
    isLastFile = False
    lTransferComplete = False
    count = root.Files.count
    For Each fle In root.Files
       If count = 1 Then
        isLastFile = True
         If transferComplete Then
            lTransferComplete = True
         End If
       End If
       
       result = uploadSingleFile(emailFolderPath & "\" & fle.Name, fle.Name, emailId, sessionId, isLastFile, lTransferComplete)
       If result = False Then
           Exit For
       End If
       count = count - 1
       ' In case of CANCEL
       If modCommon.cancel Then
            Exit For
        End If
    Next
       
    uploadEmailFilesToServer = result
    Exit Function
    
ErrorHandler:
    MsgBox Err.Description, vbCritical, "ERROR"
    Err.Clear
    uploadEmailFilesToServer = False
End Function

Private Function uploadSingleFile(ByVal filePath As String, ByVal fileName As String, ByVal emailId As String, ByVal sessionId, ByVal isLastFile As Boolean, ByVal transferComplete As Boolean) As Boolean
    
    Dim result As Boolean
    Dim strRet As String
    Dim PostData() As Byte
        
    On Error GoTo ErrorHandler
    Dim xmlHttpObject As MSXML2.XMLHTTP30
    Set xmlHttpObject = New MSXML2.XMLHTTP30
    
    result = False

    xmlHttpObject.Open "POST", modCommon.serverURL & "/bulkImportServlet.servlet", False
    
    xmlHttpObject.setRequestHeader "sessionid", sessionId
    xmlHttpObject.setRequestHeader "emailid", emailId
    xmlHttpObject.setRequestHeader "filename", fileName
    xmlHttpObject.setRequestHeader "islastfile", isLastFile
    xmlHttpObject.setRequestHeader "transfercomplete", transferComplete

    PostData = StrConv(GetFileContents(filePath), vbFromUnicode)
    
    xmlHttpObject.Send PostData
    
    strRet = xmlHttpObject.responseText
    If StrComp(strRet, "success", vbTextCompare) = 0 Then
        result = True
    End If
    uploadSingleFile = result
   
   Exit Function
ErrorHandler:
    MsgBox Err.Description, vbCritical, "ERROR"
    Err.Clear
    uploadSingleFile = False
   
End Function

Private Function getEmailHeaders(item As Outlook.MailItem) As String
    Dim emailHeader As String
    On Error GoTo ErrorHandler
    
    emailHeader = "emailfrom:"
    If StrComp(item.SenderName, item.SenderEmailAddress, vbTextCompare) = 0 Then
        emailHeader = emailHeader & item.SenderEmailAddress
    Else
        emailHeader = emailHeader & item.SenderName & " <" & item.SenderEmailAddress & ">"
    End If
    emailHeader = emailHeader & vbCrLf
    emailHeader = emailHeader & "emailto:" & item.To & vbCrLf
    emailHeader = emailHeader & "emailcc:" & item.CC & vbCrLf
    emailHeader = emailHeader & "emailbcc:" & item.BCC & vbCrLf
    emailHeader = emailHeader & "emailsubject:" & item.Subject & vbCrLf
    emailHeader = emailHeader & "datesent:" & Format(item.SentOn, "dd-MM-yyyy hh:mm:ss AM/PM") & vbCrLf
    emailHeader = emailHeader & "datereceived:" & Format(item.ReceivedTime, "dd-MM-yyyy hh:mm:ss AM/PM") & vbCrLf
    emailHeader = emailHeader & "emailsize:" & item.Size
    
    getEmailHeaders = emailHeader
    Exit Function
ErrorHandler:
    MsgBox Err.Description, vbCritical, "ERROR"
    Err.Clear
    getEmailHeaders = emailHeader
    
End Function

Private Function GetFileContents(ByVal strPath As String) As String
    Dim StrReturn As String
    Dim lngLength As Long
    
    lngLength = FileLen(strPath)
    StrReturn = String(lngLength, Chr(0))
    
    On Error GoTo ErrorHandler
    
    Open strPath For Binary As #1
    
    Get #1, , StrReturn
    
    GetFileContents = StrReturn
    
    Close #1
    
    Exit Function
    
ErrorHandler:
    MsgBox Err.Description, vbCritical, "ERROR"
    Err.Clear
End Function



