@ECHO OFF

REM  Genera el artefacto del modulo en donde se ejecute

REM - LABEL INDICATING THE BEGINNING OF THE SCRIPT
:BEGIN

REM Chequea los parametros necesarios
IF (%1) == () GOTO USAGE

GOTO MAVEN_TASK

:USAGE
echo.
echo Descripcion:
echo    Este comando genera los artefactos de este proyecto y los instala en el 
echo    repositorio local. 
echo.
echo Modo de uso:
echo  %0 ^<profileName^> 
echo. 
echo    profileName: Es el nombre del profile para configurar el jar generado
echo.
echo Ejemplo:
echo.
echo %0 development

:END

:MAVEN_TASK
mvn clean compile install -Dmaven.test.skip=true -P%1
GOTO END
