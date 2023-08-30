package virtual_machine.registers;

import java.util.ArrayList;

public class RegFlags implements StatusRegister {
    Boolean of;
    Boolean sf;
    Boolean zf;
    Boolean ifFlag;
    Boolean pf;
    Boolean cf;

    public RegFlags() {
        this.of = false;
        this.sf = false;
        this.zf = false;
        this.ifFlag = false;
        this.pf = false;
        this.cf = false;
    }

    /**
     * @return the value of overflow flag
     */
    public Boolean getOf() {
        return of;
    }

    /**
     * @return the value of signal flag
     */
    public Boolean getSf() {
        return sf;
    }

    /**
     * @return the value of zero flag
     */
    public Boolean getZf() {
        return zf;
    }

    /**
     * @return the value of interruption flag
     */
    public Boolean getIfFlag() {
        return ifFlag;
    }

    /**
     * @return the value of parity bit flag
     */
    public Boolean getPf() {
        return pf;
    }

    /**
     * @return the value of carry flag
     */
    public Boolean getCf() {
        return cf;
    }

    /**
     * Used to set the overflow flag
     *
     * @param of
     */
    public void setOf(Boolean of) {
        this.of = of;
    }

    /**
     * Used to set the signal flag
     *
     * @param sf
     */
    public void setSf(Boolean sf) {
        this.sf = sf;
    }

    /**
     * Used to set the zero flag
     *
     * @param zf
     */
    public void setZf(Boolean zf) {
        this.zf = zf;
    }

    /**
     * Used to set the interruption flag
     *
     * @param ifFlag
     */
    public void setIfFlag(Boolean ifFlag) {
        this.ifFlag = ifFlag;
    }

    /**
     * Used to set the parity bit flag
     *
     * @param pf
     */
    public void setPf(Boolean pf) {
        this.pf = pf;
    }

    /**
     * Used to set the carry flag
     *
     * @param cf
     */
    public void setCf(Boolean cf) {
        this.cf = cf;
    }

    // Overhead
    public void reset() {
        setOf(false);
        setSf(false);
        setZf(false);
        setIfFlag(false);
        setPf(false);
        setCf(false);
    }

    @Override
    public String toString() {
        return String.format(
                "&emsp;of: %s<br>" +
                "&emsp;sf: %s<br>" +
                "&emsp;zf: %s<br>" +
                "&emsp;if: %s<br>" +
                "&emsp;pf: %s<br>" +
                "&emsp;cf: %s"
                , of ? "1" : "0", sf ? "1" : "0", zf ? "1" : "0", ifFlag ? "1" : "0", pf ? "1" : "0", cf ? "1" : "0");
    }
}
