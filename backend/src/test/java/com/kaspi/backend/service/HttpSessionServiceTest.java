package com.kaspi.backend.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.kaspi.backend.dao.UserDao;
import com.kaspi.backend.domain.GasStationDto;
import com.kaspi.backend.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;

import com.kaspi.backend.dto.FindGasStationResDto;
import com.kaspi.backend.enums.GasBrand;
import com.kaspi.backend.util.exception.AuthenticationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class HttpSessionServiceTest {

    @Mock
    private HttpSession httpSession;
    @Mock
    private AuthService authService;
    @Mock
    private UserDao userDao;
    @InjectMocks
    private HttpSessionService httpSessionService;


    @Test
    @DisplayName("세션 생성 로직 테스트")
    public void testMakeHttpSession() {
        // Given
        Long userId = 1L;
        // When
        httpSessionService.makeHttpSession(userId);
        // Then
        verify(httpSession).setAttribute(HttpSessionService.SESSION_KEY, userId);
    }

    @Test
    @DisplayName("세션으로부터 유저를 가지고 오는 로직 테스트")
    void getUserFromSession() {
        // Given
        Long userNo = 1L;
        when(httpSession.getAttribute(HttpSessionService.SESSION_KEY)).thenReturn(userNo);
        User user = User.builder().userNo(userNo).build();
        when(userDao.findById(userNo)).thenReturn(Optional.of(user));

        // When
        User resultUser = httpSessionService.getUserFromSession();

        // Then
        verify(httpSession).getAttribute(HttpSessionService.SESSION_KEY);
        verify(userDao).findById(userNo);
        assertThat(user).isEqualTo(resultUser);
    }

    @Test
    @DisplayName("세션이 만료된경우 테스트")
    public void testGetUserFromSessionInvalidSession() {
        //given,when
        when(httpSession.isNew()).thenReturn(true);
        //then
        assertThrows(AuthenticationException.class, () -> httpSessionService.getUserFromSession());
    }

    @Test
    @DisplayName("세션 삭제 로직 테스트")
    void deleteSession() {
        //given
        httpSessionService.makeHttpSession(1L);
        when(httpSession.getAttribute("userNo")).thenReturn(1L);
        when(userDao.findById(1L)).thenReturn(Optional.empty());
        when(authService.checkNotValidUser(Optional.empty())).thenThrow(AuthenticationException.class);
        //when
        httpSessionService.deleteSession();
        //then
        assertThrows(AuthenticationException.class, () -> httpSessionService.getUserFromSession());
    }

    @Test
    @DisplayName("최근본 주유소 session 객체에 저장 테스트")
    void addRecentStationView() {
        //given
        String SESSION_RECENT_VIEW_STATION_KEY = "recentViewStations";
        GasStationDto gasStationDto = GasStationDto.builder().stationNo(1L).brand(GasBrand.SK_GAS.getDbName()).name("주유소").address("주소").area("지역").build();
        List<FindGasStationResDto> recentGasStation = new ArrayList<>();
        recentGasStation.add(FindGasStationResDto.toFindDto(gasStationDto));
        when(httpSession.getAttribute(SESSION_RECENT_VIEW_STATION_KEY)).thenReturn(recentGasStation);
        //when
        httpSessionService.addRecentStationView(gasStationDto);
        //then
        verify(httpSession).setAttribute(SESSION_RECENT_VIEW_STATION_KEY,recentGasStation);
    }
}
