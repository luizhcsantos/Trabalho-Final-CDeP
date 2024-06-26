@echo off

start rmiregistry
pause
start java engine.ComputeEngine localhost 1099
pause 
start java sockets/TCPServer
pause
call java sockets/TCPClient encript teste 5 
pause 