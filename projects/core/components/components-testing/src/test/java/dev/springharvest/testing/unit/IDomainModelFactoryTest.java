package dev.springharvest.testing.unit;

import dev.springharvest.shared.domains.DomainModel;
import dev.springharvest.shared.domains.embeddables.traces.dates.models.dtos.TraceDatesDTO;
import dev.springharvest.shared.domains.embeddables.traces.trace.models.dtos.TraceDataDTO;
import dev.springharvest.shared.domains.embeddables.traces.traceable.models.dtos.ITraceableDTO;
import dev.springharvest.shared.domains.embeddables.traces.users.models.dtos.AbstractTraceUsersDTO;
import dev.springharvest.testing.domains.integration.shared.domains.base.factories.IDomainModelFactory;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.error.AssertJMultipleFailuresError;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.Assert.assertThrows;

public class IDomainModelFactoryTest {


    IDomainModelFactory<DomainModel> domainModelFactory = new IDomainModelFactory<>() {
        @Override
        public DomainModel buildValidDto() {
            DomainModel validModel = new DomainModel() {
                @Override
                public boolean isEmpty() {
                    return false;
                }
            };

            return validModel;
        }

        @Override
        public DomainModel buildValidUpdatedDto(DomainModel dto) {

            DomainModel updatedModel = new DomainModel() {
                @Override
                public boolean isEmpty() {
                    return false;
                }
            };

            return updatedModel;
        }

        @Override
        public DomainModel buildInvalidDto() {
            DomainModel invalidModel = new DomainModel() {
                @Override
                public boolean isEmpty() {
                    return false;
                }
            };

            return invalidModel;
        }
    };



    @Test
    void shouldSoftlyAssertNonNullObjects() {
        SoftAssertions softly = new SoftAssertions();
        domainModelFactory.softlyAssert(softly, "actual", "actual");
        softly.assertAll();
    }
    @Test
    void shouldAssertEqualStrings() {
        SoftAssertions softly = new SoftAssertions();
        domainModelFactory.softlyAssert(softly, "testString", "testString");
        softly.assertAll(); // Should pass as both strings are equal
    }

    @Test
    void shouldAssertEqualStringsWithCaseInsensitivity() {
        SoftAssertions softly = new SoftAssertions();
        domainModelFactory.softlyAssert(softly, "testString", "TestString");
        softly.assertAll(); // Should pass as StringUtils.capitalizeFirstLetters would make both strings equal
    }

    @Test
    void shouldAssertDifferentStrings() {
        SoftAssertions softly = new SoftAssertions();

        assertThrows(AssertJMultipleFailuresError.class, () -> {
            domainModelFactory.softlyAssert(softly, "testString", "anotherString");
            softly.assertAll(); // Should throw an exception due to assertion failure
        });
    }

    @Test
    void shouldNotAssertWhenActualIsNull() {
        SoftAssertions softly = new SoftAssertions();
        domainModelFactory.softlyAssert(softly, null, "notNull");
        softly.assertAll(); // Should pass as the method does not assert when actual is null
    }

    @Test
    void shouldNotAssertWhenExpectedIsNull() {
        SoftAssertions softly = new SoftAssertions();
        domainModelFactory.softlyAssert(softly, "notNull", null);
        softly.assertAll();
    }

    @Test
    void shouldNotAssertWhenBothAreNull() {
        SoftAssertions softly = new SoftAssertions();
        domainModelFactory.softlyAssert(softly, (DomainModel) null, (DomainModel) null);
        softly.assertAll();
    }

    @Test
    void shouldAssertNonStringObjects() {
        SoftAssertions softly = new SoftAssertions();
        domainModelFactory.softlyAssert(softly, 123, 123);
        softly.assertAll(); // Should pass as the integers are equal
    }

    @Test
    void shouldAssertDifferentNonStringObjects() {
        SoftAssertions softly = new SoftAssertions();

        assertThrows(AssertJMultipleFailuresError.class, () -> {
            domainModelFactory.softlyAssert(softly, 123, 456); // Different integers
            softly.assertAll(); // Should throw an exception due to assertion failure
        });
    }

    @Test
    void softlyAssertWithNullActual() {
        SoftAssertions softly = new SoftAssertions();
        domainModelFactory.softlyAssert(softly, null, new DomainModel() {
            @Override
            public boolean isEmpty() {
                return false;
            }
        });
        softly.assertAll();
    }

    @Test
    void softlyAssertWithNullExpected() {
        SoftAssertions softly = new SoftAssertions();
        domainModelFactory.softlyAssert(softly, new DomainModel() {
            @Override
            public boolean isEmpty() {
                return false;
            }
        }, null);
        softly.assertAll();
    }

    @Test
    void softlyAssertWithBothNull() {
        SoftAssertions softly = new SoftAssertions();
        domainModelFactory.softlyAssert(softly, (DomainModel) null, (DomainModel) null);
        softly.assertAll();
    }

    @Test
    void softlyAssertWithNonTraceableDTOs() {
        SoftAssertions softly = new SoftAssertions();
        DomainModel actual = new DomainModel() {
            @Override
            public boolean isEmpty() {
                return false;
            }
        };

        DomainModel expected = new DomainModel() {
            @Override
            public boolean isEmpty() {
                return false;
            }
        };

        domainModelFactory.softlyAssert(softly, actual, expected);
        softly.assertAll();
    }
}
