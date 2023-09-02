.data segment
	val dw 0

.code segment

sum macrodef x, y, addr
	mov ax, 0
	add ax, x
	add ax, y
	mov addr, ax
endm

callm sum 12, 13, val
