package org.spring.data_jpa.controllers;

import jakarta.validation.Valid;
import org.spring.data_jpa.models.Reader;
import org.spring.data_jpa.services.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/readers")
public class ReaderController {

    private final ReaderService readerService;

    @Autowired
    public ReaderController(ReaderService readerService) {
        this.readerService = readerService;
    }

    @GetMapping
    public String showAllReaders(Model model) {
        model.addAttribute("readers", readerService.findAllReaders());
        return "readers/allReaders";
    }

    @GetMapping("/{id}")
    public String showOneReader(@PathVariable("id") int id, Model model) {
        model.addAttribute("oneReader", readerService.findReaderById(id));
        model.addAttribute("allBooksOnOneReader", readerService.getBooksByReaderId(id));
        return "readers/oneReader";
    }

    @GetMapping("/newReader")
    public String newReader(@ModelAttribute("newReader") Reader newReader) {
        return "readers/newReader";
    }

    @PostMapping
    public String createReader(@ModelAttribute("createReader") @Valid Reader reader, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "readers/newReader";
        }
        readerService.saveReader(reader);
        return "redirect:/readers";
    }

    @GetMapping("/{id}/editReader")
    public String editReader(Model model, @PathVariable("id") int id) {
        model.addAttribute("editReader", readerService.findReaderById(id));
        return "readers/editReader";
    }

    @PatchMapping("/{id}")
    public String updateReader(@ModelAttribute("upReader") @Valid Reader upReader, BindingResult bindingResult,
                               @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "readers/editReader";
        }
        readerService.updateReader(upReader, id);
        return "redirect:/readers";
    }

    @DeleteMapping("/{id}")
    public String deleteReader(@PathVariable("id") int id) {
        readerService.deleteReader(id);
        return "redirect:/readers";
    }
}