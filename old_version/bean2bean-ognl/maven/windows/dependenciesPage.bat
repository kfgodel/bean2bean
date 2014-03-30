@ECHO OFF

REM  Crea una pagina html que muestra las dependencias del proyecto

REM - LABEL INDICATING THE BEGINNING OF THE SCRIPT
:BEGIN

REM Chequea los parametros necesarios

GOTO MAVEN_TASK

:MAVEN_TASK
mvn project-info-reports:dependencies
GOTO END

:USAGE
echo.
echo Descripcion:
echo    Este comando genera la pagina html que describe las dependencias del  
echo    proyecto
echo.
echo Modo de uso:
echo  %0 
echo.

:END

