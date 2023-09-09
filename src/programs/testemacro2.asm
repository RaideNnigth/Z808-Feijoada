name testemacro2

.code segment

wnd macrodef
	mov ax, 1
	sum macrodef x,y
		add ax, x
		add ax, y
	endm
endm

linux macrodef
	mov ax, 0
	sum macrodef x,y
		add ax, x
		add ax, y
	endm
endm

callm linux
callm wnd
callm linux#sum 200,20000