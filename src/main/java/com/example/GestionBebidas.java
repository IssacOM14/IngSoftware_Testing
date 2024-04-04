package com.example;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GestionBebidas {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Bebida> listaBebidas = new ArrayList<>();

        System.out.println("Bienvenido al sistema de gestión de bebidas.");

        // Loop para ingresar datos de bebidas
        while (true) {
            System.out.println("\nIngrese el nombre de la bebida seguido de sus tamaños separados por comas:");
            System.out.println("(Ejemplo: Coca-Cola, 12, 16, 20)");
            String entrada = scanner.nextLine().replaceAll("\\s", ""); // Eliminar espacios en blanco
            String[] partes = entrada.split(",", 2);

            // Verificar si se ingresó "fin" para salir del loop
            if (partes[0].equalsIgnoreCase("fin")) {
                break;
            }

            // Verificar si se proporcionaron suficientes partes (nombre y tamaños)
            if (partes.length != 2) {
                System.out.println("Entrada inválida. Por favor, ingrese el nombre de la bebida y al menos un tamaño.");
                continue;
            }

            String nombre = partes[0];

            // Validar longitud del nombre
            if (nombre.length() < 2 || nombre.length() > 15) {
                System.out.println("El nombre de la bebida debe tener entre 2 y 15 caracteres.");
                continue;
            }

            // Validar que el nombre contenga solo caracteres alfabéticos
            if (!nombre.matches("[a-zA-Z]+")) {
                System.out.println("El nombre de la bebida debe contener solo caracteres alfabéticos.");
                continue;
            }

            String[] tamanosStr = partes[1].split(",");
            List<Integer> tamanos = new ArrayList<>();

            // Validar cantidad de tamaños
            if (tamanosStr.length > 5) {
                System.out.println("Se permiten como máximo 5 tamaños por bebida.");
                continue;
            }

            // Validar que los tamaños estén en orden ascendente y dentro del rango (1 - 48)
            int tamanoAnterior = 0;
            for (String tamanoStr : tamanosStr) {
                try {
                    int tamano = Integer.parseInt(tamanoStr);
                    if (tamano < 1 || tamano > 48) {
                        throw new NumberFormatException();
                    }
                    if (tamano <= tamanoAnterior) {
                        throw new IllegalArgumentException("Los tamaños deben estar en orden ascendente.");
                    }
                    tamanos.add(tamano);
                    tamanoAnterior = tamano;
                } catch (NumberFormatException e) {
                    System.out.println("Tamaño inválido: " + tamanoStr + ". Debe ser un valor entre 1 y 48.");
                    tamanos.clear();
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                    tamanos.clear();
                    break;
                }
            }

            // Crear objeto Bebida y agregarlo a la lista
            if (!tamanos.isEmpty()) {
                Bebida bebida = new Bebida(nombre, tamanos);
                listaBebidas.add(bebida);
                System.out.println("Bebida agregada con éxito.");
            }

            // Preguntar al usuario si desea agregar otra bebida
            System.out.println("¿Desea agregar otra bebida? (s/n): ");
            String respuesta = scanner.nextLine();
            if (!respuesta.equalsIgnoreCase("s")) {
                break;
            }
        }

        // Mostrar las bebidas almacenadas
        System.out.println("\nBebidas almacenadas:");
        for (Bebida bebida : listaBebidas) {
            System.out.println(bebida);
        }

        scanner.close();
    }
}

class Bebida {
    private String nombre;
    private List<Integer> tamanos;

    public Bebida(String nombre, List<Integer> tamanos) {
        this.nombre = nombre;
        this.tamanos = tamanos;
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + ", Tamaños: " + tamanos;
    }
}

