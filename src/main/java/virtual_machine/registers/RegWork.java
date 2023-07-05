package virtual_machine.registers;

import virtual_machine.utils.BinaryUtils;

public class RegWork {
    private byte regHigh;
    private byte regLow;

    public byte getRegHigh() {
        return regHigh;
    }

    public byte getRegLow() {
        return regLow;
    }

    /**
     * Retorna o valor de 16-bits armazenado no registrador. Esse
     * valor pode ser usado para operações.
     *
     * @return valor armazenado no registrador
     */
    public short getReg() {
        return BinaryUtils.concatBytes(regHigh, regLow);
    }

    /**
     * Retorna o valor de 16-bits armazenado no registrador no
     * formato Little Endian.
     * <p>
     * Esse valor NÃO pode ser usado para operações! Somente
     * para armazenar na memória.
     * <p>
     * Para operações, use getReg().
     *
     * @return valor armazenado no registrador em Little Endian
     */
    public short getRegLE() {
        return BinaryUtils.concatBytes(regLow, regHigh);
    }

    public void setRegHigh(int regHigh) {
        this.regHigh = (byte) regHigh;
    }

    public void setRegLow(int regLow) {
        this.regLow = (byte) regLow;
    }

    public void setReg(short reg) {
        this.regHigh = BinaryUtils.getHighByte(reg);
        this.regLow = BinaryUtils.getLowByte(reg);
    }
}
