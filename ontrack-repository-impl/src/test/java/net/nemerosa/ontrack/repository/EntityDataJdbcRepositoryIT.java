package net.nemerosa.ontrack.repository;

import net.nemerosa.ontrack.model.structure.Project;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@Transactional
public class EntityDataJdbcRepositoryIT extends AbstractRepositoryTestSupport {

    @Autowired
    private EntityDataRepository repository;

    private Project project;

    @Before
    public void create_project() {
        project = do_create_project();
    }

    @Test
    public void save_retrieve_delete_data() {
        String key = "Test 1";
        assertFalse(repository.retrieve(project, key).isPresent());

        repository.store(project, key, "Value 1");
        assertEquals(repository.retrieve(project, key).get(), "Value 1");

        repository.delete(project, key);
        assertFalse(repository.retrieve(project, key).isPresent());
    }

    @Test
    public void save_update_data() {
        String key = "Test 2";
        assertFalse(repository.retrieve(project, key).isPresent());

        repository.store(project, key, "Value 1");
        assertEquals(repository.retrieve(project, key).get(), "Value 1");

        repository.store(project, key, "Value 2");
        assertEquals(repository.retrieve(project, key).get(), "Value 2");
    }

}
