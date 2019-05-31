VERSION 5.00
Object = "{EAB22AC0-30C1-11CF-A7EB-0000C05BAE0B}#1.1#0"; "ieframe.dll"
Begin VB.Form frmSingleImport 
   BackColor       =   &H00FFFFFF&
   Caption         =   "Single Import"
   ClientHeight    =   10485
   ClientLeft      =   165
   ClientTop       =   555
   ClientWidth     =   15240
   LinkTopic       =   "SingleImport"
   ScaleHeight     =   10675.46
   ScaleMode       =   0  'User
   ScaleWidth      =   15380
   StartUpPosition =   2  'CenterScreen
   Begin SHDocVwCtl.WebBrowser wbSingleImport 
      Height          =   9975
      Left            =   0
      TabIndex        =   4
      Top             =   0
      Width           =   15255
      ExtentX         =   26908
      ExtentY         =   17595
      ViewMode        =   0
      Offline         =   0
      Silent          =   0
      RegisterAsBrowser=   0
      RegisterAsDropTarget=   1
      AutoArrange     =   0   'False
      NoClientEdge    =   0   'False
      AlignLeft       =   0   'False
      NoWebView       =   0   'False
      HideFileNames   =   0   'False
      SingleClick     =   0   'False
      SingleSelection =   0   'False
      NoFolders       =   0   'False
      Transparent     =   0   'False
      ViewID          =   "{0057D0E0-3573-11CF-AE69-08002B2E1262}"
      Location        =   "http:///"
   End
   Begin VB.CommandButton btnDone 
      BackColor       =   &H0000C493&
      Caption         =   "Done"
      Height          =   375
      Left            =   13680
      Style           =   1  'Graphical
      TabIndex        =   3
      Top             =   10080
      Width           =   1215
   End
   Begin VB.CommandButton btnCancel 
      BackColor       =   &H0000C493&
      Caption         =   "Cancel"
      Height          =   375
      Left            =   13920
      Style           =   1  'Graphical
      TabIndex        =   2
      Top             =   10080
      Width           =   975
   End
   Begin VB.Frame frmProgress 
      BackColor       =   &H80000009&
      BorderStyle     =   0  'None
      Height          =   5295
      Left            =   3600
      TabIndex        =   0
      Top             =   2400
      Width           =   7575
      Begin VB.Label Label1 
         BackColor       =   &H80000009&
         Caption         =   "Transferring files to server. Please wait...."
         Height          =   375
         Left            =   1920
         TabIndex        =   1
         Top             =   2280
         Width           =   3615
      End
   End
End
Attribute VB_Name = "frmSingleImport"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Option Explicit

Private sessionId As String
Private isValidSession As Boolean
Private entryId As String
Private result As String
Private applicantName As String

Private Sub btnCancel_Click()
    Me.Hide
    Unload Me
End Sub

Private Sub btnDone_Click()
    If modCommon.addFollowUp = 1 Then
        Dim resultArry
        Dim pos
        Dim flagMessage As String
        ' Add followup message
        If result = "SUCCESS" Then
            flagMessage = modCommon.getTimeFormat & "Email Imported for " & applicantName
            addFollowUpMessage entryId, flagMessage
        End If
    End If

    Me.Hide
    Unload Me
End Sub

'Private Function addFollowUpMessage(ByVal emailId As String)
'    Dim objNameSpace As Outlook.NameSpace
'    Dim oOMail As Outlook.MailItem
'
'    Set objNameSpace = moApp.GetNamespace("MAPI")
'    Set oOMail = objNameSpace.GetItemFromID(Trim$(emailId))
'
'    With oOMail
'    .FlagRequest = singleImportFlag
'    .Save
'    End With
'
'    Set objNameSpace = Nothing
'    Set oOMail = Nothing
'
'End Function

Private Sub Form_Activate()
    If isValidSession = True Then
        isValidSession = False
        processEmail
    End If
End Sub

Private Sub Form_Load()
    wbSingleImport.Navigate "about:blank"
    sessionId = ""
    entryId = ""
    result = ""
    resetScreenToStartUpload
    isValidSession = True
End Sub

Private Sub resetScreenToStartUpload()
    wbSingleImport.Visible = False
    btnDone.Visible = False
    frmProgress.Visible = True
End Sub
Private Sub resetScreenAfterUpload()
    wbSingleImport.Visible = True
    btnDone.Visible = False
    frmProgress.Visible = False
    btnCancel.Visible = False
End Sub

Private Sub processEmail()
    Dim url As String
    Dim item As Outlook.MailItem
    Dim emailFrom As String
    Dim fromName As String
    Dim emailId As String
    
    DoEvents
    sessionId = modAttachToCandidate.transferSelectedEmailFilesToServer
    
    If sessionId <> "" Then
        Set item = modAttachToCandidate.getFirstSelectedItem
        entryId = item.entryId
        
        url = modCommon.serverURL & "/importSingleEmailServlet.servlet?sessionId=" & sessionId & "&userId=" & modCommon.userId
        emailId = modCommon.getResponse(url, "")
        
        If emailId <> "" Then
            url = modCommon.serverURL & "/desktop.do?mode=autologin&screenType=singleimport&userId=" & modCommon.userId & "&emailId=" & emailId
            wbSingleImport.Navigate url
            DoEvents
            resetScreenAfterUpload
        End If
        'show search screen
    Else
        MsgBox "Unable to upload selected emails to server"
        Me.Hide
        Unload Me
    End If
End Sub

Private Sub wbSingleImport_DocumentComplete(ByVal pDisp As Object, url As Variant)
    Dim res As String
    
    If wbSingleImport.Document Is Nothing Then
       res = ""
    ElseIf wbSingleImport.Document.All("result") Is Nothing = False Then
       res = wbSingleImport.Document.All("result").Value
    End If
    If res <> "" Then
        btnDone.Visible = True
        applicantName = wbSingleImport.Document.All("applicantName").Value
        result = res
    End If
End Sub

