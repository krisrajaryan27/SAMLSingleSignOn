VERSION 5.00
Begin VB.Form frmMultipleFileSelect 
   BackColor       =   &H00FFFFFF&
   BorderStyle     =   1  'Fixed Single
   Caption         =   "TalentPool: Desktop Bulk Import"
   ClientHeight    =   4845
   ClientLeft      =   45
   ClientTop       =   330
   ClientWidth     =   9510
   LinkTopic       =   "Form1"
   MaxButton       =   0   'False
   ScaleHeight     =   4845
   ScaleWidth      =   9510
   StartUpPosition =   3  'Windows Default
   Begin VB.CommandButton btnFileList 
      BackColor       =   &H0000C493&
      Caption         =   "Import"
      Height          =   375
      Left            =   8040
      Style           =   1  'Graphical
      TabIndex        =   5
      Top             =   840
      Width           =   1275
   End
   Begin VB.CommandButton btnRemove 
      BackColor       =   &H0000C493&
      Caption         =   "Remove"
      Height          =   375
      Left            =   8040
      Style           =   1  'Graphical
      TabIndex        =   4
      Top             =   1800
      Width           =   1275
   End
   Begin VB.ListBox listBox 
      Height          =   3765
      Left            =   120
      TabIndex        =   2
      Top             =   420
      Width           =   7755
   End
   Begin VB.CommandButton cmdExit 
      BackColor       =   &H0000C493&
      Caption         =   "E&xit"
      Height          =   375
      Left            =   8040
      Style           =   1  'Graphical
      TabIndex        =   1
      Top             =   1320
      Width           =   1275
   End
   Begin VB.CommandButton cmdOpen 
      BackColor       =   &H0000C493&
      Caption         =   "Add"
      Height          =   375
      Left            =   8040
      Style           =   1  'Graphical
      TabIndex        =   0
      Top             =   420
      Width           =   1275
   End
   Begin VB.Label Label2 
      BackColor       =   &H00FFFFFF&
      Height          =   255
      Left            =   120
      TabIndex        =   6
      Top             =   4320
      Width           =   7815
   End
   Begin VB.Label Label1 
      BackColor       =   &H00FFFFFF&
      Caption         =   "Click Add to select multiple files"
      Height          =   255
      Left            =   120
      TabIndex        =   3
      Top             =   120
      Width           =   4995
   End
End
Attribute VB_Name = "frmMultipleFileSelect"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Option Explicit

Private WithEvents m_cHookAddDlg As cCommonDialog
Attribute m_cHookAddDlg.VB_VarHelpID = -1

'Constants for custom dialog template
Private Const DLG_DIALOG = 1900
Private Const IDC_BUTTONPWD = 3010
Private Const IDC_BUTTONWILD = 1901
Private Const IDC_LBLACTION = 508
Private Const IDC_CBOACTION = 502
Private Const IDC_LBLCOMPRESS = 509
Private Const IDC_CBOCOMPRESS = 503
Private Const IDC_LBLSPAN = 511
Private Const IDC_CBOSPAN = 504
Private Const IDC_CHK83 = 3006
Private Const IDC_GRPFOLDERS = 510
Private Const IDC_CHKSUBFLDR = 506
Private Const IDC_CHKEXTRAFLDR = 507
Private Const IDC_GRPATTRS = 203
Private Const IDC_CHKINCARCHSET = 3008
Private Const IDC_CHKRESETARCH = 3009
Private Const IDC_CHKINCHIDSYS = 3007


Private Sub cmdOpen_Click()
On Error GoTo Err_Handler
    Dim sFiles() As String
    Dim lFileCount As Long
    Dim sDir As String
    Dim I As Long
    
    'listBox.Clear
    
    With m_cHookAddDlg
        .CancelError = True
        .DefaultExt = "cab"
        .DialogTitle = "Add Files"
        '.Filter = "All files|*.*"
        .Filter = "Resume Files (*.txt;*.doc*;*.htm*;*.rtf;*.pdf)|*.txt;*.doc*;*.htm*;*.rtf;*.pdf"
        '.fileName = "*.*"
        .flags = EOpenFile.OFN_ALLOWMULTISELECT
        'Or EOpenFile.OFN_HIDEREADONLY 'Or EOpenFile.OFN_ENABLETEMPLATE Or EOpenFile.OFN_FILEMUSTEXIST Or _
                EOpenFile.OFN_EXPLORER Or EOpenFile.OFN_SHOWHELP
        .hwnd = Me.hwnd
        .HookDialog = True
