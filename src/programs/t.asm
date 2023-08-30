.data segment
penes dw 55
gustavo dw 36
danigay dw 10
tico dw 0

.code segment

; ax = 10
mov ax, danigay

; There is a bug! For some reason it is not
; writing in tico

label:
mov ax, gustavo
mov tico, ax