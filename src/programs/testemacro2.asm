.code segment

wnd macrodef
	mov ax, 1
	sum macrodef x,y
		add ax, x
		add ax, y
	endm
endm

linux
sum 1,2

linux macrodef
	mov ax, 0
	sum macrodef x,y
		add ax, x
		add ax, y
	endm
endm

