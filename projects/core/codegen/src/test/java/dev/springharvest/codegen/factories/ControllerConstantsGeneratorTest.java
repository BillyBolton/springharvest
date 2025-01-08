import com.squareup.javapoet.JavaFile;

import dev.springharvest.codegen.models.HarvestBO;
import dev.springharvest.codegen.utils.JavaPoetUtils;
import dev.springharvest.errors.constants.ExceptionMessages;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.annotation.processing.ProcessingEnvironment;

import static org.mockito.Mockito.*;

import dev.springharvest.codegen.factories.ControllerConstantsGenerator;
/* 
@ExtendWith(MockitoExtension.class)
public class ControllerConstantsGeneratorTest {

    @Mock
    private ProcessingEnvironment processingEnv;

    @Test
    void testPrivateConstructor_ThrowsUnsupportedOperationException() {
        Exception exception = assertThrows(UnsupportedOperationException.class, () -> {
            // Cannot instantiate the class
            ControllerConstantsGenerator generator = new ControllerConstantsGenerator();
        });
        assertEquals(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE, exception.getMessage());
    }

    @Test
    void testGenerate_WithValidInput_CreatesConstants() {
        // Prepare your inputs
        HarvestBO harvestBO = mock(HarvestBO.class);
        String rootPackageName = "dev.springharvest";
        String domainNameSingular = "TestDomain";
        String domainNamePlural = "TestDomains";
        TypeMirror typeMirror = mock(TypeMirror.class);

        // Call the method under test
        ControllerConstantsGenerator.generate(harvestBO, rootPackageName, domainNameSingular, domainNamePlural, typeMirror, processingEnv);

        // Verify the interaction
        verify(JavaPoetUtils.class, times(1)).writeJavaFile(any(JavaFile.class), eq(processingEnv));
    }

   
}
*/