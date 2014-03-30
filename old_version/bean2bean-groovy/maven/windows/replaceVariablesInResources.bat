@ECHO OFF

REM  Reemplaza las variables de los recursos para que se puedan utilizar desde el directorio
REM de clases

REM - LABEL INDICATING THE BEGINNING OF THE SCRIPT
:BEGIN

REM Chequea los parametros necesarios
IF (%1) == () GOTO USAGE

GOTO MAVEN_TASK

:USAGE
echo.
echo Descripcion:
echo    Este comando reemplaza las variables de maven, en los archivos de resources  
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
mvn resources:resources -P%1
GOTO END
