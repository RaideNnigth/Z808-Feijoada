package assembler.tables.datatable.dataitemtype;

public class TypeFactory {
    private TypeFactory() {}

    public static Type inferType(String type) {
        if (!isValidTypeName(type))
            throw new IllegalArgumentException("Invalid data type: " + type);

        // Will extract the type if this is an array
        String typeExtracted = type.split("\\[")[0];

        return switch (typeExtracted) {
            case CHAR.MNEMONIC -> new CHAR(type);
            case DW.MNEMONIC -> new DW(type);
            case DD.MNEMONIC -> new DD(type);

            default -> throw new IllegalArgumentException("Invalid data type: " + type);
        };
    }

    private static boolean isValidTypeName(String type) {
        return type.matches("[A-Z]+((\\[)[1-9]\\d*])?");
    }
}
