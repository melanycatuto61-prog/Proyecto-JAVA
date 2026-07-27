package orenseguard_grupo8_proyecto;

import java.util.Scanner;

public class Orenseguard_grupo8_proyecto {


    public static Scanner sc = new Scanner(System.in);

   
    public static String[] nombreZona = {"Puerto Bolivar", "Centro", "Las Brisas", "El Cambio", "La Primavera"};
    public static double[] zonaX = {2.0, 5.0, 8.0, 10.0, 7.5};
    public static double[] zonaY = {1.5, 5.0, 6.5, 9.0, 2.0};
    public static int[] riesgoZona = new int[5];
    public static String[] estadoZona = {"Seguro", "Seguro", "Seguro", "Seguro", "Seguro"};
    public static int[] conteoZonas = new int[5];
    public static double[] promedioZona = new double[5];

    
    public static String[] nombreTipo = {"Robo", "Agresion", "Acoso", "Accidente", "Actividad sospechosa"};
    public static int[] puntosTipo = {30, 40, 25, 20, 10};
    public static int[][] matrizIncidentes = new int[5][5];
    public static int[] conteoHoras = new int[24];
    public static int totalReportes = 0;

 
    public static String[] patrullaNombre = {"Unidad 1", "Unidad 2", "Unidad 3", "Unidad 4", "Unidad 5", "Unidad 6", "Unidad 7", "Unidad 8", "Unidad 9", "Unidad 10"};
    public static double[] patrullaX = {5.5, 2.5, 9.0, 1.0, 3.0, 7.0, 9.0, 4.0, 6.0, 8.5};
    public static double[] patrullaY = {4.5, 2.0, 8.0, 2.0, 8.0, 1.0, 9.0, 5.0, 3.0, 4.0};
    public static boolean[] patrullaLibre = {true, true, true, true, true, true, true, true, true, true};


    public static int lastZona = -1, lastTipo = -1, lastHora = -1, lastAfectados = -1;
    public static boolean lastArma = false, lastHeridos = false;

//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public static int calcularIndice(int puntoBase, int hora, int afectados, boolean arma) {
        int indice = puntoBase;
        
