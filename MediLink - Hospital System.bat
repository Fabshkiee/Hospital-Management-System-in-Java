@echo off
cls
echo ==========================================
echo  Compiling Hospital Management System...
echo ==========================================

:: Ensure we are in the project root
cd /d "%~dp0"

:: Create the 'bin' folder if it doesn't exist
if not exist "bin" mkdir bin

:: Compile
:: -d bin      -> Puts the .class files inside the bin folder
:: -cp "lib\*" -> Includes the Jackson libraries
:: src\...     -> The source files to compile
javac -d bin -cp "lib\*" src\hospitalsystem\*.java

if %errorlevel% neq 0 (
    echo.
    echo [ERROR] Compilation Failed!
    pause
    exit /b
)

echo.
echo [SUCCESS] Compilation complete. Starting program...
echo ------------------------------------------
echo.

:: Run
:: -cp "bin;lib\*" -> Look for YOUR classes in 'bin', and libraries in 'lib'
java -cp "bin;lib\*" hospitalsystem.Main

echo.
echo ------------------------------------------
echo Program execution finished.
pause