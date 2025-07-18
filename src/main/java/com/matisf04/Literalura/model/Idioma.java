package com.matisf04.Literalura.model;

public enum Idioma {
    ESPANOL ("es", "español"),
    INGLES ("en", "inglés"),
    FRANCES ("fr", "francés"),
    PORTUGUES ("pt", "portugués"),
    ALEMAN ("de", "alemán"),
    CHINO ("zh", "chino"),
    FINLANDES ("fi", "finlandés");

    private String idiomaAPI;
    private String idiomaNombre;

    Idioma(String idiomaAPI, String idiomaNombre){
        this.idiomaAPI = idiomaAPI;
        this.idiomaNombre = idiomaNombre;
    }

    public static String traducirCodigo(String codigo) {
        for (Idioma idioma : Idioma.values()) {
            if (idioma.idiomaAPI.equalsIgnoreCase(codigo)) {
                return idioma.idiomaNombre;
            }
        }
        return "Idioma no registrado (" + codigo + ")";
    }
}
