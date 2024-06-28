@echo off

echo Compilando arquivos do pacote compute...
call javac compute/Compute.java compute/Task.java
if %errorlevel% neq 0 pause
call jar cvf classes/compute.jar compute/*.class

echo Compilando arquivos do pacote engine...
call javac -cp ./classes/compute.jar engine/ComputeEngine.java
if %errorlevel% neq 0 pause

echo Compilando arquivos do pacote client...
call javac -cp ./classes/compute.jar client/Encript.java
if %errorlevel% neq 0 pause
call javac -cp ./classes/compute.jar client/Senha.java
if %errorlevel% neq 0 pause
call javac -cp ./classes/compute.jar client/IMC.java
if %errorlevel% neq 0 pause
call javac -cp ./classes/compute.jar;libs/core-3.5.3.jar;libs/javase-3.5.3.jar client/QRCodeGenerator.java
if %errorlevel% neq 0 pause

echo Compilando arquivos do pacote sockets...
call javac -cp ./classes/compute.jar;libs/core-3.5.3.jar;libs/javase-3.5.3.jar sockets/TCPServer.java client/Encript.java client/Senha.java client/IMC.java client/QRCodeGenerator.java
if %errorlevel% neq 0 pause 
call javac -cp ./classes/compute.jar;libs/core-3.5.3.jar;libs/javase-3.5.3.jar sockets/TCPClient.java client/Encript.java
if %errorlevel% neq 0 pause
call javac -cp ./classes/compute.jar;libs/core-3.5.3.jar;libs/javase-3.5.3.jar sockets/TCPClient.java client/Senha.java
if %errorlevel% neq 0 pause
call javac -cp ./classes/compute.jar;libs/core-3.5.3.jar;libs/javase-3.5.3.jar sockets/TCPClient.java client/IMC.java
if %errorlevel% neq 0 pause
call javac -cp ./classes/compute.jar;libs/core-3.5.3.jar;libs/javase-3.5.3.jar sockets/TCPClient.java client/QRCodeGenerator.java
if %errorlevel% neq 0 pause

echo Compilacao concluida.
pause
