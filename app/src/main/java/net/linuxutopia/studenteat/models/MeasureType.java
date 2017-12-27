package net.linuxutopia.studenteat.models;

import net.linuxutopia.studenteat.R;

public enum MeasureType {

    ML(R.string.measure_type_ml),
    CL(R.string.measure_type_cl),
    L(R.string.measure_type_l),
    MG(R.string.measure_type_mg),
    G(R.string.measure_type_g),
    KG(R.string.measure_type_kg),
    TEASPOON(R.string.measure_type_teaspoon),
    SPOON(R.string.measure_type_spoon),
    CUP(R.string.measure_type_cup),
    PINCH(R.string.measure_type_pinch),
    PIECE(R.string.measure_type_piece);

    private final int measureTypeResource;

    MeasureType(int measureTypeResource) {
        this.measureTypeResource = measureTypeResource;
    }

    public int getStringResource() {
        return measureTypeResource;
    }

}
