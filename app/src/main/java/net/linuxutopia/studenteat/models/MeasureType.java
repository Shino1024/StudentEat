package net.linuxutopia.studenteat.models;

public enum MeasureType {

    ML("ml"),
    CL("cl"),
    L("l"),
    MG("mg"),
    G("g"),
    KG("kg"),
    TEASPOON("teaspoon"),
    SPOON("spoon"),
    CUP("cup"),
    PINCH("pinch"),
    PIECE("piece");

    private final String measureType;

    MeasureType(String measureType) {
        this.measureType = measureType;
    }

    public String getStringDescription() {
        return measureType;
    }

}
