@rem @echo off
@rem for /R eclipse\ClientMirrgBuilding\mirrgClient %%d in (*) do del /Q /A:RSHA "%%d"
rmdir /S /Q eclipse\ClientMirrgBuilding\mirrgClient
xcopy src\main\java eclipse\ClientMirrgBuilding\mirrgClient /E /I /Q /Y
perl eclipse\X1_ClientForgeBinBuilding\tabtospace.pl eclipse\ClientMirrgBuilding\mirrgClient
call eclipse\X1_ClientForgeBinBuilding\pmd-bin-5.1.1\bin\pmd -t 4 -d eclipse\ClientMirrgBuilding\mirrgClient -f xml -R eclipse\X1_ClientForgeBinBuilding\clientonly.xml -v 1.6 > eclipse\ClientMirrgBuilding\log.xml
perl eclipse\X1_ClientForgeBinBuilding\deleteclientonly.pl eclipse\ClientMirrgBuilding\log.xml
