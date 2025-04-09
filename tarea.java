import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Interfaz Tarea
interface Tarea {
    void ejecutar();
    String getDescripcion();
}

// TareaSimple
class TareaSimple implements Tarea {
    private String descripcion;

    public TareaSimple(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public void ejecutar() {
        System.out.println(descripcion + " hecha");
    }

    @Override
    public String getDescripcion() {
        return descripcion;
    }
}

// TareaCompuesta
class TareaCompuesta implements Tarea {
    private String descripcion;
    private List<Tarea> subtareas = new ArrayList<>();

    public TareaCompuesta(String descripcion) {
        this.descripcion = descripcion;
    }

    public void agregarSubtarea(Tarea tarea) {
        subtareas.add(tarea);
    }

    @Override
    public void ejecutar() {
        System.out.println("Ejecutando tarea compuesta: " + descripcion);
        for (Tarea tarea : subtareas) {
            tarea.ejecutar();
        }
    }

    @Override
    public String getDescripcion() {
        return descripcion;
    }
}

// Factory de Tareas
class TareaFactory {
    public static Tarea crearTarea(String tipo, String descripcion) {
        switch (tipo.toLowerCase()) {
            case "simple":
                return new TareaSimple(descripcion);
            case "compuesta":
                return new TareaCompuesta(descripcion);
            default:
                throw new IllegalArgumentException("Tipo de tarea no válido");
        }
    }
}

// Facade de Gestión de Tareas
class GestorTareas {
    private List<Tarea> tareas = new ArrayList<>();

    public void agregarTarea(Tarea tarea) {
        tareas.add(tarea);
    }

    public void mostrarTareas() {
        System.out.println("\n--- Lista de Tareas ---");
        for (int i = 0; i < tareas.size(); i++) {
            System.out.println((i + 1) + ". " + tareas.get(i).getDescripcion());
        }
    }

    public void ejecutarTarea(int indice) {
        if (indice >= 1 && indice <= tareas.size()) {
            tareas.get(indice - 1).ejecutar();
        } else {
            System.out.println("Índice de tarea no válido");
        }
    }
}

// Menú principal
public class SistemaTareas {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GestorTareas gestor = new GestorTareas();
        
        while (true) {
            System.out.println("\n=== Menú de Gestión de Tareas ===");
            System.out.println("1. Crear tarea simple");
            System.out.println("2. Crear tarea compuesta");
            System.out.println("3. Mostrar todas las tareas");
            System.out.println("4. Ejecutar tarea");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            
            int opcion = scanner.nextInt();
            scanner.nextLine();  // Limpiar buffer
            
            switch (opcion) {
                case 1:
                    System.out.print("Ingrese descripción de la tarea simple: ");
                    String descSimple = scanner.nextLine();
                    gestor.agregarTarea(TareaFactory.crearTarea("simple", descSimple));
                    break;
                    
                case 2:
                    System.out.print("Ingrese descripción de la tarea compuesta: ");
                    String descCompuesta = scanner.nextLine();
                    TareaCompuesta tareaComp = (TareaCompuesta) TareaFactory.crearTarea("compuesta", descCompuesta);
                    
                    System.out.print("¿Cuántas subtareas desea agregar? ");
                    int numSubtareas = scanner.nextInt();
                    scanner.nextLine();
                    
                    for (int i = 0; i < numSubtareas; i++) {
                        System.out.print("Ingrese descripción de la subtarea " + (i + 1) + ": ");
                        String descSubtarea = scanner.nextLine();
                        tareaComp.agregarSubtarea(new TareaSimple(descSubtarea));
                    }
                    gestor.agregarTarea(tareaComp);
                    break;
                    
                case 3:
                    gestor.mostrarTareas();
                    break;
                    
                case 4:
                    gestor.mostrarTareas();
                    System.out.print("Ingrese el número de la tarea a ejecutar: ");
                    int numTarea = scanner.nextInt();
                    gestor.ejecutarTarea(numTarea);
                    break;
                    
                case 5:
                    System.out.println("Saliendo del sistema...");
                    scanner.close();
                    System.exit(0);
                    
                default:
                    System.out.println("Opción no válida");
            }
        }
    }
}