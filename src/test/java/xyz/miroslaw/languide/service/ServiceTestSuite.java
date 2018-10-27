package xyz.miroslaw.languide.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ArticleServiceImpTest.class,
        DictionaryServiceImpTest.class,
        NotebookServiceImpTest.class,
        TranslationServiceImpTest.class
})
public class ServiceTestSuite {
}
