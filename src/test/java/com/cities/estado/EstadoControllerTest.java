package com.cities.estado;

import com.cities.controller.EstadoController;
import com.cities.model.Estado;
import com.cities.model.Pais;
import com.cities.repository.EstadoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(EstadoController.class)
public class EstadoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EstadoRepository repository;

    @Test
    public void shouldReturnStates() throws Exception {
        Estado estado = new Estado(1L, "São Paulo", "SP", 35, new Pais(), asList(1, 2, 3));

        PageRequest page = PageRequest.of(0, 20, Sort.unsorted());

        when(repository.findAll(page)).thenReturn(new PageImpl<>(asList(estado)));

        mockMvc.perform(get("/estados").accept("application/json;charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.content.[0].nome", is("São Paulo")))
                .andExpect(jsonPath("$.content.[0].uf", is("SP")));
    }

}