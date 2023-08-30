.CODE SEGMENT

ADD ax, 10

sum MACRODEF x, y
	subtri MACRODEF x, y
		SUB ax, x
		SUB ax, y
	ENDM
	ADD ax, x
	ADD ax, y
ENDM

sum 20, 30
subtri 15, 25