import java.util.Scanner;

public class Main {

    // Constantes para los límites de validación
    private static final int NUSS_MIN = 100000;
    private static final int NUSS_MAX = 999999;
    private static final int SINTOMA_MIN = 0;
    private static final int SINTOMA_MAX = 3;
    private static final int EXPLORACION_MIN = 0;
    private static final int EXPLORACION_MAX = 3;
    private static final int PRIORIDAD_MIN = 0;
    private static final int PRIORIDAD_MAX = 5;
    private static final int TEMPERATURA_MIN = 27;
    private static final int TEMPERATURA_MAX = 45;

    // Constantes de texto para los síntomas
    private static final String SINTOMA_DOLOR = "Dolor";
    private static final String SINTOMA_LESION = "Lesión traumática";
    private static final String SINTOMA_FIEBRE = "Fiebre alta";
    private static final String SINTOMA_CONFUSION = "Confusión o desorientación";

    // Constantes de texto para las exploraciones
    private static final String[] EXPLORACION_DOLOR = {"Dolor torácico", "Dolor abdominal", "Dolor de cabeza", "Migraña"};
    private static final String[] EXPLORACION_LESION = {"Fractura ósea", "Herida de bala", "Quemadura", "Lesión cerebral traumática"};
    private static final String[] EXPLORACION_FIEBRE = {"Neumonía", "Meningitis", "Infección viral", "Reacción alérgica"};
    private static final String[] EXPLORACION_CONFUSION = {"Intoxicación por drogas o alcohol", "Deshidratación severa", "Accidente cerebrovascular", "Hipoglucemia severa"};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int nuss = obtenerNUSS(scanner);
        int sintoma = obtenerSintoma(scanner);
        int exploracion = obtenerExploracion(scanner, sintoma);
        int prioridad = obtenerNivelPrioridad(scanner);
        int temperatura = obtenerTemperatura(scanner);

        mostrarResumen(nuss, sintoma, exploracion, prioridad, temperatura);

        scanner.close();
    }

    private static int obtenerNUSS(Scanner scanner) {
        int nuss = -1;
        while (true) {
            System.out.print("NUSS?:");
            if (scanner.hasNextInt()) {
                nuss = scanner.nextInt();
                if (nuss >= NUSS_MIN && nuss <= NUSS_MAX) {
                    break;
                } else {
                    System.out.println("Error: El NUSS debe estar entre " + NUSS_MIN + " y " + NUSS_MAX + ".");
                }
            } else {
                scanner.next();  // Descartar entrada no válida
                System.out.println("Error: Introduce un número entero válido para el NUSS.");
            }
        }
        return nuss;
    }

    private static int obtenerSintoma(Scanner scanner) {
        int sintoma = -1;
        while (true) {
            System.out.println("¿Sintoma?");
            System.out.println("     " + SINTOMA_DOLOR + " (0)");
            System.out.println("     " + SINTOMA_LESION + " (1)");
            System.out.println("     " + SINTOMA_FIEBRE + " (2)");
            System.out.println("     " + SINTOMA_CONFUSION + " (3)");
            System.out.print(": ");

            if (scanner.hasNextInt()) {
                sintoma = scanner.nextInt();
                if (sintoma >= SINTOMA_MIN && sintoma <= SINTOMA_MAX) {
                    break;
                } else {
                    System.out.println("Error: Selecciona un número entre " + SINTOMA_MIN + " y " + SINTOMA_MAX + ".");
                }
            } else {
                scanner.next();
                System.out.println("Error: Introduce un número entero válido para el síntoma.");
            }
        }
        return sintoma;
    }

    private static int obtenerExploracion(Scanner scanner, int sintoma) {
        int exploracion = -1;
        String[] opcionesExploracion = obtenerOpcionesExploracion(sintoma);
        while (true) {
            System.out.println("¿Exploración inicial?");
            for (int i = 0; i < opcionesExploracion.length; i++) {
                System.out.println("     " + opcionesExploracion[i] + " (" + i + ")");
            }
            System.out.print(": ");

            if (scanner.hasNextInt()) {
                exploracion = scanner.nextInt();
                if (exploracion >= EXPLORACION_MIN && exploracion <= EXPLORACION_MAX) {
                    break;
                } else {
                    System.out.println("Error: Selecciona un número entre " + EXPLORACION_MIN + " y " + EXPLORACION_MAX + ".");
                }
            } else {
                scanner.next();
                System.out.println("Error: Introduce un número entero válido para la exploración.");
            }
        }
        return exploracion;
    }

    private static String[] obtenerOpcionesExploracion(int sintoma) {
        switch (sintoma) {
            case 0: return EXPLORACION_DOLOR;
            case 1: return EXPLORACION_LESION;
            case 2: return EXPLORACION_FIEBRE;
            case 3: return EXPLORACION_CONFUSION;
            default: return new String[0];
        }
    }

    private static int obtenerNivelPrioridad(Scanner scanner) {
        int prioridad = -1;
        while (true) {
            System.out.print("¿Nivel de prioridad?(0-5): ");
            if (scanner.hasNextInt()) {
                prioridad = scanner.nextInt();
                if (prioridad >= PRIORIDAD_MIN && prioridad <= PRIORIDAD_MAX) {
                    break;
                } else {
                    System.out.println("Error: El nivel de prioridad debe estar entre " + PRIORIDAD_MIN + " y " + PRIORIDAD_MAX + ".");
                }
            } else {
                scanner.next();
                System.out.println("Error: Introduce un número entero válido para el nivel de prioridad.");
            }
        }
        return prioridad;
    }

    private static int obtenerTemperatura(Scanner scanner) {
        int temperatura = -1;
        while (true) {
            System.out.print("¿Temperatura actual?: ");
            if (scanner.hasNextInt()) {
                temperatura = scanner.nextInt();
                if (temperatura >= TEMPERATURA_MIN && temperatura <= TEMPERATURA_MAX) {
                    break;
                } else {
                    System.out.println("Error: La temperatura debe estar entre " + TEMPERATURA_MIN + " y " + TEMPERATURA_MAX + " grados.");
                }
            } else {
                scanner.next();
                System.out.println("Error: Introduce un número entero válido para la temperatura.");
            }
        }
        return temperatura;
    }

    private static void mostrarResumen(int nuss, int sintoma, int exploracion, int prioridad, int temperatura) {
        // Encabezado
        System.out.println("NUSS   | Síntoma                | Exploración inicial            | Nivel de prioridad| Temperatura actual");

        // Datos
        System.out.printf("%-5d | %-22s | %-30s | %-17d | %-20d°C",
                nuss,
                obtenerDescripcionSintoma(sintoma),
                obtenerDescripcionExploracion(sintoma, exploracion),
                prioridad,
                temperatura);
    }

    private static String obtenerDescripcionSintoma(int sintoma) {
        switch (sintoma) {
            case 0: return SINTOMA_DOLOR;
            case 1: return SINTOMA_LESION;
            case 2: return SINTOMA_FIEBRE;
            case 3: return SINTOMA_CONFUSION;
            default: return "Síntoma desconocido";
        }
    }

    private static String obtenerDescripcionExploracion(int sintoma, int exploracion) {
        String[] opcionesExploracion = obtenerOpcionesExploracion(sintoma);
        if (exploracion >= 0 && exploracion < opcionesExploracion.length) {
            return opcionesExploracion[exploracion];
        }
        return "Exploración desconocida";
    }
}
