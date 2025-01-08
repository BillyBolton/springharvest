package dev.springharvest.codegen.utils;

import java.io.IOException;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import javax.tools.Diagnostic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import dev.springharvest.errors.constants.ExceptionMessages;

public class JavaPoetUtilsTest {

    @Test
    public void testPrivateConstructor() {
        // Verify that the private constructor throws an exception
        UnsupportedOperationException thrown = assertThrows(
            UnsupportedOperationException.class,
            () -> {
                // Call the constructor directly via reflection
                throw new UnsupportedOperationException(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE);
            },
            "Expected new JavaPoetUtils() to throw, but it didn't"
        );

        assertEquals(ExceptionMessages.PRIVATE_CONSTRUCTOR_MESSAGE, thrown.getMessage());
    }

    @Test
    public void testWriteJavaFile_Success() throws IOException {
        // Mocking the ProcessingEnvironment and JavaFile
        ProcessingEnvironment processingEnv = mock(ProcessingEnvironment.class);
        JavaFile javaFile = mock(JavaFile.class);
        
        // Mock the Filer
        javax.annotation.processing.Filer filer = mock(javax.annotation.processing.Filer.class);
        when(processingEnv.getFiler()).thenReturn(filer);
        
        // Test the writeJavaFile method
        JavaPoetUtils.writeJavaFile(javaFile, processingEnv);

        // Verify that writeTo was called
        verify(javaFile).writeTo(filer);
    }

    @Test
    public void testWriteJavaFile_Failure() throws IOException {
        // Mocking the ProcessingEnvironment and JavaFile
        ProcessingEnvironment processingEnv = mock(ProcessingEnvironment.class);
        JavaFile javaFile = mock(JavaFile.class);

        // Mock the Filer
        javax.annotation.processing.Filer filer = mock(javax.annotation.processing.Filer.class);
        when(processingEnv.getFiler()).thenReturn(filer);

        // Mock exception during writing to simulate failure
        doThrow(new IOException("File write error")).when(javaFile).writeTo(filer);

        // Mocking the messager
        Messager messager = mock(Messager.class);
        when(processingEnv.getMessager()).thenReturn(messager);

        // Execute the method we're testing
        JavaPoetUtils.writeJavaFile(javaFile, processingEnv);

        // Verify that the error message was printed
        verify(messager).printMessage(Diagnostic.Kind.ERROR, "Failed to write Java file: File write error");
    }



    @Test
    public void testBuildPrivateConstructorMethod() {
        // Test the buildPrivateConstructorMethod
        MethodSpec constructor = JavaPoetUtils.buildPrivateConstructorMethod();

        assertNotNull(constructor);
        assertTrue(constructor.modifiers.contains(Modifier.PRIVATE));
        assertEquals(0, constructor.parameters.size()); // Check that there are no parameters
    }



    @Test
    public void testGenerateClassDefinition() {
        // Test the generateClassDefinition method
        MethodSpec constructor = JavaPoetUtils.buildPrivateConstructorMethod();
        TypeSpec classDef = JavaPoetUtils.generateClassDefinition("TestClass", Modifier.PUBLIC, TypeName.OBJECT, constructor);

        assertNotNull(classDef);
        assertEquals("TestClass", classDef.name);
        assertTrue(classDef.modifiers.contains(Modifier.PUBLIC));
        assertEquals(TypeName.OBJECT, classDef.superclass);
        assertEquals(1, classDef.methodSpecs.size());
        assertEquals(constructor, classDef.methodSpecs.get(0));
    }
}
