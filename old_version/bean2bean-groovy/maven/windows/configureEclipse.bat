@ECHO OFF

REM  Crea los .projects para cada modulo, de manera que sean vistos como projectos java

REM - LABEL INDICATING THE BEGINNING OF THE SCRIPT
:BEGIN

GOTO MAVEN_TASK

:USAGE
echo.
echo Descripcion:
echo    Este comando genera la descripcion de los modulos maven como 
echo    proyectos para eclipse.
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
mvn eclipse:clean eclipse:eclipse -DlimitProjectReferencesToWorkspace=true dependency:sources
GOTO END
