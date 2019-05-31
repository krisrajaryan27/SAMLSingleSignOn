VERSION 5.00
Object = "{EAB22AC0-30C1-11CF-A7EB-0000C05BAE0B}#1.1#0"; "ieframe.dll"
Begin VB.Form frmAttachToCandidate 
   BackColor       =   &H80000014&
   BorderStyle     =   1  'Fixed Single
   Caption         =   "Attach email to candidate"
   ClientHeight    =   6075
   ClientLeft      =   3690
   ClientTop       =   3180
   ClientWidth     =   8130
   LinkTopic       =   "Form1"
   MaxButton       =   0   'False
   MinButton       =   0   'False
   ScaleHeight     =   6075
   ScaleWidth      =   8130
   Begin VB.Frame frmProgress 
      BackColor       =   &H80000009&
      Height          =   5295
      Left            =   240
      TabIndex        =   3
      Top             =   240
      Width           =   7575
      Begin VB.Label Label1 
         BackColor       =   &H80000009&
         Caption         =   "Transferring files to server. Please wait...."
         Height          =   375
         Left            =   1800
         TabIndex        =   4
         Top             =   1920
         Width           =   3615
      End
   End
   Begin VB.CommandButton btnCancel 
      BackColor       =   &H0000C493&
      Caption         =   "Cancel"
      Height          =   375
      Left            =   6840
      Style           =   1  'Graphical
      TabIndex        =   2
      Top             =   5640
      Width           =   975
   End
   Begin VB.CommandButton btnDone 
      BackColor       =   &H0000C493&
      Caption         =   "Done"
      Height          =   375
      Left            =   6600
      Style           =   1  'Graphical
      TabIndex        =   1
      Top             =   5640
      Width           =   1215
   End
   Begin SHDocVwCtl.WebBrowser wbAttachtoCandidate 
      Height          =   5415
      Left            =   0
      TabIndex        =   0
      Top             =   0
      Visible         =   0   'False
      Width           =   8055
      ExtentX         =   14208
      ExtentY         =   9551
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
End
Attribute VB_Name = "frmAttachToCandidate"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Option Explicit

Private sessionId As String
Private isValidSession As Boolean
Private emailIds As String

Private Sub btnCancel_Click()
    Me.Hide
    Unload Me
End Sub

Private Sub btnDone_Click()
    If modCommon.addFollowUp = 1 Then
        Dim resultArry
        Dim pos
        Dim emailWithApplicantName
        Dim flagMessage As String
        ' Add followup message
        If emailIds <> "" Then
            resultArry = Split(emailIds, "$", -1, vbTextCompare)
            For pos = 0 To UBound(resultArry)
            emailWithApplicantName = Split(resultArry(pos), "|", -1, vbTextCompare)
            If Trim$(resultArry(pos)) <> "" Then
                    flagMessage = modCommon.getTimeFormat & "Email attached to " & emailWithApplicantName(1)
                    addFollowUpMessage emailWithApplicantName(0), flagMessage
                End If
            Next
        End If
    End If
    Me.Hide
    Unload Me
End Sub

Private Sub Form_Activate()
    If isValidSession = True Then
        isValidSession = False
        processEmail
    End If
End Sub

Private Sub Form_Load()
    sessionId = ""
    emailIds = ""
    resetScreenToStartUpload
    isValidSession = True
    
    wbAttachtoCandidate.Navigate modCommon.serverURL & "/desktop.do?mode=searchToAttach", "", "", "", ""
End Sub

Private Sub resetScreenToStartUpload()
    wbAttachtoCandidate.Visible = False
    btnDone.Visible = False
    frmProgress.Visible = True
End Sub
Private Sub resetScreenAfterUpload()
    wbAttachtoCandidate.Visible = True
    btnDone.Visible = False
    frmProgress.Visible = False
End Sub

Private Sub resetScreenAfterComplete()
    btnDone.Visible = True
    btnCancel.Visible = False
End Sub

Private Sub processEmail()
    Dim url As String
    Dim item As Outlook.MailItem
    Dim emailFrom As String
    Dim fromName As String

    DoEvents
    sessionId = modAttachToCandidate.transferSelectedEmailFilesToServer
    
    If sessionId <> "" Then
        Set item = modAttachToCandidate.getFirstSelectedItem
        emailFrom = item.SenderEmailAddress
        fromName = ""
        If StrComp(item.SenderName, item.SenderEmailAddress, vbTextCompare) <> 0 Then
               fromName = item.SenderName
        End If
        
        url = modCommon.serverURL & "/desktop.do?mode=searchToAttach&fromEmail=" & emailFrom & "&fromName=" & fromName & "&userId=" & modCommon.userId & "&sessionId=" & sessionId
        wbAttachtoCandidate.Navigate url, "", "", "", ""
        DoEvents
        resetScreenAfterUpload
        'show search screen
    Else
        MsgBox "Unable to upload selected emails to server"
        Me.Hide
        Unload Me
    End If
End Sub

Private Sub wbAttachtoCandidate_DocumentComplete(ByVal pDisp As Object, url As Variant)
    Dim result As String
    
    If wbAttachtoCandidate.Document Is Nothing Then
       result = ""
    ElseIf wbAttachtoCandidate.Document.All("result") Is Nothing = False Then
       result = wbAttachtoCandidate.Document.All("result").Value
    End If
    
    If result <> "" Then
        resetScreenAfterComplete
        emailIds = result
    End If
End Sub

