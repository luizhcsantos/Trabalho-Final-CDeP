@echo off

start rmiregistry
pause
start java engine.ComputeEngine localhost 1099
pause
start java -cp .;libs/core-3.5.3.jar;libs/javase-3.5.3.jar sockets.TCPServer
pause
