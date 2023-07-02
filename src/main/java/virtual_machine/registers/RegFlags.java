package virtual_machine.registers;

public class RegFlags {
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

    public Boolean getOf() {
        return of;
    }
    public Boolean getSf() {
        return sf;
    }
    public Boolean getZf() {
        return zf;
    }
    public Boolean getIfFlag() {
        return ifFlag;
    }
    public Boolean getPf() {
        return pf;
    }
    public Boolean getCf() {
        return cf;
    }
    public void setOf(Boolean of) {
        this.of = of;
    }
    public void setSf(Boolean sf) {
        this.sf = sf;
    }
    public void setZf(Boolean zf) {
        this.zf = zf;
    }
    public void setIfFlag(Boolean ifFlag) {
        this.ifFlag = ifFlag;
    }
    public void setPf(Boolean pf) {
        this.pf = pf;
    }
    public void setCf(Boolean cf) {
        this.cf = cf;
    }
    public void reset() {
        this.of = false;
        this.sf = false;
        this.zf = false;
        this.ifFlag = false;
        this.pf = false;
        this.cf = false;
    }
}
