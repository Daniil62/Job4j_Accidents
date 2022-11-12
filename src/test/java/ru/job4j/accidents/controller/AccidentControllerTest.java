package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.Main;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.service.AccidentDataService;
import ru.job4j.accidents.service.AccidentTypeDataService;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
public class AccidentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccidentDataService service;

    @MockBean
    private AccidentTypeDataService typeService;

    @Test
    @WithMockUser
    public void shouldReturnDefaultMessage() throws Exception {
        mockMvc.perform(get("/index"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    @WithMockUser
    public void shouldReturn404() throws Exception {
        mockMvc.perform(get("/something"))
                .andDo(print())
                .andExpect(status().is(404));
    }

    @Test
    @WithMockUser
    public void whenCreateFormCalled() throws Exception {
        mockMvc.perform(get("/createAccidentForm"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("createAccident"));
    }

    @Test
    @WithMockUser
    public void whenUpdateFormCalled() throws Exception {
        when(service.get(1)).thenReturn(Optional.of(new Accident()));
        mockMvc.perform(get("/updateAccidentForm")
                        .param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("editAccident"));
    }

    @Test
    @WithMockUser
    public void whenAccidentCreated() throws Exception {
        AccidentType type = new AccidentType(1, "first_type");
        when(typeService.get(1)).thenReturn(Optional.of(type));
        Accident accident = new Accident(0, "new_accident",
                "some_text", "some_address", type);
        mockMvc.perform(post("/saveAccident")
                .flashAttr("accident", accident))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        ArgumentCaptor<Accident> argument = ArgumentCaptor.forClass(Accident.class);
        verify(service).create(argument.capture());
        assertThat(argument.getValue().getName(), is("new_accident"));
    }

    @Test
    @WithMockUser
    public void whenAccidentUpdated() throws Exception {
        AccidentType type = new AccidentType(1, "first_type");
        when(typeService.get(1)).thenReturn(Optional.of(type));
        Accident accident = new Accident(1, "new_accident",
                "some_text", "some_address", type);
        mockMvc.perform(post("/saveAccident")
                        .flashAttr("accident", accident))
                .andDo(print());
        accident.setName("updated_accident");
        mockMvc.perform(post("/updateAccident")
                        .flashAttr("accident", accident))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        ArgumentCaptor<Accident> argument = ArgumentCaptor.forClass(Accident.class);
        verify(service).create(argument.capture());
        assertThat(argument.getValue().getName(), is("updated_accident"));
    }
}