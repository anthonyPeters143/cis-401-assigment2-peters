# cis-401-assigment2-peters
Author      --------------------------------------------------------
Made by Anthony Peters

Description --------------------------------------------------------
Assignment 2 is a server/client software that connects two computers
though a socket using UDP protocols. It is configured to connect to 
a server at IP: 192.168.0.187, Port: 3000. The software is to allow
a client to access data-bank stored on a remote server. Will accept
an input of firstName, lastName, and ssn, no numeric characters 
allowed. If input is incorrect then user will be reprompted and 
entry not found then server will reply with -1 for ssn value. If 
input is correct and is found then server will return found 
SsnNode's ssn value. If found or not after a input is accepted
then the connection will be ended.

Instructions  --------------------------------------------------------
Run ServerUDP before any ClientsUDP, once running ServerUDP will allow
for unlimited clients connections. When client connects user will be 
prompted to enter a firstName and lastName values, when input is 
correct and sent then the ServerUDP will search and respond to 
ClientUDP with "-1" if no entries match values and if found then will
respond with ssn values of matching SsnNode.