        if (arma) {
            indice += 30;
        }
        if (hora >= 19 || hora < 6) {
            indice += 15;
        }
        if (afectados > 1) {
            indice += 20;
        }
        return indice;
    }

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public static String clasificarNivel(double indice) {
        if (indice <= 30) {
            return "Seguro";
        } else {
            if (indice <= 60) {
                return "Precaucion";
            } else {
                if (indice <= 80) {
                    return "Zona roja";
                } else {
                    return "Critico";
                }
            }
        }
    }

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public static double calcularDistancia(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public static void asignarPatrulla(double zonaX, double zonaY) {
        int patrullaIdeal = -1;
        double distMinima = 999999;


        for (int i = 0; i < patrullaLibre.length; i++) {
            if (patrullaLibre[i]) {
                double distActual = calcularDistancia(zonaX, zonaY, patrullaX[i], patrullaY[i]);
                if (distActual < distMinima) {
                    distMinima = distActual;
                    patrullaIdeal = i;
                }
            }
        }

        if (patrullaIdeal != -1) {
            System.out.println(">> UNIDAD ASIGNADA: " + patrullaNombre[patrullaIdeal]);
            System.out.printf(">> Enviando patrulla mas cercana a %.2f km.\n", distMinima);
            System.out.println(">> ESTADO DEL REPORTE: En atencion");
            patrullaLibre[patrullaIdeal] = false;
        } else {
            System.out.println(">> ¡ALERTA! Todas las unidades estan ocupadas en otros incidentes.");
            System.out.println(">> ESTADO DEL REPORTE: Pendiente (En espera de unidades)");
        }
    }

//--------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public static void registrarIncidente() {
        int zona, hora, afectados, posTipo, op;
        boolean arma, heridos;


        do {
            System.out.println("=== SELECCIONE LA ZONA ===");
         
            for (int k = 0; k < nombreZona.length; k++) {
                System.out.println((k + 1) + ". " + nombreZona[k]);
            }
            System.out.print("Ingrese numero de la zona (1-5): ");
            zona = sc.nextInt() - 1;
            if (zona < 0 || zona > 4) {
                System.out.println(">> ERROR: Zona invalida. Intente de nuevo.\n");
            }
        } while (zona < 0 || zona > 4);

       
        do {
            System.out.println("=== TIPO DE INCIDENTE ===");
           
            for (int k = 0; k < nombreTipo.length; k++) {
                System.out.println((k + 1) + ". " + nombreTipo[k]);
            }
            System.out.print("Ingrese numero del tipo (1-5): ");
            posTipo = sc.nextInt() - 1;
            if (posTipo < 0 || posTipo > 4) {
                System.out.println(">> ERROR: Tipo invalido. Intente de nuevo.\n");
            }
        } while (posTipo < 0 || posTipo > 4);

       
        do {
            System.out.print("Hora del incidente (0 a 23): ");
            hora = sc.nextInt();
            if (hora < 0 || hora > 23) {
                System.out.println(">> ERROR: Hora invalida. Intente de nuevo.");
            }
        } while (hora < 0 || hora > 23);

       
        do {
            System.out.print("Numero de personas afectadas: ");
            afectados = sc.nextInt();
            if (afectados <= 0) {
                System.out.println(">> ERROR: El numero de personas afectadas debe ser mayor a 0. Intente de nuevo.");
            }
        } while (afectados <= 0);

  
        do {
            System.out.print("Hubo uso de arma? 1.Si 2.No: ");
            op = sc.nextInt();
        } while (op != 1 && op != 2);
        arma = (op == 1);

    
        do {
            System.out.print("Hay personas heridas? 1.Si 2.No: ");
            op = sc.nextInt();
        } while (op != 1 && op != 2);
        heridos = (op == 1);

     
        if (zona == lastZona && posTipo == lastTipo && hora == lastHora && afectados == lastAfectados && arma == lastArma && heridos == lastHeridos) {
            System.out.println("\n>> ERROR: Este reporte ya fue registrado anteriormente. No se permiten duplicados.");
            return; 
        }

     
        lastZona = zona; lastTipo = posTipo; lastHora = hora; lastAfectados = afectados; lastArma = arma; lastHeridos = heridos;


        matrizIncidentes[zona][posTipo]++;
        totalReportes++;
        conteoHoras[hora]++;

        int indice = calcularIndice(puntosTipo[posTipo], hora, afectados, arma);
        String nivel = clasificarNivel(indice);

        System.out.println("--------------------------------");
        System.out.println("Indice de riesgo calculado: " + indice + " puntos.");
        System.out.println("Clasificacion del incidente: " + nivel);
        System.out.println("--------------------------------");
        System.out.println("REPORTE MATEMATICO DEL INCIDENTE:");
        System.out.println("+ " + puntosTipo[posTipo] + " puntos (Por ser " + nombreTipo[posTipo] + ")");
        if (arma) System.out.println("+ 30 puntos (Por uso de arma)");
        if (hora >= 19 || hora < 6) System.out.println("+ 15 puntos (Por horario nocturno/madrugada)");
        if (afectados > 1) System.out.println("+ 20 puntos (Por multiples afectados)");
        System.out.println("TOTAL = " + indice + " puntos.");
        System.out.println("--------------------------------");

  
        if (arma || heridos) {
            System.out.println("PRIORIDAD ALTA: Se requiere atencion inmediata. Buscando patrulla...");
            asignarPatrulla(zonaX[zona], zonaY[zona]);
        } else {
            System.out.println("PRIORIDAD MEDIA: Sin arma ni heridos, se registra para estadisticas.");
            System.out.println(">> ESTADO DEL REPORTE: Atendido (Solo registro)");
        }
        System.out.println("--------------------------------");

   
        riesgoZona[zona] += indice;
        conteoZonas[zona]++;
        promedioZona[zona] = (riesgoZona[zona] * 1.0) / conteoZonas[zona];
        estadoZona[zona] = clasificarNivel(promedioZona[zona]);
    }

