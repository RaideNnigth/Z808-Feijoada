.code segment
sum macrodef x, y
	add ax, x
	add ax, y
endm
sum 16, 20