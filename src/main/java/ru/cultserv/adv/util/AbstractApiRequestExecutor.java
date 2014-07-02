package ru.cultserv.adv.util;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.util.concurrent.Futures;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Request;
import com.ning.http.client.RequestBuilder;
import com.ning.http.client.Response;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public abstract class AbstractApiRequestExecutor implements ApiRequestExecutor {
	
	private static final AsyncHttpClient CLIENT = createHttpClient();
	
	private static AsyncHttpClient createHttpClient() {
		return new AsyncHttpClient();
	}

	@Override
	public Optional<ApiResponse> execute(ApiRequest request) {
		Future<ApiResponse> future = asFuture(request);
		try {
			return Optional.of(future.get());
		} catch (ExecutionException | InterruptedException e) {
			return Optional.absent();
		}
	}

	@Override
	public Future<ApiResponse> asFuture(ApiRequest request) {
		Request http_request = convertToHttpRequest(request);
		try {
			com.ning.http.client.ListenableFuture<Response> future = CLIENT.executeRequest(http_request);
			return Futures.lazyTransform(future, new Function<Response, ApiResponse>() {
				@Override
				public ApiResponse apply(Response response) {
					return process(response);
				}
			});
		} catch (IOException e) {
			return Futures.immediateFailedFuture(new IllegalStateException("illegal request"));
		}
	}
	
	private Request convertToHttpRequest(ApiRequest request) {
		RequestBuilder builder = new RequestBuilder()
			.setMethod(request.httpMethod())
			.setUrl(request.url());
		
		withParams(request.params(), builder);
		
		return builder.build();
	}
	
	protected void withParams(final ApiRequestParams params, final RequestBuilder builder) {
		builder.setBody(Json.toJson(params).toString());
	}
	
	protected abstract ApiResponse process(Response response);

}
