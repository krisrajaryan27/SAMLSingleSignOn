Attribute VB_Name = "modCommon"
Option Explicit

Public moApp As Object 'Outlook.Application
Public moAppInst As Object
Public tmpFolderPath As String
Public serverURL As String
Public userName As String
Public userId As String
Public screenToDisplay As String
Public applicantId As String
Public attachedToCandidateFlag As String
Public addFollowUp As String
Public singleImportFlag As String
Public cancel As Boolean

Public Sub initVariables()
    tmpFolderPath = App.path & "\data"
    createIfNotExists tmpFolderPath
    setTSSettings
    attachedToCandidateFlag = "TalentPool message: Attached to "
    singleImportFlag = "Talentscout message: Resume Imported"
    cancel = False
    'userName = "admin"
    'userId = "1"
End Sub

Public Function CheckValidSession(screen As String) As Boolean
    Dim validSession As Boolean
    validSession = False
    If userId <> "" Then
        validSession = True
    Else
        frmLoginToTalentscout.Show
        frmLoginToTalentscout.tbUserName.SetFocus
    End If
    If Not validSession Then
        screenToDisplay = screen
    End If
    CheckValidSession = validSession
    frmLoginToTalentscout.Show
End Function

Public Sub displayScreenAfterLogin()
    If StrComp(screenToDisplay, "bulkImport", vbTextCompare) = 0 Then
        frmBulkImport.Show
    End If
End Sub

Public Function generateSessionId() As String
    Dim intDay As Integer
    Dim intMonth As Integer
    Dim intYear As Integer
    Dim intHour As Integer
    Dim intMinute As Integer
    Dim intSecond As Integer
    Dim sessionId As String
    
    intDay = Day(Now)
    intMonth = Month(Now)
    intYear = Year(Now)
    intHour = Hour(Now)
    intMinute = Minute(Now)
    intSecond = Second(Now)
    sessionId = modCommon.userName & intDay & intMonth & intYear & intHour & intMinute & intSecond
    
    generateSessionId = sessionId
End Function

'Public Function countSelectedEmail() As Integer
'    Dim fol As Outlook.MAPIFolder
'    Dim emailCount As Integer
'    emailCount = 0
'    Set fol = moApp.ActiveExplorer.CurrentFolder
'    If fol = "Personal Folder" Or fol = "RSS Feeds" Then
'        GoTo Last
'    End If
'    If fol.GetExplorer.Selection.count <> 0 Then
'        emailCount = fol.GetExplorer.Selection.count
'    End If
'Last:
'    countSelectedEmail = emailCount
'End Function


Public Function SaveTextToFile(FileFullPath As String, sText As String, Optional Overwrite As Boolean = False) As Boolean
    
'Purpose: Save Text to a file
'Parameters:
       '-- FileFullPath - Directory/FileName to save file to
       '-- sText - Text to write to file
       '-- Overwrite (optional): If true, if the file exists, it
                    'is overwritten.  If false,
                    'contents are appended to file
                    'if the file exists

'Returns:   True if successful, false otherwise

'Example:
'SaveTextToFile "C:\My Documents\MyFile.txt", "Hello There"

    On Error GoTo ErrorHandler
    Dim iFileNumber As Integer
    iFileNumber = FreeFile
    
    If Overwrite Then
        Open FileFullPath For Output As #iFileNumber
    Else
        Open FileFullPath For Append As #iFileNumber
    End If
    
    Print #iFileNumber, sText
    SaveTextToFile = True
    
ErrorHandler:
    Close #iFileNumber
End Function


Public Function getResponse(ByVal url, ByVal dataToPost) As String
    Dim strRet As String
    Dim PostData() As Byte
    
    On Error GoTo ErrorHandler
    Dim xmlHttpObject As MSXML2.XMLHTTP30
    Set xmlHttpObject = New MSXML2.XMLHTTP30
    
    xmlHttpObject.Open "POST", url, False
    PostData = StrConv(dataToPost, vbFromUnicode)
    xmlHttpObject.send PostData
    
    strRet = xmlHttpObject.responseText
    getResponse = strRet
   
   Exit Function
ErrorHandler:
    MsgBox Err.Description, vbCritical, "ERROR"
    Err.Clear
    getResponse = strRet
End Function

Private Function setTSSettings() As String
    
    Const ForReading = 1, ForWriting = 2, ForAppending = 3
    Const TristateUseDefault = -2, TristateTrue = -1, TristateFalse = 0

    Dim fs, f, ts, path
    Set fs = CreateObject("Scripting.FileSystemObject")
    path = App.path & "/talentpoolProperties.txt"
    
    If Dir(path) <> "" Then
        Set f = fs.GetFile(path)
        Set ts = f.OpenAsTextStream(ForReading, TristateUseDefault)
        serverURL = ts.ReadLine
        addFollowUp = ts.ReadLine
        ts.Close
    End If

End Function

Private Sub createIfNotExists(ByVal path As String)
    On Error GoTo ErrorHandler
    Dim fs
    Set fs = CreateObject("Scripting.FileSystemObject")
    
    If Not fs.FolderExists(path) Then
        MkDir path
    End If
      Exit Sub
ErrorHandler:
        MsgBox Err.Description, vbCritical, "ERROR"
        Err.Clear
      
End Sub

Public Function checkFileNameLength(ByVal Filename As String) As String
    Dim newName As String
    Dim ext As String
    If Len(Filename) > 130 Then
        ext = Right$(Filename, 4)
        newName = Mid$(Filename, 1, 130)
        newName = newName & ext
    Else
        newName = Filename
    End If
    checkFileNameLength = newName
End Function
