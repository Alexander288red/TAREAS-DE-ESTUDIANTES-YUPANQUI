/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TAREAS.DE.ESTUDIANTES.controller;

import com.example.TAREAS.DE.ESTUDIANTES.model.Tarea;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // Usa el Model de Spring
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/tareas")
public class TareaController {

    private final List<Tarea> tareas = new ArrayList<>();
    private Long contadorId = 1L;

    // Mostrar todas las tareas
    @GetMapping
    public String listarTareas(Model model) {
        model.addAttribute("tareas", tareas);
        return "listar-tareas";
    }

    // Mostrar formulario para crear una nueva tarea
    @GetMapping("/nueva")
    public String mostrarFormularioNuevaTarea(Model model) {
        model.addAttribute("tarea", new Tarea());
        return "nueva-tarea";
    }

    // Guardar nueva tarea
    @PostMapping
    public String guardarTarea(@ModelAttribute Tarea tarea) {
        tarea.setId(contadorId++);
        tareas.add(tarea);
        return "redirect:/tareas";
    }

    // Mostrar formulario para editar una tarea
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarTarea(@PathVariable Long id, Model model) {
        Tarea tarea = tareas.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (tarea != null) {
            model.addAttribute("tarea", tarea);
            return "editar-tarea";
        }
        return "redirect:/tareas";
    }

    // Actualizar tarea
    @PostMapping("/actualizar/{id}")
    public String actualizarTarea(@PathVariable Long id, @ModelAttribute Tarea tareaActualizada) {
        tareas.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .ifPresent(tarea -> {
                    tarea.setTitulo(tareaActualizada.getTitulo());
                    tarea.setDescripcion(tareaActualizada.getDescripcion());
                    tarea.setCompletada(tareaActualizada.isCompletada());
                });
        return "redirect:/tareas";
    }

    // Eliminar tarea
    @GetMapping("/eliminar/{id}")
    public String eliminarTarea(@PathVariable Long id) {
        tareas.removeIf(t -> t.getId().equals(id));
        return "redirect:/tareas";
    }
}