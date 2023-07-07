package virtual_machine.os_interruptions.operations;
import virtual_machine.commands.operations.Command;
import virtual_machine.interpreter.OpParameters;
import java.util.HashMap;
import java.time.LocalTime;

public class GetSystemTime implements Command {
    @Override
    public void doOperation(HashMap<OpParameters, Object> args) {
        LocalTime currentTime = LocalTime.now();

        // Extract the hour, minutes, and seconds
        int CH  = currentTime.getHour();
        int CL  = currentTime.getMinute();
        int DH  = currentTime.getSecond();

    }
}