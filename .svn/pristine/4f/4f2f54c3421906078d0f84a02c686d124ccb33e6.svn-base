VERSION 5.00
Object = "{EAB22AC0-30C1-11CF-A7EB-0000C05BAE0B}#1.1#0"; "ieframe.dll"
Begin VB.Form frmBulkImport 
   BackColor       =   &H00FFFFFF&
   Caption         =   "Bulk Import"
   ClientHeight    =   6885
   ClientLeft      =   60
   ClientTop       =   450
   ClientWidth     =   9045
   LinkTopic       =   "Form1"
   ScaleHeight     =   6885
   ScaleWidth      =   9045
   StartUpPosition =   3  'Windows Default
   Begin VB.Frame framProgress 
      BackColor       =   &H00FFFFFF&
      BorderStyle     =   0  'None
      Caption         =   "frmProgress"
      Height          =   1815
      Left            =   2760
      TabIndex        =   2
      Top             =   2280
      Width           =   3855
      Begin VB.Label Label1 
         BackColor       =   &H80000009&
         Caption         =   "Transferring files to server. Please wait...."
         Height          =   375
         Left            =   240
         TabIndex        =   3
         Top             =   720
         Width           =   3615
      End
   End
   Begin VB.CommandButton btnCancel 
      BackColor       =   &H0000C493&
      Caption         =   "Cancel"
      Height          =   375
      Left            =   7800
      Style           =   1  'Graphical
      TabIndex        =   1
      Top             =   6360
      Width           =   1095
   End
   Begin SHDocVwCtl.WebBrowser wbBulkImport 
      Height          =   6255
      Left            =   0
      TabIndex        =   0
      Top             =   0
      Width           =   9015
      ExtentX         =   15901
      ExtentY         =   11033
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
Attribute VB_Name = "frmBulkImport"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Option Explicit

Private isValidSession As Boolean

Private Sub btnCancel_Click()
    modCommon.cancel = True
    Me.Hide
    Unload Me
    End
End Sub

Private Sub Form_Activate()
    If isValidSession = True Then
        isValidSession = False
        modBulkImport.selectImportParameters
    End If
End Sub

Private Sub Form_Load()
    resetScreenToFirstStart
    isValidSession = True
End Sub

Private Sub resetScreenToFirstStart()
    wbBulkImport.Visible = True
    btnCancel.Visible = True
    framProgress.Visible = False
End Sub

Private Sub resetScreenToStartUpload()
    wbBulkImport.Visible = False
    framProgress.Visible = True
End Sub

Public Sub resetScreenAfterUpload()
    wbBulkImport.Visible = True
    framProgress.Visible = False
    btnCancel.Visible = False
    
End Sub

Private Sub wbBulkImport_DocumentComplete(ByVal pDisp As Object, url As Variant)
    Dim result As String

    If wbBulkImport.Document Is Nothing Then
       result = ""
    ElseIf wbBulkImport.Document.All("result") Is Nothing = False Then
       result = wbBulkImport.Document.All("result").Value
    End If
    
    If result <> "" Then
        If result = "SUCCESS" Then
            resetScreenToStartUpload
            modBulkImport.processEmail
        End If
    End If

End Sub


