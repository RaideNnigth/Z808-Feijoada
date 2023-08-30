package assembler;

import assembler.tables.datatable.dataitemtype.Type;
import assembler.tables.datatable.dataitemtype.TypeFactory;
import assembler.utils.AssemblerUtils;

public class AssemblerTester {
    public static void main(String[] args) {
        String test = "DW[3] {256,3,6}";
        var a = AssemblerUtils.decomposeInTokens(test);
        for(String s : a) {
            System.out.println(s);
        }

        Type d = TypeFactory.inferType(a[0]);
        System.out.println(d.getSizeInBytes() + " " + d.getDefaultSize());

        System.out.println(d.parseInitialValue(a[1]));
    }
}
