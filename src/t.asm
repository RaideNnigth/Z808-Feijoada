.data segment
penes dw 55
gustavo dw 36
danigay dw 10
tico dw 0

.code segment
mov ax, danigay

; There is a bug! For some reason it is not
; writing in tico
label:
mov dx, ax
mov ax, tico
add ax, 1
mov tico, ax
mov ax, dx
sub ax, 1
jnz label

