package virtual_machine.commands;


import virtual_machine.commands.operations.Command;
import virtual_machine.commands.operations.arithmetical.AddAxDx;

import java.util.HashMap;
import java.util.List;

public class CommandExecutor {

    private Command command;

    private final HashMap<Byte, Command> opCodeMap;

    public CommandExecutor() {
        this.opCodeMap = new HashMap<>();
        this.opCodeMap.put( (byte) 30, new AddAxDx() );
    }

    private void setOperation( byte opCode ) {
        this.command = this.opCodeMap.get( opCode );
    }

    public void doOperation( byte opCode, List<Object> args ) {
        this.setOperation( opCode );
        this.command.doOperation( args );
    }

}