#If DebugOn Then
        'If we are running from the IDE the dialog resource
        'needs to be loaded from a DLL.
        .cdLoadLibrary App.path & "\WAdlgres.dll" 'NEW property
#Else
        'If we are running from an EXE the dialog resource
        'can be loaded from the included RES file.
        .hInstance = App.hInstance 'NEW property (I think I added this??)
#End If
        .TemplateName = DLG_DIALOG
    End With
    m_cHookAddDlg.ShowOpen
    
    If m_cHookAddDlg.flags And EOpenFile.OFN_ALLOWMULTISELECT Then
        'Might think about adding a FileNames collection
        m_cHookAddDlg.ParseMultiFileName sDir, sFiles(), lFileCount
        'listBox.AddItem Format(lFileCount, "###,###") & " files chosen from " & sDir & ":"
        For I = 1 To lFileCount
            If sDir = "" Then
                listBox.AddItem sFiles(I)
            Else
                listBox.AddItem sDir & "\" & sFiles(I)
            End If
            
        Next

        getTotalSelectedFiles
        btnRemove.Visible = True
        btnFileList.SetFocus

    Else
        'listBox.AddItem "One file chosen: " & m_cHookAddDlg.fileName
    End If
    
cmdShow_Exit:
#If DebugOn Then
    'If we are running from the IDE the dialog resource
    'was loaded from a DLL, free it.
    If m_cHookAddDlg.hInstance > 0 Then m_cHookAddDlg.cdFreeLibrary 'NEW property
#End If
    Exit Sub
    
Err_Handler:
    If (Err.Number <> 20001) Then
        MsgBox "Error: " & Err.Description
    Else
        'listBox.AddItem "User canceled dialog!"
    End If
    Resume cmdShow_Exit
End Sub

Private Sub Form_Load()
    Set m_cHookAddDlg = New cCommonDialog
    'chkFlatTB.Value = vbChecked
    btnRemove.Visible = False

End Sub

Private Sub Form_Unload(cancel As Integer)
    Set frmMultipleFileSelect = Nothing
End Sub

Private Sub m_cHookAddDlg_Help(ByVal hDlg As Long)
    listBox.AddItem "User requested help!"
End Sub

Private Sub m_cHookAddDlg_InitDialog(ByVal hDlg As Long)
'You can do all kinds of cool things here.  I have only done
'a few but, you can also do things like add a control box icon.
    Dim hParent As Long
    Dim hWndCtrl As Long
    Dim lRet As Long
    Const CB_INSERTSTRING = &H14A
    Const CB_SETITEMDATA = &H151
    Const CB_SETCURSEL = &H14E
    Const BM_SETCHECK = &HF1
    
    hParent = GetParent(hDlg)
    SendMessageStr hParent, CDM_SETCONTROLTEXT, ID_FOLDERLABEL, "Add &from:"
    SendMessageStr hParent, CDM_SETCONTROLTEXT, ID_OPEN, "&Add"
    
    ' Added FlatToolBar property to cCommonDialog class and
    ' the FlatCDTBar procedure to the mDialogHook module.
    ' FlatCDTBar is called from the DialogHook procedure in the
    ' GCommonDialog class.
    'If chkFlatTB.Value = vbChecked Then FlatCDTBar hParent
    
