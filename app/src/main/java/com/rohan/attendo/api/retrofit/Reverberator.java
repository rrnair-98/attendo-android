package com.rohan.attendo.api.retrofit;

/**
 * Callback triggered from the RetrofitApiClient to deliver responses.
 */
public interface Reverberator {
    /**
     * To be implemented by receivers if response is to be processed.
     * @param data The response body, must be cast to the appropriate type
     * @param httpResponseCode The response code of the http request
     */
    public void reverb(Object data, int httpResponseCode);
}
