package modelo;

public class Vertice {

    private String rotulo;

    public Vertice(String rotulo) {
        this.rotulo = rotulo;
    }

    public String getRotulo() {
        return rotulo;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Vertice outro = (Vertice) obj;

        return rotulo.equalsIgnoreCase(outro.rotulo);
    }

    @Override
    public int hashCode() {
        return rotulo.toUpperCase().hashCode();
    }

    @Override
    public String toString() {
        return rotulo;
    }
}