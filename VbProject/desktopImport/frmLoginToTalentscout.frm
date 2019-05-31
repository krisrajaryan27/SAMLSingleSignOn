VERSION 5.00
Begin VB.Form frmLoginToTalentscout 
   Appearance      =   0  'Flat
   BackColor       =   &H80000005&
   BorderStyle     =   1  'Fixed Single
   Caption         =   "Login to TalentPool"
   ClientHeight    =   2670
   ClientLeft      =   45
   ClientTop       =   435
   ClientWidth     =   5385
   LinkTopic       =   "Form1"
   MaxButton       =   0   'False
   MinButton       =   0   'False
   ScaleHeight     =   2670
   ScaleWidth      =   5385
   StartUpPosition =   3  'Windows Default
   Begin VB.Frame frm1 
      BackColor       =   &H80000014&
      Caption         =   "Login to TalentPool"
      Height          =   1695
      Left            =   360
      TabIndex        =   2
      Top             =   240
      Width           =   4575
      Begin VB.TextBox tbPassword 
         Height          =   375
         IMEMode         =   3  'DISABLE
         Left            =   1440
         PasswordChar    =   "*"
         TabIndex        =   6
         Top             =   960
         Width           =   2775
      End
      Begin VB.TextBox tbUserName 
         Height          =   375
         Left            =   1440
         TabIndex        =   5
         Top             =   480
         Width           =   2775
      End
      Begin VB.Label Label4 
         Alignment       =   1  'Right Justify
         BackColor       =   &H80000014&
         Caption         =   "Password:"
         Height          =   375
         Left            =   240
         TabIndex        =   4
         Top             =   1080
         Width           =   1095
      End
      Begin VB.Label Label3 
         Alignment       =   1  'Right Justify
         BackColor       =   &H80000014&
         Caption         =   "Username:"
         Height          =   375
         Left            =   240
         TabIndex        =   3
         Top             =   600
         Width           =   1095
      End
   End
   Begin VB.CommandButton btnCancel 
      BackColor       =   &H0000C493&
      Caption         =   "Cancel"
      Height          =   375
      Left            =   3960
      Style           =   1  'Graphical
      TabIndex        =   1
      Top             =   2040
      Width           =   945
   End
   Begin VB.CommandButton btnSubmit 
      BackColor       =   &H0000C493&
      Caption         =   "Submit"
      Height          =   375
      Left            =   2880
      Style           =   1  'Graphical
      TabIndex        =   0
      Top             =   2040
      UseMaskColor    =   -1  'True
      Width           =   945
   End
End
Attribute VB_Name = "frmLoginToTalentscout"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Option Explicit

Private Sub processResponse(ByVal result As String)
On Error GoTo ErrorHandler
Dim resultArry
If result <> "" Then
    resultArry = Split(result)
    If StrComp(resultArry(0), "success", vbTextCompare) = 0 Then
        modCommon.displayScreenAfterLogin
        frmLoginToTalentscout.Hide
        modCommon.userName = resultArry(1)
        modCommon.userId = resultArry(2)
    Else
        If StrComp(resultArry(0), "fail", vbTextCompare) = 0 Then
            MsgBox "Please check username and password"
        End If
    End If

End If
Exit Sub

ErrorHandler:
    MsgBox Err.Description, vbCritical, "ERROR"
    Err.Clear

End Sub

Private Sub btnCancel_Click()
    frmLoginToTalentscout.Hide
    End
End Sub

'
'Private Sub wbLogin_DocumentComplete(ByVal pDisp As Object, url As Variant)
'Dim result As String
'Dim resultArry
'
'If wbLogin.Document Is Nothing Then
'   result = "no document"
'Else
'   result = wbLogin.Document.Body.innerHTML
'End If
'If result <> "" Then
'    resultArry = Split(result)
'    If StrComp(resultArry(0), "success", vbTextCompare) = 0 Then
'        modCommon.displayScreenAfterLogin
'        frmLoginToTalentscout.Hide
'        modCommon.userName = resultArry(1)
'        modCommon.userId = resultArry(2)
'    Else
'        If StrComp(resultArry(0), "fail", vbTextCompare) = 0 Then
'            MsgBox "Please check username and password"
'        End If
'    End If
'
'End If
'onSubmit.Enabled = True
'End Sub


Private Sub btnSubmit_Click()

    Dim url As String
    Dim userName As String
    Dim password As String
    Dim result As String
    
    userName = tbUserName.Text
    password = tbPassword.Text
    
    url = modCommon.serverURL & "/loginServlet.servlet?mode=processRequest&userName=" & userName & "&password=" & password
    btnSubmit.Enabled = False
    result = modCommon.getResponse(url, "")
    processResponse result
    
    'wbLogin.Navigate url, 0, "", "", ""
    btnSubmit.Enabled = True

End Sub



Private Sub tbPassword_KeyPress(KeyAscii As Integer)
If KeyAscii = vbKeyReturn Then
    btnSubmit_Click
ElseIf KeyAscii = vbKeyEscape Then
    Unload Me
    End
End If
End Sub

Private Sub tbUserName_KeyPress(KeyAscii As Integer)
If KeyAscii = vbKeyReturn Then
    btnSubmit_Click
ElseIf KeyAscii = vbKeyEscape Then
    Unload Me
    End
End If

End Sub
