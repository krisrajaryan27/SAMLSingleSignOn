Attribute VB_Name = "modBulkImport"
Option Explicit

Private sessionId As String
Private selectedFiles As String
Private sessionType As String

Public Sub initialize(ByVal fileNames As String)
    frmMultipleFileSelect.Hide
    Unload frmMultipleFileSelect
    selectedFiles = fileNames
    modCommon.initVariables
    modCommon.CheckValidSession ("bulkImport")
    sessionId = ""
    modCommon.cancel = False
End Sub

Public Sub selectImportParameters()
    Dim url As String
    
    sessionType = 1 ' for desktop import
    sessionId = modCommon.generateSessionId
    
    url = modCommon.serverURL & "/desktop.do?mode=selectBulkImportParameters&sessionType=" & sessionType & "&userId=" & modCommon.userId & "&sessionId=" & sessionId
    frmBulkImport.wbBulkImport.Navigate url, "", "", "", ""
    
End Sub

Public Function showBulkImportProgress(ByVal sessionId As String)
    frmBulkImport.Hide
    Unload frmBulkImport
    If modCommon.cancel = False Then
        frmBulkImportParseResult.Show
        Dim url As String
        If sessionId <> "" Then
            sessionType = 1 ' for desktop import
            url = modCommon.serverURL & "/desktop.do?mode=autologin&screenType=bulkimport&sessionType=" & sessionType & "&userId=" & modCommon.userId & "&sessionId=" & sessionId
            frmBulkImportParseResult.wbParsedResult.Navigate url
        End If
        showBulkImportProgress = True
    End If
End Function

Public Sub processEmail()
    DoEvents
    'sessionId = transferSelectedFilesToServer(sessionId)
    If uploadEmailFilesToServer Then
        frmBulkImport.resetScreenAfterUpload
        showBulkImportProgress (sessionId)
    Else
        MsgBox "Unable to upload selected emails to server"
        frmBulkImport.Hide
        Unload frmBulkImport
    End If
End Sub

Private Function uploadEmailFilesToServer() As Boolean

    Dim fle As Scripting.file
    Dim result As Boolean
    Dim isLastFile As Boolean
    Dim count As Integer
    Dim transferComplete As Boolean
    Dim files, I, fileNames
    Dim Filename As String
    Dim filePath As String
    Dim totalFiles As Integer
    On Error GoTo ErrorHandler

    files = Split(selectedFiles, "|", -1, vbTextCompare)
    result = False
    isLastFile = False
    transferComplete = False
    totalFiles = UBound(files) + 1
    count = UBound(files) + 1
    For I = 0 To UBound(files)
        If count = 1 Then
            isLastFile = True
            transferComplete = True
       End If
       fileNames = Split(files(I), "\", -1, vbTextCompare)
       Filename = fileNames(UBound(fileNames))
       filePath = files(I)
 '      If totalFiles = 1 Then
 '          filePath = Mid(filePath, 0, 5)
 '      End If
       result = uploadSingleFile(filePath, Filename, sessionId, isLastFile, transferComplete)
       If result = False Then
           Exit For
       End If
       count = count - 1
       DoEvents
       frmBulkImport.Label1.Caption = totalFiles - count & " files transfered out of " & totalFiles
       'In case of CANCEL
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

Private Function uploadSingleFile(ByVal filePath As String, ByVal Filename As String, ByVal sessionId, ByVal isLastFile As Boolean, ByVal transferComplete As Boolean) As Boolean
    
    Dim result As Boolean
    Dim strRet As String
    Dim PostData() As Byte
        
    On Error GoTo ErrorHandler
    Dim xmlHttpObject As MSXML2.XMLHTTP30
    Set xmlHttpObject = New MSXML2.XMLHTTP30
    
    result = False

    xmlHttpObject.Open "POST", modCommon.serverURL & "/bulkImportServlet.servlet", False

    xmlHttpObject.setRequestHeader "sessionid", sessionId
    xmlHttpObject.setRequestHeader "filename", Filename
    xmlHttpObject.setRequestHeader "islastfile", isLastFile
    xmlHttpObject.setRequestHeader "transfercomplete", transferComplete

    PostData = StrConv(GetFileContents(filePath), vbFromUnicode)

    xmlHttpObject.send PostData

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

Private Function GetFileContents(ByVal strPath As String) As String
    Dim StrReturn As String
    Dim lngLength As Long

    lngLength = FileLen(Trim$(strPath))
    StrReturn = String(lngLength, Chr(0))
    
    On Error GoTo ErrorHandler
    
    Open strPath For Binary As #1
    
    Get #1, , StrReturn
    
    GetFileContents = StrReturn
    
    Close #1
    
    Exit Function
    
ErrorHandler:
    MsgBox Err.Description & Err.Source, vbCritical, "ERROR"
    Err.Clear
End Function




