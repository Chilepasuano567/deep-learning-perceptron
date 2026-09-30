package HandsOn2_OR.src;

/**
 * Representa un patrón de entrenamiento: entradas (x1, x2) y salida esperada.
 */
public class Patron {

    private final double x1;
    private final double x2;
    private final double salidaEsperada;

    public Patron(double x1, double x2, double salidaEsperada) {
        this.x1 = x1;
        this.x2 = x2;
        this.salidaEsperada = salidaEsperada;
    }

    public double getX1() { return x1; }
    public double getX2() { return x2; }
    public double getSalidaEsperada() { return salidaEsperada; }
}
