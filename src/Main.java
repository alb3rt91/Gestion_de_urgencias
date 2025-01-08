import java.util.Scanner;
import java.util.ArrayList;
import java.util.Comparator;

class Paciente {
    int nuss;
    int sintoma;
    int exploracion;
    int prioridad;
    int temperatura;

    Paciente(int nuss, int sintoma, int exploracion, int prioridad, int temperatura) {
        this.nuss = nuss;
        this.sintoma = sintoma;
        this.exploracion = exploracion;
        this.prioridad = prioridad;
        this.temperatura = temperatura;
    }
}

public class Main {

    private static final int MAX_ATTEMPTS = 3;
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

    private static final String[] SINTOMAS = {"Dolor", "Lesión traumática", "Fiebre alta", "Confusión o desorientación"};
    private static final String[][] EXPLORACIONES = {
            {"Dolor torácico", "Dolor abdominal", "Dolor de cabeza", "Migraña"},
            {"Fractura ósea", "Herida de bala", "Quemadura", "Lesión cerebral traumática"},
            {"Neumonía", "Meningitis", "Infección viral", "Reacción alérgica"},
            {"Intoxicación por drogas o alcohol", "Deshidratación severa", "Accidente cerebrovascular", "Hipoglucemia severa"}
    };

    private static ArrayList<Paciente> pacientes = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("\nGestión de Pacientes");
            System.out.println("1. Añadir paciente");
            System.out.println("2. Mostrar pacientes");
            System.out.println("3. Modificar paciente");
            System.out.println("4. Eliminar paciente");
            System.out.println("5. Ordenar por prioridad");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    añadirPaciente(scanner);
                    break;
                case 2:
                    mostrarPacientes();
                    break;
                case 3:
                    modificarPaciente(scanner);
                    break;
                case 4:
                    eliminarPaciente(scanner);
                    break;
                case 5:
                    ordenarPorPrioridad();
                    break;
                case 6:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
        scanner.close();
    }

    private static void añadirPaciente(Scanner scanner) {
        for (int i = 0; i < MAX_ATTEMPTS; i++) {
            try {
                int nuss = obtenerDato(scanner, "NUSS", NUSS_MIN, NUSS_MAX);
                int sintoma = obtenerDato(scanner, "Síntoma (0: Dolor, 1: Lesión, 2: Fiebre, 3: Confusión)", SINTOMA_MIN, SINTOMA_MAX);
                int exploracion = obtenerDato(scanner, "Exploración (0-3)", EXPLORACION_MIN, EXPLORACION_MAX);
                int prioridad = obtenerDato(scanner, "Prioridad", PRIORIDAD_MIN, PRIORIDAD_MAX);
                int temperatura = obtenerDato(scanner, "Temperatura", TEMPERATURA_MIN, TEMPERATURA_MAX);

                pacientes.add(new Paciente(nuss, sintoma, exploracion, prioridad, temperatura));
                System.out.println("Paciente añadido correctamente.");
                return;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage() + " Intentos restantes: " + (MAX_ATTEMPTS - i - 1));
            }
        }
        System.out.println("Se agotaron los intentos para añadir un paciente.");
    }

    private static void mostrarPacientes() {
        System.out.println("\nLISTADO DE PACIENTES:");
        System.out.printf("%-7s | %-20s | %-25s | %-10s | %-10s\n", "NUSS", "Síntoma", "Exploración", "Prioridad", "Temperatura");
        for (Paciente p : pacientes) {
            System.out.printf("%-7d | %-20s | %-25s | %-10d | %-10d\n",
                    p.nuss, SINTOMAS[p.sintoma], EXPLORACIONES[p.sintoma][p.exploracion], p.prioridad, p.temperatura);
        }
    }

    private static void modificarPaciente(Scanner scanner) {
        if (pacientes.isEmpty()) {
            System.out.println("No hay pacientes registrados para modificar.");
            return;
        }

        System.out.print("Ingrese el NUSS del paciente a modificar: ");
        int nuss = scanner.nextInt();
        for (Paciente p : pacientes) {
            if (p.nuss == nuss) {
                System.out.println("Paciente encontrado. Modifique los campos (deje vacío para mantener el valor actual):");
                p.nuss = obtenerDatoOpcional(scanner, "NUSS", NUSS_MIN, NUSS_MAX, p.nuss);
                p.sintoma = obtenerDatoOpcional(scanner, "Síntoma (0: Dolor, 1: Lesión, 2: Fiebre, 3: Confusión)", SINTOMA_MIN, SINTOMA_MAX, p.sintoma);
                p.exploracion = obtenerDatoOpcional(scanner, "Exploración (0-3)", EXPLORACION_MIN, EXPLORACION_MAX, p.exploracion);
                p.prioridad = obtenerDatoOpcional(scanner, "Prioridad", PRIORIDAD_MIN, PRIORIDAD_MAX, p.prioridad);
                p.temperatura = obtenerDatoOpcional(scanner, "Temperatura", TEMPERATURA_MIN, TEMPERATURA_MAX, p.temperatura);
                System.out.println("Paciente modificado correctamente.");
                return;
            }
        }
        System.out.println("Paciente con NUSS " + nuss + " no encontrado.");
    }

    private static void eliminarPaciente(Scanner scanner) {
        if (pacientes.isEmpty()) {
            System.out.println("No hay pacientes registrados para eliminar.");
            return;
        }

        System.out.print("Ingrese el NUSS del paciente a eliminar: ");
        int nuss = scanner.nextInt();
        pacientes.removeIf(p -> p.nuss == nuss);
        System.out.println("Paciente eliminado correctamente, si existía.");
    }

    private static void ordenarPorPrioridad() {
        pacientes.sort(Comparator.comparingInt((Paciente p) -> p.prioridad).reversed());
        System.out.println("Pacientes ordenados por prioridad de mayor a menor.");
    }

    private static int obtenerDato(Scanner scanner, String campo, int min, int max) throws IllegalArgumentException {
        System.out.print(campo + " (" + min + "-" + max + "): ");
        if (scanner.hasNextInt()) {
            int valor = scanner.nextInt();
            if (valor >= min && valor <= max) {
                return valor;
            }
        } else {
            scanner.next();
        }
        throw new IllegalArgumentException("Entrada inválida para " + campo);
    }

    private static int obtenerDatoOpcional(Scanner scanner, String campo, int min, int max, int valorActual) {
        System.out.print(campo + " (" + min + "-" + max + ") [Actual: " + valorActual + "]: ");
        String input = scanner.next();
        if (input.isEmpty()) {
            return valorActual;
        }
        try {
            int nuevoValor = Integer.parseInt(input);
            if (nuevoValor >= min && nuevoValor <= max) {
                return nuevoValor;
            }
        } catch (NumberFormatException e) {
            // Ignorar
        }
        System.out.println("Valor inválido. Se mantendrá el valor actual.");
        return valorActual;
    }
}
