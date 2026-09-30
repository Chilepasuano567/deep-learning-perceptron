package HandsOn2_OR.src;

import java.util.Locale;

/**
 * Perceptrón de dos entradas con función de activación escalón
 * y VELOCIDAD DE APRENDIZAJE (alpha).
 *
 * Regla de aprendizaje:
 *      w_i = w_i + alpha * e * x_i
 *      b   = b   + alpha * e
 * donde e = y - y_hat.
 */
public class Perceptron {

    /**
     * Tolerancia numérica para comparar con 0 en aritmética de punto flotante.
     * En aritmética exacta, "z >= -EPSILON" equivale a "z >= 0"; evita que
     * errores de redondeo (p. ej. 0.1 + 0.1 - 0.2 = 5e-17) alteren la clasificación.
     */
    private static final double EPSILON = 1e-9;

    private double w1;
    private double w2;
    private double b;
    private final double alpha;        // Velocidad de aprendizaje
    private final int maxEpocas;
    private int epocasEjecutadas = 0;

    public Perceptron(double w1Inicial, double w2Inicial, double bInicial,
                      double alpha, int maxEpocas) {
        this.w1 = w1Inicial;
        this.w2 = w2Inicial;
        this.b = bInicial;
        this.alpha = alpha;
        this.maxEpocas = maxEpocas;
    }

    /** Suma ponderada: z = w1*x1 + w2*x2 + b */
    public double sumaPonderada(Patron p) {
        return w1 * p.getX1() + w2 * p.getX2() + b;
    }

    /** Función escalón: f(z) = 1 si z >= 0, sino 0 */
    public int activacion(double z) {
        return (z >= -EPSILON) ? 1 : 0;
    }

    /** Formatea un número con 4 decimales (evita imprimir "-0.0000"). */
    private static String f(double v) {
        return String.format(Locale.US, "%.4f", v + 0.0);
    }

    /**
     * Entrena el perceptrón con traza paso a paso.
     * @return true si convergió (una época completa sin errores).
     */
    public boolean entrenar(Patron[] patrones) {
        System.out.println("==============================================================");
        System.out.println(" ENTRENAMIENTO DEL PERCEPTRÓN - COMPUERTA OR (con alpha)");
        System.out.println("==============================================================");
        System.out.println("Pesos iniciales: w1=" + f(w1) + ", w2=" + f(w2) + ", b=" + f(b));
        System.out.println("Velocidad de aprendizaje: alpha = " + alpha);
        System.out.println("Regla: w_i = w_i + alpha*e*x_i ;  b = b + alpha*e ;  e = y - y_hat");
        System.out.println("Activación: f(z) = 1 si z >= 0, sino 0");

        for (int epoca = 1; epoca <= maxEpocas; epoca++) {
            int errores = 0;
            System.out.println();
            System.out.println("---------------- ÉPOCA " + epoca + "  (w1=" + f(w1)
                    + ", w2=" + f(w2) + ", b=" + f(b) + ") ----------------");

            for (int i = 0; i < patrones.length; i++) {
                Patron p = patrones[i];

                // 1) Suma ponderada
                double z = sumaPonderada(p);
                // 2) Activación
                int yHat = activacion(z);
                // 3) Error
                int e = (int) p.getSalidaEsperada() - yHat;

                System.out.println("Patrón " + (i + 1) + ": x=[" + (int) p.getX1() + ","
                        + (int) p.getX2() + "]  y=" + (int) p.getSalidaEsperada());
                System.out.println("   z = (" + f(w1) + ")(" + (int) p.getX1() + ") + ("
                        + f(w2) + ")(" + (int) p.getX2() + ") + (" + f(b) + ") = " + f(z));
                System.out.println("   f(z) = " + yHat + "   |   e = "
                        + (int) p.getSalidaEsperada() + " - " + yHat + " = " + e);

                // 4) Actualización con alpha
                if (e != 0) {
                    errores++;
                    double w1Nuevo = w1 + alpha * e * p.getX1();
                    double w2Nuevo = w2 + alpha * e * p.getX2();
                    double bNuevo = b + alpha * e;
                    System.out.println("   Actualización: w1 = " + f(w1) + " + (" + alpha + ")(" + e + ")("
                            + (int) p.getX1() + ") = " + f(w1Nuevo));
                    System.out.println("                  w2 = " + f(w2) + " + (" + alpha + ")(" + e + ")("
                            + (int) p.getX2() + ") = " + f(w2Nuevo));
                    System.out.println("                  b  = " + f(b) + " + (" + alpha + ")(" + e + ")"
                            + "     = " + f(bNuevo));
                    w1 = w1Nuevo;
                    w2 = w2Nuevo;
                    b = bNuevo;
                } else {
                    System.out.println("   Sin error -> los pesos NO cambian.");
                }
            }

            epocasEjecutadas = epoca;
            System.out.println(">> Errores en la época " + epoca + ": " + errores);

            if (errores == 0) {
                System.out.println(">> Época " + epoca + " sin errores: EL ALGORITMO CONVERGIÓ.");
                return true;
            }
        }
        return false;
    }

    /** Verificación explícita de convergencia con los pesos finales. */
    public void demostrarConvergencia(Patron[] patrones) {
        System.out.println();
        System.out.println("==============================================================");
        System.out.println(" DEMOSTRACIÓN DE CONVERGENCIA");
        System.out.println("==============================================================");
        System.out.println("Con w1=" + f(w1) + ", w2=" + f(w2) + ", b=" + f(b)
                + " se evalúan todos los patrones:");
        System.out.println(" x1 x2 |    z    | f(z) | y esperada | ¿Correcto?");
        System.out.println("-------+---------+------+------------+-----------");

        boolean todosCorrectos = true;
        for (Patron p : patrones) {
            double z = sumaPonderada(p);
            int yHat = activacion(z);
            boolean ok = (yHat == (int) p.getSalidaEsperada());
            todosCorrectos &= ok;
            System.out.println("  " + (int) p.getX1() + "  " + (int) p.getX2() + " | "
                    + String.format("%7s", f(z)) + " |  " + yHat + "   |     "
                    + (int) p.getSalidaEsperada() + "      | " + (ok ? "SÍ" : "NO"));
        }
        System.out.println();
        if (todosCorrectos) {
            System.out.println("CONVERGENCIA DEMOSTRADA: 4/4 patrones clasificados correctamente en "
                    + epocasEjecutadas + " épocas.");
            System.out.println("(La OR es linealmente separable, por lo que el teorema de convergencia");
            System.out.println(" del perceptrón garantiza que el algoritmo termina.)");
        } else {
            System.out.println("NO se alcanzó convergencia dentro del máximo de épocas.");
        }
    }

    /** Imprime los valores óptimos finales. */
    public void imprimirResultadosFinales() {
        System.out.println();
        System.out.println("==============================================================");
        System.out.println(" VALORES ÓPTIMOS FINALES");
        System.out.println("==============================================================");
        System.out.println("w1 = " + f(w1));
        System.out.println("w2 = " + f(w2));
        System.out.println("b  = " + f(b));
        System.out.println("Frontera de decisión: " + f(w1) + "*x1 + " + f(w2) + "*x2 + (" + f(b) + ") = 0");
    }
}
