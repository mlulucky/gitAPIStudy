package com.example.gitapistudy;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.assertj.core.api.Assertions;
import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.eclipse.jgit.api.Git;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import static org.apache.tomcat.util.http.fileupload.FileUtils.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class githubApiStudyTest {
    @Test
    @DisplayName("git init test")
    @Order(1)
    void gitInitTest() throws IOException, GitAPIException {
    File gitDir = new File("C:\\jgit_test\\git_init_test");
        if(gitDir.exists()){
            FileUtils.deleteDirectory(gitDir);
        }

        if(gitDir.mkdirs()){
            System.out.println("dir create success");
        }
        //init
        Git git = Git.init().setDirectory(gitDir).call();
        Assertions.assertThat(git).isNotNull();
        git.close();

}

    @Test
    @Order(2)
    void gitHubCloneTest() throws GitAPIException, IOException {
        //create git folder
        File gitDir = new File("C:\\jgit_test\\git_hub_clone_test");
        if(gitDir.exists()){
            FileUtils.deleteDirectory(gitDir);
        }

        if(gitDir.mkdirs()){
            // log.info("dir create success");
        }

        //set username, access token
        CredentialsProvider credentialsProvider
                = new UsernamePasswordCredentialsProvider(
                "mlulucky"
                , "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"); //access token

        //clone
        Git git = Git.cloneRepository()
                .setURI("https://github.com/mlulucky/gitAPIStudy.git")
                .setCredentialsProvider(credentialsProvider)
                .setDirectory(gitDir)
                .call();
        Assertions.assertThat(git).isNotNull();
        git.close();
    }

    @Test
    @Order(4)
    void gitAddTest() throws GitAPIException, IOException {
        //git repo path
        String dirPath = "C:\\jgit_test\\git_hub_clone_test";
        System.out.println("dirPath = " + dirPath);
        File gitDir = new File(dirPath);
        
        //create temp file
        String fileName = UUID.randomUUID().toString();

        String contentToWrite = "Hello File!";
        String path = dirPath+"\\"+fileName+".txt";
        Files.write(Paths.get(path), contentToWrite.getBytes());


//         File file = new File(dirPath+"\\"+fileName+".txt");
//         FileUtils.writeStringToFile(file, "testing it...", StandardCharsets.UTF_8);

        //add
        Git git = Git.open(gitDir);
        Assertions.assertThat(git).isNotNull();

        AddCommand add = git.add();
        add.addFilepattern(fileName+".txt").call();

        git.close();
    }

    @Test
    @Order(5)
    void gitCommitTest() throws IOException, GitAPIException {
        //git repo path
        String dirPath = "C:\\jgit_test\\git_hub_clone_test";
        File gitDir = new File(dirPath);

        //commit
        Git git = Git.open(gitDir);
        git.commit().setMessage("JGIT commit test").call();
        Assertions.assertThat(git).isNotNull();

        git.close();
    }

    @Test
    @Order(6)
    void gitPushTest() throws IOException, GitAPIException {
        //git repo path
        String dirPath = "C:\\jgit_test\\git_hub_clone_test";
        File gitDir = new File(dirPath);

        //set username, access token
        CredentialsProvider credentialsProvider
                = new UsernamePasswordCredentialsProvider(
                "mlulucky"
                , "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"); //access token

        //push
        Git git = Git.open(gitDir);
        git.push()
                .setCredentialsProvider(credentialsProvider)
                .setRemote("https://github.com/mlulucky/gitAPIStudy.git")
                .setRefSpecs(new RefSpec("origin/main"))
                .call();
        Assertions.assertThat(git).isNotNull();

        git.close();
    }



}