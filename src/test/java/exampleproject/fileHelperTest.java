package exampleproject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Blackjack.fileHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class fileHelperTest {

    private fileHelper helper;
    private ArrayList<List<String>> testUsers;

    @BeforeEach
    void setUp() {
        helper = new fileHelper();
        testUsers = new ArrayList<>();
        testUsers.add(Arrays.asList("user1", "password1", "100"));
        testUsers.add(Arrays.asList("user2", "password2", "200"));
        testUsers.add(Arrays.asList("user3", "password3", "300"));
    }

    @Test
    void testWriteStateToFile() {
        helper.writeStateToFile(testUsers, "test.txt");
        String lastLine = helper.readLastLine("test.txt");
        Assertions.assertEquals("user3,password3,300", lastLine);
    }
}