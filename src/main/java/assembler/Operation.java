package assembler;

public interface Operation {
    String AX_STR = "AX";
    String DX_STR = "DX";
    String SI_STR = "SI";

    void assemble(String line);
}
