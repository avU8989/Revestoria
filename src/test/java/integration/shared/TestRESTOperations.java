package integration.shared;

import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.ResultActions;

public interface TestRESTOperations {

    ResultActions postActions(String path, String content);

    <T> T doGet(String path, Class<T> responseType);

    <T> T doGet(HttpStatus expectedStatus, String path, Class<T> responseType);

    <T> T doPost(String path, Object request, Class<T> responseType);

    <T> T doPostWithStatus(HttpStatus expectedStatus, String path, Object request, Class<T> responseType);

    <T> T doPut(String path, Object request, Class<T> responseType);

    ResultActions putActions(String path, String content);

    void doDelete(String path);
}