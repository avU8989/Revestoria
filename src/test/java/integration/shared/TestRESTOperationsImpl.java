package integration.shared;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestRESTOperationsImpl implements TestRESTOperations {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    public TestRESTOperationsImpl(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Override
    public ResultActions postActions(String path, String content) {
        try {
            MockHttpServletRequestBuilder requestBuilder = post(path)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(content);

            return mockMvc.perform(requestBuilder)
                    .andExpect(status().isCreated())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        } catch (Exception exception) {
            throw new AssertionError("POST request failed: " + path, exception);
        }
    }

    @Override
    public <T> T doGet(String path, Class<T> responseType) {
        return doGet(HttpStatus.OK, path, responseType);
    }

    @Override
    public <T> T doGet(HttpStatus expectedStatus, String path, Class<T> responseType) {
        try {
            String response = mockMvc.perform(get(path)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(expectedStatus.value()))
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            return readResponse(response, responseType);
        } catch (Exception exception) {
            throw new AssertionError("GET request failed: " + path, exception);
        }
    }

    @Override
    public <T> T doPost(String path, Object request, Class<T> responseType) {
        return doPostWithStatus(HttpStatus.CREATED, path, request, responseType);
    }

    @Override
    public <T> T doPostWithStatus(
            HttpStatus expectedStatus,
            String path,
            Object request,
            Class<T> responseType
    ) {
        try {
            String response = mockMvc.perform(post(path)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().is(expectedStatus.value()))
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            return readResponse(response, responseType);
        } catch (Exception exception) {
            throw new AssertionError("POST request failed: " + path, exception);
        }
    }

    @Override
    public <T> T doPut(String path, Object request, Class<T> responseType) {
        try {
            String response = mockMvc.perform(put(path)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            return readResponse(response, responseType);
        } catch (Exception exception) {
            throw new AssertionError("PUT request failed: " + path, exception);
        }
    }

    @Override
    public ResultActions putActions(String path, String content) {
        try {
            MockHttpServletRequestBuilder requestBuilder = put(path)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(content);

            return mockMvc.perform(requestBuilder)
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        } catch (Exception exception) {
            throw new AssertionError("PUT request failed: " + path, exception);
        }
    }

    @Override
    public void doDelete(String path) {
        try {
            mockMvc.perform(delete(path))
                    .andExpect(status().isNoContent());
        } catch (Exception exception) {
            throw new AssertionError("DELETE request failed: " + path, exception);
        }
    }

    private <T> T readResponse(String response, Class<T> responseType) {
        try {
            return objectMapper.readValue(response, responseType);
        } catch (Exception exception) {
            throw new AssertionError("Could not deserialize response into " + responseType.getSimpleName(), exception);
        }
    }
}