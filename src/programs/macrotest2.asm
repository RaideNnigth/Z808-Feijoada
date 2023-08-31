.code segment
oi macrodef x, y
	add ax, x

	oi2 macrodef
		sub ax, y
	endm

	callm oi2
endm

oi2 macrodef
	sub ax, 2
endm

callm oi 2, 3
callm oi.oi2
callm oi2