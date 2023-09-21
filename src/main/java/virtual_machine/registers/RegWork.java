package virtual_machine.registers;

import utils.BinaryUtils;

public class RegWork  {
    private byte regHigh;
    private byte regLow;

    public byte getHigh() {
        return regHigh;
    }

    public byte getLow() {
        return regLow;
    }

    /**
     * Retorna o valor de 16-bits armazenado no registrador. Esse
     * valor pode ser usado para operações.
     *
     * @return valor armazenado no registrador
     */
    public short getValue() {
        return BinaryUtils.concatBytes(regHigh, regLow);
    }

    /**
     * Retorna o valor de 16-bits armazenado no registrador no
     * formato Little Endian.
     * <p>
     * Esse valor NÃO pode ser usado para operações! Somente
     * para armazenar na memória.
     * <p>
     * Para operações, use getValue().
     *
     * @return valor armazenado no registrador em Little Endian
     */
    public short getValueLE() {
        return BinaryUtils.concatBytes(regLow, regHigh);
    }

    public void setHigh(int regHigh) {
        this.regHigh = (byte) regHigh;
    }

    public void setLow(int regLow) {
        this.regLow = (byte) regLow;
    }

    public void setValue(short reg) {
        this.regHigh = BinaryUtils.getHighByte(reg);
        this.regLow = BinaryUtils.getLowByte(reg);
    }

    public void reset() {
        setValue((short) 0);
    }
}
