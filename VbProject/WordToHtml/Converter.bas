Attribute VB_Name = "Converter"
Option Explicit
Public objWord
Public objDoc

Public Sub Main()
On Error Resume Next
    Dim inFile As String
    Dim outFile As String
    
    Dim params
    params = Split(Command, ",")
    
    inFile = params(0)
    outFile = params(1)
    
    convert inFile, outFile
    
    End
End Sub

Private Sub Timeout(duration As Double)
    Form1.Timer1.Interval = duration
    Form1.Timer1.Enabled = True
End Sub


Private Sub convert(c1 As String, c2 As String)
On Error GoTo lblErr
    Timeout 20000
    Set objWord = CreateObject("Word.Application")
    objWord.Visible = False
    Set objDoc = objWord.Documents.Open(c1, False, True)
    
    objDoc.SaveAs c2, 8
    closeDoc
    closeWord
    Exit Sub
    
lblErr:
    'MsgBox Err.Description
    closeDoc
    closeWord
    Err.Clear
End Sub

Public Sub closeDoc()
On Error GoTo lblErr
    objDoc.Close
    Set objDoc = Nothing
    Exit Sub
lblErr:
    
End Sub

Public Sub closeWord()
On Error GoTo lblErr
    objWord.Quit
    Set objWord = Nothing
    Exit Sub
lblErr:

End Sub

