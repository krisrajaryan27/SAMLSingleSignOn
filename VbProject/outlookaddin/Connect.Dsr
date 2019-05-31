VERSION 5.00
Begin {AC0714F6-3D04-11D1-AE7D-00A0C90F26F4} Connect 
   ClientHeight    =   9885
   ClientLeft      =   1740
   ClientTop       =   1545
   ClientWidth     =   10950
   _ExtentX        =   19315
   _ExtentY        =   17436
   _Version        =   393216
   Description     =   "Talentscout Addin"
   DisplayName     =   "Talentscout Addin"
   AppName         =   "Microsoft Outlook"
   AppVer          =   "Microsoft Outlook 11.0"
   LoadName        =   "Startup"
   LoadBehavior    =   3
   RegLocation     =   "HKEY_CURRENT_USER\Software\Microsoft\Office\Outlook"
End
Attribute VB_Name = "Connect"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = True
Attribute VB_PredeclaredId = False
Attribute VB_Exposed = True
Option Explicit

Implements IDTExtensibility2
Public objCB As Office.CommandBar

Public WithEvents tsCBAboutUs As Office.CommandBarButton
Attribute tsCBAboutUs.VB_VarHelpID = -1
Public WithEvents tsCBAttachToCandidate As Office.CommandBarButton
Attribute tsCBAttachToCandidate.VB_VarHelpID = -1
Public WithEvents tsCBSingleImport As Office.CommandBarButton
Attribute tsCBSingleImport.VB_VarHelpID = -1
Public WithEvents tsCBSettings As Office.CommandBarButton
Attribute tsCBSettings.VB_VarHelpID = -1
Public WithEvents tsCBBulkImport As Office.CommandBarButton
Attribute tsCBBulkImport.VB_VarHelpID = -1


Private Sub IDTExtensibility2_OnAddInsUpdate(custom() As Variant)
    'The OnAddInsUpdate method is called when a change occurs to the list of add-ins in the COM Add-Ins dialog box,
    'such as when an add-in is loaded or unloaded. The custom parameter is an array that can be used to provide
    'additional data to the OnAddInsUpdate method if desired.
End Sub

Private Sub IDTExtensibility2_OnBeginShutdown(custom() As Variant)
    'The OnBeginShutdown method is called while the environment is being shut down. The custom parameter is an array
    'that can be used to provide additional data to the OnBeginShutdown method if desired.
    
    
    If TypeName(tsCBAboutUs) <> "Nothing" Then
        tsCBAboutUs.Delete
    End If
    If TypeName(tsCBAttachToCandidate) <> "Nothing" Then
        tsCBAttachToCandidate.Delete
    End If
    If TypeName(tsCBBulkImport) <> "Nothing" Then
        tsCBBulkImport.Delete
    End If
    If TypeName(tsCBSingleImport) <> "Nothing" Then
        tsCBSingleImport.Delete
    End If
    If TypeName(tsCBSettings) <> "Nothing" Then
        tsCBSettings.Delete
    End If
    Set tsCBAboutUs = Nothing
    Set tsCBAttachToCandidate = Nothing
    Set tsCBSingleImport = Nothing
    Set tsCBSettings = Nothing
    Set tsCBBulkImport = Nothing
End Sub

Private Sub IDTExtensibility2_OnConnection(ByVal application As Object, ByVal connectMode As AddInDesignerObjects.ext_ConnectMode, _
    ByVal addInInst As Object, custom() As Variant)
    'The OnConnection method is called when the add-in is loaded into the environment. The addInInst parameter is an
    'object that represents the instance of the managed COM add-in. The custom parameter is an array that can be used
    'to use to provide additional data to the OnConnection method if desired. The application parameter represents the
    'host application. The connectMode parameter is an ext_cm constant that indicates how the managed COM add-in was loaded.
    Set moApp = application
    Set moAppInst = addInInst

    modCommon.initVariables
    
    If (connectMode <> AddInDesignerObjects.ext_ConnectMode.ext_cm_Startup) Then Call IDTExtensibility2_OnStartupComplete(custom)
    
