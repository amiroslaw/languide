package xyz.miroslaw.languide.repository;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ArticleRepositoryTest.class,
        NotebookRepositoryTest.class,
        TranslationRepositoryTest.class,
        UserRepositoryTest.class
})

public class RepositoryTestSuite {
}
