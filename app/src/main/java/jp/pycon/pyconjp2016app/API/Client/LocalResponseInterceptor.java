package jp.pycon.pyconjp2016app.API.Client;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * Created by rhoboro on 7/16/16.
 */
public class LocalResponseInterceptor implements Interceptor {

    private Context context;
    private static final String PREFIX_PRESENTATION = "_2017_ja_api_presentation";

    public LocalResponseInterceptor(Context ctx) {
        this.context = ctx;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl requestedUrl = request.url();

        String fileName = (requestedUrl.url().getPath()+"sample").replace("/", "_");
        if (fileName.startsWith(PREFIX_PRESENTATION)) {
            fileName = PREFIX_PRESENTATION + "_sample";
        }
        fileName = fileName.toLowerCase();

        int resourceId = context.getResources().getIdentifier(fileName, "raw",
                context.getPackageName());

        if (resourceId == 0) {
            Log.wtf("LocalResponseInterceptor", "Could not find res/raw/" + fileName + ".json");
            throw new IOException("Could not find res/raw/" + fileName + ".json");
        }

        InputStream inputStream = context.getResources().openRawResource(resourceId);

        String mimeType = URLConnection.guessContentTypeFromStream(inputStream);
        if (mimeType == null) {
            mimeType = "application/json";
        }

        Buffer input = new Buffer().readFrom(inputStream);

        return new Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .body(ResponseBody.create(MediaType.parse(mimeType), input.size(), input))
                .build();
    }
}
