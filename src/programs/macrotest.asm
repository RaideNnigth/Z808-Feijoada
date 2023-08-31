.code segment
oi macrodef x
	add ax, x
	add ax, x
endm

callm oi 1

oi macrodef x
	sub ax, x
endm

callm oi 3
