Attribute VB_Name = "basCustomDlg"
Option Explicit
            
'Common dialog errors
Public Const CDERR_DIALOGFAILURE = &HFFFF
Public Const CDERR_FINDRESFAILURE = &H6
Public Const CDERR_GENERALCODES = &H0
Public Const CDERR_INITIALIZATION = &H2
Public Const CDERR_LOADRESFAILURE = &H7
Public Const CDERR_LOADSTRFAILURE = &H5
Public Const CDERR_LOCKRESFAILURE = &H8
Public Const CDERR_MEMALLOCFAILURE = &H9
Public Const CDERR_MEMLOCKFAILURE = &HA
Public Const CDERR_NOHINSTANCE = &H4
Public Const CDERR_NOHOOK = &HB
Public Const CDERR_NOTEMPLATE = &H3
Public Const CDERR_REGISTERMSGFAIL = &HC
Public Const CDERR_STRUCTSIZE = &H1
Public Const CDERR_CANCELED = 2001
Public Const FNERR_BUFFERTOOSMALL = &H3003

' Messages which can be sent to the standard dialog elements
Public Const CDM_FIRST = (WM_USER + 100)
Public Const CDM_LAST = (WM_USER + 200)
Public Const CDM_GETSPEC = (CDM_FIRST + &H0)
Public Const CDM_GETFILEPATH = (CDM_FIRST + &H1)
Public Const CDM_GETFOLDERPATH = (CDM_FIRST + &H2)
Public Const CDM_GETFOLDERIDLIST = (CDM_FIRST + &H3)
Public Const CDM_SETCONTROLTEXT = (CDM_FIRST + &H4)
Public Const CDM_HIDECONTROL = (CDM_FIRST + &H5)
Public Const CDM_SETDEFEXT = (CDM_FIRST + &H6)

' IDs for standard common dialog controls
Public Const ID_OPEN = &H1 'Open or Save button
Public Const ID_CANCEL = &H2 'Cancel Button
Public Const ID_HELP = &H40E 'Help Button
Public Const ID_READONLY = &H410 'Read-only check box
Public Const ID_FILETYPELABEL = &H441 'Files of type label
Public Const ID_FILELABEL = &H442 'File name label
Public Const ID_FOLDERLABEL = &H443 'Look in label
Public Const ID_LIST = &H461 'Parent of file list
Public Const ID_FORMAT = &H470 'File type combo box
Public Const ID_FOLDER = &H471 'Folder combo box
Public Const ID_FILETEXT = &H480 'File name text box

Public Function Get_LoWord(ByRef lThis As Long) As Long
   Get_LoWord = (lThis And &HFFFF&)
End Function

Public Sub Set_LoWord(ByRef lThis As Long, ByVal lLoWord As Long)
   lThis = lThis And Not &HFFFF& Or lLoWord
End Sub

Public Function Get_HiWord(ByRef lThis As Long) As Long
   If (lThis And &H80000000) = &H80000000 Then
      Get_HiWord = ((lThis And &H7FFF0000) \ &H10000) Or &H8000&
   Else
      Get_HiWord = (lThis And &HFFFF0000) \ &H10000
   End If
End Function

Public Sub Set_HiWord(ByRef lThis As Long, ByVal lHiWord As Long)
   If (lHiWord And &H8000&) = &H8000& Then
      lThis = lThis And Not &HFFFF0000 Or ((lHiWord And &H7FFF&) * &H10000) Or &H80000000
   Else
      lThis = lThis And Not &HFFFF0000 Or (lHiWord * &H10000)
   End If
End Sub