'    lRet = EnableWindow(GetDlgItem(hDlg, IDC_BUTTONPWD), False)
    
    hWndCtrl = GetDlgItem(hDlg, IDC_CBOACTION)
    lRet = SendMessageStr(hWndCtrl, CB_INSERTSTRING, 0&, "Add Files")
    lRet = SendMessageLong(hWndCtrl, CB_SETITEMDATA, 0&, 2&)
    lRet = SendMessageStr(hWndCtrl, CB_INSERTSTRING, 1&, "Move Files")
    lRet = SendMessageLong(hWndCtrl, CB_SETITEMDATA, 1&, -1&)
    lRet = SendMessageLong(hWndCtrl, CB_SETCURSEL, 0&, 0&)

    hWndCtrl = GetDlgItem(hDlg, IDC_CBOCOMPRESS)
    lRet = SendMessageStr(hWndCtrl, CB_INSERTSTRING, 0&, "None")
    lRet = SendMessageLong(hWndCtrl, CB_SETITEMDATA, 0&, 0&)
    lRet = SendMessageStr(hWndCtrl, CB_INSERTSTRING, 1&, "MSZIP")
    lRet = SendMessageLong(hWndCtrl, CB_SETITEMDATA, 1&, 1&)
    lRet = SendMessageStr(hWndCtrl, CB_INSERTSTRING, 2&, "LZX")
    lRet = SendMessageLong(hWndCtrl, CB_SETITEMDATA, 2&, 3&)
    lRet = SendMessageLong(hWndCtrl, CB_SETCURSEL, 1&, 0&)
    
    hWndCtrl = GetDlgItem(hDlg, IDC_CBOSPAN)
    lRet = SendMessageStr(hWndCtrl, CB_INSERTSTRING, 0&, "Not implemented")
    lRet = SendMessageLong(hWndCtrl, CB_SETCURSEL, 0&, 0&)
    lRet = EnableWindow(GetDlgItem(hDlg, IDC_LBLSPAN), False)
    lRet = EnableWindow(GetDlgItem(hDlg, IDC_CBOSPAN), False)

    hWndCtrl = GetDlgItem(hDlg, IDC_CHK83)
    lRet = SendMessageLong(hWndCtrl, BM_SETCHECK, 0&, 0&)

    hWndCtrl = GetDlgItem(hDlg, IDC_CHKSUBFLDR)
    lRet = SendMessageLong(hWndCtrl, BM_SETCHECK, 1&, 0&)

    hWndCtrl = GetDlgItem(hDlg, IDC_CHKEXTRAFLDR)
    lRet = SendMessageLong(hWndCtrl, BM_SETCHECK, 1&, 0&)

    hWndCtrl = GetDlgItem(hDlg, IDC_CHKINCARCHSET)
    lRet = SendMessageLong(hWndCtrl, BM_SETCHECK, 0&, 0&)

    hWndCtrl = GetDlgItem(hDlg, IDC_CHKRESETARCH)
    lRet = SendMessageLong(hWndCtrl, BM_SETCHECK, 0&, 0&)

    hWndCtrl = GetDlgItem(hDlg, IDC_CHKINCHIDSYS)
    lRet = SendMessageLong(hWndCtrl, BM_SETCHECK, 0&, 0&)
End Sub

'Private Sub FlatCDTBar(hParent As Long)
'    Dim lStyle As Long
'    Dim hToolbar As Long
'    Dim lRet As Long
'    Const TB_SETSTYLE = &H400 + 56
'    Const TB_GETSTYLE = &H400 + 57
'    Const TBSTYLE_FLAT = &H800
'
'    hToolbar = FindWindowEx(hParent, 0&, _
'                "ToolbarWindow32", vbNullString)
'    lStyle = SendMessageLong(hToolbar, _
'                    TB_GETSTYLE, 0&, 0&)
'    If lStyle And TBSTYLE_FLAT Then
'        lStyle = lStyle Xor TBSTYLE_FLAT
'    Else
'        lStyle = lStyle Or TBSTYLE_FLAT
'    End If
'    lRet = SendMessageLong(hToolbar, TB_SETSTYLE, 0, lStyle)
'End Sub


Private Sub m_cHookAddDlg_InitDone(ByVal hDlg As Long)
    m_cHookAddDlg.CentreDialog hDlg, Me
End Sub

