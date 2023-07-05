package virtual_machine.memory;

import virtual_machine.utils.BinaryUtils;

public class MemoryController {
    private final Memory mainMemory;
    private int codeSegment;
    private int dataSegment;
    private int stackSegment;

    public static final int standardCodeSegment = 0;
    public static final int standardDataSegment = 1001;
    public static final int standardStackSegment = 64_000;

    public MemoryController() {
        mainMemory = Memory.getInstance();
        codeSegment = standardCodeSegment;
        dataSegment = standardDataSegment;
        stackSegment = standardStackSegment;
    }

    /**
     * Esse método garante que o endereço recebido esteja dentro do
     * intervalo de endereços permitos pelo Z808 [0 - 65.535].
     *
     * @param addr endereço
     * @return endereço dentro do permitido
     */
    private int validAddr(int addr) {
        return Math.abs(addr % Memory.MEM_SIZE);
    }


    /**
     * Esse método retorna uma instrução da memória num dado endereço
     * em Big Endian.
     * <p>
     * Lembre-se que as instruções no Z808 são todas armazenadas em
     * Little Endian. Esse método converte ela para Big Endian e a
     * retorna.
     *
     * @param address endereço onde está a instrução na memória
     * @return instrução em Big Endian
     */
    public short getInstructionBE(int address) {
        short inst = getInstructionLE(address);

        return BinaryUtils.swapHighAndLowOrder(inst);
    }

    /**
     * Esse método retorna uma instrução da memória num dado endereço
     * em Little Endian.
     * <p>
     * Lembre-se que as instruções no Z808 são todas armazenadas em
     * Little Endian. Esse método retorna a instrução como ela está
     * na memória.
     *
     * @param address endereço onde está a instrução na memória
     * @return instrução em Little Endian
     */
    public short getInstructionLE(int address) {
        return mainMemory.read(validAddr(address) + codeSegment);
    }

    /**
     * Esse método escreve um dado na região de instruções da
     * memória do Z808.
     * <p>
     * Lembre-se que o dado precisa estar em Little Endian!
     *
     * @param value   dado em Little Endian para escrever na
     *                memória
     * @param address endereço onde o dado será escrito
     */
    public void writeInstruction(short value, int address) {
        mainMemory.write(value, (validAddr(address) + codeSegment));
    }

    /**
     * Esse método retorna um dado da memória num dado endereço
     * em Big Endian.
     * <p>
     * Lembre-se que os dados no Z808 são todos armazenados em
     * Little Endian. Esse método converte o dado para Big Endian
     * e o retorna.
     *
     * @param address endereço onde está a dado na memória
     * @return dado em Big Endian
     */
    public short getDataBE(int address) {
        short data = getDataLE(address);

        return BinaryUtils.swapHighAndLowOrder(data);
    }

    /**
     * Esse método retorna um dado da memória num dado endereço
     * em Little Endian.
     * <p>
     * Lembre-se que os dados no Z808 são todos armazenados em
     * Little Endian. Esse método retorna o dado como está.
     *
     * @param address endereço onde está a dado na memória
     * @return dado em Little Endian
     */
    public short getDataLE(int address) {
        return mainMemory.read((validAddr(address) + dataSegment));
    }

    /**
     * Esse método escreve um dado na região de dados da
     * memória do Z808.
     * <p>
     * Lembre-se que o dado precisa estar em Little Endian!
     *
     * @param value   dado em Little Endian para escrever na
     *                memória
     * @param address endereço onde o dado será escrito
     */
    public void writeData(short value, int address) {
        mainMemory.write(value, (validAddr(address) + dataSegment));
    }

    /**
     * Pop from stack, it also verifies if the given address is a valid stack address
     *
     * @param address address to pop from
     * @return data at that address
     */
    public short getStack(int address) {
        if (address < stackSegment)
            return 0x0000;

        short value = mainMemory.read(validAddr(address));
        mainMemory.write((short) 0x0000, validAddr(address));
        return value;
    }

    /**
     * Push to stack, it also verifies if the given address is a valid stack address
     *
     * @param address addres to push from
     * @param value   data at thad address
     */
    public void writeStack(int address, short value) {
        if (address < stackSegment)
            return;

        mainMemory.write(value, validAddr(address));
    }

    public void resetSegments() {
        codeSegment = standardCodeSegment;
        dataSegment = standardDataSegment;
        stackSegment = standardStackSegment;
    }
}
