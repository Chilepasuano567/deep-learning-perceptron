package HandsOn2_OR.src;

/**
 * Hands-on 2: Perceptrón para la compuerta lógica OR (con velocidad de aprendizaje).
 */
public class Main {

    public static void main(String[] args) {

        // ---- Patrones de entrenamiento (definidos en el código fuente) ----
        Patron[] patrones = {
            new Patron(0, 0, 0),
            new Patron(0, 1, 1),
            new Patron(1, 0, 1),
            new Patron(1, 1, 1)
        };

        // ---- Inicialización de parámetros (definidos en el código fuente) ----
        double w1 = 0.0;
        double w2 = 0.0;
        double b = 0.0;
        double alpha = 0.1;   // Velocidad de aprendizaje
        int maxEpocas = 100;

        Perceptron perceptron = new Perceptron(w1, w2, b, alpha, maxEpocas);

        boolean convergio = perceptron.entrenar(patrones);
        perceptron.demostrarConvergencia(patrones);
        perceptron.imprimirResultadosFinales();

        if (!convergio) {
            System.out.println("Advertencia: se alcanzó el máximo de épocas sin converger.");
        }
    }
}
