package xyz.miroslaw.languide.controller;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ArticleControllerTest.class,
        NotebookControllerTest.class,
        TranslationControllerTest.class,
        DictionaryControllerTest.class,
        HomeControllerTest.class,
        RegistrationControllerTest.class
})

public class ControllerTestSuite {

}