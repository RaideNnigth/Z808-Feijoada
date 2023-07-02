package virtual_machine.commands;


import virtual_machine.commands.operations.Add;
import virtual_machine.commands.operations.Command;
import virtual_machine.types.UnsignedByte;

import java.util.HashMap;
import java.util.List;

public class Invoker {

    private Command command;

    private final HashMap<UnsignedByte, Command> opCodeMap;

    public Invoker() {
        this.opCodeMap = new HashMap<>();
        this.opCodeMap.put( new UnsignedByte( 30 ), new Add() );
    }

    private void setOperation( UnsignedByte opCode ) {
        this.command = this.opCodeMap.get( opCode );
    }

    public void doOperation( UnsignedByte opCode, List<Object> args ) {
        this.setOperation( opCode );
        this.command.doOperation( args );
    }

}
