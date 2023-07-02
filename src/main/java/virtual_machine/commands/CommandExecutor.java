package virtual_machine.commands;


<<<<<<< Updated upstream
import virtual_machine.commands.operations.*;
import virtual_machine.commands.operations.arithmetical.AddAxDx;
import virtual_machine.types.UnsignedByte;
=======
import virtual_machine.commands.operations.AddAxDx;
import virtual_machine.commands.operations.Command;
import virtual_machine.types.byte;
>>>>>>> Stashed changes

import java.util.HashMap;
import java.util.List;

public class CommandExecutor {

    private Command command;

    private final HashMap<byte, Command> opCodeMap;

    public CommandExecutor() {
        this.opCodeMap = new HashMap<>();
        this.opCodeMap.put( new byte( 30 ), new AddAxDx() );
    }

    private void setOperation( byte opCode ) {
        this.command = this.opCodeMap.get( opCode );
    }

    public void doOperation( byte opCode, List<Object> args ) {
        this.setOperation( opCode );
        this.command.doOperation( args );
    }

}
