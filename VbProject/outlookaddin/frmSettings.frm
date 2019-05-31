VERSION 5.00
Begin VB.Form frmSettings 
   BackColor       =   &H00FFFFFF&
   Caption         =   "TalentPool Settings"
   ClientHeight    =   2940
   ClientLeft      =   60
   ClientTop       =   450
   ClientWidth     =   7260
   LinkTopic       =   "Form1"
   ScaleHeight     =   2940
   ScaleWidth      =   7260
   StartUpPosition =   3  'Windows Default
   Begin VB.Frame frmSettings 
      BackColor       =   &H8000000E&
      Caption         =   "TalentPool Settings"
      Height          =   1935
      Left            =   240
      TabIndex        =   4
      Top             =   240
      Width           =   6735
      Begin VB.CheckBox chkFollowUp 
         BackColor       =   &H8000000E&
         Caption         =   "Add a flag and a message to each mail copied to TalentPool"
         Height          =   495
         Left            =   1680
         TabIndex        =   1
         Top             =   1200
         Width           =   4695
      End
      Begin VB.TextBox txtURL 
         Height          =   375
         Left            =   1680
         TabIndex        =   0
         Top             =   360
         Width           =   4695
      End
      Begin VB.Label Label5 
         BackColor       =   &H8000000E&
         Caption         =   "Example: http://localhost:9090/TalentPool"
         Height          =   375
         Left            =   1680
         TabIndex        =   6
         Top             =   840
         Width           =   4575
      End
      Begin VB.Label Label4 
         BackColor       =   &H8000000E&
         Caption         =   "TalentPool URL:"
         Height          =   255
         Left            =   240
         TabIndex        =   5
         Top             =   480
         Width           =   1455
      End
   End
   Begin VB.CommandButton cmdCancel 
      BackColor       =   &H0000C493&
      Caption         =   "Cancel"
      Height          =   375
      Left            =   6000
      MaskColor       =   &H00FFFFFF&
      Style           =   1  'Graphical
      TabIndex        =   3
      Top             =   2280
      Width           =   975
   End
   Begin VB.CommandButton cmdSave 
      BackColor       =   &H0000C493&
      Caption         =   "Save"
      Height          =   375
      Left            =   4920
      MaskColor       =   &H00FFFFFF&
      Style           =   1  'Graphical
      TabIndex        =   2
      Top             =   2280
      UseMaskColor    =   -1  'True
      Width           =   975
   End
End
Attribute VB_Name = "frmSettings"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Option Explicit


Private Sub chkFollowUp_KeyPress(KeyAscii As Integer)
If KeyAscii = vbKeyReturn Then
    cmdSave_Click
ElseIf KeyAscii = vbKeyEscape Then
    Unload Me
End If
End Sub

Private Sub cmdCancel_Click()
    Me.Hide
    Unload Me
End Sub

Private Sub cmdSave_Click()
    
    Dim url As String
    Dim result As String
    Dim resultArry
    Dim validURL As Boolean
    
    url = txtURL.Text
    validURL = False
    
    If url <> "" Then
        result = modCommon.getResponse(url, "")
        If InStr(1, LCase(result), "talentpool", vbTextCompare) > 0 Then
            If InStr(1, LCase(result), LCase("HTTP Status 404"), vbTextCompare) = 0 Then
                validURL = True
            End If
        End If
    Else
        MsgBox "please fill the TalentPool url"
    End If
    
    
    
    If result <> "" Then
        'check for talentpool
        saveTSSettings txtURL.Text
        modCommon.initVariables
        
        If validURL = True Then
            Me.Hide
            Unload Me
        Else
            MsgBox "Entered URL is not avialable"
        End If
    End If
    
End Sub

Private Function saveTSSettings(ByVal url As String)
    
    Const ForReading = 1, ForWriting = 2, ForAppending = 3
    Const TristateUseDefault = -2, TristateTrue = -1, TristateFalse = 0
    Dim fs, f, ts, s, path
    path = App.path & "/talentpoolProperties.txt"
    Set fs = CreateObject("Scripting.FileSystemObject")
    fs.CreateTextFile path            'Create a file
    Set f = fs.GetFile(path)
    Set ts = f.OpenAsTextStream(ForWriting, TristateUseDefault)
    ts.Write url & vbCrLf & chkFollowUp.Value
    ts.Close
    
    'set common variables
    modCommon.serverURL = url
    modCommon.addFollowUp = chkFollowUp.Value
    
End Function



Private Sub Form_Load()
    Const ForReading = 1, ForWriting = 2, ForAppending = 3
    Const TristateUseDefault = -2, TristateTrue = -1, TristateFalse = 0

    Dim fs, f, ts, path
    Dim url As String
    Set fs = CreateObject("Scripting.FileSystemObject")
    path = App.path & "/talentpoolProperties.txt"
    If Dir(path) <> "" Then
        Set f = fs.GetFile(path)
        Set ts = f.OpenAsTextStream(ForReading, TristateUseDefault)
        txtURL = ts.ReadLine
        chkFollowUp = ts.ReadLine
        ts.Close
    End If
End Sub


Private Sub txtURL_KeyPress(KeyAscii As Integer)
If KeyAscii = vbKeyReturn Then
    cmdSave_Click
ElseIf KeyAscii = vbKeyEscape Then
    Unload Me
End If
End Sub