End Sub

Private Sub IDTExtensibility2_OnDisconnection(ByVal RemoveMode As AddInDesignerObjects.ext_DisconnectMode, _
    custom() As Variant)
    'The OnDisconnection method is called when the managed COM add-in is unloaded, such as when the user closes the
    'host application. The custom parameter is an array that can be used to provide additional data to the OnDisconnection
    'method if desired. The RemoveMode parameter is an ext_dm constant that indicates how the managed COM add-in was unloaded.
    If TypeName(tsCBAboutUs) <> "Nothing" Then
        tsCBAboutUs.Delete
    End If
    If TypeName(tsCBAttachToCandidate) <> "Nothing" Then
        tsCBAttachToCandidate.Delete
    End If
    If TypeName(tsCBBulkImport) <> "Nothing" Then
        tsCBBulkImport.Delete
    End If
    If TypeName(tsCBSingleImport) <> "Nothing" Then
        tsCBSingleImport.Delete
    End If
    If TypeName(tsCBSettings) <> "Nothing" Then
        tsCBSettings.Delete
    End If
    Set tsCBAboutUs = Nothing
    Set tsCBAttachToCandidate = Nothing
    Set tsCBSingleImport = Nothing
    Set tsCBSettings = Nothing
    Set tsCBBulkImport = Nothing
End Sub

Private Sub IDTExtensibility2_OnStartupComplete(custom() As Variant)

    'The OnAction property is optional but recommended. It should be set to the ProgID of the add-in, so that if
    'the add-in is not loaded when a user clicks the button, MSO loads the add-in automatically and then raises
    'the Click event for the add-in to handle.
    
    Dim applicationPath As String
    applicationPath = App.path
    
    Set objCB = moApp.ActiveExplorer().CommandBars.Add("TalentPool Addin", MsoBarPosition.msoBarTop, False, True)
    objCB.Visible = True
   
   
    Set tsCBAboutUs = objCB.FindControl(, , "790", False, True)
    If TypeName(tsCBAboutUs) = "Nothing" Then
        Set tsCBAboutUs = objCB.Controls.Add(msoControlButton, , "", , True)
    End If
   
    Set tsCBAttachToCandidate = objCB.FindControl(, , "890", False, True)
    If TypeName(tsCBAttachToCandidate) = "Nothing" Then
        Set tsCBAttachToCandidate = objCB.Controls.Add(msoControlButton, , "", , True)
    End If
    
    Set tsCBBulkImport = objCB.FindControl(, , "990", False, True)
    If TypeName(tsCBBulkImport) = "Nothing" Then
        Set tsCBBulkImport = objCB.Controls.Add(msoControlButton, , "", , True)
    End If
    
    Set tsCBSingleImport = objCB.FindControl(, , "1090", False, True)
    If TypeName(tsCBSingleImport) = "Nothing" Then
        Set tsCBSingleImport = objCB.Controls.Add(msoControlButton, , "", , True)
    End If
    
     Set tsCBSettings = objCB.FindControl(, , "1190", False, True)
    If TypeName(tsCBSettings) = "Nothing" Then
        Set tsCBSettings = objCB.Controls.Add(msoControlButton, , "", , True)
    End If
    
    
    With tsCBAboutUs
        .BeginGroup = True
        .Caption = ""
        .DescriptionText = "TalentPool Addin"
        .Enabled = True
        .OnAction = "!"
        'Uncomment and change path to your image file
        Clipboard.Clear
        Clipboard.SetData LoadPicture(applicationPath & "\ts_icon.JPEG")
        .PasteFace
        .Style = msoButtonIconAndCaption
        .Tag = "790"
        .ToolTipText = "TalentPool Addin"
        .Visible = True
    End With
    
    With tsCBAttachToCandidate
        .BeginGroup = True
        .Caption = "Attach to candidate"
        .DescriptionText = "Attach to candidate"
        .Enabled = True
        .OnAction = "!"
        'Uncomment and change path to your image file
        Clipboard.Clear
        Clipboard.SetData LoadPicture(applicationPath & "\AttachToCandidate.jpg")
        .PasteFace
        .Style = msoButtonIconAndCaption
        .Tag = "890"
        .ToolTipText = "Attach to candidate"
        .Visible = True
    End With
    
    With tsCBBulkImport
        .BeginGroup = True
        .Caption = "Bulk Import"
        .DescriptionText = "Bulk Import"
        .Enabled = True
        .OnAction = "!"
        'Uncomment and change path to your image file
        Clipboard.Clear
        Clipboard.SetData LoadPicture(applicationPath & "\BulkImport.jpg")
        .PasteFace
        .Style = msoButtonIconAndCaption
        .Tag = "990"
        .ToolTipText = "Bulk Import"
        .Visible = True
    End With
    
    With tsCBSingleImport
        .BeginGroup = True
        .Caption = "Import"
        .DescriptionText = "Import"
        .Enabled = True
        .OnAction = "!"
        'Uncomment and change path to your image file
        Clipboard.Clear
        Clipboard.SetData LoadPicture(applicationPath & "\Import.jpg")
        .PasteFace
        .Style = msoButtonIconAndCaption
        .Tag = "1090"
        .ToolTipText = "Import"
        .Visible = True
    End With
    
     With tsCBSettings
        .BeginGroup = True
        .Caption = "Settings"
        .DescriptionText = "Settings"
        .Enabled = True
        .OnAction = "!"
        'Uncomment and change path to your image file
        Clipboard.Clear
        Clipboard.SetData LoadPicture(applicationPath & "\Settings.jpg")
        .PasteFace
        .Style = msoButtonIconAndCaption
        .Tag = "1190"
        .ToolTipText = "Settings"
        .Visible = True
    End With


