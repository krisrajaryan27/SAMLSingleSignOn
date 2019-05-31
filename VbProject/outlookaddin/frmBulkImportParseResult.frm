VERSION 5.00
Object = "{EAB22AC0-30C1-11CF-A7EB-0000C05BAE0B}#1.1#0"; "ieframe.dll"
Begin VB.Form frmBulkImportParseResult 
   BackColor       =   &H00FFFFFF&
   Caption         =   "Resume selected for import"
   ClientHeight    =   10170
   ClientLeft      =   60
   ClientTop       =   450
   ClientWidth     =   14655
   LinkTopic       =   "Form1"
   ScaleHeight     =   10170
   ScaleWidth      =   14655
   StartUpPosition =   2  'CenterScreen
   Begin VB.CommandButton btnCancel 
      BackColor       =   &H0000C493&
      Caption         =   "Cancel"
      Height          =   375
      Left            =   13200
      Style           =   1  'Graphical
      TabIndex        =   2
      Top             =   9720
      Width           =   975
   End
   Begin VB.CommandButton btnDone 
      BackColor       =   &H0000C493&
      Caption         =   "Done"
      Height          =   375
      Left            =   13080
      Style           =   1  'Graphical
      TabIndex        =   1
      Top             =   9720
      Width           =   1215
   End
   Begin SHDocVwCtl.WebBrowser wbParsedResult 
      Height          =   9615
      Left            =   0
      TabIndex        =   0
      Top             =   0
      Width           =   14655
      ExtentX         =   25850
      ExtentY         =   16960
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
Attribute VB_Name = "frmBulkImportParseResult"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Option Explicit

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
                    flagMessage = modCommon.getTimeFormat & "Email Imported for " & emailWithApplicantName(1)
                    addFollowUpMessage emailWithApplicantName(0), flagMessage
                End If
            Next
        End If
    End If

    Me.Hide
    Unload Me
End Sub

Private Sub Form_Load()
    btnDone.Visible = False
    btnCancel.Visible = True
End Sub

Private Sub wbParsedResult_DownloadComplete()
    Dim result As String

    If wbParsedResult.Document Is Nothing Then
       result = ""
    ElseIf wbParsedResult.Document.All("result") Is Nothing = False Then
       result = wbParsedResult.Document.All("result").Value
    End If
    
    If result <> "" Then
       btnDone.Visible = True
        btnCancel.Visible = False
        If result <> "SUCCESS" Then
            emailIds = result
        End If
    End If
End Sub

