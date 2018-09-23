package com.github.monosoul.fortuneteller.domain;

import com.github.monosoul.fortuneteller.da.FortuneResponseRepository;
import com.github.monosoul.fortuneteller.da.PersonalDataRepository;
import com.github.monosoul.fortuneteller.model.FortuneRequest;
import com.github.monosoul.fortuneteller.model.FortuneResponse;
import com.github.monosoul.fortuneteller.model.PersonalData;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class GlobaTest {


    @Mock
    private FortuneResponseRepository responseRepository;
    @Mock
    private Function<FortuneRequest, PersonalData> personalDataExtractor;
    @Mock
    private PersonalDataRepository personalDataRepository;

    @Mock
    private FortuneRequest request;
    @Mock
    private PersonalData personalData;
    @Mock
    private FortuneResponse response;

    @BeforeEach
    void setUp() {
        initMocks(this);

        when(responseRepository.get()).thenReturn(response);
        when(personalDataExtractor.apply(request)).thenReturn(personalData);
    }

    @Test
    void tell() {
        val actual = new Globa(responseRepository, personalDataExtractor, personalDataRepository).tell(request);

        verify(personalDataExtractor).apply(request);
        verify(personalDataRepository).save(personalData);
        verify(responseRepository).get();
        verifyNoMoreInteractions(responseRepository, personalDataExtractor, personalDataRepository);

        assertThat(actual).isSameAs(response);
    }
}