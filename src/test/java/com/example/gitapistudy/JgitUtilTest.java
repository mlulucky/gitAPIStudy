package com.example.gitapistudy;

import org.eclipse.jgit.api.Git;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JgitUtilTest {
    @Test
    void jgitTest() throws Exception {
        File dir = new File("C:\\jgit_test\\gitTest");
//        String dirPath = "C:\\jgit_test\\git_hub_clone_test";
        JgitUtil.checkOut(dir);
        Git git = JgitUtil.open(dir);
        JgitUtil.remoteAdd(git);
        JgitUtil.pull(git);
        JgitUtil.add(git, "404.md");
        JgitUtil.rm(git, "ReadMe.md");
        JgitUtil.commit(git, "8888");
        JgitUtil.push(git);
    }
}