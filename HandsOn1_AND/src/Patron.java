package HandsOn1_AND.src;
/**
 * Representa un patrón de entrenamiento: un vector de entradas (x1, x2)
 * y la salida esperada (y) asociada.
 */
public class Patron {

    private final int x1;
    private final int x2;
    private final int salidaEsperada;

    public Patron(int x1, int x2, int salidaEsperada) {
        this.x1 = x1;
        this.x2 = x2;
        this.salidaEsperada = salidaEsperada;
    }

    public int getX1() { return x1; }
    public int getX2() { return x2; }
    public int getSalidaEsperada() { return salidaEsperada; }

    @Override
    public String toString() {
        return "[" + x1 + "," + x2 + " -> " + salidaEsperada + "]";
    }
}
