package com.sahan.t_mobile;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.sahan.t_mobile");

        noClasses()
            .that()
                .resideInAnyPackage("com.sahan.t_mobile.service..")
            .or()
                .resideInAnyPackage("com.sahan.t_mobile.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.sahan.t_mobile.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
