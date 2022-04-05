
; Creating with Inno Setup Compiler 5.5.3
; http://www.jrsoftware.org/isinfo.php


[Setup]
AppName=CircuitMod
AppVerName=CircuitMod v2.6
DefaultDirName={pf}\CircuitMod
DefaultGroupName=CircuitMod
UninstallDisplayIcon={app}\circuitmod.ico
WizardImageFile=installfront.bmp
; WizardSmallImageFile=installtop.bmp
SetupIconFile=install.ico

AppPublisher=CircuitMod
AppPublisherURL=http://circuitmod.sourceforge.net
AppVersion=2.6

; ShowLanguageDialog=yes
LicenseFile=gpl3win.txt
InfoBeforeFile=infobefore.txt
ChangesAssociations=yes


[Files]
Source: "circuitmod.jar"; DestDir: "{app}"
Source: "circuitmod.exe"; DestDir: "{app}"
Source: "circuitmod.ico"; DestDir: "{app}"
Source: "setuplist.txt"; DestDir: "{app}"
Source: "gpl3win.txt"; DestDir: "{app}"

; Circuits Files
Source: "circuits\*"; DestDir: "{app}\circuits"

; Tutorial
Source: "tutorial\index.html"; DestDir: "{app}\tutorial"; Flags: isreadme
Source: "tutorial\*"; DestDir: "{app}\tutorial"


[Languages]
Name: en; MessagesFile: "compiler:Default.isl"
;Name: es; MessagesFile: "compiler:Languages\Spanish.isl"


[Tasks]
Name: desktopicon; Description: {cm:CreateDesktopIcon}; GroupDescription: {cm:AdditionalIcons}
Name: quicklaunchicon; Description: {cm:CreateQuickLaunchIcon}; GroupDescription: {cm:AdditionalIcons}; Flags: unchecked


[Icons]
Name: "{group}\CircuitMod"; Filename: "{app}\circuitmod.exe"; IconFilename: "{app}\circuitmod.ico"
Name: "{group}\CircuitMod (Jar File)"; Filename: "{app}\circuitmod.jar"; IconFilename: "{app}\circuitmod.ico"
Name: "{group}\CircuitMod Help"; Filename: "{app}\tutorial\index.html";
Name: "{group}\{cm:UninstallProgram,CircuitMod}"; Filename: "{uninstallexe}"
Name: "{commondesktop}\CircuitMod"; Filename: "{app}\circuitmod.jar"; IconFilename: "{app}\circuitmod.ico"

[Registry]
Root: HKCR; Subkey: ".cmf"; ValueType: string; ValueData: "cmffile"
Root: HKCR; Subkey: ".cmf"; ValueType: string; ValueName: "Content Type"; ValueData: "text/txt"
Root: HKCR; Subkey: ".cmf"; ValueType: string; ValueName: "PerceivedType"; ValueData: "text"

Root: HKCR; Subkey: "cmffile"; ValueType: string; ValueData: "CircuitMod File"; Flags: uninsdeletekey
Root: HKCR; Subkey: "cmffile\Shell\Open"; Flags: uninsdeletekey
Root: HKCR; Subkey: "cmffile\Shell\Open\Command"; ValueType: string; ValueData: """{app}\circuitmod.exe"" ""%1"""; Flags: uninsdeletekey
Root: HKCR; Subkey: "cmffile\DefaultIcon"; ValueType: string; ValueData: "{app}\circuitmod.ico"; Flags: uninsdeletekey

Root: HKCU; Subkey: "Software\Microsoft\Windows\CurrentVersion\Explorer\FileExts\.cmf\OpenWithList";
Root: HKCU; Subkey: "Software\Microsoft\Windows\CurrentVersion\Explorer\FileExts\.cmf\OpenWithList"; ValueType: string; ValueName: "a"; ValueData: "notepad.exe"
Root: HKCU; Subkey: "Software\Microsoft\Windows\CurrentVersion\Explorer\FileExts\.cmf\OpenWithList"; ValueType: string; ValueName: "MRUList"; ValueData: "a"




