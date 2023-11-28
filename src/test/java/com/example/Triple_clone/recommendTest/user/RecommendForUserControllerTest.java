package com.example.Triple_clone.recommendTest.user;

import com.example.Triple_clone.controller.recommend.user.RecommendForUserController;
import com.example.Triple_clone.service.recommend.user.RecommendForUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RecommendForUserController.class)
public class RecommendForUserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecommendForUserService service;

    @Test
    void Controller_레이어_장소_전체_조회_이름순_테스트() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/recommend/user/all")
                        .param("orderType", "name"))
                .andExpect(status().isOk());
    }

    @Test
    void Controller_레이어_장소_전체_조회_날짜순_테스트() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/recommend/user/all"))
                .andExpect(status().isOk());
    }

    @Test
    void Controller_레이어_장소_단일_조회_테스트() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/recommend/user")
                        .param("placeId", "1")
                        .param("userId", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void Controller_레이어_리뷰_작성_테스트() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/recommend/user/review")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":\"1\", \"placeId\":\"1\", \"content\":\"test\", \"image\":\"test\"}"))
                .andExpect(status().isNoContent());
    }

    @Test
    void Controller_레이어_좋아요_테스트() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/recommend/user/like")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":\"1\", \"placeId\":\"1\"}"))
                .andExpect(status().isNoContent());
    }

    @Test
    void Controller_레이어_장소_내_여행에_추가_테스트() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/recommend/user/redirect/plan")
                        .param("target", "test")
                        .param("placeId", "1"))
                .andExpect(status().is2xxSuccessful());
    }
}