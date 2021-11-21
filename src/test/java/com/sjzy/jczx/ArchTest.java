package com.sjzy.jczx;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.sjzy.jczx");

        noClasses()
            .that()
            .resideInAnyPackage("com.sjzy.jczx.service..")
            .or()
            .resideInAnyPackage("com.sjzy.jczx.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.sjzy.jczx.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
