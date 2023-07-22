package virtual_machine.memory;

import virtual_machine.utils.BinaryUtils;

import java.util.ArrayList;

public class MemoryController implements Observable {
    private final Memory mainMemory;
    private ArrayList<Observer> observers;

    public MemoryController() {
        mainMemory = Memory.getInstance();
        observers = new ArrayList<>();
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
     * Get the word at the memory address specified as Little Endian, it considers that the memory is Little Endian
     * @param address
     * @return
     */
    public short getWordLE(short address) {
        return mainMemory.read(Short.toUnsignedInt(address));
    }

    /**
     * Get the word at the memory address specified as Big Endian, it considers that the memory is Little Endian
     * @param address
     * @return
     */
    public short getWordBE(short address) {
        short temp = mainMemory.read(Short.toUnsignedInt(address));
        return BinaryUtils.swapHighAndLowOrder(temp);
    }

    /**
     * Since our data is handled as big-endian internally we have to convert to little-endian to the memory in order
     * to stay correct.
     * @param word
     * @param address
     */
    public void writeWord(short word, short address) {
        mainMemory.write(BinaryUtils.swapHighAndLowOrder(word), Short.toUnsignedInt(address));
    }

    public void register(Observer observer) {
        if (!observers.contains(observer))
            observers.add(observer);
    }

    public void unregister(Observer observer) {
        if (observers.contains(observer))
            observers.remove(observer);
    }

    public void notifyObservers() {
        for (Observer observer : observers)
            observer.update();
    }

    public Object getUpdate(Observer observer) {
        return null;
    }
}
