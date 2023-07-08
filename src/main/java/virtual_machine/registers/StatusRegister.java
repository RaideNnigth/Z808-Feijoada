package virtual_machine.registers;

public interface StatusRegister {
    public Boolean getOf();
    public Boolean getSf();
    public Boolean getZf();
    public Boolean getIfFlag();
    public Boolean getPf();
    public Boolean getCf();
    public void setOf(Boolean of);
    public void setSf(Boolean sf);
    public void setZf(Boolean zf);
    public void setIfFlag(Boolean ifFlag);
    public void setPf(Boolean pf);
    public void setCf(Boolean cf);
}
