package virtual_machine.os_interruptions.operations;
import virtual_machine.commands.operations.Command;
import virtual_machine.interpreter.OpParameters;
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.util.HashMap;

public class GetSystemDate implements Command {
    @Override
    public void doOperation(HashMap<OpParameters, Object> args) {
        LocalDate currentDate = LocalDate.now();

        // Extract the year, month, day, and day of the week
        int CX  = currentDate.getYear();
        int DH  = currentDate.getMonthValue();
        int DL  = currentDate.getDayOfMonth();
        DayOfWeek AL  = currentDate.getDayOfWeek();

    }
}
