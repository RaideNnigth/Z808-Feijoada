package virtual_machine.observerpattern;

import virtual_machine.registers.Registers;

import java.util.HashMap;

public interface RegObsListener {
     void updatedRegs(HashMap<Registers, Short> workRegsValues, HashMap<String, Short> regFlags);
}
