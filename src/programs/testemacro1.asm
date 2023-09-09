NAME hehe

.code segment

sum macrodef x, y
	add ax, x
	add ax, y
endm
callm sum 16, 20