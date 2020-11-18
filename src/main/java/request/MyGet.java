package request;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

import java.net.URI;

public class MyGet extends HttpEntityEnclosingRequestBase {
    public final static String METHOD_NAME = "get";

    public MyGet() {
        super();
    }

    public MyGet(final URI uri) {
        super();
        setURI(uri);
    }


    public MyGet(final String uri) {
        super();
        setURI(URI.create(uri));
    }

    @Override
    public String getMethod() {
        return METHOD_NAME;
    }
}