Private Sub m_cHookAddDlg_WMCommand(ByVal hDlg As Long, wParam As Long, lParam As Long)
 'NEW event!  This is provided so you can respond to users who play
 'with your newly added toys.
    Dim lRet As Long
    Dim lNotify As Long
    Dim lCtrlID As Long
    Dim sCaption As String
    Dim sCaption1 As String
    Const BN_CLICKED = 0
    Const CBN_CLOSEUP = 8
    Const BM_GETCHECK = &HF0
    Const CB_GETCURSEL = &H147
    Const CB_GETITEMDATA = &H150

    lNotify = Get_HiWord(wParam)
    Select Case lNotify
        Case BN_CLICKED
            lCtrlID = Get_LoWord(wParam)
            Select Case lCtrlID
                Case IDC_CHKEXTRAFLDR
                    listBox.AddItem "'Save Extra Folder Info' checked = " & _
                        CBool(SendMessageLong(lParam, _
                        BM_GETCHECK, 0&, 0&))
                Case IDC_CHKSUBFLDR
                    listBox.AddItem "'Recurse Sub Folders' checked = " & _
                        CBool(SendMessageLong(lParam, _
                        BM_GETCHECK, 0&, 0&))
                Case IDC_CHK83
                    listBox.AddItem "'Save Filenames In 8.3 Format' checked = " & _
                        CBool(SendMessageLong(lParam, _
                        BM_GETCHECK, 0&, 0&))
                Case IDC_CHKINCARCHSET
                    listBox.AddItem "'Include Only If Archive Bit Set' checked = " & _
                        CBool(SendMessageLong(lParam, _
                        BM_GETCHECK, 0&, 0&))
                Case IDC_CHKRESETARCH
                    listBox.AddItem "'Reset Archive Bit' checked = " & _
                        CBool(SendMessageLong(lParam, _
                        BM_GETCHECK, 0&, 0&))
                Case IDC_CHKINCHIDSYS
                    listBox.AddItem "'Include Hidden and System Files' checked = " & _
                        CBool(SendMessageLong(lParam, _
                        BM_GETCHECK, 0&, 0&))

                Case IDC_BUTTONWILD
                    listBox.AddItem "'Add/Move With Wildcard' clicked"
                    lRet = SendMessageLong(GetParent(hDlg), _
                                        WM_CLOSE, _
                                        0&, _
                                        0&)
            End Select
        Case CBN_CLOSEUP
            lCtrlID = Get_LoWord(wParam)
            Select Case lCtrlID
                Case IDC_CBOACTION
                    lRet = SendMessageLong(lParam, CB_GETCURSEL, 0&, 0&)
                    Select Case lRet
                        Case 0
                            sCaption = "&Add"
                            sCaption1 = "Add With &Wildcards"
                            listBox.AddItem "Action 'Add Files' chosen"
                            SendMessageStr GetParent(hDlg), CDM_SETCONTROLTEXT, _
                                ID_FOLDERLABEL, "Add &from:"
                        Case 1
                            sCaption = "&Move"
                            sCaption1 = "Move With &Wildcards"
                            listBox.AddItem "Action 'Move Files' chosen"
                            SendMessageStr GetParent(hDlg), CDM_SETCONTROLTEXT, _
                                ID_FOLDERLABEL, "Move &from:"
                    End Select
                    SendMessageStr GetParent(hDlg), _
                            CDM_SETCONTROLTEXT, ID_OPEN, sCaption
                    SetDlgItemText GetParent(lParam), _
                            IDC_BUTTONWILD, sCaption1
                    listBox.AddItem "Action item data = " & SendMessageLong(lParam, _
                        CB_GETITEMDATA, lRet, 0&)
                Case IDC_CBOCOMPRESS
                    lRet = SendMessageLong(lParam, CB_GETCURSEL, 0&, 0&)
                    Select Case lRet
                        Case 0
                            listBox.AddItem "Compression 'None' chosen"
                        Case 1
                            listBox.AddItem "Compression 'MSZip' chosen"
                        Case 2
                            listBox.AddItem "Compression 'LZX' chosen"
                    End Select
                    listBox.AddItem "Compression item data = " & SendMessageLong(lParam, _
                        CB_GETITEMDATA, lRet, 0&)
            End Select
    End Select
End Sub


Private Sub cmdExit_Click()
  End
End Sub

'Private Sub cmdOpen_Click()
'  Dim strFileNames As Variant
'  Dim selectedFiles As String
'  Dim i As Integer
'
'  selectedFiles = GetFiles
'  MsgBox selectedFiles
'  If selectedFiles <> "" Then
'  strFileNames = Split(selectedFiles, Chr(0))
'    With listBox
'      If UBound(strFileNames) > 0 Then
'        '.Clear
'        'Path is stored in index 0 of array files in index 1...
'        'Label1.Caption = "Files selected from: " & strFileNames(0)
'        For i = 1 To UBound(strFileNames)
'          .AddItem strFileNames(0) & "\" & strFileNames(i)
'        Next
'      Else
'          If strFileNames(0) <> "" Then
'              .AddItem strFileNames(0)
'          End If
'      End If
'    End With
'    getTotalSelectedFiles
'    btnRemove.Visible = True
'    btnFileList.SetFocus
'  End If
'End Sub



Private Sub btnRemove_Click()
    If listBox.ListIndex > -1 Then
        listBox.RemoveItem (listBox.ListIndex)
        getTotalSelectedFiles
    Else
        MsgBox "Please select file to remove"
    End If
End Sub

Private Sub btnFileList_Click()
    Dim I As Integer
    Dim selectedFile As String
    selectedFile = ""
    If listBox.ListCount > 0 Then
    For I = 1 To listBox.ListCount
        If selectedFile = "" Then
            selectedFile = listBox.List(I - 1)
        Else
            selectedFile = selectedFile & "|" & listBox.List(I - 1)
        End If
    Next
    Else
        MsgBox "Plese add files to import"
        Exit Sub
    End If
    modBulkImport.initialize (selectedFile)
    
End Sub

Private Sub getTotalSelectedFiles()
    Dim count As Integer
    count = listBox.ListCount
    Label2.Caption = "Total number of files selected: " & count
End Sub



