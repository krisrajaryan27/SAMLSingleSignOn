VERSION 5.00
Begin VB.Form frmPassword 
   BorderStyle     =   3  'Fixed Dialog
   Caption         =   "Password Entry"
   ClientHeight    =   2295
   ClientLeft      =   1125
   ClientTop       =   2130
   ClientWidth     =   4395
   Icon            =   "frmPassword.frx":0000
   LinkTopic       =   "Form1"
   LockControls    =   -1  'True
   MaxButton       =   0   'False
   MinButton       =   0   'False
   ScaleHeight     =   2295
   ScaleWidth      =   4395
   ShowInTaskbar   =   0   'False
   WhatsThisButton =   -1  'True
   WhatsThisHelp   =   -1  'True
   Begin VB.CommandButton cmdHelp 
      Caption         =   "&Help"
      Height          =   330
      Left            =   3135
      TabIndex        =   6
      Top             =   1080
      Width           =   1140
   End
   Begin VB.CommandButton cmdCancel 
      Cancel          =   -1  'True
      Caption         =   "&Cancel"
      Height          =   330
      Left            =   3135
      TabIndex        =   5
      Top             =   525
      Width           =   1140
   End
   Begin VB.CommandButton cmdOK 
      Caption         =   "&OK"
      Default         =   -1  'True
      Height          =   330
      Left            =   3135
      TabIndex        =   4
      Top             =   120
      Width           =   1140
   End
   Begin VB.CheckBox chkMask 
      Caption         =   "&Mask Password"
      Height          =   240
      Left            =   105
      TabIndex        =   3
      Top             =   2010
      Width           =   1470
   End
   Begin VB.TextBox txtPassword 
      Height          =   315
      Left            =   105
      TabIndex        =   1
      Top             =   285
      Width           =   2820
   End
   Begin VB.Label Label2 
      Caption         =   $"frmPassword.frx":0442
      Height          =   1110
      Left            =   105
      TabIndex        =   2
      Top             =   765
      Width           =   2820
   End
   Begin VB.Label lblPassword 
      AutoSize        =   -1  'True
      Caption         =   "&Password:"
      Height          =   195
      Left            =   105
      TabIndex        =   0
      Top             =   15
      Width           =   735
   End
End
Attribute VB_Name = "frmPassword"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Option Explicit

Private mbCanceled As Boolean
Private msPassword As String
Private mbReenter As Boolean


Private Sub chkMask_Click()
    If chkMask.Value = vbChecked Then
        txtPassword.PasswordChar = "*"
    Else
        txtPassword.PasswordChar = ""
    End If
    If txtPassword.Visible Then txtPassword.SetFocus
End Sub

Private Sub cmdCancel_Click()
    msPassword = ""
    Me.Hide
End Sub

Private Sub cmdOK_Click()
    Dim formX As Form
    
    If Not mbReenter Then
        If chkMask.Value = vbChecked Then
            Set formX = New frmPassword
ShowAgain:
            msPassword = formX.Display(True, Me)
            If txtPassword = msPassword Then
                Me.Hide
            ElseIf Len(msPassword) = 0 Then
                mbCanceled = True
                Me.Hide
            Else
                MsgBox "Passwords didn't match!", vbExclamation, "Password Mismatch"
                GoTo ShowAgain
            End If
        Else
            msPassword = txtPassword
            Me.Hide
        End If
    Else
        msPassword = txtPassword
        Me.Hide
    End If
    Set formX = Nothing
End Sub

Public Function Display(bReenter As Boolean, frmParent As Form) As String
    If bReenter Then
        Me.Move frmParent.Left + (frmParent.Height - frmParent.ScaleHeight), _
            frmParent.Top + (frmParent.Height - frmParent.ScaleHeight)
        Me.Caption = "Password Reentry"
        lblPassword = "&Please reenter your password:"
        chkMask.Visible = False
        txtPassword.PasswordChar = "*"
        mbReenter = True
    Else
        Me.Move (Screen.Width - Me.Width) / 2, _
            (Screen.Height - Me.Height) / 2
    End If
    Me.Show vbModal, frmParent
    Display = msPassword
    Unload Me
End Function

Private Sub Form_Unload(Cancel As Integer)
    Set frmPassword = Nothing
End Sub