//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public static void mostrarMatrizYEstadisticas() {
        System.out.println("=================================================================================================");
        System.out.println("                      MATRIZ DE INCIDENTES Y PROMEDIOS POR ZONA");
        System.out.println("=================================================================================================");
        System.out.print("ZONA            ");
        System.out.print("Robo          ");
        System.out.print("Agresion      ");
        System.out.print("Acoso         ");
        System.out.print("Accidente     ");
        System.out.print("Act. Sosp.    ");
        System.out.println("PROMEDIO");
        System.out.println("-------------------------------------------------------------------------------------------------");

    
        for (int i = 0; i < nombreZona.length; i++) {
            String nombreFormateado = String.format("%-16s", nombreZona[i]);
            System.out.print(nombreFormateado);
            for (int j = 0; j < matrizIncidentes[i].length; j++) {
                System.out.printf("%-14d", matrizIncidentes[i][j]);
            }
            System.out.printf("%.2f\n", promedioZona[i]);
        }
        System.out.println("--------------------------------------------------------------------------------------------------");

        if (totalReportes > 0) {
            System.out.println("=================================================================================================");
            System.out.println("                              ESTADISTICAS ADICIONALES (PORCENTAJES Y PICOS)");
            System.out.println("=================================================================================================");

            int maxDelito = -1, tipoFrecuente = 0;
        
            for (int j = 0; j < nombreTipo.length; j++) {
                int sumaDelito = 0;
                for (int i = 0; i < matrizIncidentes.length; i++) {
                    sumaDelito += matrizIncidentes[i][j];
                }

                if (sumaDelito > maxDelito) {
                    maxDelito = sumaDelito;
                    tipoFrecuente = j;
                }

                double porc = (sumaDelito * 100.0) / totalReportes;
                System.out.printf(">> Porcentaje de %s: %.2f%%\n", nombreTipo[j], porc);
            }

            int maxHora = -1, horaFrecuente = 0;
        
            for (int i = 0; i < conteoHoras.length; i++) {
                if (conteoHoras[i] > maxHora) {
                    maxHora = conteoHoras[i];
                    horaFrecuente = i;
                }
            }

            System.out.println("--------------------------------------------------------------------------------------------------");
            System.out.println(">> INCIDENCIA MAYOR (DELITO): " + nombreTipo[tipoFrecuente] + " con " + maxDelito + " casos registrados.");
            System.out.println(">> INCIDENCIA MAYOR (HORARIO): " + horaFrecuente + ":00 hrs con " + maxHora + " casos registrados.");
            System.out.println("=================================================================================================");
        } else {
            System.out.println("No existen reportes registrados en el sistema para generar estadisticas.");
        }
    }

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public static void mostrarEstadoZonas() {
        String[] tempNombre = new String[5];
        double[] tempRiesgo = new double[5];
        String[] tempEstado = new String[5];

    
        for (int i = 0; i < nombreZona.length; i++) {
            tempNombre[i] = nombreZona[i];
            tempRiesgo[i] = promedioZona[i];
            tempEstado[i] = estadoZona[i];
        }

 
        for (int i = 0; i < tempRiesgo.length - 1; i++) {
            for (int j = i + 1; j < tempRiesgo.length; j++) {
                if (tempRiesgo[i] < tempRiesgo[j]) {
                    double auxRiesgo = tempRiesgo[i];
                    tempRiesgo[i] = tempRiesgo[j];
                    tempRiesgo[j] = auxRiesgo;

                    String auxNombre = tempNombre[i];
                    tempNombre[i] = tempNombre[j];
                    tempNombre[j] = auxNombre;

                    String auxEstado = tempEstado[i];
                    tempEstado[i] = tempEstado[j];
                    tempEstado[j] = auxEstado;
                }
            }
        }

        System.out.println("==========================================================");
        System.out.println("       ESTADO ACTUALIZADO DE ZONAS      ");
        System.out.println("==========================================================");
       
        for (int i = 0; i < tempNombre.length; i++) {
            System.out.printf("%s -> Riesgo Promedio: %.2f | Estado: %s\n", tempNombre[i], tempRiesgo[i], tempEstado[i]);
        }
        System.out.println("==========================================================");
    }

//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public static void limpiarConsola() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        for (int i = 0; i < 15; i++) System.out.println(); 
    }
//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public static void esperarTecla() {
        System.out.println("\nPresione ENTER para retornar al menu principal...");
        try {
            System.in.read();
            sc.nextLine(); 
        } catch (Exception e) {}
    }

//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public static void main(String[] args) {
        boolean continuar = true;

        while (continuar) {
            System.out.println("===========================================");
            System.out.println("      SISTEMA ORENSEGUARD - UTMACH         ");
            System.out.println("===========================================");
            System.out.println("1. Registrar nuevo incidente y asignar recurso");
            System.out.println("2. Ver estado actual de las zonas");
            System.out.println("3. Ver matriz de delitos y estadisticas");
            System.out.println("4. Liberar unidades de patrullaje");
            System.out.println("5. SALIR DEL SISTEMA");
            System.out.print("Elija una opcion: ");
            
            int opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    limpiarConsola();
                    registrarIncidente();
                    esperarTecla();
                    limpiarConsola();
                    break;
                case 2:
                    limpiarConsola();
                    mostrarEstadoZonas();
                    esperarTecla();
                    limpiarConsola();
                    break;
                case 3:
                    limpiarConsola();
                    mostrarMatrizYEstadisticas();
                    esperarTecla();
                    limpiarConsola();
                    break;
                case 4:
                    limpiarConsola();
                    for (int i = 0; i < patrullaLibre.length; i++) {
                        patrullaLibre[i] = true;
                    }
                    System.out.println(">> NOTIFICACIÓN: Todas las unidades han sido liberadas y se encuentran operativas.");
                    esperarTecla();
                    limpiarConsola();
                    break;
                case 5:
                    limpiarConsola();
                    continuar = false;
                    System.out.println("Finalizando ejecucion del sistema ORENSEGUARD...");
                    break;
                default:
                    System.out.println(">> ERROR: Opción no valida. Por favor, intente nuevamente.");
            }
        }
    }
}