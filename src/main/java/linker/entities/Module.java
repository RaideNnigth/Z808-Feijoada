package linker.entities;

import linker.tables.DefinitionsTable;
import linker.tables.UsageTable;
import utils.BinaryUtils;

public class Module {
    private byte[] moduleCode;
    private int codeSegmentSize;
    private int dataSegmentSize;

    public DefinitionsTable definitionsTable;
    public UsageTable usageTable;
    public String binPath;

    public Module() {
        this.definitionsTable = new DefinitionsTable();
        this.usageTable = new UsageTable();
        this.binPath = "";
        this.moduleCode = null;
        this.codeSegmentSize = 0;
        this.dataSegmentSize = 0;
    }

    public void setModuleCode(byte[] moduleCode) {
        this.moduleCode = moduleCode;
        calculateSegments();
    }

    public byte[] getModuleCode() {
        return moduleCode;
    }

    public void writeInModuleCode(short address, short value) {
        // Write in little endian
        BinaryUtils.writeShortInByteArrayLE(moduleCode, address, value);
    }

    /**
     * Returns the code segment size. This value does not include the header size!
     */
    public int getCodeSegmentSize() {
        return codeSegmentSize;
    }

    /**
     * Returns the data segment size. This value does not include the header size!
     */
    public int getDataSegmentSize() {
        return dataSegmentSize;
    }

    private void calculateSegments() {
        short csStart = BinaryUtils.concatBytes(moduleCode[1], moduleCode[0]);
        short csEnd = BinaryUtils.concatBytes(moduleCode[3], moduleCode[2]);
        codeSegmentSize = csEnd - csStart;

        short dsStart = BinaryUtils.concatBytes(moduleCode[5], moduleCode[4]);
        short dsEnd = BinaryUtils.concatBytes(moduleCode[7], moduleCode[6]);
        dataSegmentSize = dsEnd - dsStart;
    }
}