package com.example.Triple_clone.web.controller.recommend.user;

import com.example.Triple_clone.dto.recommend.user.RecommendReadDto;
import com.example.Triple_clone.service.recommend.user.RecommendService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RecommendController.class)
public class RecommendControllerTest {

    @MockBean
    private RecommendService recommendService;

    @Autowired
    private MockMvc mockMvc;

    private Pageable pageable;

    private Page<RecommendReadDto> placePage() {
        List<RecommendReadDto> placeList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            placeList.add(new RecommendReadDto(i, "test" + i, "test", "test", "test", "test", LocalDateTime.now(), true));
        }

        return new PageImpl<>(placeList);
    }

    @Test
    void Controller_레이어_장소_전체_조회_이름순_테스트() throws Exception {
        //given
        when(recommendService.findAll("name", pageable)).thenReturn(placePage());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/recommend/places")
                        .param("sort","name"))
                .andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    void Controller_레이어_장소_전체_조회_날짜순_테스트() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/recommend/places"))
                .andExpect(status().isOk());
    }

    @Test
    void Controller_레이어_장소_단일_조회_테스트() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/recommend/place")
                        .param("placeId", "1")
                        .param("userId", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void Controller_레이어_좋아요_테스트() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/recommend/place/like")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":\"1\", \"placeId\":\"1\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void Controller_레이어_장소_내_여행에_추가_테스트() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/recommend/user/plan")
                        .param("target", "test")
                        .param("placeId", "1"))
                .andExpect(status().is2xxSuccessful());
    }
}