End Sub

Private Sub tsCBAttachToCandidate_Click(ByVal Ctrl As Office.CommandBarButton, CancelDefault As Boolean)

    If modCommon.countSelectedEmail() < 1 Then
        MsgBox "Please select an Email"
        Exit Sub
    End If


    If modCommon.CheckValidSession("attachToCandidate") Then
        frmAttachToCandidate.Show
'        frmAttachToCandidate.processEmail
    Else
        frmLoginToTalentPool.Show
        frmLoginToTalentPool.tbUserName.SetFocus
    End If
    
End Sub


Private Sub tsCBSingleImport_Click(ByVal Ctrl As Office.CommandBarButton, CancelDefault As Boolean)
    
    If modCommon.countSelectedEmail() < 1 Then
        MsgBox "Please select an Email to import"
        Exit Sub
    ElseIf modCommon.countSelectedEmail() > 1 Then
        MsgBox "Please select only one Email"
        Exit Sub
    Else
        If modCommon.CheckValidSession("singleImport") Then
            frmSingleImport.Show
        Else
            frmLoginToTalentPool.Show
            frmLoginToTalentPool.tbUserName.SetFocus
        End If
    End If
End Sub

Private Sub tsCBSettings_Click(ByVal Ctrl As Office.CommandBarButton, CancelDefault As Boolean)
    frmSettings.Show
End Sub

Private Sub tsCBBulkImport_Click(ByVal Ctrl As Office.CommandBarButton, CancelDefault As Boolean)

    If modCommon.countSelectedEmail() < 1 Then
        MsgBox "Please select an Email"
        Exit Sub
    Else
        If modCommon.CheckValidSession("bulkImport") Then
            modBulkImport.initialize
        Else
            frmLoginToTalentPool.Show
            frmLoginToTalentPool.tbUserName.SetFocus
        End If
    End If
End Sub
