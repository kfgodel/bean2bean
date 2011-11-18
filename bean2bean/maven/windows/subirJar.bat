@ECHO OFF

REM establece que las variables son locales a este batch
SETLOCAL
set REPOSITORY_LOCATION=serru\mavenRepository
set REPOSITORY_ID=embuRemoteDrive
set ARTIFACT_TYPE=jar
set USE_POM_COMMAND= 


REM - LABEL INDICATING THE BEGINNING OF THE DOCUMENT.
:BEGIN

REM Chequea los parametros necesarios
IF (%1) == () GOTO USAGE
IF (%2) == () GOTO USAGE
IF (%3) == () GOTO USAGE
IF (%4) == () GOTO USAGE
IF not (%5) == () SET USE_POM_COMMAND=-DpomFile=%5

GOTO UPLOAD_JAR


:UPLOAD_JAR
mvn deploy:deploy-file -Dfile=%1 -DgroupId=%2 -DartifactId=%3 -Dversion=%4 -Dpackaging=%ARTIFACT_TYPE% -Durl="file:\\%REPOSITORY_LOCATION%" -DrepositoryId=%REPOSITORY_ID% %USE_POM_COMMAND%
GOTO END


:USAGE
echo.
echo Descripcion:
echo    Este comando sube el jar al repositorio embu que debe ser accesible como 
echo    "%REPOSITORY_LOCATION%" en el filesystem.
echo    Debe ser ejecutado en el directorio del master-pom y el pom debe tener 
echo    definido el repositorio "%REPOSITORY_ID%".
echo.
echo Modo de uso:
echo  subirJar.bat ^<pathAlJar^> ^<groupId^> ^<artifactId^> ^<versionId^> [^<pathAlPomDelJar^>]
echo. 
echo    El pom del jar es opcional, si no se especifica se creara uno muy simple 
echo    en el repositorio. 
echo.
echo Ejemplo:
echo.
echo subirJar.bat "d:\jarBajado\commons-web-0.1.jar" "com.fdv" "commons-web" "0.1" "d:\jarBajado\commons-web-0.1.pom"
echo.

:END
