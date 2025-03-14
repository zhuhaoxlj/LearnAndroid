package com.example.learnandroid.problem

/**
 * @author zhuhao
 * @date  13:45
 **/
//@GlideModule
//class UnsafeOkHttpGlideModule : AppGlideModule() {
//    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
//        // Create a trust manager that does not validate certificate chains
//        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
//            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
//            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
//            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
//        })
//
//        // Install the all-trusting trust manager
//        val sslContext = SSLContext.getInstance("SSL")
//        sslContext.init(null, trustAllCerts, SecureRandom())
//
//        // Create an OkHttpClient that trusts all certificates
//        val okHttpClient = OkHttpClient.Builder()
//            .sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
//            .hostnameVerifier { _, _ -> true }
//            .build()
//
//        // Register the OkHttp client for Glide
//        registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(okHttpClient))
//    }
//}