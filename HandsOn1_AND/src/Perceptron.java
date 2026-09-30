package HandsOn1_AND.src;
/**
 * Perceptrón de dos entradas con función de activación escalón.
 *
 * Regla de aprendizaje SIN velocidad de aprendizaje (alpha implícito = 1):
 *      w_i = w_i + e * x_i
 *      b   = b   + e
 * donde e = y - y_hat.
 */
public class Perceptron {

    // Parámetros del modelo (se usan enteros: con alpha = 1 y datos binarios
    // todos los cálculos son exactos).
    private int w1;
    private int w2;
    private int b;

    private final int maxEpocas;       // Límite de seguridad para evitar bucles infinitos
    private int epocasEjecutadas = 0;  // Épocas realmente recorridas

    public Perceptron(int w1Inicial, int w2Inicial, int bInicial, int maxEpocas) {
        this.w1 = w1Inicial;
        this.w2 = w2Inicial;
        this.b = bInicial;
        this.maxEpocas = maxEpocas;
    }

    /** Suma ponderada: z = w1*x1 + w2*x2 + b */
    public int sumaPonderada(Patron p) {
        return w1 * p.getX1() + w2 * p.getX2() + b;
    }

    /** Función escalón: f(z) = 1 si z >= 0, en otro caso 0 */
    public int activacion(int z) {
        return (z >= 0) ? 1 : 0;
    }

    /** Predicción completa para un patrón (sin modificar pesos). */
    public int predecir(Patron p) {
        return activacion(sumaPonderada(p));
    }

    /**
     * Entrena el perceptrón mostrando la traza paso a paso.
     * @return true si convergió (una época completa sin errores).
     */
    public boolean entrenar(Patron[] patrones) {
        System.out.println("==============================================================");
        System.out.println(" ENTRENAMIENTO DEL PERCEPTRÓN - COMPUERTA AND (sin alpha)");
        System.out.println("==============================================================");
        System.out.printf("Pesos iniciales: w1=%d, w2=%d, b=%d%n", w1, w2, b);
        System.out.println("Regla: w_i = w_i + e*x_i ;  b = b + e ;  e = y - y_hat");
        System.out.println("Activación: f(z) = 1 si z >= 0, sino 0");

        for (int epoca = 1; epoca <= maxEpocas; epoca++) {
            int errores = 0;
            System.out.println();
            System.out.printf("---------------- ÉPOCA %d  (w1=%d, w2=%d, b=%d) ----------------%n",
                    epoca, w1, w2, b);

            for (int i = 0; i < patrones.length; i++) {
                Patron p = patrones[i];

                // 1) Suma ponderada
                int z = sumaPonderada(p);
                // 2) Activación
                int yHat = activacion(z);
                // 3) Error
                int e = p.getSalidaEsperada() - yHat;

                System.out.printf("Patrón %d: x=[%d,%d]  y=%d%n",
                        i + 1, p.getX1(), p.getX2(), p.getSalidaEsperada());
                System.out.printf("   z = (%d)(%d) + (%d)(%d) + (%d) = %d%n",
                        w1, p.getX1(), w2, p.getX2(), b, z);
                System.out.printf("   f(z) = %d   |   e = %d - %d = %d%n",
                        yHat, p.getSalidaEsperada(), yHat, e);

                // 4) Actualización de pesos (solo si hay error)
                if (e != 0) {
                    errores++;
                    int w1Nuevo = w1 + e * p.getX1();
                    int w2Nuevo = w2 + e * p.getX2();
                    int bNuevo = b + e;
                    System.out.printf("   Actualización: w1 = %d + (%d)(%d) = %d%n", w1, e, p.getX1(), w1Nuevo);
                    System.out.printf("                  w2 = %d + (%d)(%d) = %d%n", w2, e, p.getX2(), w2Nuevo);
                    System.out.printf("                  b  = %d + (%d)     = %d%n", b, e, bNuevo);
                    w1 = w1Nuevo;
                    w2 = w2Nuevo;
                    b = bNuevo;
                } else {
                    System.out.println("   Sin error -> los pesos NO cambian.");
                }
            }

            epocasEjecutadas = epoca;
            System.out.printf(">> Errores en la época %d: %d%n", epoca, errores);

            if (errores == 0) {
                System.out.printf(">> Época %d sin errores: EL ALGORITMO CONVERGIÓ.%n", epoca);
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
        System.out.printf("Con w1=%d, w2=%d, b=%d se evalúan todos los patrones:%n", w1, w2, b);
        System.out.println(" x1 x2 |  z | f(z) | y esperada | ¿Correcto?");
        System.out.println("-------+----+------+------------+-----------");

        boolean todosCorrectos = true;
        for (Patron p : patrones) {
            int z = sumaPonderada(p);
            int yHat = activacion(z);
            boolean ok = (yHat == p.getSalidaEsperada());
            todosCorrectos &= ok;
            System.out.printf("  %d  %d | %2d |  %d   |     %d      | %s%n",
                    p.getX1(), p.getX2(), z, yHat, p.getSalidaEsperada(), ok ? "SÍ" : "NO");
        }
        System.out.println();
        if (todosCorrectos) {
            System.out.printf("CONVERGENCIA DEMOSTRADA: 4/4 patrones clasificados correctamente en %d épocas.%n",
                    epocasEjecutadas);
            System.out.println("(La AND es linealmente separable, por lo que el teorema de convergencia");
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
        System.out.println("w1 = " + w1);
        System.out.println("w2 = " + w2);
        System.out.println("b  = " + b);
        System.out.printf("Frontera de decisión: %d*x1 + %d*x2 + (%d) = 0%n", w1, w2, b);
    }
}
