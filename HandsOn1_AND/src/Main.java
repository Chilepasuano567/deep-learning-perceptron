package HandsOn1_AND.src;
/**
 * Hands-on 1: Perceptrón para la compuerta lógica AND (sin velocidad de aprendizaje).
 */
public class Main {

    public static void main(String[] args) {

        // ---- Patrones de entrenamiento (definidos en el código fuente) ----
        Patron[] patrones = {
            new Patron(0, 0, 0),
            new Patron(0, 1, 0),
            new Patron(1, 0, 0),
            new Patron(1, 1, 1)
        };

        // ---- Inicialización de pesos y bias (definidos en el código fuente) ----
        int w1 = 0;
        int w2 = 0;
        int b = 0;
        int maxEpocas = 100;

        Perceptron perceptron = new Perceptron(w1, w2, b, maxEpocas);

        boolean convergio = perceptron.entrenar(patrones);
        perceptron.demostrarConvergencia(patrones);
        perceptron.imprimirResultadosFinales();

        if (!convergio) {
            System.out.println("Advertencia: se alcanzó el máximo de épocas sin converger.");
        }
    }
}
