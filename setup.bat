@echo off

call javac compute/Compute.java compute/Task.java
pause 
call jar cvf classes/compute.jar compute/*.class
pause 
call javac -cp ./classes/compute.jar engine/ComputeEngine.java
pause
call javac -cp ./classes/compute.jar client/Encript.java
pause
call javac -cp ./classes/compute.jar sockets/TCPServer.java client/Encript.java
pause 
call javac -cp ./classes/compute.jar sockets/TCPClient.java client/Encript.java
pause 