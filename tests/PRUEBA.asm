LD A, B
LD A, (BC)
LD DE, 300
POP IX
CPI
ADD B
ADD 10
HALT
DEC IX
RLCA
BIT 0, C
JP (IX)
RETI