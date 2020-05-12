package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.services.VetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


class VetControllerTest {

    @Mock
    VetService vetService;

    VetController vetController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        vetController = new VetController(vetService);
        mockMvc = MockMvcBuilders.standaloneSetup(vetController).build();

    }

    @Test
    void listVets() throws Exception {

        //given
        Set<Vet> vets = new HashSet<>();
        Vet vet = new Vet();
        vet.setId(3L);
        vets.add(vet);

        //when
        when(vetService.findAll()).thenReturn(vets);

        ArgumentCaptor<Set<Vet>> ac = ArgumentCaptor.forClass(Set.class);

        //then
        mockMvc.perform(get("/vets"))
                .andExpect(status().isOk())
                .andExpect(view().name("vets/index"))
                .andExpect(model().attribute("vets", vets));

        verify(vetService, times(1)).findAll();


    }
}