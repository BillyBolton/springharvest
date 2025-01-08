package dev.springharvest.testing.domains.integration.crud.domains.embeddables.traces.factories;

import dev.springharvest.shared.domains.embeddables.traces.dates.models.dtos.TraceDatesDTO;
import dev.springharvest.shared.domains.embeddables.traces.trace.models.dtos.TraceDataDTO;
import dev.springharvest.testing.domains.integration.crud.domains.embeddables.traces.factories.TraceDataModelFactory;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.*;

class TraceDataModelFactoryTest {

    private final TraceDataModelFactory factory = new TraceDataModelFactory();

    @Test
    void buildValidDto_returnsDtoWithCurrentUtcTimestamp() {
        TraceDataDTO dto = factory.buildValidDto();

        assertNotNull(dto);
        assertNotNull(dto.getTraceDates());
        assertEquals(LocalDate.ofInstant(Instant.now(), ZoneOffset.UTC), dto.getTraceDates().getDateCreated());
        assertEquals(LocalDate.ofInstant(Instant.now(), ZoneOffset.UTC), dto.getTraceDates().getDateUpdated());
    }

    @Test
    void buildValidUpdatedDto_updatesDateUpdated() {
        TraceDataDTO originalDto = factory.buildValidDto();
        TraceDataDTO updatedDto = factory.buildValidUpdatedDto(originalDto);

        assertNotNull(updatedDto);
        assertNotNull(updatedDto.getTraceDates());
        assertEquals(originalDto.getTraceDates().getDateCreated(), updatedDto.getTraceDates().getDateCreated());
        assertEquals(LocalDate.ofInstant(Instant.now(), ZoneOffset.UTC), updatedDto.getTraceDates().getDateUpdated());
    }

    @Test
    void buildInvalidDto_returnsDtoWithNullTraceDates() {
        TraceDataDTO dto = factory.buildInvalidDto();

        assertNotNull(dto);
        assertNull(dto.getTraceDates());
    }
}