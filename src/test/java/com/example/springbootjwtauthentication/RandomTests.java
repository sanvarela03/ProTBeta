package com.example.springbootjwtauthentication;

import org.junit.jupiter.api.Test;

import java.util.EnumSet;

public class RandomTests {

    @Test
    void randomTest1() {
        Fecha fecha = new Fecha(2, 4, 2020);
    }

    @Test
    void PruebaEmpleado() {
        Fecha nacimiento = new Fecha(7, 24, 1949);
        Fecha contratacion = new Fecha(3, 12, 1988);
        Empleado empleado = new Empleado("Bob", "Blue", nacimiento, contratacion);
        System.out.println(empleado);
    }

    @Test
    void PruebaEnum() {
        System.out.println("Todos los libros: ");

        for (Libro libro : Libro.values()) {
            System.out.printf("%-10s%-45s%s%n", libro, libro.obtenerTitulo(), libro.obtenerAnioCopyright());
        }

        System.out.printf("%nMostrar un rango de constantes enum: %n%n");

        for (Libro libro : EnumSet.range(Libro.JHTP, Libro.CPPHTP)) {
            System.out.printf("%-10s%-45s%s%n", libro, libro.obtenerTitulo(), libro.obtenerAnioCopyright());

        }


    }
}

class Fecha {
    private int mes;
    private int dia;
    private int anio;

    static final int[] diasPorMes = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public Fecha(int mes, int dia, int anio) {
        if (mes <= 0 || mes > 12) {
            throw new IllegalArgumentException("mes (" + mes + "debe ser 1-12");
        }

        if (dia <= 0 || (dia > diasPorMes[mes] && !(mes == 2 && dia == 29)))
            throw new IllegalArgumentException("dia (" + dia + ") fuera de rango para el mes y anio especificados");

        if (mes == 2 && dia == 29 && !(anio % 400 == 0 || (anio % 4 == 0 && anio % 100 != 0)))
            throw new IllegalArgumentException("dia (" + dia + ") fuera de rango para el mes y anio especificados");

        this.mes = mes;
        this.dia = dia;
        this.anio = anio;

        System.out.printf("Constructor de objeto Fecha para la fecha %s%n", this);
    }

    public String toString() {
        return String.format("%d/%d/%d", mes, dia, anio);
    }

}

class Empleado {
    private String nombre;
    private String apellido;
    private Fecha fechaNacimiento;
    private Fecha fechaContratacion;

    public Empleado(String nombre, String apellido, Fecha fechaNacimiento, Fecha fechaContratacion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaContratacion = fechaContratacion;
    }

    public String toString() {
        return String.format("%s, %s Contratado : %s Cumpleanios: %s", apellido, nombre, fechaContratacion, fechaNacimiento);
    }
}

enum Libro {
    JHTP("Java How to Program", "2015"),
    CHTOP("C. How to program", "2013"),
    IW3HTP("Internet &  World Wide Web How to program", "2012"),
    CPPHTP("C++ How to Program", "2014"),
    VBHTP("Visual Basic How to Program", "2014"),
    CSHARPHTP("Visual C# How to Program", "2014");

    private final String titulo;
    private final String anioCopyright;

    Libro(String titulo, String anioCopyright) {
        this.titulo = titulo;
        this.anioCopyright = anioCopyright;
    }

    public String obtenerTitulo() {
        return titulo;
    }

    public String obtenerAnioCopyright() {
        return anioCopyright;
    }
}

