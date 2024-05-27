package server;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class Certificate {
    private static final TrustManager[] trustAllCerts = new TrustManager[] {
        new X509TrustManager() {
          public java.security.cert.X509Certificate[] getAcceptedIssuers() {
           return null;
          }
          @Override
          public void checkClientTrusted(X509Certificate[] arg0, String arg1)
           throws CertificateException {}

          @Override
          public void checkServerTrusted(X509Certificate[] arg0, String arg1)
            throws CertificateException {}
          }
     };
    private static SSLContext sc = null;
    static {
        try {
            sc = SSLContext.getInstance("SSL");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }
    private static HostnameVerifier validHost = new HostnameVerifier() {
        @Override
        public boolean verify(String s, SSLSession sslSession) {
            return true;
        }
    };

    public static SSLContext getSc() {
        return sc;
    }

    public static HostnameVerifier getValidHost() {
        return validHost;
    }
}
